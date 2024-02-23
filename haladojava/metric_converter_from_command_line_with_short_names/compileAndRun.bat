dir /s /b *.java > sources.txt
javac @sources.txt

@echo off

echo "bad input case 1:"
java hu.elte.haladojava.gyak1.MetricConverter keves parameter

echo "bad input case 2:"
java hu.elte.haladojava.gyak1.MetricConverter x rossz szam

echo "bad input case 3:"
java hu.elte.haladojava.gyak1.MetricConverter 10.0 rossz unit

echo "happy path:
java hu.elte.haladojava.gyak1.MetricConverter 1 METER MILLI_METER

@echo on

pause