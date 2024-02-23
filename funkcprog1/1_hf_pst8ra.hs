intExpr1 :: Int
intExpr2 :: Int
intExpr3 :: Int

charExpr1 :: Char
charExpr2 :: Char
charExpr3 :: Char

boolExpr1 :: Bool
boolExpr2 :: Bool
boolExpr3 :: Bool

intExpr1 = 1
intExpr2 = 10
intExpr3 = 100

charExpr1 = 'A'
charExpr2 = 'B'
charExpr3 = 'C'

boolExpr1 = True 
boolExpr2 = False
boolExpr3 = True

canPlantAll :: Int -> Int -> Bool 

remainingSeeds :: Int -> Int -> Int 

canPlantAll seed pot = mod seed pot == 0

remainingSeeds seed pot = mod seed pot

