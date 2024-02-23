# 2. gyakorlat

# 2.8 feladat:

floor(runif(100, min = 1, max = 7))

# floor: als� eg�szr�sz
# runif: v�letlenszer�en gener�l 100 db elemet (1;7) param�ter� egyenletes eloszl�sb�l.

set.seed(1)
minta = floor(runif(100, min = 1, max = 7))

# a, Tapasztalati �tlag:
# Mit v�runk? 3.5

tap_atl = mean(minta)
tap_atl

# b, Eredm�nyek t�bl�zatosan:

Minta = data.frame(minta)
View(Minta)

# c, Szorozva (-3)-mal:
# Mit v�runk, hogyan v�ltozik a minta�tlag? A minta�tlag is (-3)-szoros�ra v�ltozik.

minta2 = minta * (-3)
tap_atl2 = mean(minta2)
tap_atl2

Minta[,2] = minta2
View(Minta)


# 2.9 feladat:

ht = c(180, 163, 1500, 157, 165, 165, 174, 191, 172, 165, 1-68, 186)

plot(ht)

# a, Hib�k jav�t�sa:
# Az 1500 nem re�lis, feltehet�en v�letlen�l lett t�bb nulla => 150
# Az 1-68 sem t�nik j�nak, feltehet�en v�letlen�l ker�lt bele a "-" jel => 168

ht[3] = 150
ht[11] = 168
ht

ht[ht == 1500] = 150
ht[ht == 1-68] = 168
ht

# b, Sorbarendez�s:

novekvo_ht = sort(ht)
novekvo_ht
csokkeno_ht = sort(ht, decreasing = TRUE)
csokkeno_ht

