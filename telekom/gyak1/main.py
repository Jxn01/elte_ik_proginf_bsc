import json


def is_leap_year(year):
    return year % 4 == 0 and (year % 100 != 0 or year % 400 == 0)


def fibonacci(n):
    if n == 0: return 0
    if n == 1 or n == 2: return 1
    return fibonacci(n - 1) + fibonacci(n - 2)


if __name__ == '__main__':
    with open("leapyears.txt", "r") as leap_file:
        for line in leap_file:
            year = int(line)
            print(year, is_leap_year(year))

    grades = {2: 50, 3: 60, 4: 75, 5: 85}
    scores_needed = [0, 0, 0, 0]
    sum_score = 0
    with open("mininet.txt") as mininet_file:
        scores = json.load(mininet_file)
        sum_score += scores["haziPont"]["elert"]
        sum_score += scores["zhPont"]["elert"]
        if sum_score >= grades.get(2):
            scores_needed[0] = scores["mininetPont"]["min"]

    print(sum_score)
    print(fibonacci(0))
    print(fibonacci(1))
    print(fibonacci(2))
    print(fibonacci(3))
    print(fibonacci(10))
    # print(fibonacci(100))
