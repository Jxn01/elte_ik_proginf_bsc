module Vizsga where
    import Data.Char
    import Data.List

    splitQuadruple :: (a,b,c,d) -> ((a,b),(c,d))
    splitQuadruple (a,b,c,d) = ((a,b),(c,d))

    dist1 :: Num a => a -> a -> a
    dist1 a b = abs(a-b)

    kroeneckerDelta :: Eq a => a -> a -> Int
    kroeneckerDelta a b
        | a == b = 1
        | otherwise = 0

    freq :: Eq a => a -> [a] -> Int
    freq _ [] = 0
    freq e (x:xs)
        | e == x = 1 + freq e xs
        | otherwise = freq e xs

    hasUpperCase :: String -> Bool
    hasUpperCase [] = False
    hasUpperCase (x:xs)
        | isUpper x = True
        | otherwise = hasUpperCase xs

    identifier :: String -> Bool
    identifier [] = False
    identifier string = identAux string 1 where
        identAux :: String -> Int -> Bool
        identAux (x:xs) n
            | n == 1 && not (isAlpha x) = False
            | n == 1 && isAlpha x = identAux xs (n+1)
            | n /= 1 && isAlpha x || isDigit x || x == '_' = identAux xs (n+1)
            | null (x:xs) = True
            | otherwise = False

    paripos :: [Int] -> Bool
    paripos [] = True
    paripos list = f (zip [0..] list) where
        f [] = True
        f ((a,b):xs)
            | odd a && odd b = f xs
            | odd a && even b = False
            | even a && even b = f xs
            | even a && odd b = False

    safeDiv :: Int -> Int -> Maybe Int
    safeDiv a b
        | b == 0 = Nothing
        | otherwise = Just (div a b)

    parseCSV :: String -> [String]
    parseCSV list
        | takeWhile (/=';') list == list = [list]
        | otherwise = takeWhile (/=';') list : parseCSV (tail (dropWhile (/=';') list))