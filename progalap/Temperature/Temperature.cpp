
//Név: Oláh Norbert
//Neptun-kód: PST8RA
//E-mail: PST8RA@inf.elte.hu
//Feladat: Hőmérsékletvizsgálás

/*Reggeli hőmérséklet értékelése, átszámítása -re. [°F] = [°C] · 9/5 + 32 Olvassuk be a
hőmérsékletet - a Földön eddig előfordult leghidegebb
és legmelegebb hőmérsékletek a kódban adottak(-89° C,
58° C). (Hibás beolvasás esetén jelezzük a hibát és újra
bekérjük az adatot, mindaddig, amíg helyes nem lesz.)
Helyes érték esetén adjuk meg és írjuk is ki, hogy
fagypont alatti vagy feletti - e a hőmérséklet és számoljuk
azt át Fahrenheitre!” */

//Specifikáció:
//BE: hom E Z, [konstans minH, maxH E Z (-89, 58)]
//KI: fagyott E L, homF E R
//EF: hom E [minH...maxH]
//UF: fagyott=hom<=0 és homF=hom*9/5+32
#include <iostream>
using namespace std;


int main()
{
    setlocale(LC_ALL, "hun");
    const int minH = -89;
    const int maxH = 58;
    bool fagyott = false;
    bool hiba = false;
    double hom = 0;
    double homF = 0;
    
    cout << "Ez egy reggeli hőmérsélet értékelő és átváltó program.\n" << endl;
    cout << "Kérem adja meg a reggeli hőmérsékletet Celsiusban! \n(Intervallum -89-től 58-ig.)" << endl;

    do {
        cin >> hom;

        if (hom < -89 || hom > 58 || cin.fail() || cin.peek()!='\n' ){
            hiba = true;
        }
        else {
            hiba = false;
        }

        if (hiba) {
            cout << "Hibás értéket adott meg, ellenőrizze, hogy a megadott értéke az intervallumon belül van-e." << endl;
            cin.clear();
            cin.ignore(999, '\n');
        }


    } while (hiba);

    homF = hom * 9 / 5 + 32;
    
    if (hom <= 0) {
        fagyott = true;
    }
    if (fagyott) {
        cout << "A hőmérséklet fagypont alatti." << endl;
    }
    else {
        cout << "A hőmérséklet fagypont feletti." << endl;
    }

    cout << "A hőmérséklet Fahrenheitben " << homF << " fok." << endl;

    return 0;
}