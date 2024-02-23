{-# OPTIONS --guardedness #-}

open import Agda.Builtin.Nat renaming (Nat to ℕ)

record Stream {i} (A : Set i) : Set i where
  coinductive                     --important!
  field
    head : A
    tail : Stream A
open Stream

_!!_ : ∀ {i} → {A : Set i} → Stream A → ℕ → A
s !! 0 = head s
s !! suc n = tail s !! n

