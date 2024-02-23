module Lesson8 where

    import Data.Char ( isSpace, isUpper, toLower )
    
    --magasabb rendű függvények
    --definíció: 
    --az adott fgv-nek két kritériumot kell teljesítenie:
    --vagy fgv-t vár paraméterül, vagy fgv-t ad vissza értékül
    --haskellben: függvényt kell kapnia paraméterül, mert minden függvény, úgyis függvényt ad vissza

    inc :: (Int -> Int)
    --inc a = a + 1
    --inc a = 1 + a
    inc = (1+)

    add :: Int -> (Int -> Int)
    add = (+)

    timesTwo :: Int -> Int 
    timesTwo = (2*)

    map' :: (a -> b) -> [a] -> [b]
    map' _ [] = []
    map' f (x:xs) = f x : map' f xs

    filter' :: (a -> Bool) -> [a] -> [a]
    filter' _ [] = []
    filter' f (x:xs)
        | f x = x : filter' f xs
        | otherwise = filter' f xs

    upperToLower :: String -> String
    upperToLower [] = []
    upperToLower x = map' toLower(filter' isUpper x)

    trim :: String -> String
    trim [] = []
    trim x = filter' (not . isSpace) x 