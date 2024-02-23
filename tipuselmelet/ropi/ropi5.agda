open import lib

comm⊎ : {A B : Set} → A ⊎ B ↔ B ⊎ A
comm⊎ = ((λ x → case x (λ a → inr a) λ b → inl b) , (λ x → case x ({!!}) {!!}))
