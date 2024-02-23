{-# LANGUAGE InstanceSigs, DeriveFunctor #-}
{-# OPTIONS_GHC -Wincomplete-patterns #-}

import Data.Char
import Data.Maybe
import Control.Applicative
import Control.Monad
import Debug.Trace

newtype Parser a = Parser {runParser :: String -> Maybe (a, String)}
  deriving Functor

evalParser :: Parser a -> String -> Maybe a
evalParser pa = (fst <$>) . runParser pa

execParser :: Parser a -> String -> Maybe String
execParser pa = (snd <$>) . runParser pa

instance Applicative Parser where
  pure  = return
  (<*>) = ap

instance Monad Parser where

  return :: a -> Parser a
  return a = Parser $ \s -> Just (a, s)

  (>>=) :: Parser a -> (a -> Parser b) -> Parser b
  Parser f >>= g = Parser $ \s ->
    case f s of
      Nothing      -> Nothing
      Just (a, s') -> runParser (g a) s'

instance Alternative Parser where
  empty :: Parser a
  empty = Parser $ const Nothing

  (<|>) :: Parser a -> Parser a -> Parser a
  (<|>) (Parser f) (Parser g) =
    Parser $ \s -> case f s of
      Nothing      -> g s
      Just (a, s') -> Just (a, s')

debug :: String -> Parser a -> Parser a
debug msg pa = Parser $ \s -> trace (msg ++ " : " ++ s) (runParser pa s)

-- üres input
eof :: Parser ()
eof = Parser $ \s -> case s of
  [] -> Just ((), [])
  _  -> Nothing

-- feltételnek megfelelő karakter
satisfy :: (Char -> Bool) -> Parser Char
satisfy f = Parser $ \s -> case s of
  c:cs | f c -> Just (c, cs)
  _          -> Nothing

-- konkrét karakter
char :: Char -> Parser ()
char c = () <$ satisfy (== c)

-- bármilyen karakter
anyChar :: Parser Char
anyChar = satisfy (const True)

-- konkrét string
string :: String -> Parser ()
string = mapM_ char

-- + Control.Applicative-ból importálva:
--  - many     : nulla vagy több érték olvasása
--  - some     : egy vagy több érték olvasása
--  - optional : nulla vagy egy érték olvasása, Parser a -> Parser (Maybe a)

many_ :: Parser a -> Parser ()
many_ p = () <$ many p

some_ :: Parser a -> Parser ()
some_ p = () <$ some p

optional_ :: Parser a -> Parser ()
optional_ p = () <$ optional p

sepBy1 :: Parser a -> Parser sep -> Parser [a]
sepBy1 pa psep = (:) <$> pa <*> many (psep >> pa)

sepBy :: Parser a -> Parser sep -> Parser [a]
sepBy pa psep = sepBy1 pa psep <|> pure []

digit :: Parser Int
digit = digitToInt <$> satisfy isDigit

posInt :: Parser Int
posInt = foldl (\acc curr -> acc * 10 + curr) 0 <$> some digit

infixLeft :: Parser a -> Parser sep -> (a -> a -> a) -> Parser a
infixLeft pa psep combine = foldl1 combine <$> sepBy1 pa psep

infixRight :: Parser a -> Parser sep -> (a -> a -> a) -> Parser a
infixRight pa psep combine = foldr1 combine <$> sepBy1 pa psep

infixNonAssoc :: Parser a -> Parser sep -> (a -> a -> a) -> Parser a
infixNonAssoc pa psep combine = do
  exps <- sepBy1 pa psep
  case exps of
    [exp]        -> pure exp
    [exp1, exp2] -> pure $ combine exp1 exp2
    _            -> empty

--------------------

    -- A következő regexek támogatottak:
data RegEx
  -- Atomok:
  -- - (p) : (nincs külön konstruktora,
  --         hiszen a zárójelek nem jelennek meg az absztrakt szintaxisfában)
  -- - a : Karakter literál, amely betű, szóköz vagy szám lehet
  = REChar Char

  -- - [c1-c2] : Két karakter által meghatározott (mindkét oldalról zárt) intervallum
  --             Példák: [a-z], [0-9], ...
  | RERange Char Char

  -- - . : Tetszőleges karakter
  | REAny

  -- - $ : Üres bemenet ("End of file")
  | REEof

  -- Posztfix operátorok:
  -- - p* : Nulla vagy több ismétlés
  | REMany RegEx

  -- - p+ : Egy vagy több ismétlés
  | RESome RegEx

  -- - p? : Nulla vagy egy előfordulás
  | REOptional RegEx

  -- - p{n} : N-szeres ismétlés
  | RERepeat RegEx Int

  -- Infix operátorok:
  -- - Regex-ek egymás után futtatása.
  --   Jobbra asszociáló infix művelet, a szintaxisban "láthatatlan", egyszerűen
  --   egymás után írunk több regexet.
  | RESequence RegEx RegEx

  -- - p1|p2 : Először p1 futtatása, ha az nem illeszkedik, akkor p2.
  -- - Jobbra asszociál.
  | REChoice RegEx RegEx
  deriving (Eq, Show)


---------------------------------
---- MEGOLDÁS ----

-- prefix :: (a -> a) -> Parser a -> Parser op -> Parser a
-- prefix f pa pop = f <$> (pop *> prefix f pa pop) <|> pa

postfix :: (a -> a) -> Parser a -> Parser op -> Parser a
postfix f pa pop = f <$> (postfix f pa pop <* pop) <|> pa

pLit :: Parser RegEx
-- pLit = REChar <$> anyChar -- talán <* ???
pLit = debug "5" (do
  c <- anyChar
  return (REChar c))

pPar :: Parser RegEx
pPar = (char '(') *> pRegEx <* (char ')')

pAtom :: Parser RegEx
pAtom = pLit <|> pPar


pRange :: Parser RegEx
pRange = do
  char '['
  fromChar <- anyChar
  char '-'
  toChar <- anyChar
  char ']'
  return (RERange fromChar toChar)

pAny :: Parser RegEx
pAny = do
  char '.'
  return REAny

pEof :: Parser RegEx
pEof = do
  char '$'
  return REEof


pMol :: Parser RegEx
pMol = pAtom <|> pRange <|> pAny <|> pEof


pMany :: Parser RegEx
-- pMany = postfix REMany pMol (char '*')
pMany = do
  rex <- pMol
  char '*'
  return (REMany rex)

pSome :: Parser RegEx
-- pSome = postfix RESome pMany (char '+')
pSome = do
  rex <- pMany
  char '+'
  return (RESome rex)

pOptional :: Parser RegEx
-- pOptional = postfix REOptional pSome (char '?')
pOptional = do
  rex <- pSome
  char '?'
  return (REOptional rex)

pRepeat :: Parser RegEx
pRepeat = debug "4" (do
  rex <- pOptional
  char '{'
  num <- digit
  char '}'
  return (RERepeat rex num))


pSequence :: Parser RegEx
-- pSequence = debug "3" (infixRight pRepeat (char ' ') RESequence)
pSequence = do
  rex1 <- pRepeat
  rex2 <- pRepeat
  return (RESequence rex1 rex2)

pChoice :: Parser RegEx
-- pChoice = debug "2" (infixRight pSequence (char '|') REChoice)
pChoice = do
  rex1 <- pSequence
  char '|'
  rex2 <- pSequence
  return (REChoice rex1 rex2)


pRegEx :: Parser RegEx
pRegEx = debug "1" pChoice
