p = 0.2

# Mennyi a valószínűsége annak, hogy kevesebb mint 4 dobásból kosarat dobunk? 
prob_lt_4 = sum(dgeom(1:3, p))
print(prob_lt_4)

# Mennyi a valószínűsége annak, hogy az első 8 dobásból nem találunk be?
prob_not_fst_8 = pgeom(7, p, lower.tail = FALSE)
print(prob_not_fst_8)

