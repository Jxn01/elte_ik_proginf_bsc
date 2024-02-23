#10. feladat
#Nézzük az első feladat adatait. Teszteljük, hogy vajon a férfiak és nők közötti jövedelem között szignifikáns különbség van-e. Mi jött ki p-értéknek, hogyan döntenénk ez alapján?

t.test(xd$jövedelem ~ xd$nem)
print("p-érték = 0.0002882, ami kisebb mint 0.05, tehát a két csoport között szignifikáns különbség van.")