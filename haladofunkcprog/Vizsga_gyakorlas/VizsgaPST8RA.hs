{-# language DeriveFunctor, InstanceSigs #-}
{-# options_ghc -Wincomplete-patterns #-}

module VizsgaPST8RA where

import Control.Applicative
import Control.Monad
import Debug.Trace
import Data.Char
import Data.Ord
import Data.List

-- State
--------------------------------------------------------------------------------

newtype State s a = State {runState :: s -> (a, s)}
  deriving Functor

instance Applicative (State s) where
  pure  = return
  (<*>) = ap

instance Monad (State s) where
  return a = State (\s -> (a, s))
  (>>=) (State f) g = State $ \s -> case f s of
    (a, s) -> runState (g a) s

put :: s -> State s ()
put s = State $ const ((), s)

get :: State s s
get = State $ \s -> (s, s)

modify :: (s -> s) -> State s ()
modify f = do {s <- get; put (f s)}

evalState :: State s a -> s -> a
evalState sta s = fst (runState sta s)

execState :: State s a -> s -> s
execState sta s = snd (runState sta s)


-- FELADATOK
--------------------------------------------------------------------------------

data EitherList a b = Nil | ConsA a (EitherList a b) | ConsB b (EitherList a b)

infixr 5 `ConsA`
infixr 5 `ConsB`

instance (Show a, Show b) => Show (EitherList a b) where
    show xs = let
        showRest Nil = "]"
        showRest (ConsA x xs) = ',' : show x ++ showRest xs
        showRest (ConsB x xs) = ',' : show x ++ showRest xs in
            case xs of
                Nil -> "[]"
                ConsA a as -> '[' : show a ++ showRest as
                ConsB b bs -> '[' : show b ++ showRest bs

l1 :: EitherList Int Bool
l1 = ConsA 4 $ ConsB True $ ConsA 5 $ ConsA 8 $ ConsA 10 Nil

l2 :: EitherList Char Integer
l2 = ConsB (-2) $ ConsB 0 $ ConsB 10 $ ConsB 5 $ ConsA 'a' $ ConsA 't' $ ConsB 4 Nil

l3 :: EitherList String Bool
l3 = ConsB False $ ConsA "" $ ConsA "alma" $ ConsA "szilva" $ ConsB True Nil

l4 :: EitherList Bool Int
l4 = ConsA False $ ConsB 9 $ ConsA True $ ConsB 8 $ ConsB 10 Nil


infiniteList :: EitherList Integer Double
infiniteList = ConsA 5 $ ConsB 5.5 $ ConsA 0 $ ConsA 25 $ ConsB 9.75 infiniteList

instance (Eq a, Eq b) => Eq (EitherList a b) where
     (==) Nil Nil = True
     (==) (ConsA a l) (ConsA a' l') = (==) a a' && (==) l l'
     (==) (ConsB b l) (ConsB b' l') = (==) b b' && (==) l l'
     (==) _           _             = False


instance Functor (EitherList a) where
     fmap f Nil = Nil
     fmap f (ConsA a l) = ConsA a (fmap f l)
     fmap f (ConsB b l) = ConsB (f b) (fmap f l)


instance Foldable (EitherList a) where
--     -- A kettő közül az egyiket definiáld.
     foldr f start Nil = start
     foldr f start (ConsA a l) = foldr f start l
     foldr f start (ConsB b l) = f b (foldr f start l)
--     foldMap = undefined

instance Traversable (EitherList a) where
--     -- A kettő közül az egyiket definiáld.
     traverse f Nil = pure Nil
     traverse f (ConsA a l) = ConsA a <$> traverse f l
     traverse f (ConsB b l) = ConsB <$> f b <*> traverse f l
--     sequenceA = undefined

unpack :: EitherList a b -> ([a],[b])
unpack l = unpackAux l 1 ([],[])

unpackAux :: EitherList a b -> Int -> ([a],[b]) -> ([a],[b])
unpackAux l n (as, bs) = if n > 100000 then (as, bs) else
    case l of
    Nil -> (as, bs)
    ConsA a l'  -> unpackAux l' (n+1) (as++[a], bs)
    ConsB b l'' -> unpackAux l'' (n+1) (as, bs++[b])

pack :: Ord c => ([(a,c)],[(b,c)]) -> EitherList a b
pack p = packAux2 Nil (packAux p)

packAux :: Ord c => ([(a,c)],[(b,c)]) -> ([(a,c)],[(b,c)])
packAux (as, bs) = (sortBy (comparing snd) as, sortBy (comparing snd) bs)

packAux2 :: Ord c => EitherList a b ->  ([(a,c)],[(b,c)])  -> EitherList a b
packAux2 l ([],[]) = l
packAux2 l (a:as, b:bs)
    | snd a >= snd b = packAux2 (ConsA (fst a) $ ConsB (fst b) l) (as, bs)
    | otherwise      = packAux2 (ConsB (fst b) $ ConsA (fst a) l) (as, bs)

packAux2 l (a:as, []) = packAux2 (ConsA (fst a) l) (as, [])
packAux2 l ([], b:bs) = packAux2 (ConsB (fst b) l) ([], bs)

listNumbering :: Integer -> EitherList a b -> EitherList (a,Integer) (b,Integer)
listNumbering n Nil = Nil
listNumbering n (ConsA a l) = ConsA (a, n) (listNumbering (n+1) l)
listNumbering n (ConsB b l) = ConsB (b, n) (listNumbering (n+1) l) 

reverseAs :: EitherList a b -> EitherList a b
reverseAs = undefined

-- Parser lib
--------------------------------------------------------------------------------

newtype Parser a = Parser {runParser :: String -> Maybe (a, String)}
  deriving (Functor)

instance Applicative Parser where
  pure  = return
  (<*>) = ap

instance Monad Parser where
  return :: a -> Parser a
  return a = Parser $ \s -> Just (a, s)   -- nincs hatás

  (>>=) :: Parser a -> (a -> Parser b) -> Parser b
  Parser f >>= g = Parser $ \s -> do {(a, s) <- f s; runParser (g a) s}

-- pontosan az üres inputot olvassuk
eof :: Parser ()
eof = Parser $ \s -> case s of
  [] -> Just ((), [])
  _  -> Nothing

-- olvassunk egy karaktert az input elejéről, amire igaz egy feltétel
satisfy :: (Char -> Bool) -> Parser Char
satisfy f = Parser $ \s -> case s of
  c:s | f c -> Just (c, s)
  _         -> Nothing

-- olvassunk egy konkrét karaktert
char :: Char -> Parser ()
char c = void (satisfy (==c))

-- olvassunk egy konkrét String-et
string :: String -> Parser ()   -- String ~ [Char]
string = mapM_ char         -- egymás után olvasom az összes Char-t a String-ben


instance Alternative Parser where
  -- mindig hibázó parser
  empty :: Parser a
  empty = Parser $ const Nothing

  -- választás két parser között
  Parser f <|> Parser g = Parser $ \s -> case f s of
    Nothing -> g s
    res     -> res

-- Control.Applicative-ból:
--    many  :: Parser a -> Parser [a]       -- 0-szor vagy többször futtatja
--    some  :: Parser a -> Parser [a]       -- 1-szer vagy többször futtatja

many_ :: Parser a -> Parser ()
many_ pa = void (many pa)

some_ :: Parser a -> Parser ()
some_ pa = void (some pa)

-- Control.Applicative-ból:
-- optional :: Parser a -> Parser (Maybe a)   -- hibát értékként visszaadja (soha nem hibázik)
-- optional pa = (Just <$> pa) <|> pure Nothing

-- 0 vagy 1 eredményt olvasunk
optional_ :: Parser a -> Parser ()
optional_ pa = void (optional pa)

-- olvassunk 1 vagy több pa-t, psep-el elválasztva
sepBy1 :: Parser a -> Parser sep -> Parser [a]
sepBy1 pa psep = do
  a  <- pa
  as <- many (psep *> pa)
  pure (a:as)

-- olvassunk 0 vagy több pa-t, psep-el elválasztva
sepBy :: Parser a -> Parser sep -> Parser [a]
sepBy pa psep = sepBy1 pa psep <|> pure []

debug :: String -> Parser a -> Parser a
debug msg pa = Parser $ \s -> trace (msg ++ " : " ++ s) (runParser pa s)


-- token/ws parsing

ws :: Parser ()
ws = many_ (satisfy isSpace)

satisfy' :: (Char -> Bool) -> Parser Char
satisfy' f = satisfy f <* ws

char' :: Char -> Parser ()
char' c = char c <* ws

string' :: String -> Parser ()
string' s = string s <* ws

topLevel :: Parser a -> Parser a
topLevel pa = ws *> pa <* eof

-- operátor segédfüggvények

rightAssoc :: (a -> a -> a) -> Parser a -> Parser sep -> Parser a
rightAssoc f pa psep = foldr1 f <$> sepBy1 pa psep

leftAssoc :: (a -> a -> a) -> Parser a -> Parser sep -> Parser a
leftAssoc f pa psep = foldl1 f <$> sepBy1 pa psep

nonAssoc :: (a -> a -> a) -> Parser a -> Parser sep -> Parser a
nonAssoc f pa psep = do
  exps <- sepBy1 pa psep
  case exps of
    [e]      -> pure e
    [e1,e2]  -> pure (f e1 e2)
    _        -> empty


-- While nyelv
--------------------------------------------------------------------------------

data Exp =
    IntLit Int          -- int literál (pozitív)
  | Add Exp Exp         -- e + e
  | Sub Exp Exp         -- e - e
  | Mul Exp Exp         -- e * e
  | BoolLit Bool        -- true|false
  | And Exp Exp         -- e && e
  | Or Exp Exp          -- e || e
  | Not Exp             -- not e
  | Eq Exp Exp          -- e == e
  | Var String          -- (változónév)
  | StringLit String
  | Reverse Exp
  deriving (Eq, Show)

{-
Változónév: nemüres alfabetikus string, ami nem kulcsszó

Kötési erősségek csökkenő sorrendben:
  - atom: zárójelezett kifejezés, literál, változónév
  - not alkalmazás
  - *  : jobbra asszoc
  - +  : jobbra asszoc
  - -  : jobbra asszoc
  - && : jobbra asszoc
  - || : jobbra asszoc
  - == : nem asszoc
-}

posInt' :: Parser Int
posInt' = do
  digits <- some (satisfy isDigit)
  ws
  pure (read digits)

keywords :: [String]
keywords = ["not", "true", "false", "while", "if", "do", "end", "then", "else", "reverse"]

ident' :: Parser String
ident' = do
  x <- some (satisfy isAlpha) <* ws
  if x `elem` keywords
    then empty
    else pure x

keyword' :: String -> Parser ()
keyword' str = do
  x <- some (satisfy isAlpha) <* ws
  if x == str
    then pure ()
    else empty

atom :: Parser Exp
atom =
        Var <$> ident'
    <|> IntLit <$> posInt'
    <|> BoolLit True <$ keyword' "true"
    <|> BoolLit False <$ keyword' "false"
    <|> pString
    <|> char' '(' *> pExp <* char' ')'

pString :: Parser Exp
pString = do
    char '\"'
    string <- many (satisfy (/= '\"'))
    char' '\"'
    return (StringLit string)

pReverse :: Parser Exp
pReverse = keyword' "reverse" *> (Reverse <$> atom)
       <|> atom

pNot :: Parser Exp
pNot = keyword' "not" *> (Not <$> atom)
   <|> pReverse

mulExp :: Parser Exp
mulExp = rightAssoc Mul pNot (char' '*')

addExp :: Parser Exp
addExp = rightAssoc Add mulExp (char' '+')

subExp :: Parser Exp
subExp = rightAssoc Sub addExp (char' '-')

andExp :: Parser Exp
andExp = rightAssoc And subExp (string' "&&")

orExp :: Parser Exp
orExp = rightAssoc Or andExp (string' "||")

eqExp :: Parser Exp
eqExp = nonAssoc Eq orExp (string' "==")

pExp :: Parser Exp
pExp = eqExp

data Val = VInt Int | VBool Bool | VString String
  deriving (Eq, Show)

type Env = [(String, Val)]

evalExp :: Env -> Exp -> Val
evalExp env e = case e of
  IntLit n  -> VInt n
  BoolLit b -> VBool b
  Add e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VInt n1, VInt n2)       -> VInt (n1 + n2)
    (VString s1, VString s2) -> VString (s1++s2)
    _                        -> error "type error"
  Sub e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VInt n1, VInt n2) -> VInt (n1 - n2)
    _                  -> error "type error"
  Mul e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VInt n1, VInt n2) -> VInt (n1 * n2)
    _                  -> error "type error"
  Or e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VBool b1, VBool b2) -> VBool (b1 || b2)
    _                    -> error "type error"
  And e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VBool b1, VBool b2) -> VBool (b1 && b2)
    _                    -> error "type error"
  Eq e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VBool b1, VBool b2)     -> VBool (b1 == b2)
    (VInt n1,  VInt n2 )     -> VBool (n1 == n2)
    (VString s1, VString s2) -> VBool (s1 == s2)
    _                        -> error "type error"
  Not e -> case evalExp env e of
    VBool b -> VBool (not b)
    _       -> error "type error"
  Var x -> case lookup x env of
    Just v  -> v
    Nothing -> error $ "name not in scope: " ++ x
  StringLit str -> VString str
  Reverse e -> case evalExp env e of
      VString str -> VString (reverse str)
      _           -> error "type error"

--------------------------------------------------------------------------------

type Program = [Statement]  -- st1; st2; st3; ... st4

data Statement
  = Assign String Exp       -- x := e
  | While Exp Program       -- while e do prog end
  | If Exp Program Program  -- if e then prog1 else prog2 end
  deriving (Eq, Show)

statement :: Parser Statement
statement =
        Assign <$> ident'
                <*> (string' ":=" *> pExp)
    <|> While <$> (keyword' "while" *> pExp <* keyword' "do")
               <*> (program <* keyword' "end")
    <|> If <$> (keyword' "if" *> pExp <* keyword' "then")
            <*> (program <* keyword' "else")
            <*> (program <* keyword' "end")

program :: Parser Program
program = sepBy statement (char' ';')

updateEnv :: String -> Val -> Env -> Env
updateEnv x v [] = [(x, v)]
updateEnv x v ((x', v'):env)
  | x == x'   = (x', v):env
  | otherwise = (x', v') : updateEnv x v env

inNewScope :: State Env a -> State Env a
inNewScope ma = do
  env <- get
  let len = length env
  a <- ma
  env' <- get
  put $ take len env'
  pure a

evalStatement :: Statement -> State Env ()
evalStatement st = case st of

  -- ha x nincs env-ben, akkor vegyük fel az értékkel,
  -- egyébként pedig írjuk át az értékét
  Assign x e -> do
    env <- get
    let val = evalExp env e
    put $ updateEnv x val env

  -- while-on belüli új változók kívül nem látszanak
  While e p -> do
    env <- get
    case evalExp env e of
      VBool True  -> inNewScope (evalProgram p) >> evalStatement (While e p)
      VBool False -> pure ()
      VInt _      -> error "type error"
      VString _   -> error "type error"

  -- if ágakban új változók kívül nem látszanak
  If e p1 p2 -> do
    env <- get
    case evalExp env e of
      VBool True  -> inNewScope (evalProgram p1)
      VBool False -> inNewScope (evalProgram p2)
      VInt _      -> error "type error"
      VString _   -> error "type error"

evalProgram :: Program -> State Env ()
evalProgram = mapM_ evalStatement

run :: String -> Env
run str = case runParser (topLevel program) str of
  Just (prog, _) -> execState (evalProgram prog) []
  Nothing        -> error "parse error"

p1 :: String
p1 = "i := 10; acc := 0; while not (i == 0) do acc := acc + i; i := i - 1 end"