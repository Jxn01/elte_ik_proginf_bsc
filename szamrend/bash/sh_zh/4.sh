#/bin/bash

if [ $# -eq 0 ]
then
	echo Nem adott meg paramétert!
	exit
fi

if [ -f $1 ]
then
	szoveg=`cat $1`
	for i in $szoveg
	do 
		szam1=`echo $i | cut -f 1 -d " "`
		szam2=`echo $i | cut -f 2 -d " "`
		echo `expr $szam1 + $szam2`
	done
else
	echo Ilyen fájl nem létezik!
	exit
fi
