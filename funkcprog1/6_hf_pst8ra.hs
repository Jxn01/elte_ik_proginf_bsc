module Homework6 where

    data Vector3 = V Int Int Int
        deriving(Show, Eq)

    data Storage = HDD String Int Int | SSD String Int {-gyarto, fordulat, kapacitas-}
        deriving(Show, Eq)

    componentSum :: Vector3 -> Int
    componentSum (V a b c) = a+b+c

    crossProduct :: Vector3 -> Vector3 -> Vector3
    crossProduct (V a1 b1 c1) (V a2 b2 c2) = V(b1*c2-c1*b2) (c1*a2-a1*c2) (a1*b2-b1*a2)

    vectorListSum :: [Vector3] -> Vector3
    vectorListSum [] = V 0 0 0
    vectorListSum (x:xs)
        | null xs = x
        | otherwise = vectorSum x (vectorListSum xs) where

            vectorSum :: Vector3 -> Vector3 -> Vector3
            vectorSum (V a1 b1 c1) (V a2 b2 c2) = V (a1+a2) (b1+b2) (c1+c2)

    capacity :: Storage -> Int
    capacity (HDD g f k) = k
    capacity (SSD g k) = k

    isHDD :: Storage -> Bool
    isHDD (HDD {}) = True
    isHDD (SSD _ _) = False

    hugeHDDs :: [Storage] -> [Storage]
    hugeHDDs [] = []
    hugeHDDs (x:xs)
        | null xs && capacity x > maxSSD(toSSDlist(x:xs)) 0 = [x]
        | null xs && capacity x <= maxSSD(toSSDlist(x:xs)) 0 = []
        | isHDD x && capacity x > maxSSD(toSSDlist(x:xs)) 0 = x : hugeHDDs xs
        | otherwise = hugeHDDs xs where

                toSSDlist :: [Storage] -> [Storage]
                toSSDlist (x:xs)
                    | not (isHDD x) && not (null xs)= x:toSSDlist xs
                    | null xs = [x]
                    | otherwise = toSSDlist xs

                maxSSD :: [Storage] -> Int -> Int
                maxSSD (SSD s1 a:xs) max
                    | null xs && a >= max = a
                    | null xs && a < max = max
                    | a > max = maxSSD xs a
                    | otherwise = maxSSD xs max