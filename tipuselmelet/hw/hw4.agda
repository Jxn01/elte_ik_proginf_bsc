------------------------
-- technikai
------------------------
{-
az _⊎_ konstruktorait és a _×_ mezőneveit a zh-ban
ugy kell majd hivni, mint ahogy a lib.agdaban
(mert onnan lehet majd importalni a feladatokhoz)
-}
open import lib

------------------------
-- elmeleti kerdesek
------------------------

-- mi a data? mi a record?
-- hogyan kell konstruktort generalni egy rekordhoz?
-- mi az ⊎, × operatorok jelolese?
-- mi a ⊤, mi a ⊥?
-- melyik operator jeloli a (beepitett) egyenloseget Agdaban?
-- melyik tipus felel meg az alabbi logikai allitasoknak:
   -- A ∧ B 
   -- A ∨ B
   -- A ⇒ B
   -- A ⇔ B
   -- ¬ A
   -- azonosan igaz
   -- azonosan hamis

------------------------
-- gyakorlati feladatok
------------------------

-- definialj egy structot, amiben egy id nevu ℕ es egy gender nevu 𝔹 mezo van
-- definiald ennek a tipusnak egy elemet

record Person : Set where
  field
    id : ℕ
    gender : Bool



-- definiald az egyelemu tipust

record T : Set where
  constructor tt
  
-- definiald az ures tipust
data Bot : Set where

-- bizonyitsd be, hogy suc 2 egyenlo 3-mal

issuc2eq3 : (suc 2) ≡ 3
issuc2eq3 = refl

-- bizonyitsd be, hogy 2 * 2 nem egyenlo 5-tel

--2*2=5 : ((2 * 2) ≡ 5)
--2*2=5 () 

id : ⊥ → ⊥
id x = x

-- adj meg egy-egy elemet a lenti tipusokhoz:
e1 : (⊤ ⊎ ⊤) ⊎ (⊤ × ⊤)
e1 = inr (tt , tt)
e2 : ((⊥ ⊎ ⊥) × (⊤ ⊎ ⊥)) × ⊤
e2 = (inl {!!} , inl tt) , tt
e3 : (⊥ ⊎ (⊤ × ⊥)) ⊎ ⊤
e3 = inr tt
e4 : (⊤ × ⊤) ⊎ (⊥ ⊎ (⊤ ⊎ ⊤))
e4 = inl (tt , tt)
e5 : (⊥ ⊎ ⊥) ⊎ (⊤ × ⊥)
e5 = {!!}
-- hany eleme van ezeknek a tipusoknak?

