package ora1;

//1. feladat 
//Készítsük el a lehető legrövidebb Java fordítási egységet.

//class A{}

//Fordítsuk is le!
//javac A.java

//2. feladat
//Készítsük el a SKIP program megfelelőjét Javában!
/*
class Skip {
    public static void main(String[] args){
        int a;
    }
}
*/
// Fordítsuk és futtassuk!
// javac Skip.java
// java Skip

// 3. feladat
// Készítsünk programot, amely a billentyűzetről kéri be a felhasználó nevét,
// majd üdvözli! Használjuk az alábbi két parancsot!
// System.console().readLine()
// System.console().printf(...)

import java.util.Date;

class HelloUser {
    public static void main(String[] args) {

        System.out.print("Please enter your name: "); // println
        String line = System.console().readLine();
        System.out.printf("Your name: " + line + "\n");
        System.out.printf("Your name: %s \n", line);
        System.out.printf("Your name: %s%n", line);
        System.out.printf("%c\n", 'c');
        System.out.printf("%10.2f\n", 6.6788);
        System.out.printf("%5.2e%n", 1213.6788);
        Date date = new Date();
        System.out.printf("Time: %tT%n", date);
        System.out.printf("h--> %tH: m--> %tM: s--> %tS\n", date, date, date);
    }

}

// 4. feladat

// Készítsünk programot, amely a felhasználónevet parancssori argumentumként
// kapja, majd üdvözli a felhasználót! A szabványos kimenetre történő kiírás
// ezzel a paranccsal is elvégezhető - ezt használjuk majdnem mindig.
// System.out.println(...)

class Arg {
    public static void usage(String[] args) {
        System.out.println("java Arg ----");
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            String arg = args[0];
            System.out.println("Hello " + arg);
        } else {
            usage(args);
        }
    }
}

// 5. feladat

// Az alábbi Java program feladata, hogy kiírja az 1-től 4-ig lévő számok felét.
// Az elvárt kimenet:
// 0.5
// 1.0
// 1.5
// 2.0
// Kiirtam 4 szamot
// Javítsa ki a programot!
class Print {
    public static void main() {
        for( int i = 1; i < 4; i++ ) {
            System.out.println(i/2);
        }
        int i = 4;
        System.out.println("Kiirtam " + i + " szamot");
    }
}

class Print2 {

    public static void main(String[] args) {
        // int i;
        for (int i = 1; i <= 4; i++) {
            System.out.println((double) i / 2); // i/2.0;
        }
        int i = 4;
        System.out.println("Kiirtam " + i + " szamot");
    }
}

// 6. feladat
// Készítsünk programot, amely bekér két egész számot, és kiírja a köztük lévő
// egész számok felét. A beolvasás során kapott sztringeket egész számmá az
// alábbi konverziós függvénnyel alakíthatjuk át.
// Integer.parseInt(...)

class Beker {
    public static void main(String[] args) {

        System.out.print("Kerem az elso szamot: ");
        int also = Integer.parseInt(System.console().readLine());
        System.out.print("Kerem a masodik szamot: ");
        int felso = Integer.parseInt(System.console().readLine());
        for (int i = also; i <= felso; i++) {
            System.out.println((double) i / 2); // 2.0
        }
    }
}

// 7. feladat
// Készítsünk egy programot, amely kiszámolja két egész szám összegét,
// különbségét, szorzatát, hányadosát, és az osztási maradékot is megadja!
// Figyeljen a nullával való osztásra (ez esetben ne végezze el az osztást)! A
// két számot parancssori paraméterként kell megadni. Vizsgáljuk meg azt is,
// hogy megfelelő számú parancssori paramétert adtunk–e át!

class Muveletek {

    public static void usage(String[] args) {
        System.out.println("java Muveletek szam1 szam2");
    }

    public static void main(String[] args) {

        if (args.length == 2) {
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            System.out.printf("A két szám összege: %d%n", a + b);
            System.out.format("A két szám különbsége: %d%n", a - b);
            System.out.println("A két szám szorzata: " + a * b);
            if (b != 0) {
                System.out.printf("A két szám hányadosa: %.2f%n ", (double) a / b);
            } else {
                System.out.println("Nullával való osztás!");
            }
        } else {
            usage(args);
        }
    }

}

// 8. feladat
// Írjuk meg az n faktoriálisát kiszámoló programot.

class Factorial {
    public static void main(String[] args) {
        int num = 10;
        long factorial = 1;
        for (int i = 1; i <= num; ++i) {
            factorial *= i;
        }
        System.out.printf("Factorial of %d = %d%n", num, factorial);
    }
}

// +. feladat Sin(x)

class Trig {
    public static long fact(int x) {
        long factorial = 1;
        for (int i = 1; i <= x; ++i) {
            factorial *= i;
        }
        return factorial;
    }

    public static double mySin(int x, int prec) {
        double mySin = 0;
        for (int n = 0; n < prec; n++) {
            mySin += (Math.pow(-1, n) / fact(2 * n + 1)) * Math.pow(x, 2 * n + 1);
        }
        return mySin;
    }

    public static void main(String[] args) {
        int x = 2;
        int prec = 50;
        for (int i = 1; i < prec; i++) {
            System.out.println(i + " " + mySin(x, i));
        }
        System.out.println(Math.sin(x));
    }
}
