module Lesson11 where

    data Privilege = Unprivileged | Admin
        deriving(Show, Eq, Enum)

    data Cookie = LoggedIn String Privilege | LoggedOut
        deriving(Show, Eq)

    type Database =  [(String, String, Privilege)]

    db :: Database
    db = [("dumbledore","abracadabra",Unprivileged), ("root", "secret", Admin), ("bela", "korte", Unprivileged)]

    register :: String -> String -> Cookie -> Database -> Database
    register nev jelszo (LoggedIn _ Admin) db = (nev,jelszo,Unprivileged):db
    register _ _ _ db = db

    getUser :: String -> Database -> Maybe (String, Privilege)
    getUser _ [] = Nothing
    getUser nev ((a,b,c):xs)
        | a == nev = Just (b,c)
        | otherwise = getUser nev xs

    login :: String -> String -> Database -> Cookie
    login _ _ [] = LoggedOut
    login nev jelszo ((a,b,c):xs)
        | a == nev && b == jelszo = LoggedIn a c
        | otherwise = login nev jelszo xs

    passwd :: String -> Cookie -> Database -> Database
    passwd _ _ [] = []
    passwd _ LoggedOut db = db
    passwd jelszo (LoggedIn b role2) ((x,y,z):xs)
        | b == x = (x,jelszo,z) : xs
        | otherwise = (x, y, z) : passwd jelszo (LoggedIn b role2) xs

    users :: Database -> [String]
    users = map (\(x,_,_) -> x)

    delete :: String -> Cookie -> Database -> Database
    delete user (LoggedIn _ Admin) db = filter (\(userr,_,_) -> user == userr) db
    delete _ _ db = db



    