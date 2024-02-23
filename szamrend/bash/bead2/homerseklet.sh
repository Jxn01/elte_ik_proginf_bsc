#!/bin/bash

if [ $# -ne 2 ]
then
	echo "Rossz paraméterszám! Egy szélességet és egy hosszúságot adjon meg!"
       	exit
fi

if [ `echo $1 | grep "^[0-9][0-9].[0-9]\+\$" | wc -l` -ne 1 ]
then 
	echo "Rossz szélességet adott meg!"
	exit
fi

if [ `echo $2 | grep "^[0-9][0-9].[0-9]\+\$" | wc -l` -ne 1 ]
then 
	echo "Rossz hosszúságot adott meg!"
	exit
fi

sorokSzama=`cat adat.txt | wc -l`
utolsoIndex=`expr $sorokSzama - 1`
elsoOszlop=`cat adat.txt | cut -f 1 -d ","`
szamlalo1=0

for i in $elsoOszlop
do
	SZELESSEG[$szamlalo1]=$i
	szamlalo1=`expr $szamlalo1 + 1`
done

masodikOszlop=`cat adat.txt | cut -f 2 -d ","`
szamlalo2=0

for i in $masodikOszlop
do
	HOSSZUSAG[$szamlalo2]=$i
	szamlalo2=`expr $szamlalo2 + 1`
done


harmadikOszlop=`cat adat.txt | cut -f 3 -d ","`
szamlalo3=0
for i in $harmadikOszlop
do
	DATUM[$szamlalo3]=$i
	szamlalo3=`expr $szamlalo3 + 1`
done

napSzamlalo=0
napVolt=0
for i in `seq 0 $utolsoIndex`
do	
	if [ ${HOSSZUSAG[$i]} == $2 -a ${SZELESSEG[$i]} == $1 ]
	then
		if [ $napSzamlalo -eq 0 ]
		then
			VOLTMAR[$napSzamlalo]="${DATUM[$i]}"
			napSzamlalo=`expr $napSzamlalo + 1`
		else
			for j in `seq 0 $napSzamlalo`
			do
				if [ "${VOLTMAR[$j]}" == "${DATUM[$i]}" ]
				then
					j=$napSzamlalo
					napVolt=1
				fi	
			done

		if [ $napVolt -eq 0 ]
		then
			napSzamlalo=`expr $napSzamlalo + 1`
		fi
		napVolt=0

		fi
	fi
done

echo "$napSzamlalo különböző napon végeztek méréseket."


