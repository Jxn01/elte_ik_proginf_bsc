module Homework11 where
    import Data.Char
    import Data.List

    type ABC = String

    abc :: ABC
    abc = ['A'..'Z']

    type Riddle = String
    type RightGuesses = String
    type WrongGuesses = String

    data State = St Riddle RightGuesses WrongGuesses
        deriving(Show, Eq)

    isValidLetter :: Char -> ABC -> Bool
    isValidLetter _ [] = False
    isValidLetter y abc
        | not $ null $ filter (==toUpper y) abc = True
        | not $ null $ filter (==toLower y) abc = True
        | otherwise = False

    startState :: ABC -> String -> Maybe State
    startState abc s
        | length(group(sort(abc ++ map toUpper s))) > length(group (abc ++ " ")) = Nothing
        | otherwise = Just (St (map toUpper s) "" "")

    guessLetter :: ABC -> Char -> State -> Maybe State
    guessLetter abc char (St a b c)
        | not $ isValidLetter char abc = Nothing
        | isValidLetter char b = Just (St a b c)
        | isValidLetter char c = Just (St a b c)
        | isValidLetter char a = Just (St a (toUpper char:b) c)
        | not $ isValidLetter char a = Just (St a b (toUpper char:c))

    showRiddle :: State -> String
    showRiddle (St [] _ _) = ""
    showRiddle (St (a:ax) b c)
        | a == ' ' = " " ++ showRiddle (St ax b c)
        | a `elem` b = (a:"") ++ showRiddle (St ax b c)
        | a `notElem` b = "_" ++ showRiddle (St ax b c)

    isRiddleComplete :: State -> Bool
    isRiddleComplete (St a [] _) = False
    isRiddleComplete (St a b c)
        | length(group(filter (/=' ') (sort a))) == length(group (filter f (filter (/=' ') (sort b)))) = True
        | otherwise = False where
            f x
                | x `elem` a = True
                | otherwise = False

    isGameOver :: State -> Bool
    isGameOver (St a b c)
        | length c > 5 = True
        | isRiddleComplete (St a b c) = True
        | otherwise = False

    play :: ABC -> String -> Maybe State -> State
    play _ [] (Just (St a b c)) = St a b c
    play abc (x:xs) (Just (St a b c))
        | isGameOver (St a b c) = St a b c
        | otherwise = play abc xs $ guessLetter abc x (St a b c)


    