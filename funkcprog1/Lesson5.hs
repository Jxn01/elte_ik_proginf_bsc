module Lesson5 where

    import Prelude hiding (concat, (++), zip, drop)

    concat :: [[a]] -> [a]
    concat [] = []
    concat [x] = x
    concat (x:xs) = x ++ concat xs

    (++) :: [a] -> [a] -> [a]
    (++) [] [] = []
    (++) xs [] = xs
    (++) [] xs = xs
    (++) (x:xs) x1 = x : (++) xs x1

    zip :: [a] -> [b] -> [(a,b)]
    zip [] [] = []
    zip xs [] = []
    zip [] xs = []
    zip (x1:xs1) (x2:xs2) = (x1,x2) : zip xs1 xs2

    fact :: Int -> Int
    fact n = factH n 1 where
        factH 0 x = x
        factH n x = factH (n-1) (n*x)

    pow :: Int -> Int -> Int
    pow 0 _ = 0
    pow _ 0 = 1
    pow n 1 = n
    pow n k = n * pow n (k-1)

    drop :: Int -> [a] -> [a]
    drop 0 xs = xs
    drop _ [] = []
    drop n l@(x:xs)
        | n < 0 = l
        | otherwise = drop (n-1) xs

    