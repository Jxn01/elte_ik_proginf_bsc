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
