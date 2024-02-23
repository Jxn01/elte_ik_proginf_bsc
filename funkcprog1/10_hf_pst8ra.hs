module Homework10 where
    import Data.Char

    mapping :: [(Char, Char)]
    mapping = [ (x,y) | x <- ['0'..'6'], y <- ['3'..'9'], ord x+3==ord y] ++ [('7','A'),('8','B'),('9','C')] ++ [(x,y) | x <- ['A'..'W'], y <- ['D'..'Z'], ord x+3==ord y] ++ [('X','a'),('Y','b'),('Z','c')] ++ [ (x,y) | x <- ['a'..'w'], y <- ['d'..'z'], ord x+3==ord y] ++ [('x','0'),('y','1'),('z','2')]

    encodeCaesar :: String -> String
    encodeCaesar = map f where
        f :: Char -> Char
        f c
            | any (\(x,y) -> x==c) mapping = snd(head(filter (\(x,y) -> x==c) mapping))
            | otherwise = '?'

    decodeCaesar :: String -> String 
    decodeCaesar = map f where
        f :: Char -> Char
        f c
            | any (\(x,y) -> y==c) mapping = fst(head(filter (\(x,y) -> y==c) mapping))
            | otherwise = '?'