open import Agda.Builtin.Bool renaming (Bool to 𝔹)

nor : 𝔹 → 𝔹 → 𝔹
nor false false = true
nor _ _ = false
