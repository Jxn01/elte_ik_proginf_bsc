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
-- ugy add meg, hogy q1 ℕ 0 es q2 ℕ 0 ne legyen definicio szerint egyenloek
q1 q2 : (A : Set) → A → A ⊎ ⊤
-- END FIX

q1 A = λ x → inl x 
q2 A = λ x → inr tt
-- BEGIN FIX
test-q1-q2 : ¬ (q1 ℕ 0 ≡ q2 ℕ 0)
test-q1-q2 ()
-- END FIX

-- BEGIN FIX
iso'' : (X Y Z : Set) → (X → (Z × Y)) ↔ ((X → Y ⊎ ⊥) × (X ⊎ ⊥ → Z))
-- END FIX
iso'' X Y Z = (λ x → (λ x₁ → inl (snd (x x₁))) , λ x₁ → case x₁ (λ z → fst (x z)) λ ()) , λ x x₁ → snd x (inl x₁) , case  (fst x x₁) (λ x₂ → x₂) λ ()

-- BEGIN FIX
uncurry'' : (A C : Set)(B : A → Set) → ((x : A) → B x → C) ↔ (Σ A B → C)
-- END FIX
uncurry'' A C B = (λ x x₁ → x (fst x₁) (snd x₁)) , λ x x₁ x₂ → x (x₁ , x₂)

-- BEGIN FIX
prop : {P : Set} → P ⊎ ¬ P → (¬ ( ¬ P) → P)
-- END FIX
prop punp nnp = case punp (λ z → z) λ x → {!   !}

-- BEGIN FIX
lemma3 : (n : ℕ) → n ≡ 3 → ¬ (n ≡ 4)
-- END FIX
lemma3 = {!   !}
-- BEGIN FIX
eq : (a b c : ℕ) → (a + 1) + (b + c) ≡ (0 + a) + ((1 + c) + b)
-- END FIX
eq a b c = {!   !}

-- BEGIN FIX
lemma7 : ¬ (Σ ℕ λ n → n + 2 ≡ n + 3)
-- END FIX
lemma7 f = {!   !}

-- BEGIN FIX
mm : ¬ ((n : ℕ) → Σ ℕ λ m → suc (suc m) ≡ n)
-- END FIX
mm f = {!   !}

-- BEGIN FIX
fel : ¬ ((R : ℕ → ℕ → Set) → (R zero zero → R (suc zero) (suc zero)))
-- END FIX

fel f = 

-- BEGIN FIX
is<1000 : ℕ → Bool
-- END FIX
is<1000 n = n < 1000
-- BEGIN FIX
test-is<1000-1 : is<1000 1000 ≡ false
test-is<1000-1 = refl
test-is<1000-2 : is<1000 999 ≡ true
test-is<1000-2 = refl
test-is<1000-3 : is<1000 1001 ≡ false
test-is<1000-3 = refl
test-is<1000-4 : is<1000 0 ≡ true
test-is<1000-4 = refl
-- END FIX

  