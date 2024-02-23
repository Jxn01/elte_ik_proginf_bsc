{-# options_ghc -Wincomplete-patterns #-}
module C05 where

-- Define a password entry function that requires a minimum length of eight
-- characters and ensures that the same string is entered twice in succession!
-- ps.: Since we only looked at `IO ()` functions last time, you don't need to
--      return the value.

passwordInput :: IO ()
passwordInput = do
    putStrLn "Enter the password!"
    pass1 <- getLine 
    if length pass1 < 8 then putStrLn "The password must be at least 8 characters long!" >> passwordInput else do 
    putStrLn "Enter the password again!"
    pass2 <- getLine
    if pass1 == pass2 then putStrLn "Correct" else
        putStrLn "Passwords mismatch!" >>
        passwordInput


-- Examples:
-- ∙ > 12345678
--   > 12345678
--   "Success!"
--
-- ∙ > sajt
--   "Too short!"
--   > valami
--   "Too short!"
--   > szendvics
--   > sandwich
--   "Not the same!"
--   > password
--   > password
--   "Success!"