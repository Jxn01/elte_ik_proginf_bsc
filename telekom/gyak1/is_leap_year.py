def is_leap_year(num):
  try:
    retValue = True
    if (num % 4) == 0:
        if ((num % 100) == 0) and ((num % 400) != 0):
            retValue = False
    else:
        retValue = False
    return retValue
  except TypeError:
    print("Error: num is not integer")

with open('years.txt', 'r') as f:
  for year in f:
    year = year.rstrip('\n')
    print(year, is_leap_year(int(year)))
