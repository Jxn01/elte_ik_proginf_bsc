CREATE TABLE gyak1 AS SELECT * FROM nikovits.szeret;
SELECT * FROM gyak1;
CREATE TABLE dolgozo AS SELECT * FROM nikovits.dolgozo;
SELECT * FROM dolgozo;
/* 1.  Kik azok a dolgozók, akiknek a fizetése nagyobb, mint 2800? */
SELECT DNEV FROM dolgozo WHERE FIZETES > 2800;
/* 2.  Kik azok a dolgozók, akik a 10-es vagy a 20-as osztályon dolgoznak? */
SELECT DNEV FROM dolgozo WHERE OAZON = 10 OR OAZON = 20;
/* 3.  Kik azok, akiknek a jutaléka nagyobb, mint 600? */
SELECT DNEV FROM dolgozo WHERE JUTALEK > 600;
/* 4.  Kik azok, akiknek a jutaléka nem nagyobb, mint 600? */
SELECT DNEV FROM dolgozo WHERE JUTALEK <= 600;
/* 5.  Kik azok a dolgozók, akiknek a jutaléka ismeretlen (nincs kitöltve, vagyis NULL)? */
SELECT DNEV FROM dolgozo WHERE JUTALEK IS NULL;
/* 6.  Adjuk meg a dolgozók között előforduló foglalkozások neveit. */
SELECT FOGLALKOZAS FROM dolgozo;
/* 7.  Adjuk meg azoknak a nevét és kétszeres fizetését, akik a 10-es osztályon dolgoznak. */
SELECT DNEV, FIZETES*2 FROM dolgozo WHERE OAZON = 10;
/* 8.  Kik azok a dolgozók, akik 1982.01.01 után léptek be a céghez? */


