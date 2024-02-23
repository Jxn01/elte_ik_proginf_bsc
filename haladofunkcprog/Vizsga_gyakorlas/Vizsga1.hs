{-# language DeriveFunctor, InstanceSigs #-}
{-# options_ghc -Wincomplete-patterns #-}

module Vizsga1 where

import Control.Applicative
import Control.Monad
import Debug.Trace
import Data.Char    -- isSpace, isDigit

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

data Tree a = Leaf a
            | Node (Maybe (Tree a)) (Maybe (Tree a))
  deriving (Show)

t1 :: Tree Int
t1 = Node (Just (Node (Just (Leaf 1)) (Just (Leaf 2)))) (Just (Leaf 3))

t2 :: Tree Int
t2 = Node Nothing (Just (Node Nothing Nothing))

t3 :: Tree Int
t3 = Node (Just (Node (Just (Leaf 10)) (Just (Leaf 5)))) (Just (Node Nothing Nothing))

instance (Eq a) => Eq (Tree a) where
  (==) (Leaf a) (Leaf b)        = (==) a b
  (==) (Node l r) (Node l' r')  = (==) l l' && (==) r r'
  (==) _ _                      = False

instance Functor Tree where
  fmap f (Leaf a)                 = Leaf (f a)
  fmap f (Node (Just l) (Just r)) = Node (Just (fmap f l)) (Just (fmap f r))
  fmap f (Node (Just l) Nothing)  = Node (Just (fmap f l)) Nothing
  fmap f (Node Nothing (Just r))  = Node Nothing (Just (fmap f r))
  fmap _ (Node Nothing Nothing)   = Node Nothing Nothing

instance Foldable Tree where
  foldr f start (Leaf a)                 = f a start
  foldr f start (Node (Just l) (Just r)) = foldr f (foldr f start r) l
  foldr _ start _                        = start

instance Traversable Tree where
  traverse f (Leaf a)                 = Leaf <$> f a
  traverse f (Node (Just l) (Just r)) = Node <$> (Just <$> traverse f l) <*> (Just <$> traverse f r)
  traverse f (Node (Just l) Nothing)  = Node <$> (Just <$> traverse f l) <*> pure Nothing
  traverse f (Node Nothing (Just r))  = Node Nothing <$> (Just <$> traverse f r)
  traverse f (Node Nothing Nothing)   = pure (Node Nothing Nothing)


countElems :: Tree a -> Int
countElems (Leaf a)                 = 1
countElems (Node (Just l) (Just r)) = countElems l + countElems r
countElems (Node (Just l) Nothing)  = countElems l
countElems (Node Nothing (Just r))  = countElems r
countElems (Node Nothing Nothing)   = 0

--Példák
--countElems t1 == 3
--countElems t2 == 0
--countElems t3 == 2

countJusts :: Tree a -> Int
countJusts (Leaf a)                 = 0
countJusts (Node (Just l) (Just r)) = 2 + countJusts l + countJusts r
countJusts (Node (Just l) Nothing)  = 1 + countJusts l
countJusts (Node Nothing (Just r))  = 1 + countJusts r
countJusts (Node Nothing Nothing)   = 0

--Példák
--countJusts (Node (Just (Leaf True)) Nothing) == 1
--countJusts (Node (Just (Leaf True)) (Just (Leaf True))) == 2
--countJusts (Leaf True) == 0

replaceNothings :: Maybe (Tree a) -> Tree a -> Tree a
replaceNothings mt (Leaf a)                 = Leaf a
replaceNothings mt (Node (Just l) (Just r)) = Node (Just (replaceNothings mt l)) (Just (replaceNothings mt r))
replaceNothings mt (Node (Just l) Nothing)  = Node (Just (replaceNothings mt l)) mt
replaceNothings mt (Node Nothing (Just r))  = Node mt (Just (replaceNothings mt r))
replaceNothings mt (Node Nothing Nothing)   = Node mt mt

--Példák
--replaceNothings (Just (Leaf 1)) t1 == Node (Just (Node (Just (Leaf 1)) (Just (Leaf 2)))) (Just (Leaf 3))
--replaceNothings (Just (Leaf 1)) t2 == Node (Just (Leaf 1)) (Just (Node (Just (Leaf 1)) (Just (Leaf 1))))
--replaceNothings (Just (Leaf 1)) t3 == Node (Just (Node (Just (Leaf 10)) (Just (Leaf 5)))) (Just (Node (Just (Leaf 1)) (Just (Leaf 1))))

shiftLeftElems :: a -> Tree a -> Tree a
shiftLeftElems a t = evalState (shiftLeftElemsAux t) a

shiftLeftElemsAux :: Tree a -> State a (Tree a)
shiftLeftElemsAux (Leaf curr) = do
  prev <- get
  put curr
  return (Leaf prev)
shiftLeftElemsAux (Node (Just l) (Just r)) = do
  r' <- shiftLeftElemsAux r
  l' <- shiftLeftElemsAux l
  return (Node (Just l') (Just r'))
shiftLeftElemsAux (Node (Just l) Nothing)  = do
  l' <- shiftLeftElemsAux l
  return (Node (Just l') Nothing)
shiftLeftElemsAux (Node Nothing (Just r))  = do
  r' <- shiftLeftElemsAux r
  return (Node Nothing (Just r'))
shiftLeftElemsAux (Node Nothing Nothing)   = return (Node Nothing Nothing)


--Példák
--shiftLeftElems 0 (Leaf 1) == Leaf 0
--shiftLeftElems 0 (Node (Just (Leaf 1)) (Just (Leaf 2))) == Node (Just (Leaf 2)) (Just (Leaf 0))
--shiftLeftElems 0 (Node (Just (Node (Just (Leaf 1)) (Just (Leaf 2)))) (Just (Leaf 3))) == Node (Just (Node (Just (Leaf 2)) (Just (Leaf 3)))) (Just (Leaf 0))

simplifyTree :: Tree a -> Maybe (Tree a)
simplifyTree t = case simplifyTreeAux t of
  Just (Node Nothing Nothing) -> Nothing
  _                           -> simplifyTreeAux t

simplifyTreeAux :: Tree a -> Maybe (Tree a)
simplifyTreeAux (Leaf a)                 = Just (Leaf a)
simplifyTreeAux (Node (Just l) (Just r)) = Just (Node (simplifyTreeAux l) (simplifyTreeAux r))
simplifyTreeAux (Node (Just l) Nothing)  = Just (Node (simplifyTreeAux l) Nothing)
simplifyTreeAux (Node Nothing (Just r))  = Just (Node Nothing (simplifyTreeAux r))
simplifyTreeAux (Node Nothing Nothing)   = Nothing


--Példák
--simplifyTree t1 == Just (Node (Just (Node (Just (Leaf 1)) (Just (Leaf 2)))) (Just (Leaf 3)))
--simplifyTree t2 == Nothing
--simplifyTree t3 == Just (Node (Just (Node (Just (Leaf 10)) (Just (Leaf 5)))) Nothing)

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
  | ENothing
  | EJust Exp
  | Case Exp Program String Program
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
keywords = ["not", "true", "false", "while", "if", "do", "end", "then", "else", "Just", "Nothing", "case", "of", "end"]

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



pCase :: Parser Exp
pCase = do
  keyword' "case"
  e1 <- atom
  keyword' "of"
  keyword' "Nothing"
  string "->"
  p1 <- program
  char ';'
  keyword' "Just"
  x <- ident'
  string "->"
  p2 <- optional program
  keyword' "end"
  case p2 of
    Just p2 -> return (Case e1 p1 x p2)
    Nothing -> return (Case e1 p1 x [])
  

pJust :: Parser Exp
pJust = keyword' "Just" *> (EJust <$> atom)

pNot :: Parser Exp
pNot = keyword' "not" *> (Not <$> atom)
    <|> pJust <|> atom

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

atom :: Parser Exp
atom =
        Var <$> ident'
    <|> IntLit <$> posInt'
    <|> BoolLit True <$ keyword' "true"
    <|> BoolLit False <$ keyword' "false"
    <|> ENothing <$ keyword' "Nothing"
    <|> pCase
    <|> char' '(' *> pExp <* char' ')'

data Val = VInt Int | VBool Bool | VMaybe (Maybe Val)
  deriving (Eq, Show)

type Env = [(String, Val)]

evalExp :: Env -> Exp -> Val
evalExp env e = case e of
  IntLit n  -> VInt n
  BoolLit b -> VBool b
  Add e1 e2 -> case (evalExp env e1, evalExp env e2) of
    (VInt n1, VInt n2) -> VInt (n1 + n2)
    _                  -> error "type error"
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
    (VBool b1, VBool b2) -> VBool (b1 == b2)
    (VInt n1,  VInt n2 ) -> VBool (n1 == n2)
    _                    -> error "type error"
  Not e -> case evalExp env e of
    VBool b -> VBool (not b)
    _       -> error "type error"
  Var x -> case lookup x env of
    Just v  -> v
    Nothing -> error $ "name not in scope: " ++ x
  ENothing  -> VMaybe Nothing 
  EJust exp -> VMaybe (Just (evalExp env exp)) 
  Case e1 p1 x p2 -> undefined

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

  -- if ágakban új változók kívül nem látszanak
  If e p1 p2 -> do
    env <- get
    case evalExp env e of
      VBool True  -> inNewScope (evalProgram p1)
      VBool False -> inNewScope (evalProgram p2)
      VInt _      -> error "type error"

evalProgram :: Program -> State Env ()
evalProgram = mapM_ evalStatement

run :: String -> Env
run str = case runParser (topLevel program) str of
  Just (prog, _) -> execState (evalProgram prog) []
  Nothing        -> error "parse error"

p1 :: String
p1 = "i := 10; acc := 0; while not (i == 0) do acc := acc + i; i := i - 1 end"