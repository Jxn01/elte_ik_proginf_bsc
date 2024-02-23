# 2. gyakorlat

# 2.8 feladat:

floor(runif(100, min = 1, max = 7))

# floor: alsó egészrész
# runif: véletlenszerûen generál 100 db elemet (1;7) paraméterû egyenletes eloszlásból.

set.seed(1)
minta = floor(runif(100, min = 1, max = 7))

# a, Tapasztalati átlag:
# Mit várunk? 3.5

tap_atl = mean(minta)
tap_atl

# b, Eredmények táblázatosan:

Minta = data.frame(minta)
View(Minta)

# c, Szorozva (-3)-mal:
# Mit várunk, hogyan változik a mintaátlag? A mintaátlag is (-3)-szorosára változik.

minta2 = minta * (-3)
tap_atl2 = mean(minta2)
tap_atl2

Minta[,2] = minta2
View(Minta)


# 2.9 feladat:

ht = c(180, 163, 1500, 157, 165, 165, 174, 191, 172, 165, 1-68, 186)

plot(ht)

# a, Hibák javítása:
# Az 1500 nem reális, feltehetõen véletlenül lett több nulla => 150
# Az 1-68 sem tûnik jónak, feltehetõen véletlenül került bele a "-" jel => 168

ht[3] = 150
ht[11] = 168
ht

ht[ht == 1500] = 150
ht[ht == 1-68] = 168
ht

# b, Sorbarendezés:

novekvo_ht = sort(ht)
novekvo_ht
csokkeno_ht = sort(ht, decreasing = TRUE)
csokkeno_ht

