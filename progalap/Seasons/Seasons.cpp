//N�v: Ol�h Norbert
//Neptun-k�d: PST8RA
//E-mail: PST8RA@inf.elte.hu
//Feladat: �vszakok

/*�rjunk programot, amely megadja egy h�napr�l, hogy
melyik �vszakba tartozik!*/

#include <iostream>
#include <string>

using namespace std;
static void clearInput();
int main()
{
	setlocale(LC_ALL, "hun");
	const string Havak [12] = {"januar", "februar", "marcius", "aprilis", "majus", "junius", "julius", "augusztus", "szeptember", "oktober", "november", "december"};
	const string Evszakok[4] = { "t�l", "tavasz", "ny�r", "�sz" };
	string evszak;
	string ho;
	bool hiba = true;
	int hsz;

	do
	{
		cout << "K�rem adjon meg egy h�napot! P�lda: januar (�kezetes bet�ket ne haszn�ljon!)" << endl;
		cin >> ho;
		for(int i = 0; i < 12; i++)
		{
			if(ho.compare( Havak[i])==0)
			{
				hiba = false;
				hsz = i+1;
			}
		}
		if(hiba)
		{
			clearInput();
			hiba = true;
		}
	} while (hiba);


	evszak = Evszakok[((hsz / 3) % 4)];

	cout << "A " << ho << " h�nap a " << evszak << " �vszakban van." << endl;

	return 0;
}

static void clearInput() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
