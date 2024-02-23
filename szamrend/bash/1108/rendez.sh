#!/bin/sh

echo "Kerek egy szot (vege)"
read szo

while test "$szo" != "vege"
do 
   echo $szo >> szavak.txt
   read szo

done
if test -f szavak.txt
then
   cat szavak.txt | sort
   rm szavak.txt
fi

