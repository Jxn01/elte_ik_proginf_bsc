# 5. heti feladatok

# 5.5 feladat:

x = rexp(10000, 1/6000)
hist(x)

# a,

# kísérlet:
sum(x < 4000)/10000

# elmélet:
pexp(4000, 1/6000)

# b,

# kísérlet:
sum(x > 6500)/10000

# elmélet:
1 - pexp(6500, 1/6000)

# c,

# kísérlet:
sum(x > 4000 & x < 6000)/10000

# elmélet:
pexp(6000, 1/6000) - pexp(4000, 1/6000)

# d,

# kísérlet:
x_ = sort(x)
x_[2000]

# elmélet:
qexp(0.2, 1/6000)


# 5.6 feladat:

y = rnorm(10000, 22.1, 1.5)
hist(y)

# a,

# kísérlet:
sum(y > 23 & y < 25)/10000

# elmélet:
pnorm(25, 22.1, 1.5) - pnorm(23, 22.1, 1.5)

# b,

# kísérlet:
sum(y > 20.5 & y < 23.5)/10000

# elmélet:
pnorm(23.5, 22.1, 1.5) - pnorm(20.5, 22.1, 1.5)
# standardizálás után kerekítési hibától eltekintve, ugyanazt kell kapnunk:
2*pnorm(1) - 1


# 5.7 feladat:

# kísérlet:
z = rnorm(10000, 10, 2)
hist(z)

z_ = sort(z)
z_[1000]

# elmélet:
qnorm(0.1, 10, 2)


# 5.8 feladat:

# kísérlet:
w = rnorm(10000, 110, 10)
hist(w)

sum(w > 120)/10000

# elmélet:
1 - pnorm(120, 110, 10)

