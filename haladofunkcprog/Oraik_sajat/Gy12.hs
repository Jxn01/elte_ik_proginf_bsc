{-# language InstanceSigs, DeriveFunctor #-}
{-# options_ghc -Wincomplete-patterns #-}
module Gy12 where
import Control.Monad
import Control.Applicative
import Debug.Trace

import Data.Maybe


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


-- Könnyebb
--------------------------------------------------------------------------------

data MaybeTree a
  = Node (MaybeTree a) (Maybe a) (MaybeTree a)
  | Leaf a
  deriving (Show)

-- Írd meg az instance-okat!
instance (Eq a) => Eq (MaybeTree a) where
  (==) (Leaf a) (Leaf b) = a == b
  (==) (Node mta ma mta') (Node mtb mb mtb') = ma == mb && mta == mtb && mta' == mtb'
  (==) _ _ = False

instance Functor MaybeTree where
  fmap f (Leaf a) = Leaf (f a)
  fmap f (Node mta ma mta') = Node (fmap f mta) (fmap f ma) (fmap f mta')

instance Foldable MaybeTree where
  foldr :: (a -> b -> b) -> b -> MaybeTree a -> b
  foldr f b (Leaf a) = f a b
  foldr f b (Node mta ma mta') = undefined

instance Traversable MaybeTree where
  traverse f (Leaf a) = Leaf <$> f a
  traverse f (Node mta ma mta') = Node <$> traverse f mta <*> traverse f ma <*> traverse f mta'

-- Számold meg a tárolt `Just` konstruktorokat a fában.
countJusts :: MaybeTree a -> Int
countJusts (Leaf a) = 0
countJusts (Node mta Nothing mta') = countJusts mta + 0 + countJusts mta' 
countJusts (Node mta (Just _) mta') = countJusts mta + 1 + countJusts mta' 

-- Add vissza csak a `Leaf`-ekben tárolt értékek listáját.
leaves :: MaybeTree a -> [a]
leaves (Leaf a) = [a]
leaves (Node mta _ mta') = leaves mta ++ leaves mta'

-- Egy fában told el az összes "a" elemet egy pozícióval jobbra, a balról-jobbra
-- bejárási sorrendben. A leginkább baloldali elem helyére kerüljön egy megadott
-- "default" érték.

-- Tipp: használd a `State a`-t a bejáráshoz! Az állapot legyen legutóbb bejárt
-- `a` típusú érték, vagy pedig az adott default érték, ha még nem jártunk be
-- egy elemet sem.

shiftElems :: a -> MaybeTree a -> MaybeTree a
shiftElems d t = evalState (traverse go t) d where
  go :: a -> State a a
  go a = do
    prev <- get
    put a
    return prev


-- példák a működésre:
--   shiftElems 10 (Leaf 0) == Leaf 10
--   shiftElems 10 (Node (Leaf 0) (Just 1) (Leaf 2)) == Node (Leaf 10) (Just 0) (Leaf 1)


-- Nehezebb
--------------------------------------------------------------------------------

data RoseTree a = Branch a [RoseTree a]
  deriving (Ord, Show)

ex1 :: RoseTree Int
ex1 = Branch 2
      [ Branch 3
          [ Branch 11 [] -- Leaf x ~ Branch x []
          ]
      , Branch 5 []
      , Branch 7
          [ Branch 13 []
          ]
      ]

-- Írd meg az alábbi instance-okat!
instance Eq a => Eq (RoseTree a) where
  (==) = undefined

instance Functor RoseTree where
  fmap = undefined

instance Foldable RoseTree where
  foldr   = undefined

instance Traversable RoseTree where
  traverse = undefined

-- Add vissza az "a" típusú elemek számát egy fában!
countElems :: RoseTree a -> Int
countElems = undefined

-- Add vissza a maximális "a" értéket egy fából!
maxElem :: Ord a => RoseTree a -> a
maxElem = undefined

-- Számozd be bal-jobb bejárási sorrendben a fát!
label :: RoseTree a -> RoseTree (a, Int)
label = undefined

-- Írj egy függvényt, ami egy fában az összes "n :: Int" értéket kicseréli az
-- adott "[a]" lista n-edik elemére! Ha "n" bárhol nagyobb vagy egyenlő mint a
-- lista hossza, akkor legyen a végeredmény Nothing.
transformWithList :: [a] -> RoseTree Int -> Maybe (RoseTree a)
transformWithList = undefined