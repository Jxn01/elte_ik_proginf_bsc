open import Agda.Builtin.Nat renaming (Nat to ℕ)

data Vec {i} (A : Set i) : ℕ → Set i where
  [] : Vec A zero
  _∷_ : {n : ℕ} → A → Vec A n → Vec A (suc n)

sum : {n : ℕ} → Vec ℕ n → ℕ
sum [] = 0
sum (x ∷ xs) = x + sum xs