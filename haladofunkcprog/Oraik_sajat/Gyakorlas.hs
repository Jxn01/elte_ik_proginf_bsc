{-# options_ghc -Wincomplete-patterns #-}
module Gyakorlas where


data Tree a = Leaf a | Node (Tree a) (Tree a) deriving (Show)

-- Extract a list of values from a list of Maybe values!
-- If there is a Nothing in the list, return Nothing.
p1 :: [Maybe a] -> Maybe [a]
p1 [] = Just []
p1 (a:as) = do
    a' <- a
    as' <- p1 as
    return (a':as')

-- Execute a series of operations on an initial values that may fail!
-- If any of the operations fail, return Nothing.
p2 :: [a -> Maybe a] -> a -> Maybe a
p2 [] a = Just a
p2 (f:fs) a = do
    a' <- f a 
    a' <- p2 fs a'
    return a'

-- Check whether a given condition holds over all elements of a tree!
-- If the condition check results in Nothing for any elements, return Nothing.
p3 :: (a -> Maybe Bool) -> Tree a -> Maybe Bool
p3 f (Leaf a) = do
    b' <- f a
    return b'
p3 f (Node tr1 tr2) = do
    tr1' <- p3 f tr1
    tr2' <- p3 f tr2
    return (tr1' && tr2')

-- Extract a value from a nested Maybe!
p4 :: Maybe (Maybe a) -> Maybe a
p4 a = do
    Just a' <- a
    return a'

-- If all leaves of the tree contain values, return just a tree with the values,
-- if any of the values are missing, return Nothing!
p5 :: Tree (Maybe a) -> Maybe (Tree a)
p5 = undefined