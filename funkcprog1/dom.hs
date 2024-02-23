--numberLines :: [String] -> [String]
numberLines (x:xs) = zip3 [1..] (iterate ":") (x:xs)