# Modellezzünk R-ben egy kísérletet, ahol egy szabálytalan érmével dobunk 1000-szer. 
# Tudjuk, hogy a fej valószínűsége 0.8, míg az írásé 0.2. Hasonlítsuk össze a 
# tapasztalati eredményeket az elvárt eredményekkel!

# gyakorlati eredmenyek
n = 1000
s = sample(c("head", "tail"), n, replace = TRUE, prob = c(0.8, 0.2))
heads = sum(s == "head")
tails = sum(s == "tail")
heads; tails

# elmeleti eredmenyek
n * 0.8; n * 0.2


