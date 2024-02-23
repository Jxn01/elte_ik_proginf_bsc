module gyak where

open import Agda.Builtin.Nat
  renaming (Nat to ℕ)
  public

open import Agda.Builtin.Bool
  public

{-
data Bool : Set where
  true  : Bool
  false : Bool
-}

data Answer : Set where
  yes   : Answer
  no    : Answer
  maybe : Answer

data Quarter : Set where
  east  : Quarter
  west  : Quarter
  north : Quarter
  south : Quarter

data ⊥ : Set where

exfalso : ∀{i}{A : Set i} → ⊥ → A
exfalso ()

data ⊤ : Set where
  tt : ⊤

{-
 data ℕ : Set where
  zero : ℕ
  suc  : ℕ → ℕ
-}

data ℤ : Set where
  zeroℤ : ℤ
  sucℤ  : ℤ → ℤ
  predℤ : ℤ → ℤ

data BinTree : Set where
  leaf : BinTree
  node : BinTree → BinTree → BinTree

bt1 : BinTree
bt1 = leaf

bt2 : BinTree
bt2 = node leaf leaf

bt3 : BinTree
bt3 = node (node leaf leaf) (node leaf leaf)

bt4 : BinTree
bt4 = node (node (node leaf leaf) leaf) leaf

data BinTreeℕ : Set where
  leaf : ℕ → BinTreeℕ
  node : BinTreeℕ → BinTreeℕ → BinTreeℕ

bt5 : BinTreeℕ
bt5 = node (leaf (suc zero)) (leaf zero)

data BinTreeℕ' : Set where
  leaf : BinTreeℕ'
  node : ℕ → BinTreeℕ' → BinTreeℕ' → BinTreeℕ'

bt6 : BinTreeℕ'
bt6 = node (suc (suc zero)) leaf leaf

data BinTreeBoolℕ : Set where
  leaf : ℕ → BinTreeBoolℕ
  node : Bool → BinTreeBoolℕ → BinTreeBoolℕ → BinTreeBoolℕ

bt7 : BinTreeBoolℕ
bt7 = node true (leaf (suc zero)) (leaf zero)

data List (A : Set) : Set where
  [] : List A
  _∷_ : A → List A → List A 

list1 : List ℕ
list1 = zero ∷ [] 

data _×_ (A B : Set) : Set where
  _,_ : A → B → A × B

data _⊎_ (A B : Set) : Set where
  inl : A → A ⊎ B
  inr : B → A ⊎ B

infixr 4 _,_
infixr 2 _×_
infixr 1 _⊎_

⊤×⊤ : ⊤ × ⊤
⊤×⊤ = tt , tt

⊤⊎⊤ : ⊤ ⊎ ⊤
⊤⊎⊤ = inl tt

⊥⊎⊤⊎⊤×⊥⊎⊥⊎⊤ : ⊥ ⊎ ⊤ ⊎ ⊤ × (⊥ ⊎ ⊥) ⊎ ⊤
⊥⊎⊤⊎⊤×⊥⊎⊥⊎⊤ = inr (inl tt)
