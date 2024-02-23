#!/bin/sh
#
#Pár tudnivaló a scriptmeghívós paraméterekről
#
# $$: PID szám
# $1, $2, $3 paraméterek
# $# paraméterek száma
#
# Echo-val kapcsolatos dolgok:
# echo -n : a következő echo nem új sorba kerül
#
#
# test: logikai műveletek
#
# test -eq: equal
# test -ne: not equal
# test -gt: greater than
# test -lt: less than
# test -le: less equal than
# test -ge: greater equal than
# test -a: and
# test -o: or
# test ! : negáció
#
# also test = [ ]
# szóval: test 6 -eq 5 = [ 6 -eq 5 ]
#
# $?: előző művelet eredménye
# ha igaz a kifejezés akkor az eredmény 0
# ha hamis akkor 1
#
# regex ismeretek with grep
# használat: grep "[regex]"
# szűri az inputot tehát nem boolt ad vissza
# grep -c : visszaadja a darabszámot ahány dologra illeszkedik
# 
# regex minták:
# "[0-9]": 0-9 - ig legyen EGY karakter
# "^[0-9]": 0-9 - ig legyen EGY karakter, a LEGELSŐ
# "^[0-9]$": 0-9 - ig legyen EGY karakter, az ELEJÉN is és a VÉGÉN is UGYANAZ
# "^[0-9]\+$": 0-9 - ig legyen AKÁRMENNYI, DE MINIMUM 1 karakter (a + elé 
# muszály a \, mert amúgy a +-t mint karaktert akarná nézni)
# plusz ha ? van akkor az a rész lehet egyszer és 0-szor is
#
# TEHÁT
# ^: első karakter
# $: utolsó karakter
# \+: összes karakterre
#
# wc parancs (word count, számláló)
# wc -l sorokat számol
#
#
# cut parancs
# cut -f 1 -d " "
# -f: field, hanyadik oszlopot szeretnénk levágni
# -d: delimeter, meddig akarjuk levágni
#
#
# for ciklus seq- el
# a seq generál számokat k-tól n-ig l-esével: seq k l n
#
# sort | uniq: (basically a sorted set)
# sort: sortol valamit
# uniq: duplicate-eket töröl (csak ha egymás mellett vannak)
#
# Fájlba írás: 
# Felülírva: echo valami > valami.txt
# append-elve: echo valami >> valami.txt
#
# Test használata fájlokhoz:
# test -f valami.txt: megnézzük hogy létezik-e a fájl


x1=`echo $1 | grep "^[0-9]\+$" | wc -l`
x2=`echo $2 | grep "^[0-9]\+$" | wc -l`
if test $x1 -eq 0
then
		echo Az első paraméter nem egész szám!
fi

if test $x2 -eq 0  
then
		echo A második paraméter nem egész szám!
fi

if test $# -ne 2
then
	echo Kevés argumentumot adott meg, két számot adjon meg argumentumként!
	exit
else
	echo expr $1 + $2: `expr $1 + $2`
	echo expr $1 % $2: `expr $1 % $2`
	echo expr $1 - $2: `expr $1 - $2`
	echo expr $1 / $2: `expr $1 / $2`
	echo expr $1 \* $2: `expr $1 \* $2`
	echo \( $1 + $2 \) \* $1: `expr \( $1 + $2 \) \* $1`
fi

echo Kérem adjon meg egy egész számot!
read szam
while [ `echo $szam | grep "^[0-9]\+$" | wc -l` -eq 0 ]
do 
	echo Rossz adatot adott meg, kérem adja meg újból!
	read szam
done

if [ $szam -gt 5 -a $szam -lt 10 ]
then
	echo [6-9] intervallumban van a megadott szám.
else
	echo A szam nincs benne a [6-9] intervallumban.
fi

echo

for i in `seq 0 10`
do
	echo $i.edik ciklusiteráció
done

# fájlokkal való munka
# while ban lehet read-el sort olvasni egy fileból, ha a done ki van vezetve
# a file nevéhez
#
# also:
# for i in `cat $file` : ez szavanként olvas

echo Kérem adjon meg egy fájlnevet!
read fajlnev

if [ -f $fajlnev ]
then
	sorSzam=1
	while read sor
	do
		echo "$sorSzam sor: $sor"
		sorSzam=`expr $sorSzam + 1`
	done < $fajlnev
else
	echo Ez a fajl nem letezik.

fi

# sed
# stream editor
# két szó megcseréléséhez tökéletes (soronként)
# pl.: cat szavak.txt | sed "s/ \([a-z]\+\) \([a-z]\+\) /2 /1"

