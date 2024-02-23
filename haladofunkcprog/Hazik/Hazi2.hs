{-# LANGUAGE InstanceSigs, DeriveFunctor, LambdaCase #-}
{-# OPTIONS_GHC -Wincomplete-patterns #-}

module Hazi2 where

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
char c = void (satisfy (== c))

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
many_ p = void (many p)

some_ :: Parser a -> Parser ()
some_ p = void (some p)

optional_ :: Parser a -> Parser ()
optional_ p = void (optional p)

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

--Használjuk az alábbi adattípust reguláris kifejezések absztrakt szintaxisának reprezentálására. 
--Alul a szintaxis-beli kötési erősség csökkenő sorrendjében listázzuk a konstruktorokat.

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

--1. Feladat: írj egy parser-t, ami olvas egy RegEx-et egy String-ből.

--Parser segédfüggvények

nothing :: Parser ()
nothing = return ()

nTimes_ :: Int -> Parser a -> Parser ()
nTimes_ n p = if n == 0 then return ()
              else p >> nTimes_ (n-1) p

decimalNumber :: Parser Int
decimalNumber = foldl (\a c -> a * 10 + c) 0 <$> some digit

--Általános segédfüggvények

literals :: String
literals = ['a'..'z']++['A'..'Z']++['0'..'9']++[' ']

isAlphaNumOrSpace :: Char -> Bool
isAlphaNumOrSpace c1 = isAlphaNum c1 || isSpace c1

--RegEx parserek

pParens :: Parser RegEx
pParens = do
  char '('
  e1 <- pChoice
  char ')'
  return e1

pRange :: Parser RegEx
pRange = do
  char '['
  lit1 <- satisfy isAlphaNumOrSpace
  char '-'
  lit2 <- satisfy isAlphaNumOrSpace
  char ']'
  return (RERange lit1 lit2)

pAny :: Parser RegEx
pAny = do
  p <- char '.'
  return REAny

pEof :: Parser RegEx
pEof = do
  p <- char '$'
  return REEof

pAtom :: Parser RegEx
pAtom = pParens
    <|> REChar <$> satisfy isAlphaNumOrSpace
    <|> pRange
    <|> pAny
    <|> pEof

pPostfixOperators :: Parser RegEx
pPostfixOperators = pMany 
                <|> pSome
                <|> pOptional
                <|> pRepeat
                <|> pAtom

pMany :: Parser RegEx
pMany = do
  p <- pAtom
  char '*'
  return (REMany p)

pSome :: Parser RegEx
pSome = do
  p <- pAtom
  char '+'
  return (RESome p)

pOptional :: Parser RegEx
pOptional = do
  p <- pAtom
  char '?'
  return (REOptional p)

pRepeat :: Parser RegEx
pRepeat = do
  p <- pAtom
  char '{'
  i <- decimalNumber
  char '}'
  return (RERepeat p i)

pSequence :: Parser RegEx
pSequence = infixRight pPostfixOperators nothing RESequence

pChoice :: Parser RegEx
pChoice = infixRight pSequence (char '|') REChoice

pRegEx :: Parser RegEx
pRegEx = pChoice

--Példák
--runParser pRegEx "abc" == Just (RESequence (REChar 'a') (RESequence (REChar 'b') (REChar 'c')),"")
--runParser pRegEx "(x|y)*" == Just (REMany (REChoice (REChar 'x') (REChar 'y')),"")
--runParser pRegEx "[a-z]{4}$" == Just (RESequence (RERepeat (RERange 'a' 'z') 4) REEof,"")

--2. Feladat: készíts parser-t egy RegEx-ből, ami leellenőrzi adott bemenetről, hogy illeszkedik-e a RegEx által specifikált mintára.
makeParser :: RegEx -> Parser ()
makeParser r = case r of
    REChar c            -> char c
    RERange cr1 cr2     -> void (satisfy (`elem` [cr1..cr2]))
    REAny               -> void anyChar
    REEof               -> eof
    REMany rm           -> many_ (makeParser rm)
    RESome rs           -> some_ (makeParser rs)
    REOptional ro       -> optional_ (makeParser ro)
    RERepeat re i       -> nTimes_ i (makeParser re)
    RESequence rs1 rs2  -> makeParser rs1 >> makeParser rs2
    REChoice rc1 rc2    -> makeParser rc1 <|> makeParser rc2

--Példák
--runParser (makeParser (RESome (RERange 'a' 'f'))) "adsf" == Just ((), "sf")
--runParser (makeParser (RESome (RERange 'a' 'f'))) "sfad" == Nothing

--Tesztelő függvény:
--Lehetséges eredmények:
--  Nothing - Helytelen minta
--  Just False - A minta helyes, azonban a bemenet nem illeszkedik
--  Just True - A minta is helyes és a bemenet is illeszkedik

test :: String -> String -> Maybe Bool
test pattern input = do
  regEx <- evalParser pRegEx pattern
  return (isJust (evalParser (makeParser regEx) input))

--Példák a működésre

test' :: String -> String -> Bool
test' regex str = fromJust $ test regex str

licensePlate = "[A-Z]{3}[0-9]{3}$"
hexColor = "0x([0-9]|[A-F]){6}$"

-- regex101.com/r/rkScYV
-- regexr.com/5rrhl
streetName = "([A-Z][a-z]* )+(utca|út) [0-9]+([A-Z])?"

tests :: [Bool]
tests =
  [       test' licensePlate "ABC123"
  ,       test' licensePlate "IRF764"
  ,       test' licensePlate "LGM859"
  ,       test' licensePlate "ASD789"
  , not $ test' licensePlate "ABCD1234"
  , not $ test' licensePlate "ABC123asdf"
  , not $ test' licensePlate "123ABC"
  , not $ test' licensePlate "asdf"

  --

  ,       test' hexColor "0x000000"
  ,       test' hexColor "0x33FE67"
  ,       test' hexColor "0xFA55B8"
  , not $ test' hexColor "1337AB"
  , not $ test' hexColor "0x1234567"
  , not $ test' hexColor "0xAA1Q34"

  --

  ,       test' streetName "Ady Endre út 47C"
  ,       test' streetName "Karinthy Frigyes út 8"
  ,       test' streetName "Budafoki út 3"
  ,       test' streetName "Szilva utca 21A"
  ,       test' streetName "Nagy Lantos Andor utca 9"
  ,       test' streetName "T utca 1"
  , not $ test' streetName "ady Endre út 47C"
  , not $ test' streetName "KarinthyFrigyes út 8"
  , not $ test' streetName "út 3"
  , not $ test' streetName "Liget köz 21A"
  , not $ test' streetName "Nagy  Lantos  Andor utca 9"
  , not $ test' streetName "T utca"
  ]