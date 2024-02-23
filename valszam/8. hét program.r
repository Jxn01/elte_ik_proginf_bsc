# 8. feladatsor

# 8.1 feladat:

kocka = c(1,2,3,4,5,6)
minta = sample(kocka, 1000, replace = TRUE)
tablazat = table(minta)

barplot(tablazat, main = "1000 kockadobás eredménye", xlab = "számok", ylab = "gyakoriság",
     col = "lightblue1")

# a, tapasztalati közép:
mean(minta)

# b, tapasztalati módusz:
names(tablazat[tablazat == max(tablazat)])

# c, tapasztalati medián:
median(minta)

# d, elsõ három tapasztalati momentum
mean(minta)
mean(minta^2)
mean(minta^3)

# e, tapasztalati szórás
sqrt(mean((minta - mean(minta))^2))

# korrigált tapasztalati szórás
sd(minta)

# f, terjedelem
max(minta) - min(minta)
range(minta)


# 8.2 feladat:
m1 = rexp(30, 3)
m2 = rexp(100, 3)
m3 = rexp(500, 3)
m4 = rexp(1000, 3)

par(mfrow = c(2,2))
plot(ecdf(m1))
curve(pexp(x, 3), col = "green", add = TRUE, lwd = 2)
plot(ecdf(m2))
curve(pexp(x, 3), col = "green", add = TRUE, lwd = 2)
plot(ecdf(m3))
curve(pexp(x, 3), col = "green", add = TRUE, lwd = 2)
plot(ecdf(m4))
curve(pexp(x, 3), col = "green", add = TRUE, lwd = 2)
par(mfrow = c(1,1))

# 8.3 feladat:

df = data.frame(state.area, row.names = state.name)

# a, hisztogram:
hist(df$state.area, breaks = 10)
abline(v = mean(df$state.area), col = "red", lwd = 2)
abline(v = median(df$state.area), col = "blue", lwd = 2)
legend("topright", legend = c("átlag", "medián"), col = c("red", "blue"),
       lty = c(1,1), lwd = c(2,2))

# b, tapasztalati közép:
mean(df$state.area)

# c, tapasztalati medián:
median(df$state.area)

# d, korrigált tapasztalati szórás:
sd(df$state.area)

# e, Tapasztalati kvartilisek:
quantile(df$state.area, c(0.25, 0.5, 0.75))

# alapstatisztikák:
summary(df$state.area)

# f, Interkvartilis-terjedelem:
IQR(df$state.area)

# g, Tapasztalati eloszlásfüggvény:
plot(ecdf(df$state.area))
abline(v = median(df$state.area), col = "blue", lwd = 2)
abline(h = 0.5, col = "blue", lwd = 2)

# h, Boxplot:
boxplot(df$state.area, horizontal = TRUE, col = "gold", pch = 16)


# 8.4 feladat:

hw = read.csv("C:/Users/User/Desktop/Egyetem/Valstat Inf/Height_and_weight.csv",
              header = TRUE, sep = ",", col.names = c("Height", "Weight", "Sex"))

# Alapstatisztikák:
atln = mean(hw[hw$Sex == "Female","Height"])
atlf = mean(hw[hw$Sex == "Male","Height"])
atln2 = mean(hw[hw$Sex == "Female","Weight"])
atlf2 = mean(hw[hw$Sex == "Male","Weight"])
medn = median(hw[hw$Sex == "Female", "Height"])
medf = median(hw[hw$Sex == "Male", "Height"])
medn2 = median(hw[hw$Sex == "Female", "Weight"])
medf2 = median(hw[hw$Sex == "Male", "Weight"])
szn = sd(hw[hw$Sex == "Female", "Height"])
szf = sd(hw[hw$Sex == "Male", "Height"])
szn2 = sd(hw[hw$Sex == "Female", "Weight"])
szf2 = sd(hw[hw$Sex == "Male", "Weight"])

Magassag = data.frame(row.names = c("Nõk", "Férfiak"), Átlag = c(atln, atlf),
                      Medián = c(medn, medf), Szórás = c(szn, szf))
Tomeg = data.frame(row.names = c("Nõk", "Férfiak"), Átlag = c(atln2, atlf2),
                      Medián = c(medn2, medf2), Szórás = c(szn2, szf2))

# Ábrák:

library(ggplot2)
ggplot(hw, aes(Weight, Sex)) + 
  geom_boxplot(fill = c("pink", "lightblue1")) + 
  ggtitle("Testtömeg boxplot ábrája")

ggplot(hw, aes(Height, Sex)) + 
  geom_boxplot(fill = c("pink", "lightblue1")) + 
  ggtitle("Testmagasság boxplot ábrája")

ggplot(hw, aes(Weight, fill = Sex)) +
  geom_histogram(col = "black") +
  facet_grid(vars(Sex)) +
  ggtitle("Testtömeg hisztogramja") +
  scale_fill_manual(values = c("pink", "lightblue1")) +
  xlab("Tömeg") +
  ylab("Gyakoriság")

ggplot(hw, aes(Height, fill = Sex)) +
  geom_histogram(col = "black") +
  facet_grid(vars(Sex)) +
  ggtitle("Testmagasság hisztogramja") +
  scale_fill_manual(values = c("pink", "lightblue1")) +
  xlab("Magasság") +
  ylab("Gyakoriság")
