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