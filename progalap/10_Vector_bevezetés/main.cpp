#include <iostream>
#include <vector>//Kell a vector t�pushoz

using namespace std;

typedef struct
        {
            string nev;
            int pont;
        }versenyzo;

void be_vector(vector <float> &tomb);//C�m szerinti �tad�s
void ki_vector(vector <float> tomb);//�rt�k szerinti �tad�s

int main()
{
    setlocale(LC_ALL,"hun");
    //Deklar�l�s
    vector <int> egesz_tomb;
    vector <float> valos_tomb;
    vector <versenyzo> versenyzok;//�ltalunk defini�lt t�pussal
    //Inicializ�l�s
    vector <int> szamok(5);//�t inicializ�lt elem
    for (int i=0; i<szamok.size(); i++)//.size() a t�mb elemsz�ma
    {
        cout << szamok[i] << " ";
    }
    cout << endl;// 5 db 0 a kimenet
    vector <int> szamok2;//Nincs inicializ�l�s
    szamok2.reserve(5);//�t elemnek van hely lefoglalva, de nincs inicializ�lva
    for (int i=0; i<szamok2.size(); i++)//.size() a t�mb elemsz�ma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//"semmi" a kimenet, mert a size 0 (l�sd lent)
    //Capacity �s size k�z�tti k�l�nbs�g
    cout << szamok2.size() << endl;//0
    cout << szamok2.capacity() << endl;//5
    szamok2.push_back(1);//Hozz�adtunk egy elemet a t�mbh�z
    cout << szamok2.size() << endl;//1
    cout << szamok2.capacity() << endl;//5
    szamok2.resize(3);//3-ra cs�kkentj�k a m�ret�t
    cout << szamok2.size() << endl;//3
    cout << szamok2.capacity() << endl;//5
    for (int i=0; i<szamok2.size(); i++)//.size() a t�mb elemsz�ma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//1 0 0 (a resize inicializ�lja a nem inicializ�lt elemeket)
    szamok2.resize(6,22);//a nem inicializ�lt r�szeket 22-es sz�mmal t�ltj�k fel
    for (int i=0; i<szamok2.size(); i++)//.size() a t�mb elemsz�ma
    {
        cout << szamok2[i] << " ";
    }
    cout << endl;//1 0 0 22 22 22 (a resize inicializ�lja a nem inicializ�lt elemeket)
    //T�mb t�lindexel�s
    cout << szamok2[szamok2.size()] << endl;//Ezt sajnos engedi a c++ :(
    //T�mb felt�lt�se
    cout << "6 elem beolvas�sa:" << endl;
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
    //�rt�kad�s
    cout << egesz_tomb[2] << endl;
    egesz_tomb[2] = 100;
    cout << egesz_tomb[2] << endl;
    //Els� elem el�r�se
    cout << egesz_tomb.front() << endl;
    cout << egesz_tomb[0] << endl;
    cout << egesz_tomb.at(0) << endl;
    //Utols� elem el�r�se
    cout << egesz_tomb.back() << endl;
    cout << egesz_tomb[egesz_tomb.size()-1] << endl;
    cout << egesz_tomb.at(egesz_tomb.size()-1) << endl;
    //T�mb elemeinek t�rl�se
    //Utols� elem t�rl�se
    egesz_tomb.pop_back();
    cout << egesz_tomb.back() << endl;
    //Felhaszn�l�s param�terk�nt: c�m �s �rt�k szerinti �tad�s
    be_vector(valos_tomb);
    ki_vector(valos_tomb);
    return 0;
}

void be_vector(vector <float> &tomb)//c�m szerinti �tad�s
{
    int db;
    float elem;
    cout << "H�ny eleme van a t�mbnek? " << endl;
    cin >> db;
    for (int i=0;i<db;i++)
    {
        cout << "�rd be a(z) " << i+1 << ". elemet: " << endl;
        cin >> elem;
        tomb.push_back(elem);
    }
}

void ki_vector(vector <float> tomb)//�rt�k szerinti �tad�s
{
    for (int i=0;i<tomb.size();i++)
    {
        cout << tomb[i] << " ";
    }
    cout << endl;
}

