# 4. hét feladatok 1.

# 4.3 feladat

N = 100

doboz = rep(c(1:N),each = 2)

m = 50
maradek = sample(doboz, 2*N - m)

parok_szama = 0
for (i in 1:(length(maradek)-1)){
  for (j in (i+1):length(maradek)){
    if (maradek[i] == maradek[j]){
      parok_szama = parok_szama + 1
    }
  }
}

parok_szama

N*choose(2*N-2, m)/choose(2*N, m)


# 4.5 feladat

Poisson = function(lambda,k){
  P = ((lambda^k)/factorial(k))*exp(-lambda)
  return(P)
}

# a

sum(Poisson(2.5, c(0,1)))
sum(dpois(c(0,1), 2.5))
ppois(1, 2.5)

# b

1 - ppois(3, 2.5)
