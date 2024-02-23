module Homework9 where

    rook :: (Int, Int) -> [(Int, Int)]
    rook (x,y) = [ (i,y) | i <- [0..7], i /= x] ++ [ (x,j) | j <- [0..7], j /= y]

    knight :: (Int, Int) -> [(Int, Int)]
    knight (x,y) = [(i, j) |
       i <- [0 .. 7],
       i /= x,
       j <- [0 .. 7],
       j /= y,
       abs (x - i) == 1 ||
       abs (x - i) == 2,
       abs (y - j) == 1 ||
       abs (y - j) == 2,
       abs ((x+y) - (i+j)) == 3 ||
       abs ((x+y) - (i+j)) == 1]

    attacks :: ((Int, Int) -> [(Int, Int)]) -> (Int, Int) -> [(Int, Int)] -> Bool
    attacks _ _ [] = False
    attacks f (x,y) (h:t)
        | h `elem` f (x,y) = True
        | otherwise = attacks f (x,y) t
