module Lesson4 where

    sum' :: [Int] -> Int
    sum' [] = 0
    sum' [x] = x
    sum' (h:t) = h + sum' t

    {-
        [1,2,3]

        1 + (sum' [2,3])
        1 + (2 + sum' [3])
        1 + (2 + (3 + sum' []))
    -}

    len :: [a] -> Int
    len [] = 0
    len [x] = 1
    len (h:t) = 1 + len t

    replaceFirst :: Char -> Char -> String -> String
    replaceFirst _ _ "" = ""
    replaceFirst old new (h:t)
        | h == old = new : t
        | otherwise = h : replaceFirst old new t --default ág

    range :: Int -> Int -> [Int]
    range 0 0 = []
    range first last
        | first /= last = first : range (first+1) last
        | otherwise = [last]

    pow :: Int -> Int -> Int
    pow 0 0 = 1
    pow 0 _ = 1
    pow _ 0 = 1
    pow alap hatvany
        | hatvany > 0 = alap * pow alap (hatvany-1)
        | otherwise = 1

    everySecond :: [a] -> [a]
    everySecond [] = []
    everySecond [x] = [] -- (x:[])
    everySecond (_:h:t) = h : everySecond t

    elem' :: Eq a => a -> [a] -> Bool
    elem' _ [] = False
    elem' a (x:xs)
        | a == x = True
        | otherwise = elem' a xs
