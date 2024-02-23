{-# options_ghc -Wincomplete-patterns #-}
module C04 where

-- Define composition for functions that do not always produce a value,
-- can sometimes fail and return nothing.
-- In case of a failure, it should be propagated to the final result.

composeMaybe :: (b -> Maybe c) -> (a -> Maybe b) -> a -> Maybe c
composeMaybe f g a = do
    h <- g a
    h' <- f h
    return h'

-- Examples:
-- ∙ composeMaybe (Just . (*5)) (Just . (+3)) 5 == Just 40
-- ∙ composeMaybe (Just . (+2)) (Just . (*4)) 6 == Just 26
-- ∙ composeMaybe (Just . (+2)) (const Nothing) 7 == Nothing
-- ∙ composeMaybe (\b -> if b then Just "ok" else Nothing) (Just . not) True == Nothing
-- ∙ composeMaybe (\b -> if b then Just "ok" else Nothing) (Just . not) False == Just "ok"
