open import Agda.Builtin.Nat renaming (Nat to ℕ)
open import Agda.Builtin.MOnad

--TODO: I don't know whether it has too much of the material of the lecture

-- TODO: Type!
-- TODO: internal representation of ℕ (is it optimised?)

--data (like Haskell)

data Colour : Set where
  red green blue : Colour

colToℕ : Colour → ℕ
colToℕ red = 1
colToℕ green = 2
colToℕ blue = 3

-- NOTE: we were here --------------------------------------------------------------------------

{-
https://agda.readthedocs.io/en/v2.5.2/language/built-ins.html
The Agda type checker knows about, and has special treatment for, a number of different concepts. The most prominent is natural numbers, which has a special representation as Haskell integers and support for fast arithmetic. The surface syntax of these concepts are not fixed, however, so in order to use the special treatment of natural numbers (say) you define an appropriate data type and then bind that type to the natural number concept using a BUILTIN pragma.

Binding the built-in natural numbers as above has the following effects:

    The use of natural number literals is enabled. By default the type of a natural number literal will be Nat, but it can be overloaded to include other types as well.
    Closed natural numbers are represented as Haskell integers at compile-time.
    The compiler backends compile natural numbers to the appropriate number type in the target language.
    Enabled binding the built-in natural number functions described below.
-}

{-
Type was only an alias for Set in lib.agda; it does not mean anything more
-}

data Maybe (A : Set) : Set where
  nothing : Maybe A
  just : A → Maybe A

nt j3 j5 : Maybe ℕ
nt = nothing
j3 = just 3
j5 = just 5

maybemaybea : {A : Set} → Maybe (Maybe A) → A
maybemaybea mma = do
  ma ← mma
  a ← ma
  a

-- this is an often-used operator
data _⊎_ (A B : Set) : Set where
  inj₁ : A → A ⊎ B
  inj₂ : B → A ⊎ B

optone opttwo : ℕ ⊎ Colour --abbreviation
optone = inj₁ 0
opttwo = inj₂ red

retnum : ℕ ⊎ Colour → Maybe Colour
retnum x = {!!} --try C-c C-c!

--record (rather like C-structs)

record Person : Set where
  field
    id : ℕ
    phone : ℕ

--open Person -- no need for 'Person.' before field names

bill : Person
Person.id bill = 12
Person.phone bill = 301234567

--this is like the Cartesius product
--it's not in Haskell
record _×_ (A B : Set) : Set where
  constructor _,_
  field
    fst : A
    snd : B

open _×_

point : ℕ × ℕ
point = {!!} --try C-c C-c here!

{-
-- that's what it generates
_,_ : {A B : Set} → A → B → A × B
proj₁ (a , b) = a
proj₂ (a , b) = b
-}

point2 : ℕ × ℕ
point2 = 1 , 2

--the unit type: an empty record
record ⊤ : Set where

tt : ⊤
tt = record {}

--the empty type: an empty data (no constructors ⇒ cannot construct an element)
data ⊥ : Set where
