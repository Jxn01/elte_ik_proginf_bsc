#!/bin/sh

if test $# -ne 1
then 
   echo "Kerek egy fajlnevet"
   exit
fi

if test -f $1
then
   ssz=1
   while read sor
   do
      echo "$ssz $sor"
      ssz=`expr $ssz + 1`
   done < $1

else
   echo "Nem fajl"
fi
