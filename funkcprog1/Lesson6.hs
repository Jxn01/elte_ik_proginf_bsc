module Lesson6 where

    newtype MyPoint = P (Int,Int)
        deriving (Show)

    newtype MyPoint2 = P2(Int,Int)
        deriving(Show)

    translate :: (Int, Int) -> MyPoint -> MyPoint
    translate (x,y) (P (x1,y1)) = P (x+x1, y+y1)
    data Day = Hetfo | Kedd | Szerda | Csutortok | Pentek | Szombat | Vasarnap
        deriving(Show)

    isFirstDayOfWeek :: Day -> Bool 
    isFirstDayOfWeek Hetfo = True 
    isFirstDayOfWeek _ = False

    isWeekend :: Day -> Bool 
    isWeekend Szombat = True
    isWeekend Vasarnap = True
    isWeekend _ = False

    newtype Time = T (Int, Int)
        deriving(Show, Eq)

    showTime :: Time -> String 
    showTime (T (x,y)) = show x ++ "." ++ show y

    eqTime :: Time -> Time -> Bool 
    eqTime a b = a==b