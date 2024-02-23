#!/bin/bash

if [ $# -eq 0 ]
then
	echo Nem adott meg paramétert!
	exit
fi

if [ -f $1 ]
then
	szamlalo=0
	while read sor
	do
		karakter=0
		harmas=0
		if [ `echo $sor | grep "[a-z]" | wc -l` -eq 1 ]
		then
			karakter=1
		fi

		if [ `echo $sor | grep "[A-Z]" | wc -l` -eq 1 ]
		then
			karakter=1
		fi

		if [ `echo $sor | grep "3" | wc -l` -eq 1 ]
		then
			harmas=1
		fi
	
		if [ $karakter -eq 1 && $harmas -eq 1 ]
		then
			szamlalo=`expr $szamlalo + 1`
		fi

	done < $1

	echo $szamlalo olyan sor volt, ahol volt karakter és hármas is! 

else
	echo A megadott fájl nem létezik!
	exit
fi
