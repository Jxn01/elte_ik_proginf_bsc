import Data.List
import Data.Char

revTup :: (a, b, c) -> (c, b, a)
revTup (a,b,c) = (c,b,a)

which :: (String, String, String) -> Char -> Maybe Int
which (a,b,c) char
    | char `elem` a = Just 1
    | char `elem` b = Just 2
    | char `elem` c = Just 3
    | otherwise = Nothing

sumFirstThree :: Num a => [a] -> Maybe a
sumFirstThree [] = Nothing
sumFirstThree list
    | sumFirstThreeAux list 0 = Nothing
    | otherwise = Just $ f list 0 where
        f (x:xs) n
            | n /= 2 = x + f xs (n+1)
            | n == 2 = x

sumFirstThreeAux [] _ = True
sumFirstThreeAux (x:xs) n
    | n == 2 && not (null (x:xs)) = False
    | n /= 2 && not (null (x:xs)) = sumFirstThreeAux xs (n+1)
    | n < 2 && null (x:xs) = True

mirrorReduction :: String -> String
mirrorReduction [] = []
mirrorReduction list
    | head list == list!!(length list-1) = mirrorReduction (drop (length(take (length list-1) list)-1) (take (length list-1) list))
    | otherwise = list

encode = zip ['a'..'z']  (reverse ['a'..'z'])

encodeText :: String -> String
encodeText [] = []
encodeText (x:xs)
    | not $ isAlpha x = x : encodeText xs
    | otherwise = f x encode : encodeText xs where
        f y (x:xs)
            | y /= fst x && not (null xs) = f y xs
            | y == fst x = snd x
            | y == toUpper (fst x) = toUpper (snd x)
            | otherwise = y

sumOddEven :: [Integer] -> (Integer, Integer)
sumOddEven [] = (0,0)
sumOddEven list = sumOddEvenAux list (0,0) where
    sumOddEvenAux (x:xs) (a,b)
        | null (x:xs) = (a,b)
        | odd x && not (null xs) = sumOddEvenAux xs (a+x,b)
        | even x && not (null xs)= sumOddEvenAux xs (a,b+x)
        | x == 0 && not (null xs)= sumOddEvenAux xs (a,b)
        | odd x = (a+x,b)
        | even x = (a,b+x)
        | x == 0 = (a,b)
        | otherwise = (a,b)

selectiveApply :: (a -> a) -> (a -> Bool) -> [a] -> [a]
selectiveApply _ _ [] = []
selectiveApply f g (x:xs)
    | g x && not (null xs) = f x : selectiveApply f g xs
    | g x = [f x]
    | otherwise = x : selectiveApply f g xs

--mergeTwoToOne :: [a] -> [a] -> [a]

--isUniform :: Eq b => (a -> b) -> [a] -> Bool

data Weather = Sunny | Cloudy | Rainy Int | Snowy Int
    deriving (Eq, Show)

rainyDays :: [Weather] -> Int
rainyDays [] = 0
rainyDays ((Rainy x):xs)
    | x >= 5 = 1 + rainyDays xs
rainyDays (x:xs) = rainyDays xs

