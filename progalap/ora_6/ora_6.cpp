#include <iostream>
using namespace std;
int be_egesz_szam(const string& input_szoveg, int minert, int maxert, const string& hibauzenet);
void be_egesz_tomb(const int elemszam, int tomb[], int minert, int maxert);

int main()
{
    setlocale(LC_ALL, "hun");//Ékezetes betûk
    
    //Bemeneti változók
    const int db = be_egesz_szam("Írd be a tömb elemszámát (1<=szám<=20)!", 1, 20, "Hibás számot adtál meg!");

    //tömb deklarálása
    const auto szamok = new int[db];

    be_egesz_tomb(db, szamok, 1, 20);
    
    return 0;
}


int be_egesz_szam(const string& input_szoveg, const int minert, const int maxert, const string& hibauzenet)
{
    int szam;
    bool hiba = false;
    do
    {
        cout << input_szoveg << endl;
        cin >> szam;
        hiba = cin.fail() || cin.peek() != '\n' || (szam < minert || szam>maxert);
        if (hiba)//Nem érvényes az input
        {
            cout << hibauzenet << endl;
        }
        cin.clear();
        cin.ignore(999, '\n');
    } while (hiba);

    return szam;
}

void be_egesz_tomb(const int elemszam, int tomb[], const int minert, const int maxert)
{
    bool hiba = false;

    for (int i = 0; i < elemszam; i++)
    {
        cout << i + 1 << ".elem: ";
        tomb[i] = be_egesz_szam("Írd be a tömb elemszámát (1<=szám<=20)!", minert, maxert, "Rossz számot adtál meg!");
    }
}