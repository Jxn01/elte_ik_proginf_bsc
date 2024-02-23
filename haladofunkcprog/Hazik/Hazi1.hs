{-# LANGUAGE OverloadedStrings, DeriveFunctor, ViewPatterns #-}
{-# OPTIONS_GHC -Wincomplete-patterns #-}

module Hazi1 where

import Data.Traversable
import Control.Applicative
import Control.Monad
import Data.String
import Data.Maybe
import Debug.Trace

newtype State s a = State {runState :: s -> (a, s)} deriving Functor

instance Applicative (State s) where
  pure  = return
  (<*>) = ap

instance Monad (State s) where
  return a = State (\s -> (a, s))
  State f >>= g = State (\s -> case f s of (a, s') -> runState (g a) s')

get :: State s s
get = State (\s -> (s, s))

put :: s -> State s ()
put s = State (\_ -> ((), s))

modify :: (s -> s) -> State s ()
modify f = do {s <- get; put (f s)}

evalState :: State s a -> s -> a
evalState ma = fst . runState ma

execState :: State s a -> s -> s
execState ma = snd . runState ma

data ProgState = ProgState {
  r1     :: Int,
  r2     :: Int,
  r3     :: Int,
  cmp    :: Ordering,
  memory :: [Int]
  } deriving (Eq, Show)

startState :: ProgState
startState = ProgState 0 0 0 EQ (replicate 10 0)

type Label = String  -- címke a programban, ahová ugrani lehet

data Register
  = R1
  | R2
  | R3
  deriving (Eq, Show)

data Destination
  = DstReg Register     -- regiszterbe írunk
  | DstDeref Register   -- memóriába írunk, az adott regiszterben tárolt index helyére
  deriving (Eq, Show)

data Source
  = SrcReg Register     -- regiszterből olvasunk
  | SrcDeref Register   -- memóriából olvasunk, az adott regiszterben tárolt index helyéről
  | SrcLit Int          -- szám literál
  deriving (Eq, Show)

data Instruction
  = Mov Destination Source   -- írjuk a Destination-be a Source értékét
  | Add Destination Source   -- adjuk a Destination-höz a Source értékét
  | Mul Destination Source   -- szorozzuk a Destination-t a Source értékével
  | Sub Destination Source   -- vonjuk ki a Destination-ből a Source értékét
  | Cmp Source Source        -- hasonlítsunk össze két Source értéket `compare`-el, az eredményt
                             -- írjuk a `cmp` regiszterbe

  | Jeq Label                -- Ugorjunk az adott címkére ha a `cmp` regiszterben `EQ` van
  | Jlt Label                -- Ugorjunk az adott címkére ha a `cmp` regiszterben `LT` van
  | Jgt Label                -- Ugorjunk az adott címkére ha a `cmp` regiszterben `GT` van
  deriving (Eq, Show)

type RawProgram = [Either Label Instruction]

-- Beírunk r1-be 10-et, r2-be 20-at
p1 :: RawProgram
p1 = [
  Left "start",
  Right $ Mov (DstReg R1) (SrcLit 10),
  Left "l1",                            -- tehetünk bárhova címkét, nem muszáj használni a programban
  Right $ Mov (DstReg R2) (SrcLit 20)
  ]

-- Kiszámoljuk 10 faktoriálisát, az eredményt r2-ben tároljuk
p2 :: RawProgram
p2 = [
  Left "start",
  Right $ Mov (DstReg R1) (SrcLit 10),
  Right $ Mov (DstReg R2) (SrcLit 1),
  Left "loop",
  Right $ Mul (DstReg R2) (SrcReg R1),
  Right $ Sub (DstReg R1) (SrcLit 1),
  Right $ Cmp (SrcReg R1) (SrcLit 0),
  Right $ Jgt "loop"
  ]

-- Feltöltjük 0-9-el a memóriát
p3 :: RawProgram
p3 = [
  Left "start",
  Right $ Mov (DstDeref R1) (SrcReg R1),
  Right $ Add (DstReg R1) (SrcLit 1),
  Right $ Cmp (SrcReg R1) (SrcLit 10),
  Right $ Jlt "start"
  ]

-- Megnöveljük 1-el a memória összes mezőjét
p4 :: RawProgram
p4 = [
  Left "start",
  Right $ Add (DstDeref R2) (SrcLit 1),
  Right $ Add (DstReg R2) (SrcLit 1),
  Right $ Cmp (SrcReg R2) (SrcLit 10),
  Right $ Jlt "start"
  ]

-- Kétszer hozzáadunk 1-et a harmadik regiszterhez
p5 :: RawProgram
p5 = [
  Left "start",
  Right $ Jeq "first",
  Left "first",
  Right $ Add (DstReg R3) (SrcLit 1),
  Left "second",
  Right $ Add (DstReg R3) (SrcLit 1)
  ]

-- runProgram p4 == ProgState {r1 = 0, r2 = 10, r3 = 0, cmp = EQ, memory = [1,1,1,1,1,1,1,1,1,1]}
-- runProgram p5 == ProgState {r1 = 0, r2 = 0, r3 = 2, cmp = EQ, memory = [0,0,0,0,0,0,0,0,0,0]}

type Program = [(Label, [Instruction])]

toProgram :: RawProgram -> Program
toProgram [] = []
toProgram x = toProgramAux [] x

toProgramAux :: Program -> RawProgram -> Program
toProgramAux p [] = p
toProgramAux p (r:rs) = case r of
    Left a  -> toProgramAux (p++[(a,[])]) rs
    Right b -> toProgramAux (applySnd p (++[b])) rs

applySnd :: [(a,b)] -> (b -> b) -> [(a,b)]
applySnd xs f = [(a, f b) | (a,b) <- xs]

--Példák a működésre
--toProgram p1 == [("start",[Mov (DstReg R1) (SrcLit 10),Mov (DstReg R2) (SrcLit 20)]),("l1",[Mov (DstReg R2) (SrcLit 20)])]
--toProgram [Left "start", Left "l1", Right $ Mov (DstReg R1) (SrcLit 10)] == [("start",[Mov (DstReg R1) (SrcLit 10)]),("l1",[Mov (DstReg R1) (SrcLit 10)])]
--toProgram [Left "start", Right $ Mov (DstReg R1) (SrcLit 10),  Left "l1"] == [("start",[Mov (DstReg R1) (SrcLit 10)]),("l1",[])]

type M a = State ProgState a

eval :: Program -> [Instruction] -> M ()
eval _ [] = do return ()
eval p (i:ins) = do
    bool <- evalIns p i
    b    <- evalLabel i
    if  bool && b
        then eval p (labelJump i p)
    else eval p ins

labelJump :: Instruction -> Program -> [Instruction]
labelJump _ [] = []
labelJump l@(Jeq label) ((label', ins):chunks) = if label == label'
    then ins
    else labelJump l chunks

labelJump l@(Jlt label) ((label', ins):chunks) = if label == label'
    then ins
    else labelJump l chunks

labelJump l@(Jgt label) ((label', ins):chunks) = if label == label'
    then ins
    else labelJump l chunks

labelJump _ _ = []

evalIns :: Program -> Instruction -> M Bool --True ha label
evalIns p (Mov dest src) = do
    n <- evalSource src
    evalDestination dest n
    return False

evalIns p (Cmp src1 src2) = do
    n1 <- evalSource src1
    n2 <- evalSource src2
    putCmpRegisterState (n1 `compare` n2)
    return False

evalIns p (Add dest src) = do
    n <- evalSource src
    evalDestinationModify (+) dest n
    return False

evalIns p (Mul dest src) = do
    n <- evalSource src
    evalDestinationModify (*) dest n
    return False

evalIns p (Sub dest src) = do
    n <- evalSource src
    evalDestinationModify (-) dest n
    return False

evalIns _ _ = do return True

evalLabel :: Instruction -> M Bool
evalLabel (Jeq _) = do
    eq <- getCmpRegisterState
    if eq == EQ then return True
    else return False

evalLabel (Jlt _) = do
    lt <- getCmpRegisterState
    if lt == LT then return True
    else return False

evalLabel (Jgt _) = do
    gt <- getCmpRegisterState
    if gt == GT then return True
    else return False

evalLabel _ = do return False

evalSource :: Source -> M Int
evalSource ins = case ins of
    SrcReg   reg -> getRegisterState reg False
    SrcDeref reg -> getRegisterState reg True
    SrcLit   int -> do return int

evalDestination :: Destination -> Int -> M ()
evalDestination dest n = case dest of
    DstReg   reg -> putRegisterState n reg False
    DstDeref reg -> putRegisterState n reg True

evalDestinationModify :: (Int -> Int -> Int) -> Destination -> Int -> M ()
evalDestinationModify f dest n = case dest of
    DstReg   reg -> modifyRegisterState f n reg False
    DstDeref reg -> modifyRegisterState f n reg True

getRegisterState :: Register -> Bool -> M Int
getRegisterState reg mem = do
    (ProgState r1 r2 r3 cmp memory) <- get
    if mem then case reg of
        R1 -> return (memory!!r1)
        R2 -> return (memory!!r2)
        R3 -> return (memory!!r3)
    else case reg of
        R1 -> return r1
        R2 -> return r2
        R3 -> return r3

getCmpRegisterState :: M Ordering
getCmpRegisterState = do
    (ProgState r1 r2 r3 cmp memory) <- get
    return cmp

putRegisterState :: Int -> Register -> Bool -> M ()
putRegisterState n reg mem = do
    (ProgState r1 r2 r3 cmp memory) <- get
    if mem then case reg of
                R1 -> put (ProgState r1 r2 r3 cmp (changeAt n r1 memory))
                R2 -> put (ProgState r1 r2 r3 cmp (changeAt n r2 memory))
                R3 -> put (ProgState r1 r2 r3 cmp (changeAt n r3 memory))
    else case reg of
                R1 -> put (ProgState n r2 r3 cmp memory)
                R2 -> put (ProgState r1 n r3 cmp memory)
                R3 -> put (ProgState r1 r2 n cmp memory)
    return ()

putCmpRegisterState :: Ordering -> M ()
putCmpRegisterState ord = do
    (ProgState r1 r2 r3 cmp memory) <- get
    put (ProgState r1 r2 r3 ord memory)
    return ()

modifyRegisterState :: (Int -> Int -> Int) -> Int -> Register -> Bool -> M ()
modifyRegisterState f n reg mem = do
    (ProgState r1 r2 r3 cmp memory) <- get
    if mem then case reg of
                R1 -> put (ProgState r1 r2 r3 cmp (changeAt (f (memory!!r1) n) r1 memory))
                R2 -> put (ProgState r1 r2 r3 cmp (changeAt (f (memory!!r2) n) r2 memory))
                R3 -> put (ProgState r1 r2 r3 cmp (changeAt (f (memory!!r3) n) r3 memory))
    else case reg of
                R1 -> put (ProgState (f r1 n) r2 r3 cmp memory)
                R2 -> put (ProgState r1 (f r2 n) r3 cmp memory)
                R3 -> put (ProgState r1 r2 (f r3 n) cmp memory)
    return ()

changeAt :: Int -> Int -> [Int] -> [Int]
changeAt n i list = take i list ++ n : tail (drop i list)

-- futtatunk egy nyers programot a startState-ből kiindulva
runProgram :: RawProgram -> ProgState
runProgram rprog = case toProgram rprog of
  []                  -> startState
  prog@((_, start):_) -> execState (eval prog start) startState


--tesztesetek
--runProgram p1 == ProgState {r1 = 10, r2 = 20, r3 = 0, cmp = EQ, memory = [0,0,0,0,0,0,0,0,0,0]}
--runProgram p2 == ProgState {r1 = 0, r2 = 3628800, r3 = 0, cmp = EQ, memory = [0,0,0,0,0,0,0,0,0,0]}
--runProgram p3 == ProgState {r1 = 10, r2 = 0, r3 = 0, cmp = EQ, memory = [0,1,2,3,4,5,6,7,8,9]}