//Név: Oláh Norbert
//Neptun-kód: PST8RA
//E-mail: PST8RA@inf.elte.hu
//Feladat: Évszakok

/*Írjunk programot, amely megadja egy hónapról, hogy
melyik évszakba tartozik!*/

#include <iostream>
#include <string>

using namespace std;
static void clearInput();
int main()
{
	setlocale(LC_ALL, "hun");
	const string Havak [12] = {"januar", "februar", "marcius", "aprilis", "majus", "junius", "julius", "augusztus", "szeptember", "oktober", "november", "december"};
	const string Evszakok[4] = { "tél", "tavasz", "nyár", "õsz" };
	string evszak;
	string ho;
	bool hiba = true;
	int hsz;

	do
	{
		cout << "Kérem adjon meg egy hónapot! Példa: januar (ékezetes betûket ne használjon!)" << endl;
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

	cout << "A " << ho << " hónap a " << evszak << " évszakban van." << endl;

	return 0;
}

static void clearInput() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
