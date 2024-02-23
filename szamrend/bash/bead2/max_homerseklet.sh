#!/bin/bash

legmagasabbHolSzel=""
legmagasabbHolHossz=""
legmagasabbMikor=""
legmagasabb=-1000

oszlop1=`cat adat.txt | cut -f 1 -d ","`
oszlop2=`cat adat.txt | cut -f 2 -d ","`
oszlop3=`cat adat.txt | cut -f 3 -d ","`
oszlop4=`cat adat.txt | cut -f 4 -d ","`
oszlop5=`cat adat.txt | cut -f 5 -d " "`
hosszAux=`cat adat.txt | wc -l`
hossz=`expr $hosszAux - 1`
szamlalo=0

for i in $oszlop1
do
	SZELESSEG[$szamlalo]=$i
	szamlalo=`expr $szamlalo + 1`
done
szamlalo=0

for i in $oszlop2
do
	HOSSZUSAG[$szamlalo]=$i
	szamlalo=`expr $szamlalo + 1`
done
szamlalo=0

for i in $oszlop3
do
	DATUM[$szamlalo]=$i
	szamlalo=`expr $szamlalo + 1`
done
szamlalo=0

for i in $oszlop4
do
	IDO[$szamlalo]=$i
	szamlalo=`expr $szamlalo + 1`
done
szamlalo=0

for i in $oszlop5
do
	HOM[$szamlalo]=$i
	szamlalo=`expr $szamlalo + 1`
done
szamlalo=0

for i in `seq 0 $hossz`
do
	if [ ${HOM[$i]} -gt $legmagasabb ]
	then
		legmagasabb=${HOM[$i]}
		legmagasabbHossz="${HOSSZUSAG[$i]}"
		legmagasabbSzel="${SZELESSEG[$i]}"
		legmagasabbMikor="${DATUM[$i]} ${IDO[$i]}"
	fi
done

echo A legmagasabb hőmérséklet: $legmagasabb
echo A következő koordinátákon: 
echo Szélesség: $legmagasabbSzel
echo Hosszúság: $legmagasabbHossz
echo Dátum, idő: $legmagasabbMikor




