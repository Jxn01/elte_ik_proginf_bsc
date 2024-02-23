module Homework4 where

    putIntoList :: Enum a => a -> [a]
    putIntoList a = [a]

    headTail :: Enum a => [a] -> (a, [a])
    headTail a = (head a, tail a)

    doubleHead :: (Enum a, Enum b) => [a] -> [b] -> (a,b)
    doubleHead a b = (head a, head b)

    hasZero :: [Int] -> Bool
    hasZero [] = False
    hasZero (h:t)
        | h==0 = True
        | null t = False
        | otherwise = hasZero t

    hasEmpty :: Eq a => [[a]] -> Bool
    hasEmpty [] = False
    hasEmpty (h:t)
        | null h = True
        | null t = False
        | otherwise = hasEmpty t

    doubleAll :: [Int] -> [Int]
    doubleAll [] = []
    doubleAll (h:t)
        | not (null (h:t)) = h*2 : doubleAll t
        | otherwise = []

    isLonger :: (Eq a, Num a, Eq b, Num b)  => [a] -> [b] -> Bool
    isLonger [] [] = False
    isLonger _ [] = True
    isLonger [] _ = False
    isLonger (h1:t1) (h2:t2)
        | null (h1:t1) && null (h2:t2) = False
        | null (h1:t1) = False
        | null (h2:t2) = True
        | otherwise = isLonger t1 t2