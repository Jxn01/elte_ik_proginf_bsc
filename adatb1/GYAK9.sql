--CREATE OR REPLACE FUNCTION get_foglalkozas(o_nev varchar2) RETURN varchar2 IS

CREATE OR REPLACE FUNCTION get_foglalkozas(o_nev varchar2) RETURN varchar2 IS
    v_foglalkozasok VARCHAR2(4000);
BEGIN
    SELECT LISTAGG(foglalkozas, '-') WITHIN GROUP (ORDER BY foglalkozas) INTO v_foglalkozasok
    FROM DOLGOZO
    WHERE oazon = (SELECT oazon FROM OSZTALY WHERE o_nev = onev);

    RETURN v_foglalkozasok;
END;
/

CREATE OR REPLACE PROCEDURE make_gyak9_kotelezo_feladat IS
BEGIN
    
    INSERT INTO GYAK9 (OSZTALY, JOBCONCAT)
    VALUES ('ACCOUNTING', get_foglalkozas('ACCOUNTING'));
    
    INSERT INTO GYAK9 (OSZTALY, JOBCONCAT)
    VALUES ('RESEARCH', get_foglalkozas('RESEARCH'));
    
    INSERT INTO GYAK9 (OSZTALY, JOBCONCAT)
    VALUES ('SALES', get_foglalkozas('SALES'));
    
    COMMIT;
       

END;
/

CREATE OR REPLACE FUNCTION kat_atlag(n integer) RETURN number IS
    v_atlag FLOAT;
BEGIN
    
    SELECT AVG(FIZETES)
    INTO v_atlag
    FROM DOLGOZO D, FIZ_KATEGORIA F
    WHERE D.FIZETES BETWEEN F.ALSO AND F.FELSO AND n = F.KATEGORIA;
    
    RETURN v_atlag;
END;
/

SELECT kat_atlag(2) FROM dual;



SELECT * FROM FIZ_KATEGORIA;
SELECT * FROM DOLGOZO;
SELECT * FROM OSZTALY;

EXECUTE make_gyak9_kotelezo_feladat;

SELECT * FROM GYAK9;

SELECT * FROM GYAK9;

SELECT get_foglalkozas('ACCOUNTING') FROM dual;