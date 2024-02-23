module BitList where
    import Data.List
    
    type BitList = [Int]

    countEliminable :: BitList -> Int
    countEliminable list = length(countEliminableIndices list)

    countEliminableIndices :: BitList -> [Int]
    countEliminableIndices [] = []
    countEliminableIndices list = f (group list) 1 where
        f :: [BitList] -> Int -> [Int]
        f (x:xs) n
            | null xs && length x == 1 = []
            | null xs && length x > 1 = [n]
            | length x == 1 = f xs (n+1)
            | length x > 1 = n : f xs (n+1)

    
    eliminateNth :: BitList -> Int -> BitList
    eliminateNth list n = eliminateNthAux list (n-1) where
        eliminateNthAux :: BitList -> Int -> BitList
        eliminateNthAux [] _ = []
        eliminateNthAux [x] _ = [x]
        eliminateNthAux list n = f (group list) where
            f :: [BitList] -> BitList
            f gList
                | countEliminable list == 0 = concat gList
                | n <= 0 = concat (takeIndex gList (head(countEliminableIndices(concat gList))-1))
                | n/=0 && n >= countEliminable list = concat (takeIndex gList (last(countEliminableIndices(concat gList))-1))
                | otherwise = concat (takeIndex gList ((countEliminableIndices(concat gList)!!n)-1)) where
                    takeIndex :: [a] -> Int -> [a]
                    takeIndex x i = take i x ++ drop (1+i) x 


    elimination :: BitList -> Bool
    elimination list = f (group list) where
        f :: [BitList] -> Bool
        f gList
            | null gList = True
            | countEliminable (concat gList) == 0 = False
            | otherwise = f (group(eliminateNth (concat gList) 1)) || f (group(eliminateNth (concat gList) 2)) || f (group(eliminateNth (concat gList) 3))
