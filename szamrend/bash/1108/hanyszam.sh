#!/bin/sh

egesz=`cat vegyes.txt | grep -c "^[0-9]\+$"`
echo $egesz

valos=`cat vegyes.txt | grep -c "^[0-9]\+,[0-9]\+$"`

echo $valos

expr $egesz + $valos

cat vegyes.txt | grep -c "^[0-9]\+\(,[0-9]\+\)\?$"
