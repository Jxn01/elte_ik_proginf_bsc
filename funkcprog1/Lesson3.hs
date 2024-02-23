module Lesson3 where
    import Data.Char
    import Data.Maybe
    import Data.List

    -- [elem amit visszaadunk (x) | (x <- lista, y <- lista) logikai feltételek]

    f :: [Int]
    f = [ 2^x | x <- [0..10]]

    evens :: Int -> [Int]
    evens n = [ x | x <- [1..n], even x]

    allPositive :: [Int] -> Bool
    --allPositive = all (>0)
    allPositive n = null [x | x <- n, x <= 0 ]

    dominoes :: [(Int, Int)]
    dominoes = [(x, y) | x <- [0..6], y <- [x..6]]

    alphabet :: [(Int, Char)]
    alphabet = zip [0..] ['a'..'z']

    toUpperAll :: String -> String
    toUpperAll s = [toUpper x | x <- s]

    courses :: [( String, [(String, String, String)])]
    courses = [("Programozasi nyelvek II.", [("Horvath", "Istvan", "BDE91E"), ("Fodros", "Aniko", "DDA3KX")]), ("Imperativ programozas", [("Nemeth", "Eniko", "ALX1K0"), ("Horvath", "Istvan", "BDE91E")]), ("Funkcionalis programozas", [("Kiss", "Elemer", "ABCDE6"), ("Nagy", "Jakab", "CDE560")])]


    --házi 8-as feladat
    funcIndex :: Int -> Int
    funcIndex n = if fst (courses !! n) == "Funkcionalis programozas" then n else funcIndex (n+1)

    get3th :: (a, b, c) -> c
    get3th (_,_,a) = a

    studentsAux :: Int -> [String]
    studentsAux n = if length(snd(courses !! funcIndex 0))/=n then get3th (snd (courses !! funcIndex 0)!! n) : studentsAux (n+1) else []

    students :: [String]
    students = studentsAux 0