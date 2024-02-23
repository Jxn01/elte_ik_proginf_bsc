isSmallPrime :: Int -> Bool
isSmallPrime 2 = True
isSmallPrime 3 = True
isSmallPrime 5 = True
isSmallPrime 7 = True
isSmallPrime _ = False

equivalent :: Bool -> Bool -> Bool
equivalent True True = True
equivalent False False = True
equivalent True False = False
equivalent False True = False

implies :: Bool -> Bool -> Bool
implies _ True = True
implies _ False = False

invertO :: (Int, Int) -> (Int, Int)
invertO (x, y) = (-x, -y)

isOnNegId :: (Int, Int) -> Bool
isOnNegId (x, y) = -x == y || -y == x

distance :: (Int, Int) -> (Int, Int) -> Double
distance (x1, y1) (x2, y2) = sqrt(fromIntegral((abs (x1) + abs (x2))^2 + (abs (y1) + abs (y2))^2))

add :: (Int, Int) -> (Int, Int) -> (Int, Int)
add (sz1, n1) (sz2, n2) = ((sz1*n2 + sz2*n1) , n1*n2)

multiply :: (Int, Int) -> (Int, Int) -> (Int, Int)
multiply (sz1, n1) (sz2, n2) = (sz1*sz2, n1*n2)

divide :: (Int, Int) -> (Int, Int) -> (Int, Int)
divide (sz1, n1) (sz2, n2) = (n2*sz1 , n1*sz2)
