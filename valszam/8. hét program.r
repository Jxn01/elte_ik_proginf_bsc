# 8. feladatsor

# 8.1 feladat:

kocka = c(1,2,3,4,5,6)
minta = sample(kocka, 1000, replace = TRUE)
tablazat = table(minta)

barplot(tablazat, main = "1000 kockadob�s eredm�nye", xlab = "sz�mok", ylab = "gyakoris�g",
     col = "lightblue1")

# a, tapasztalati k�z�p:
mean(minta)

# b, tapasztalati m�dusz:
names(tablazat[tablazat == max(tablazat)])

# c, tapasztalati medi�n:
median(minta)

# d, els� h�rom tapasztalati momentum
mean(minta)
mean(minta^2)
mean(minta^3)

# e, tapasztalati sz�r�s
sqrt(mean((minta - mean(minta))^2))

# korrig�lt tapasztalati sz�r�s
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
legend("topright", legend = c("�tlag", "medi�n"), col = c("red", "blue"),
       lty = c(1,1), lwd = c(2,2))

# b, tapasztalati k�z�p:
mean(df$state.area)

# c, tapasztalati medi�n:
median(df$state.area)

# d, korrig�lt tapasztalati sz�r�s:
sd(df$state.area)

# e, Tapasztalati kvartilisek:
quantile(df$state.area, c(0.25, 0.5, 0.75))

# alapstatisztik�k:
summary(df$state.area)

# f, Interkvartilis-terjedelem:
IQR(df$state.area)

# g, Tapasztalati eloszl�sf�ggv�ny:
plot(ecdf(df$state.area))
abline(v = median(df$state.area), col = "blue", lwd = 2)
abline(h = 0.5, col = "blue", lwd = 2)

# h, Boxplot:
boxplot(df$state.area, horizontal = TRUE, col = "gold", pch = 16)


# 8.4 feladat:

hw = read.csv("C:/Users/User/Desktop/Egyetem/Valstat Inf/Height_and_weight.csv",
              header = TRUE, sep = ",", col.names = c("Height", "Weight", "Sex"))

# Alapstatisztik�k:
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

Magassag = data.frame(row.names = c("N�k", "F�rfiak"), �tlag = c(atln, atlf),
                      Medi�n = c(medn, medf), Sz�r�s = c(szn, szf))
Tomeg = data.frame(row.names = c("N�k", "F�rfiak"), �tlag = c(atln2, atlf2),
                      Medi�n = c(medn2, medf2), Sz�r�s = c(szn2, szf2))

# �br�k:

library(ggplot2)
ggplot(hw, aes(Weight, Sex)) + 
  geom_boxplot(fill = c("pink", "lightblue1")) + 
  ggtitle("Testt�meg boxplot �br�ja")

ggplot(hw, aes(Height, Sex)) + 
  geom_boxplot(fill = c("pink", "lightblue1")) + 
  ggtitle("Testmagass�g boxplot �br�ja")

ggplot(hw, aes(Weight, fill = Sex)) +
  geom_histogram(col = "black") +
  facet_grid(vars(Sex)) +
  ggtitle("Testt�meg hisztogramja") +
  scale_fill_manual(values = c("pink", "lightblue1")) +
  xlab("T�meg") +
  ylab("Gyakoris�g")

ggplot(hw, aes(Height, fill = Sex)) +
  geom_histogram(col = "black") +
  facet_grid(vars(Sex)) +
  ggtitle("Testmagass�g hisztogramja") +
  scale_fill_manual(values = c("pink", "lightblue1")) +
  xlab("Magass�g") +
  ylab("Gyakoris�g")
