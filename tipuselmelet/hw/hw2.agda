open import Agda.Builtin.Nat renaming (Nat to ℕ)
open import Agda.Builtin.Bool renaming (Bool to 𝔹)

-------------------------------
-- elméleti/meggondolós kérdések
-------------------------------
-- ilyen típusú kérdések lesznek, de nem feltétlen pont ezek

-- milyen paraméter(eke)t vár és mit ad vissza egy (ℕ → ℕ) → ℕ típusú függvény?

-- ha f típusa ℕ → ℕ → ℕ, értelmes-e (f 5)? ha igen, mi a típusa?

-- értelmes-e add 3 (add 9 5)? ha nem, miért nem? ha igen, mi az értéke?

-- mit jelentenek az alábbi definícióban az aláhúzásjelek?
if_then_else : {A : Set} → 𝔹 → A → A → A
if true  then x else y = x
if false then x else y = y

-- hogyan kell a * operátort normál függvényként leírni? (a haskelles (*) megfelelőjére gondolok)

-- az alábbi definíció hibás; hogyan javítható?
{-
id : A → A
id x = x
-}

-- adott n-re hány ténylegesen különböző, n db 𝔹-t váró és egy 𝔹-t visszaadó függvény létezik?

------------------------------
-- kódolós feladatok
------------------------------

-- írj függvényt, ami a (3 +_)-ra (ez másképp leírva az add3) és 5-re 14-et ad vissza, (4 +_)-ra és 6-ra pedig 18-at, és nem használ műveleti operátorokat:

add3 : ℕ → ℕ
add3 n = n + 3

add4 : ℕ → ℕ
add4 n = n + 4 

nTimes : (ℕ → ℕ) → ℕ → ℕ → ℕ
nTimes f zero x = x 
nTimes f (suc n) x = nTimes f n (f x) 

f1 : (ℕ → ℕ) → ℕ → ℕ
f1 f n = nTimes f 3 n
-- (hint: itt is igaz, hogy az egyenlőségjel elé is írhatsz, csak akkor ne felejts el C-c C-l-ezni utána)

-- írd meg a nand függvényt boolokra (nand b1 b2 pontosan akkor igaz, amikor and b1 b2 hamis):
nand : 𝔹 → 𝔹 → 𝔹
nand false false = true
nand = \ a b -> false

-- írd meg az and függvényt if_then_else használatával:
and : 𝔹 → 𝔹 → 𝔹
and a b = if a then b else false

-- add meg függvényben a választ arra, hogy adott n-re hány ténylegesen különböző, n db 𝔹-t váró és egy 𝔹-t visszaadó függvény létezik:
howmanyn𝔹→𝔹 : ℕ → ℕ        --ez csak demó, hogy lehet ilyen is a függvénynév;)
howmanyn𝔹→𝔹 n = {!!}

-- írj egy faktoriálist kiszámító függvényt:

factAux : ℕ → ℕ → ℕ
factAux zero x = x
factAux (suc zero) x = x
factAux (suc n) x = factAux n (x * n)

fact : ℕ → ℕ
fact 0 = 1
fact n = factAux n n


