CREATE OR REPLACE FUNCTION is_prime(n IN NUMBER) RETURN NUMBER IS
  i NUMBER;
BEGIN
  IF n < 2 THEN
    RETURN 0;
  END IF;

  FOR i IN 2..TRUNC(SQRT(n)) LOOP
    IF MOD(n, i) = 0 THEN
      RETURN 0;
    END IF;
  END LOOP;

  RETURN 1;
END;
/

CREATE TABLE GYAK8
AS (SELECT dkod, dnev FROM dolgozo WHERE is_prime(dkod) = 1);

SELECT dkod, dnev FROM dolgozo WHERE is_prime(dkod) = 1;

SELECT * FROM GYAK8;