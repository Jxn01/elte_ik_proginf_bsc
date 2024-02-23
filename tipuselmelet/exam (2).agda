-- BEGIN FIX
open import Agda.Primitive

open import Agda.Builtin.Nat
  renaming (Nat to ℕ)
  public
open import Agda.Primitive
Type = Set
open import Agda.Builtin.Equality
  public
open import Agda.Builtin.Bool
  public
open import Agda.Builtin.Sigma
  public

infixr 4 _,_
infixr 2 _×_
infixr 1 _⊎_
infix 0 _↔_

if_then_else_ : ∀{i}{A : Set i}(t : Bool)(u v : A) → A
if true  then u else v = u
if false then u else v = v

-- Product types
record _×_ {i}{j}(A : Set i)(B : Set j) : Set (i ⊔ j) where
  constructor _,_
  field
    fst : A
    snd : B
open _×_ public

-- Sum types
data _⊎_ {i}{j}(A : Set i)(B : Set j) : Set (i ⊔ j) where
  inl : A → A ⊎ B
  inr : B → A ⊎ B

case : ∀ {i j k}{A : Set i}{B : Set j}{C : Set k}
         (t : A ⊎ B)(u : A → C)(v : B → C) → C
case (inl t) u v = u t
case (inr t) u v = v t

-- Empty type
data ⊥ : Set where

exfalso : ∀{i}{A : Set i} → ⊥ → A
exfalso ()

-- Unit type
record ⊤ : Set where
  constructor tt
open ⊤ public

_↔_ : ∀{i j} → Set i → Set j → Set (i ⊔ j)
A ↔ B = (A → B) × (B → A)

¬_ : ∀{i} → Set i → Set i
¬ A = A → ⊥

sym : ∀{i}{A : Set i}{x y : A} → x ≡ y → y ≡ x
sym refl = refl

trans : ∀{i}{A : Set i}{x y z : A} → x ≡ y → y ≡ z → x ≡ z
trans refl refl = refl

cong : ∀{i j}{A : Set i}{B : Set j}(f : A → B){x y : A} → x ≡ y → f x ≡ f y
cong f refl = refl

transp : ∀{i j}{A : Set i}(P : A → Set j){x y : A} → x ≡ y → P x → P y
transp P refl p = p

ass+ : (a b c : ℕ) → (a + b) + c ≡ a + (b + c)
ass+ zero    b c = refl
ass+ (suc a) b c = cong suc (ass+ a b c)

idr+ : (a : ℕ) → a + 0 ≡ a
idr+ zero    = refl
idr+ (suc a) = cong suc (idr+ a)

+suc : (a b : ℕ) → suc a + b ≡ a + suc b
+suc zero    b = refl
+suc (suc a) b = cong suc (+suc a b)

comm+ : (a b : ℕ) → a + b ≡ b + a
comm+ zero b = sym (idr+ b)
comm+ (suc a) b = trans (cong suc (comm+ a b)) (+suc b a)

_≤_ : ℕ → ℕ → Set
x ≤ y = Σ ℕ λ m → m + x ≡ y

pred : ℕ → ℕ
pred zero = zero
pred (suc n) = n
-- END FIX

-- BEGIN FIX
-- m1 and m2 should be such that m1 ℕ (λ x y → x + y) 1 ≠ m2 ℕ (λ x y → x + y) 1
m1 m2 : (A : Set) → (A → A → A) → A → A
-- END FIX
m1 A f a = f a a
m2 A f a = a
-- BEGIN FIX
test-m1-m2 : ¬ (m1 ℕ (λ x y → x + y) 1 ≡ m2 ℕ (λ x y → x + y) 1)
test-m1-m2 ()
-- END FIX

-- BEGIN FIX
iso : (A B C : Set) → (A × C) ⊎ B ↔ (A ⊎ B) × (B ⊎ C) 
-- END FIX
iso A B C = (λ x → {!   !} , {!   !}) , λ x → {!   !}

-- BEGIN FIX
logeq3 : (A : Set)(P : A → Set)(Q : A → Set) → (Σ A λ a → P a ⊎ Q a)  ↔ Σ A P ⊎ Σ A Q
-- END FIX
logeq3 A P Q = (λ x → {!   !}) , λ x → {!   !} , (inl {!   !})

-- BEGIN FIX
prop : {P : Set} → P ⊎ ¬ P → (¬ ( ¬ P) → P)
-- END FIX
prop = λ x x₁ → {!   !}

-- BEGIN FIX
tr' : (a b : ℕ) → ((P : ℕ → Set) → P a → P b) → a + b ≡ a + a
-- END FIX
tr' a b  = {!   !}

-- BEGIN FIX
fcomm : (x y : ℕ) → (f : ℕ → ℕ) → f y + f x ≡ f x + f y
-- END FIX
fcomm  =  λ x y f → comm+ (f y) (f x)

-- BEGIN FIX
plusEq : (x y : ℕ) → x + y ≡ x → y ≡ 0
-- END FIX
plusEq zero y e = e
plusEq (suc x) y e = plusEq x y (cong pred e)
-- BEGIN FIX
lemma6 : ¬ ((n : ℕ) → ¬ (3 ≡ n) → n ≡ 2)
-- END FIX
lemma6 f = 0≠2 (f 0 (λ ()))
  where
    0≠2 : ¬ (0 ≡ 2)
    0≠2 = λ ()

-- this might be hard
-- BEGIN FIX
kd : ¬ ((A B : Set)(R : A → B → Set) →
            (∀ (a : A) → Σ B (λ b → R a b)) →  Σ B (λ b → ((a' : A) → R a' b)))
-- END FIX
kd = {!!}

-- BEGIN FIX
_>both_and_ : ℕ → ℕ → ℕ → Bool
-- END FIX
_>both_and_ a b c = if a > b then if a > c then true else false else false 
  where
    _>_ : ℕ → ℕ → Bool
    a > b = if a < b then false else if a == b then false else true
-- BEGIN FIX
test->both-1 : 100 >both 1 and 2 ≡ true
test->both-1 = refl
test->both-2 : 100 >both 99 and 99 ≡ true
test->both-2 = refl
test->both-3 : 100 >both 99 and 100 ≡ false
test->both-3 = refl
test->both-4 : 100 >both 100 and 99 ≡ false
test->both-4 = refl
test->both-5 : 100 >both 200 and 200 ≡ false
test->both-5 = refl
test->both-6 : 100 >both 10 and 1000 ≡ false
test->both-6 = refl
-- END FIX

   