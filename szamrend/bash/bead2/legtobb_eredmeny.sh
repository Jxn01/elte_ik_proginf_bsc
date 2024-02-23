#!/bin/bash


oszlop1=`cat adat.txt | cut -f 1 -d ","`
oszlop2=`cat adat.txt | cut -f 2 -d ","`
oszlop3=`cat adat.txt | cut -f 3 -d ","`
oszlop4=`cat adat.txt | cut -f 4 -d ","`
oszlop5=`cat adat.txt | cut -f 5 -d ","`
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
volt=0
for i in `seq 0 $hossz`
do
	if [ $szamlalo -eq 0 ]
	then
		UNIQUE[$szamlalo]="${SZELESSEG[$i]} ${HOSSZUSAG[$i]}"
		szamlalo=`expr $szamlalo + 1`
	else
		for j in `seq 0 $szamlalo`
		do
			if [ "${UNIQUE[$j]}" == "${SZELESSEG[$i]} ${HOSSZUSAG[$i]}" ]
			then
				volt=1
			fi
		done

		if [ $volt -eq 0 ]
		then 
			UNIQUE[$szamlalo]="${SZELESSEG[$i]} ${HOSSZUSAG[$i]}"
			szamlalo=`expr $szamlalo + 1`
		fi
		volt=0
	fi
done


for i in `seq 0 $szamlalo`
do
	UNIQUESZAM[$i]=0
done

for i in `seq 0 $hossz`
do
	for j in `seq 0 $szamlalo`
	do
		if [ "${SZELESSEG[$i]} ${HOSSZUSAG[$i]}" == "${UNIQUE[$j]}" ]
		then
			UNIQUESZAM[$j]=`expr ${UNIQUESZAM[$j]} + 1 `
		fi
	done
done

max=0
uni=""

for i in `seq 0 $szamlalo`
do
	if [ ${UNIQUESZAM[$i]} -gt $max ]
	then
		max=${UNIQUESZAM[$i]}
		uni="${UNIQUE[$i]}"
	fi
done

echo "A legtöbb mérési eredmény: $max"
echo "Ami a következő helyen történt: $uni"

