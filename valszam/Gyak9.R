ml = c()
mm = c()

for (i in 1000){
    minta = sample(c(-1, 1, 2), 20, replace = TRUE, c(0.1, 0.3, 0.6))
    t = table(minta)
    ml = c(ml, (t[1] + t[2]) / (4 * (t[1] + t[2] + t[3])))
    mm = c(mm, (2 - mean(minta))/6)
}

mean(ml)
mean(mm)