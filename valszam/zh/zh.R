#1. feladat
#Olvassuk be R-ben a csatolt adathalmazt, amely jövedelmi adatokat tartalmaz (Letöltés után R-ben: File > Open File > Jövedelem.RData; ekkor az adatok egy "xd" nevű dataframe-ben jelennek meg). 

#a) Számoljuk ki a jövedelem oszlopra vonatkozó alapstatisztikákat (átlag, korrigált tapasztalati szórás, medián, módusz) és írjunk szöveges magyarázatot is arra, hogy miért van nagy eltérés a medián és az átlag között.

atlag = mean(xd$jövedelem)
szoras = sd(xd$jövedelem)
median = median(xd$jövedelem)
modusz = names(which.max(table(xd$jövedelem)))

print(paste("Átlag: ", atlag))
print(paste("Szórás: ", szoras))
print(paste("Medián: ", median))
print(paste("Modusz: ", modusz))
print("A medián és az átlag közötti eltérés azért van, mert a jövedelem oszlopban van néhány nagyon magas érték, amik nagyban befolyásolják az átlagot, de a mediánt nem.")

#b) Ábrázoljuk hisztogramon a fenti adatokat, és jelöljük be rajta az átlagot és a mediánt!

hist(xd$jövedelem, main = "Jövedelem", xlab = "Jövedelem", ylab = "Gyakoriság", col = "lightblue")
abline(v = atlag, col = "red")
abline(v = median, col = "green")

#c) Ábrázoljuk csak a férfiakra és csak a nőkre vonatkozó jövedelmeket boxplot ábrán, és vizsgáljuk meg van e köztük különbség!

boxplot(xd$jövedelem ~ xd$nem, main = "Jövedelem", xlab = "Nem", ylab = "Jövedelem", col = "lightblue")
print("A férfiak jövedelme nagyobb, mint a nőké.")

#7. feladat
#Legyen X_1,X_2,X_3,X_4,X_5,X_6 független azonos N(mu,2^2) eloszlású minta.
#A megfigyelt értékek a következők: 4,31;  4,02;  -0,27;  5,45;  0,68;  4,09.
#Adjunk 95 %-os megbízhatóságú konfidenciaintervallumot mu-re!



#9. feladat
#Milyen próbát végeznénk? (Hány mintás, párosított vagy párosítatlan, hány oldali, u/t/Welch/F)
#a) Jancsi az elmúlt hónapban minden nap gombát szedett. Kíváncsi rá, hogy az általa szedett egynapi gombamennyiség tömege több-e 2 kg-nál?
#Válasz: Egyoldali, egymintás, t-próba, mert csak azt vizsgáljuk, hogy a gombamennyiség tömege több-e 2 kg-nál.


#b) Két csoport 100 pontos zh eredménye a következők:
#1. csoport: átlagosan 70 pont
#2. csoport: átlagosan 72 pont
#Állíthatjuk-e, hogy a két csoport eredménye között szignifikáns különbség van?
#Válasz: Párosítatlan, kétoldali, t-próba, mert azt vizsgáljuk, hogy a két csoport eredménye között van-e szignifikáns különbség.
# A két csoport között nincs szignifikáns különbség, mert a p-érték nagyobb mint 0.05.

print("Párosítatlan, kétoldali, t-próba")
print("A két csoport között nincs szignifikáns különbség, mert a p-érték nagyobb mint 0.05.")

#10. feladat
#Nézzük az első feladat adatait. Teszteljük, hogy vajon a férfiak és nők közötti jövedelem között szignifikáns különbség van-e. Mi jött ki p-értéknek, hogyan döntenénk ez alapján?

t.test(xd$jövedelem ~ xd$nem)
print("p-érték = 0.0002882, ami kisebb mint 0.05, tehát a két csoport között szignifikáns különbség van.")

#12. feladat
#Tekintsük az R "mtcars" beépített adatbázisát! Modellezzük lineáris regresszióval, hogy hány mérföldet tud megtenni egy tankkal egy autó, az autó súlyával. Mik lettek az együtthatók? Milyen becslést kapunk egy 3500 lbs súlyú autóra? Mennyi az R^2 értéke, mit jelent ez?
#+ 2 pontért ábrázoljuk a megfigyeléseket a regressziós egyenessel együtt!

print(mtcars)

reg = lm(mpg ~ wt, data = mtcars)
print(summary(reg))

print("Az együtthatók:")
print(reg$coefficients)

print("Egy 3500 lbs súlyú autóra a becslés:")
print(predict(reg, data.frame(wt = 3.500)))

print("Az R^2 értéke:")
print(summary(reg)$r.squared)
print("Az R^2 értéke 0.7528328, ami azt jelenti, hogy az adatok 75.28%-át magyarázza az egyenes.")

plot(mtcars$wt, mtcars$mpg, main = "Autók", xlab = "Súly", ylab = "Mérföld")
abline(reg, col = "red")
