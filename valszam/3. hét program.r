# 3. gyakorlat

# 3.1 feladat:

sim = 10000
maximum = c()

for (i in 1:sim){
  minta = sample(c(1:6), 5, replace = TRUE)
  maximum = c(maximum, max(minta))
}

# tapasztalati eredmények:
legnagyobb_szam = c(1:6)

tapasztalati_gyakorisagok = rep(0,6)
for (i in 1:6){
  tapasztalati_gyakorisagok[i] = sum(maximum == legnagyobb_szam[i])
}

tapasztalati_valsegek = tapasztalati_gyakorisagok/sim

# elméleti eredmények:
elmeleti_valsegek = round((c(1:6)/6)^5 - (c(0:5)/6)^5, 5)
elmeleti_gyakorisagok = ((elmeleti_valsegek)*sim)

# táblázat:
df = data.frame(legnagyobb_szam, tapasztalati_gyakorisagok, elmeleti_gyakorisagok, tapasztalati_valsegek, elmeleti_valsegek)
View(df)


# 3.3 feladat:

jo = c('F', 'I')
rossz = c('F', 'F')
ermek = matrix(c(rossz, rep(jo, 99)), nrow = 100, byrow = TRUE)
ermek

kivalasztott = sample(1:100, 1)
sim = 100
fej = c()

for (i in 1:sim){
  dobasok = sample(ermek[kivalasztott,], 10, replace = TRUE)
  fej = c(fej, sum(dobasok == "F"))
}

# tapasztalati eredmények:
fejek_szama = c(1:10)

tap_gyak = rep(0,10)
for (i in 1:10){
  tap_gyak[i] = sum(fej == fejek_szama[i])
}

tap_val = tap_gyak/sim

# elméleti eredmények:
f = function(k){
  if(k != 10){
    value = (99/100)*(1/2)^k*(1/2)^(10-k)*choose(10,k)
    return (value)
  } else {
    value = (99/100)*(1/2)^k*(1/2)^(10-k)*choose(10,k) + 1/100
    return (value)
  }
}

elm_val = rep(0, 10)
for (i in 1:10){
  elm_val[i] = f(i)
}
elm_gyak = ((elm_val)*sim)

# táblázat:
df2 = data.frame(fejek_szama, tap_gyak, elm_gyak, tap_val, elm_val)
View(df2)


# 3.4 feladat:

sim = 100
nem = c('F', 'L')

fiuk = c()

for (i in 1:sim){
  gyerekek = sample(nem, 6, replace = TRUE)
  fiuk = c(fiuk, sum(gyerekek == 'F'))
}

# tapasztalati eredmények:
fiuk_szama = c(0:6)

tapasztalati_gyakorisagok = rep(0,7)
for (i in 1:7){
  tapasztalati_gyakorisagok[i] = sum(fiuk == fiuk_szama[i])
}

tapasztalati_valsegek = tapasztalati_gyakorisagok/sim

# elméleti eredmények:
elmeleti_valsegek = round(choose(6,fiuk_szama)*(1/2)^6, 3)
elmeleti_gyakorisagok = ((elmeleti_valsegek)*sim)

# táblázat:
df3 = data.frame(fiuk_szama, tapasztalati_gyakorisagok, elmeleti_gyakorisagok, tapasztalati_valsegek, elmeleti_valsegek)
View(df3)


