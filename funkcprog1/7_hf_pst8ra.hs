module Homework7 where
    data Player = Player1 | Player2

    switchPlayer :: Player -> Player
    switchPlayer Player1 = Player2
    switchPlayer Player2 = Player1

    data Fruit = Apple Int | Pear Int | Peach Int
        deriving(Show)

    countPears :: [Fruit] -> Int
    countPears [] = 0
    countPears ((Apple x):xs) = 0+countPears xs
    countPears ((Peach x):xs) = 0+countPears xs
    countPears ((Pear x):xs) = x+countPears xs

    data Result a = OK a | Error String
        deriving(Show)

    safeAdd :: Num a => Result a -> Result a -> Result a
    safeAdd (OK a) (Error x) = Error x
    safeAdd (Error x) (OK a) = Error x
    safeAdd (Error x) (Error y) = Error x
    safeAdd (OK a) (OK b) = OK (a+b)

    safeDiv :: Integer -> Integer -> Result Integer
    safeDiv a b
        | b == 0 = Error "divide by zero"
        | otherwise = OK (div a b)
    
    safeHead :: [a] -> Result a
    safeHead [] = Error "empty list"
    safeHead (x:xs)
        | null (x:xs) = Error "empty list"
        | otherwise = OK x

