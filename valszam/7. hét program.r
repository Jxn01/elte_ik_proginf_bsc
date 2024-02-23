# 7. hét feladatok

# 7.5 feladat:

kocka = c(1,2,3,4,5,6)

p = c()
for (i in 1:10000){
  p = c(p, mean(sample(kocka, 1000, replace = TRUE)))
}

sum(p < 3.49)/10000

# 7.6 feladat:

st_összegek = c()
m = 1/2
s = sqrt(1/12)

for (i in 1:1000){
  összeg = sum(runif(12))
  st_összeg = (összeg - 12*m)/(sqrt(12)*s)
  st_összegek = c(st_összegek, st_összeg)
}

# ábrázolás:

par(mfrow = c(1, 2))

hist(st_összegek, main = "Standardizált egyenletes eloszlású összegek")
hist(rnorm(1000), main = "Standard normális eloszlású változók")

# szebb ábrázolás:

library(ggplot2)

df = data.frame(Értékek = c(st_összegek, rnorm(1000)),
                Adathalmaz = rep(c("St.összegek", "St.normális"), each = 1000))

ggplot(df, aes(x = Értékek, fill = Adathalmaz, col = Adathalmaz)) + 
  geom_histogram(position = "identity", alpha = 0.5) +
  ggtitle("Hisztogramok") + 
  ylab("Gyakoriságok") +
  scale_fill_manual(values = c("blue", "red")) +
  scale_color_manual(values = c("darkblue", "darkred"))
