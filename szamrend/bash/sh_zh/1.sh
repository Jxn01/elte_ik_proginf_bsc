#!/bin/bash

if [ $# -ne 2 ]
then
	echo A paraméterek száma nem 2!
	exit
fi

if [ `echo $1 | grep "^[0-9]\+\$" | wc -l` -ne 1 ]
then
	echo Az első paraméter nem egész szám!
	exit
fi

if [ `echo $2 | grep "^[0-9]\+\$" | wc -l` -ne 1 ]
then
	echo Az második paraméter nem egész szám!
	exit
fi

echo `expr $1 - $2`

