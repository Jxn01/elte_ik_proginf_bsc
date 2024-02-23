module Homework3 where

import Data.List
import Data.Maybe

isPrime :: Int -> Bool
isPrime n = length [ x | x <- [1..n], mod n x == 0] == 2

primes = [x | x <- [1..], isPrime x]

--compressAux :: String -> Int -> [(Int, Char)]
--compressAux s n = if ((n)/=length(group s)) then ((length ((group s)!!n)), (head ((group s)!!n))) : [] ++ compressAux s (n+1) else []

--compress :: String -> [(Int, Char)]
--compress s = compressAux s 0

--decompressAux :: [(Int, Char)] -> Int -> String
--decompressAux s n = if (n/=length s) then (replicate (fst (s!!n)) (snd (s!!n))) ++ [] ++ decompressAux s (n+1) else []

--decompress :: [(Int, Char)] -> String
--decompress s = decompressAux s 0

compress :: String -> [(Int, Char)]
compress s = [(length x, head x) | x <- group s]
