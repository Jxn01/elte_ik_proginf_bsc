{-# language InstanceSigs, DeriveFunctor #-}
{-# options_ghc -Wincomplete-patterns #-}

import Control.Monad
import Graphics.Win32 (c_ArcTo)
import Data.Char
import Text.Parsec.Prim (putState)

-- IO monád
--------------------------------------------------------------------------------

-- getLine  :: IO String             -- beolvas
-- print    :: Show a => a -> IO ()  -- kinyomtat értéket
-- putStrLn :: String -> IO ()       -- String-et nyomtat ki

-- (>>=)  :: IO a -> (a -> IO b) -> IO b
-- return :: a -> IO a
-- fmap   :: (a -> b) -> IO a -> IO b


-- Írj egy függvényt, ami beolvas egy sort, majd kiírja a sorban található
-- 'a' betűk számát.
io1 :: IO ()
io1 = do
    line <- getLine
    print $ length $ filter (=='a') line

printNTimes :: Int -> String -> IO ()
printNTimes 0 s = return ()
printNTimes n s = do
    putStrLn s
    printNTimes (n-1) s

-- Írj egy függvényt, ami beolvas egy sort, majd a sort kinyomtatja annyiszor,
-- ahány karakter van a sorban!
io2 :: IO ()
io2 = do
    line <- getLine
    putStrLn "----"
    let len = length line
    printNTimes len line

io2' :: IO ()
io2' = do
    line <- getLine
    let len = length line
    replicateM_ len (putStrLn line)

-- Írj egy függvényt, ami addig olvas be ismételten sorokat, amíg a sor nem
-- tartalmaz 'x' karaktert.

hasnotX :: String -> Bool
hasnotX l
    | length (filter (=='x') l) >= 1 = False
    | otherwise = True

io3 :: IO ()
io3 = do
    line <- getLine
    if hasnotX line then io3 else return ()


kisNum :: String -> Int
kisNum line = length (filter (isLower) line)

-- A következőt ismételd végtelenül: olvass be egy sort, majd nyomtasd ki a
-- sorban a kisbetűk számát.  A Ctrl-c-c -vel lehet megszakítani a futtatást
-- ghci-ben.
io4 :: IO ()
io4 = do
    line <- getLine
    print  (kisNum line)
    io4





--------------------------------------------------------------------------------

-- Definiáld a következő függvényeket tetszőlegesen, de típushelyesen.

f1 :: Monad m => (a -> b) -> m a -> m b -- fmap
f1 f ma = do
    a <- ma
    return (f a)

f2 :: Monad m => m a -> m b -> m (a, b) -- liftm2
f2 ma mb = do
    a <- ma
    b <- mb
    return (a,b)

f3 :: Monad m => m (m a) -> m a -- join művelet
f3 mma = do
    ma <- mma
    ma


bind' :: m a -> (a -> m b) -> m b
bind' = undefined

f4 :: Monad m => m (a -> b) -> m a -> m b
f4 f ma = do
    f' <- f
    a <- ma
    return (f' a)

f5 :: Monad m => (a -> m b) -> m a -> m b
f5 f ma = do
    a <- ma
    f a 

f6 :: Monad m => (a -> b -> c) -> m a -> m b -> m c
f6 f ma mb = do
    a <- ma
    b <- mb
    return $ f a b

f7 :: Monad m => (a -> b -> c -> d) -> m a -> m b -> m c -> m d
f7 f ma mb mc = do
    a <- ma
    b <- mb
    c <- mc
    return $ f a b c

f8 :: Monad m => (a -> m b) -> (b -> m c) -> a -> m c
f8 f g a = do
    mb <- f a
    g mb 

-- State monád definíció
--------------------------------------------------------------------------------

newtype State s a = State {runState :: s -> (a, s)} deriving Functor

instance Applicative (State s) where
  pure  = return
  (<*>) = ap

instance Monad (State s) where
  return a = State (\s -> (a, s))
  State f >>= g = State (\s -> case f s of (a, s') -> runState (g a) s')

get :: State s s
get = State (\s -> (s, s))

put :: s -> State s ()
put s = State (\_ -> ((), s))

modify :: (s -> s) -> State s ()
modify f = do {s <- get; put (f s)}

evalState :: State s a -> s -> a
evalState ma = fst . runState ma

execState :: State s a -> s -> s
execState ma = snd . runState ma



-- Definiálj egy függvényt, ami a lista állapotot kiegészíti egy elemmel
push :: a -> State [a] ()
push = undefined

-- példák:
-- runState (push 10) [] == ((), [10])
-- runState (push 10 >> push 10 >> push 20) [] == ((), [20, 10, 10])


-- Ha az állapot lista nem üres, akkor a következő függvény leveszi az első
-- elemet és visszaadja Just értékként, egyébként Nothing-ot ad.
pop :: State [a] (Maybe a)
pop = undefined


-- Írj egy függvényt, ami egy Int listában minden elemet kicserél az addigi
-- elemek maximumára.  Tegyük fel, hogy a lista nem-negatív számokat tartalmaz.
-- Használd a (State Int)-et. Legyen az állapot a jelenlegi maximális Int.
maxs :: [Int] -> [Int]
maxs = undefined


-- Definiálj egy függvényt, ami kicseréli egy fa leveleiben tárolt értékeket
-- balról jobbra haladva egy megadott lista elemeire. Használj State monádot!

data Tree a = Leaf a | Node (Tree a) (Tree a)
  deriving (Functor, Show)

-- pl: replaceLeaves [10, 20, 30] (   Node (Leaf 2) (Leaf 3))
--                                 == Node (Leaf 10) (Leaf 20)
--     replacereplaceLeaves [5] (Leaf 10) == Leaf 5
--     replacereplaceLeaves [5]
--        (Node (Leaf 0) (Node (Leaf 0) (Leaf 0))) ==
--        (Node (Leaf 5) (Node (Leaf 0) (Leaf 0)))

replaceLeaves :: [a] -> Tree a -> Tree a
replaceLeaves = undefined


-- Definiáld a függvényt, ami megfordítja a fa leveleiben tárolt értékek
-- sorrendjét!  tipp: használhatod a replaceLeaves függvényt.
reverseElems :: Tree a -> Tree a
reverseElems = undefined

-- IO gyakorlo pelda
-- What's your name?
-- > István
-- Hello, István!
-- When were you born?
-- > 1997
-- Did you already have your birthday this year? (Y/N)
-- > Y
-- You are 25 years old.

getYesNo :: IO Bool
getYesNo = do
    line <- getLine
    if line == "Y" then return True else
        if line == "N" then return False else do
            putStrLn "Please enter 'Y' or 'N'!"
            getYesNo

io5 :: IO ()
io5 = do
    putStrLn "What's your name?"
    name <- getLine
    putStrLn $ "Hello, " ++ name ++ "!"
    putStrLn "When were you born?"
    year <- readLn
    putStrLn "Did you already have your birthday this year? (Y/N)"
    bday <- getYesNo
    let age = if bday then 2022 - year else 2022 - year - 1
    putStrLn $ "You are " ++ show age ++ " old."
