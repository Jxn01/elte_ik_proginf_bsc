#!/bin/bash

if [ $# -ne 2 ]
then
	echo A paraméterek száma nem 2!
	exit
fi

if [ `echo $1 | grep "^[0-9]\+\$" | wc -l` -ne 1 ]
then
	echo Az első paraméter nem szám!
	exit
fi

if [ `echo $2 | grep "^[A-Z]\$" | wc -l` -ne 1 ]
then
	echo Az második paraméter nem nagybetű, vagy nem 1 darab!
	exit
fi

for i in `seq 1 $1`
do
	echo -n $2
done

