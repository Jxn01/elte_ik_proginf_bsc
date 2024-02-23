module Lesson10 where
    import Data.List ( group, sort )

    iterate'' :: (a -> a) -> a -> [a]
    iterate'' f e = e : iterate'' f (f e)

    fibonacci :: [Integer]
    fibonacci = map fst (iterate'' (\(x,y) -> (y, x+y)) (0,1))

    repeated :: Ord a => [a] -> [a]
    repeated [] = []
    repeated a =  map head (filter (\l -> length l > 1) (group(sort a)))

    monogram :: String -> String
    monogram "" = ""
    monogram x = unwords (map (\(x:_) -> [x, '.']) (words x))


    -- (1+(2+(3+(4+(5+(6+(7+(8+(9+(10+0))))))))))
    foldR :: (a -> b -> b) -> b -> [a] -> b
    foldR _ e [] = e
    foldR f e (x:xs) = f x (foldR f e xs)

    -- (((((((((0+1)+2)+3)+4)+5)+6)+7)+8)+9)+10)
    foldL :: (b -> a -> b) -> b -> [a] -> b
    foldL _ e [] = e
    foldL f e (x:xs) = foldL f (f e x) xs

    sum' :: Num a => [a] -> a
    sum' = foldL (+) 0

    product' :: Num a => [a] -> a
    product' = foldL (*) 1

    and' :: [Bool] -> Bool 
    and' = foldL (&&) True

    length' :: [a] -> Int 
    length' = foldL (\c _ -> c+1) 0



