# Egy szabályos dobókockát 5ször dobunk fel, mi a dobott számok maximumának az eloszlása?

# A dobások száma
n <- 5

# A dobókocka számai
k <- 1:6

# A dobások száma
m <- 100

# A dobások
dobasok <- matrix(sample(k, n * m, replace = TRUE), nrow = n)

# A dobások maximuma
maxdobasok <- apply(dobasok, 2, max)

# A dobások maximumának eloszlása
hist(maxdobasok, breaks = seq(0.5, 6.5, 1), freq = FALSE, ylim = c(0, 1), main = "A dobások maximumának eloszlása", xlab = "A dobások maximuma")

# 100 érme közül az egyik hamis (ennek mindkét oldalán fej található). Egy érmét véletlenszer˝uen kiválasztunk
# és azzal 10-szer dobunk, Mi a dobott fejek számának eloszlása? Modellezze ezt a kísérletet R-ben és ismételje meg 100-szor.
# Táblázatolja az eredményeket és hasonlítsa össze azokat az elméleti értékekkel


# 3.4 feladat
sim = 100
nem = c('F', 'L')

fiuk = c()

for(i in 1:sim){
  gyerekek = sample(nem, 6, replace = TRUE)
  fiuk = c(fiuk, sum(fiuk == 'F'))
}

#tapasztalat:
fiuk_szama = c(0:6)

tapasztalati_gyakorisagok = rep(0,7)
for(i in 1:7){
  tapasztalati_gyakorisagok[i] = sum(fiuk == fiuk_szama[i])
}

tapasztalati_valoszinegek = tapasztalati_gyakorisagok / sim

#elmeleti:

elmeleti_valoszinegek = choose(6, fiuk_szama) * 0.5^6
elmeleti_gyakorisagok = elmeleti_valoszinegek * sim

#osszehasonlitas:

osszehasonlitas = data.frame(fiuk_szama, tapasztalati_gyakorisagok, tapasztalati_valoszinegek, elmeleti_gyarikisagok, elmeleti_valoszinegek)
View(osszehasonlitas)








