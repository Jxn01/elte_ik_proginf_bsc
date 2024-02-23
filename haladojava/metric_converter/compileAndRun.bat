dir /s /b *.java > sources.txt
javac @sources.txt
java hu.elte.haladojava.gyak1.MetricConverter

pause