open import Agda.Builtin.Sigma
open import lib

module _
  --these are like postulates
  (Person    : Set)
  (Ann       : Person)
  (Kate      : Person)
  (Peter     : Person)
  (_childOf_ : Person → Person → Set)
  (_sameAs_  : Person → Person → Set) -- ez most itt az emberek egyenlosege
  where
  
  _hasGrandchild : Person → Set
  _hasGrandchild p = Σ Person λ c → _childOf_ p c × Σ Person λ gc → _childOf_ c gc


