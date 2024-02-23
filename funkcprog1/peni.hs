module Homework where
    import Data.List
    import Data.Maybe

    type Username = String
    type Password = String

    data Privilege = Simple | Admin
        deriving (Eq, Show)

    data Cookie = LoggedOut | LoggedIn Username Privilege
        deriving (Eq, Show)

    data Entry = Entry Password Privilege [Username]
        deriving (Eq, Show)

    type Database = [(Username, Entry)]

    richard, charlie, carol, david, kate :: (Username, Entry)
    richard = ("Richard", Entry "password1" Admin  ["Kate"])
    charlie = ("Charlie", Entry "password2" Simple ["Carol"])
    carol   = ("Carol",   Entry "password3" Simple ["David", "Charlie"])
    david   = ("David",   Entry "password4" Simple ["Carol"])
    kate    = ("Kate",    Entry "password5" Simple ["Richard"])

    testDB :: Database
    testDB = [ richard, charlie, carol, david, kate ]

    testDBWithoutCarol :: Database
    testDBWithoutCarol =
        [("Richard", Entry "password1" Admin  ["Kate"]),("Charlie", Entry "password2" Simple []),("David",   Entry "password4" Simple []), ("Kate",    Entry "password5" Simple ["Richard"])]

    password :: Entry -> Password
    password (Entry a _ _) = a

    privilege :: Entry -> Privilege
    privilege (Entry _ a _) =a

    friends :: Entry -> [Username]
    friends (Entry _ _ a) = a

    mkCookie :: Username -> Password -> Entry -> Cookie
    mkCookie a b (Entry c d e)
        |b==c=LoggedIn a d
        |otherwise=LoggedOut

    login :: Username -> Password -> Database -> Cookie
    login a b c
        |isJust (lookup a c) =mkCookie a b (fromJust(lookup a c))
        |otherwise=LoggedOut

    seged:: Username ->Entry -> Entry
    seged a b
        |a `elem` friends b = Entry (password b) (privilege b) (delete a (friends b))
        |otherwise=b

    updateEntry :: Username -> (Username, Entry) -> Maybe (Username, Entry)
    updateEntry a (b,c)
        |a == b = Nothing :: Maybe (Username, Entry)
        |otherwise=Just(b, seged a c)

    deleteUser :: Cookie -> Username -> Database -> Database
    deleteUser e@(LoggedIn a d) b c
        |d==Admin=mapMaybe (updateEntry b) c
        |otherwise=c
    deleteUser _ _ c=c


----------------------------------------------------------------
    getFriends :: Username -> Database -> [Username]
    getFriends a b
        |isNothing(lookup a b)=[]
        |otherwise = friends(fromJust(lookup a b))

    getFriendsRefl :: Username -> Database -> [Username]
    getFriendsRefl a b
        |isNothing (lookup a b) =[]
        |otherwise= a : getFriends a b

    fixPoint :: Eq a => (a -> a) -> a -> a
    fixPoint a b
        |b==a b=b
        |otherwise = fixPoint a (a b)

    sortUnique :: (Eq a, Ord a) => [a] -> [a]
    sortUnique a = concatMap (take 1) (group (sort a))

    getSocialNetwork :: Username -> Database -> [Username]
    getSocialNetwork [] _ = []
    getSocialNetwork a (x:xs)
        | not $ null (x:xs) = fixPoint sortUnique $ getFriendsRefl a (x:xs) ++ getSocialNetwork a [x]
        | otherwise = []