# 7. h�t feladatok

# 7.5 feladat:

kocka = c(1,2,3,4,5,6)

p = c()
for (i in 1:10000){
  p = c(p, mean(sample(kocka, 1000, replace = TRUE)))
}

sum(p < 3.49)/10000

# 7.6 feladat:

st_�sszegek = c()
m = 1/2
s = sqrt(1/12)

for (i in 1:1000){
  �sszeg = sum(runif(12))
  st_�sszeg = (�sszeg - 12*m)/(sqrt(12)*s)
  st_�sszegek = c(st_�sszegek, st_�sszeg)
}

# �br�zol�s:

par(mfrow = c(1, 2))

hist(st_�sszegek, main = "Standardiz�lt egyenletes eloszl�s� �sszegek")
hist(rnorm(1000), main = "Standard norm�lis eloszl�s� v�ltoz�k")

# szebb �br�zol�s:

library(ggplot2)

df = data.frame(�rt�kek = c(st_�sszegek, rnorm(1000)),
                Adathalmaz = rep(c("St.�sszegek", "St.norm�lis"), each = 1000))

ggplot(df, aes(x = �rt�kek, fill = Adathalmaz, col = Adathalmaz)) + 
  geom_histogram(position = "identity", alpha = 0.5) +
  ggtitle("Hisztogramok") + 
  ylab("Gyakoris�gok") +
  scale_fill_manual(values = c("blue", "red")) +
  scale_color_manual(values = c("darkblue", "darkred"))
