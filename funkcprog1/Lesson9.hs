module Lesson9 where

    import Data.List

    dropSpaces :: String -> String
    dropSpaces "" = ""
    dropSpaces x = dropWhile (==' ') x

    trim :: String -> String
    trim "" = ""
    trim x = reverse(dropSpaces(reverse(dropSpaces x)))

    monogram :: String -> String
    monogram "" = ""
    monogram x = unwords (map f (words x)) where
        f (x:_) = [x, '.']

    uniq :: Ord a => [a] -> [a]
    uniq [] = []
    uniq a = map head(group(sort a)) 

    repeated :: Ord a => [a] -> [a]
    repeated [] = []
    repeated a =  map head (filter p(group(sort a))) where
        p l = length l > 1 

    zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
    zipWith' _ [] _ = []
    zipWith' _ _ [] = []
    zipWith' f (x:xs) (y:ys) = f x y : zipWith' f xs ys

    dotProduct :: [Int] -> [Int] -> Int
    dotProduct x y = sum (zipWith' (*) x y) 