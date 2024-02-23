#include "Konyvtar.hpp"
#include "Mufaj.hpp"
#include "Konyv.hpp"
#include "Szemely.hpp"
#include "Kolcson.hpp"

#include <iostream>

int testCounter = 1;
bool jo = true;

void check(bool l)
{
    if (!l)
    {
        jo = false;
        std::cerr << testCounter << ". teszt sikertelen." << std::endl;
    }
    testCounter++;
}

int main()
{

    Konyvtar* konyvtar = new Konyvtar();

    Konyv* konyv1 = new Konyv("Cim1", "Szerzo1", 100, Termeszettudomanyos::instance());
    Konyv* konyv2 = new Konyv("Cim2", "Szerzo2", 200, Szepirodalmi::instance());

    Szemely* szemely1 = new Szemely("Szemely1");


    konyvtar->Bevetelez(konyv1);
    check(konyvtar->get_konyvek().size() == 1);
    check(konyvtar->get_konyvek()[0] == konyv1);
    konyvtar->Bevetelez(konyv2);

    check(konyvtar->get_konyvek()[0]->get_azon() == 1);
    check(konyvtar->get_konyvek()[0]->get_cim() == "Cim1");
    check(konyvtar->get_konyvek()[0]->get_szerzo() == "Szerzo1");
    check(konyvtar->get_konyvek()[0]->get_oldal() == 100);
    check(konyvtar->get_konyvek()[0]->get_kinn() == false);
    check(konyvtar->get_konyvek()[0]->get_mufaj() == Termeszettudomanyos::instance());

    konyvtar->Belep(szemely1);
    check(szemely1->get_konyvtar() == konyvtar);
    check(konyvtar->get_tagok().size() == 1);
    check(konyvtar->get_tagok()[0]->get_nev() == "Szemely1");

    konyvtar->Kolcsonoz(szemely1, { 1,2 }, 0);
    check(konyv1->get_kinn() == true);
    check(szemely1->get_kolcs()[0]->get_tetelek()[0] == konyv1);
    check(szemely1->get_kolcs()[0]->get_tetelek().size() == 2);
    check(konyv1->get_fej() == konyvtar->get_kolcs()[0]);


    check(konyv1->get_mufaj()->Dij() == 100);
    check(konyv2->get_mufaj()->Dij() == 50);

    Termeszettudomanyos::destroy();
    Szepirodalmi::destroy();
    Ifjusagi::destroy();
    delete konyvtar;
    delete konyv1;
    delete konyv2;
    delete szemely1;

    if (!jo)
    {
        return 1;
    }
    else
    {
        std::cout << "Ok";
        return 0;
    }

}