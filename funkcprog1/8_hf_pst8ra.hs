module Homework8 where
    all' :: (a -> Bool) -> [a] -> Bool
    all' _ [] = True
    all' f (x:xs)
        | f x = all' f xs
        | otherwise = False

    any' :: (a -> Bool) -> [a] -> Bool
    any' _ [] = False
    any' f (x:xs)
        | f x = True
        | otherwise = any' f xs

    hasAny :: Eq a => [a] -> [a] -> Bool
    hasAny [] [] = False
    hasAny _ [] = False
    hasAny [] _ = False
    hasAny (x:xs) (z:zs)
        | any' (x==) (z:zs) = True
        | otherwise = hasAny xs (z:zs)

    takeWhile' :: (a -> Bool) -> [a] -> [a]
    takeWhile' _ [] = []
    takeWhile' f (x:xs)
        | f x = x : takeWhile' f xs
        | otherwise = []

    dropWhile' :: (a -> Bool) -> [a] -> [a]
    dropWhile' _ [] = []
    dropWhile' f (x:xs)
        | f x = dropWhile' f xs
        | otherwise = x:xs