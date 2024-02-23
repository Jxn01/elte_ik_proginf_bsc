module Homework5 where

    geometricSequence :: Num a => a -> a -> [a]
    geometricSequence a b = [ a*b^i | i <- [0..]]

    isSorted :: Ord a => [a] -> Bool
    isSorted [] = True
    isSorted [x] = True
    isSorted (x1:x2:xs)
        | null xs = True
        | x1 > x2 = False
        | otherwise = isSorted (x2:xs)

    fromTo :: Int -> Int -> [a] -> [a]
    fromTo a b x
        | a > b = []
        | a == b = []
        | a < 0 = fromTo 0 b x
        | b < 0 = []
        | b-a == 1 = [x!!a]
        | otherwise = x!!a : fromTo (a+1) b x

    runs :: Int -> [a] -> [[a]]
    runs a [] = [[]]
    runs 0 x = [ [] | i <- x ]
    runs 1 x = [ [i] | i <- x]
    runs a (x:xs)
        | null xs = []
        | a >= length (x:xs) = [x : xs]
        | otherwise = take a (x : xs) : runs a (drop a (x:xs))