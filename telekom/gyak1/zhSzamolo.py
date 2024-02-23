import math
import json

erdemJegy_szuksegesSzazalek = {2:50, 3:60, 4:75, 5:85}
sikertelen = "Remenytelen"

def szuksegesPont(erdemJegy, aktSzazalek, minMininet, maxMininet):
  szuksegesSzazalek = erdemJegy_szuksegesSzazalek[erdemJegy]
  retVal = ((szuksegesSzazalek - aktSzazalek) / (100/3)) * maxMininet
  if retVal > maxMininet:
    retVal = sikertelen
  elif retVal < minMininet:
    retVal = minMininet
  else:
    retVal = round(retVal)
  return retVal

with open('pontok.json', 'r') as f:
  data = json.load(f)

aktSzazalek = ((data["haziPont"]["elert"] / data["haziPont"]["max"]) + (data["zhPont"]["elert"] / data["zhPont"]["max"])) * (100/3)
minMininet = data["mininetPont"]["min"]
maxMininet = data["mininetPont"]["max"]

#print("Eddigi: " + str(aktSzazalek))

for erdemJegy in range(2,6):
  szukseges_pont = szuksegesPont(erdemJegy, aktSzazalek, minMininet, maxMininet)
  print(str(erdemJegy) + " : " + str(szukseges_pont))
