#include <iostream>
#include <vector>//Kell a vector típushoz

using namespace std;

typedef struct
        {
            string nev;
            int pont;
        }versenyzo;

void be_vector(vector <float> &tomb);//Cím szerinti átadás
void ki_vector(vector <float> tomb);//érték szerinti átadás

int main()
{
    setlocale(LC_ALL,"hun");
    //Deklarálás
    vector <int> egesz_tomb;
    vector <float> valos_tomb;
    vector <versenyzo> versenyzok;//Általunk definiált típussal
    //Inicializálás
    vector <int> szamok(5);//Öt inicializált elem
    for (int i=0; i<szamok.size(); i++)//.size() a tömb elemszáma
    {
        cout << szamok[i] << " ";
    }
    cout << endl;// 5 db 0 a kimenet
    vector <int> szamok2;//Nincs inicializálás
    szamok2.reserve(5);//Öt elemnek van hely lefoglalva, de nincs inicializálva
    for (int i=0; i<szamok2.size(); i++)//.size() a tömb elemszáma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//"semmi" a kimenet, mert a size 0 (lásd lent)
    //Capacity és size közötti különbség
    cout << szamok2.size() << endl;//0
    cout << szamok2.capacity() << endl;//5
    szamok2.push_back(1);//Hozzáadtunk egy elemet a tömbhöz
    cout << szamok2.size() << endl;//1
    cout << szamok2.capacity() << endl;//5
    szamok2.resize(3);//3-ra csökkentjük a méretét
    cout << szamok2.size() << endl;//3
    cout << szamok2.capacity() << endl;//5
    for (int i=0; i<szamok2.size(); i++)//.size() a tömb elemszáma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//1 0 0 (a resize inicializálja a nem inicializált elemeket)
    szamok2.resize(6,22);//a nem inicializált részeket 22-es számmal töltjük fel
    for (int i=0; i<szamok2.size(); i++)//.size() a tömb elemszáma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//1 0 0 22 22 22 (a resize inicializálja a nem inicializált elemeket)
    //Tömb túlindexelés
    cout << szamok2[szamok2.size()] << endl;//Ezt sajnos engedi a c++ :(
    //Tömb feltöltése
    cout << "6 elem beolvasása:" << endl;
    int db = 6;
    int elem;
    for (int i=0; i<db; i++)
    {
        cin >> elem;
        egesz_tomb.push_back(elem);
    }
    for (int i=0; i<egesz_tomb.size(); i++)
    {
        cout << egesz_tomb[i] << " ";
    }
    cout << endl;
    //Értékadás
    cout << egesz_tomb[2] << endl;
    egesz_tomb[2] = 100;
    cout << egesz_tomb[2] << endl;
    //Elsõ elem elérése
    cout << egesz_tomb.front() << endl;
    cout << egesz_tomb[0] << endl;
    cout << egesz_tomb.at(0) << endl;
    //Utolsó elem elérése
    cout << egesz_tomb.back() << endl;
    cout << egesz_tomb[egesz_tomb.size()-1] << endl;
    cout << egesz_tomb.at(egesz_tomb.size()-1) << endl;
    //Tömb elemeinek törlése
    //Utolsó elem törlése
    egesz_tomb.pop_back();
    cout << egesz_tomb.back() << endl;
    //Felhasználás paraméterként: cím és érték szerinti átadás
    be_vector(valos_tomb);
    ki_vector(valos_tomb);
    return 0;
}

void be_vector(vector <float> &tomb)//cím szerinti átadás
{
    int db;
    float elem;
    cout << "Hány eleme van a tömbnek? " << endl;
    cin >> db;
    for (int i=0;i<db;i++)
    {
        cout << "Írd be a(z) " << i+1 << ". elemet: " << endl;
        cin >> elem;
        tomb.push_back(elem);
    }
}

void ki_vector(vector <float> tomb)//érték szerinti átadás
{
    for (int i=0;i<tomb.size();i++)
    {
        cout << tomb[i] << " ";
    }
    cout << endl;
}

