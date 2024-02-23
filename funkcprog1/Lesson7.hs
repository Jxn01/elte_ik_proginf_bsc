module Lesson7 where

    newtype Time = T (Int, Int)
        deriving(Show, Eq)

    

    showTime :: Time -> String
    showTime (T (x,y)) = show x ++ "." ++ show y

    eqTime :: Time -> Time -> Bool
    eqTime a b = a==b

    isEarlier :: Time -> Time -> Bool
    isEarlier (T x) (T y) = x < y

    isBetween :: Time -> Time -> Time -> Bool
    isBetween (T x) (T y) (T z)
        | x < y && y < z = True
        | z < y && y < x = True
        | otherwise = False

    data USTime = AM (Int, Int) | PM (Int, Int)

    showUSTime :: USTime -> String
    showUSTime (AM (x, y)) = show x ++ ":" ++ show y
    showUSTime (PM (x, y)) = show x ++ ":" ++ show y

    data Maybe' a = Just' a | Nothing'
        deriving (Show, Eq)

    fromJust :: Maybe' a -> a
    fromJust (Just' x)  = x
    fromJust Nothing' = error "ERROR: Nothing"

    divided :: [(Int, Int)] -> [Maybe' Double]
    divided ((x,y):xs)
        | null xs = []
        | y /= 0 = Just' (fromIntegral x/fromIntegral y) : divided xs
        | y == 0 = Nothing' : divided xs

    sumMaybe :: [Maybe' Int] -> Int
    sumMaybe [] = 0
    sumMaybe (Nothing' : xs) = sumMaybe xs
    sumMaybe (Just' x : xs) = x + sumMaybe xs