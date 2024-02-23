/*
 Készítette: Oláh Norbert
 Neptun: PST8RA
 E-mail: pst8ra@inf.elte.hu
 Feladat: Progalap komplex beadandó téma
 */

//#define BIRO
#include <iostream>
#include <vector>
using namespace std;

// fuggvenydeklaraciok
vector<vector<int>> beolvasas();
vector<int> minimumKivalogatas(const vector<vector<int>>&);
void kiiratas(const vector<int>&);
static void clearInput();

int main()
{
	setlocale(LC_ALL, "hun"); // ekezetes karakterek lehetove tetele
	const vector<vector<int>> kutyak = beolvasas(); // kutyak matrix beolvasasa
	const vector<int> sorszamok = minimumKivalogatas(kutyak);
	// a kutyak matrixbol kivalogatjuk az eppen minimalis pontszamu kutyakat
	kiiratas(sorszamok); // kiiratjuk az eredmenyt

	return 0;
}

vector<vector<int>> beolvasas() // a fuggveny beolvassa a standard inputrol az adatokat
{
	vector<vector<int>> kutyak; // Matrix, merete: (resztvevokSzama + 2 (min és maxpontszam)) * szempontokSzama

	int resztvevokSzama;
	int szempontokSzama;

	bool hiba; // a bemenet ellenorzesenel hasznalt segedvaltozo

	do // a resztvevok szamanak beolvasasa
	{
		hiba = false;
		clog << "Kérem adja meg a szépségversenyen részt vevõ kutyák számát!" << endl;
		clog << "Résztvevõk száma: ";
		cin >> resztvevokSzama;
		clog << endl;
		if (cin.fail() || resztvevokSzama < 1 || resztvevokSzama > 100)
		{
			hiba = true;
			cerr << "Hibás adat! Adja meg újból az adatot! (Egy egész számot 1 és 100 között (zárt intervallum))\n\n" <<
				endl;
			clearInput();
		}
	}
	while (hiba);

	do // a szempontok szamanak beolvasasa
	{
		hiba = false;
		clog << "Kérem adja meg a szépségversenyen a szempontok számát!" << endl;
		clog << "Szempontok száma: ";
		cin >> szempontokSzama;
		clog << endl;
		if (cin.fail() || szempontokSzama < 1 || szempontokSzama > 100)
		{
			hiba = true;
			cerr << "Hibás adat! Adja meg újból az adatot! (Egy egész számot 1 és 100 között (zárt intervallum))\n\n" <<
				endl;
			clearInput();
		}
	}
	while (hiba);

	vector<int> maxPontszamok; // ket segedvaltozo a max- és minpontszamok eltarolasara
	vector<int> minPontszamok;

	for (int i = 0; i < szempontokSzama; i++) // maxpontszamok bekerese
	{
		int maxPontszam;
		do
		{
			hiba = false;
			clog << "Kérem adja meg a szépségversenyen az " << i + 1 << ". szempont maximum pontszámát!" << endl;
			clog << i + 1 << ". szempont maximum pontszáma: ";
			cin >> maxPontszam;
			clog << endl;
			if (cin.fail() || maxPontszam < 1 || maxPontszam > 100)
			{
				hiba = true;
				cerr <<
					"Hibás adat! Adja meg újból az adatot! (Egy egész számot 1 és 100 között (zárt intervallum))\n\n" <<
					endl;
				clearInput();
			}
			else
			{
				maxPontszamok.push_back(maxPontszam);
			}
		}
		while (hiba);
	}

	for (int i = 0; i < szempontokSzama; i++) // minpontszamok bekerese
	{
		int minPontszam;
		do
		{
			hiba = false;
			clog << "Kérem adja meg a szépségversenyen az " << i + 1 << ". szempont minimum pontszámát!" << endl;
			clog << i + 1 << ". szempont minimum pontszáma: ";
			cin >> minPontszam;
			clog << endl;
			if (cin.fail() || minPontszam < 1 || minPontszam > maxPontszamok.at(i))
			{
				hiba = true;
				cerr << "Hibás adat! Adja meg újból az adatot! (Egy egész számot 1 és " << maxPontszamok.at(i) <<
					" között (zárt intervallum))\n\n" << endl;
				clearInput();
			}
			else
			{
				minPontszamok.push_back(minPontszam);
			}
		}
		while (hiba);
	}

	kutyak.push_back(maxPontszamok); // a kutyak matrix elso ket soraban eltaroljuk a min es maxpontszamokat
	kutyak.push_back(minPontszamok);
	// innentol amikor a kutyak matrixon vegigmegyunk es csak a kutyakat akarjuk megnezni, a ciklust 2-tol kell kezdeni

	for (int i = 2; i < resztvevokSzama + 2; i++) // minden kutya ...
	{
		vector<int> kutya;
		kutyak.push_back(kutya);

		for (int j = 0; j < szempontokSzama; j++) // ... minden pontszamat bekerjuk
		{
			int pontszam;
			do
			{
				hiba = false;
				clog << "Kérem adja meg a szépségversenyen az " << i + 1 - 2 << ". kutya " << j + 1 <<
					". szempont szerint elért pontszámát!" << endl;
				clog << i + 1 - 2 << ". kutya " << j + 1 << ". szempont szerint elért pontszáma: ";
				cin >> pontszam;
				clog << endl;
				if (cin.fail() || pontszam < 0 || pontszam > maxPontszamok.at(j))
				{
					hiba = true;
					cerr << "Hibás adat! Adja meg újból az adatot! (Egy egész számot 0 és " << maxPontszamok.at(j) <<
						" között (zárt intervallum))\n\n" << endl;
					clearInput();
				}
				else
				{
					kutyak.at(i).push_back(pontszam);
				}
			}
			while (hiba);
		}
	}

	return kutyak;
}

vector<int> minimumKivalogatas(const vector<vector<int>>& kutyak)
// a fuggveny kivalogatja az eppen minimalis pontszamu kutyakat
{
	vector<int> sorszamok; // deklaraljuk a megoldas listat, ebben lesznek a jo kutyak sorszamai
	const vector<int> minPontszamok = kutyak.at(1);

	for (int i = 2; i < kutyak.size(); i++) // vegigmegyunk a kutyakon
	{
		vector<int> kutya = kutyak.at(i); // egy segedvaltozoban eltaroljuk a jelenlegi kutyat
		bool nemVoltMeg = true; // egy logikai segedvaltozot inicializalunk

		for (int j = 0; j < kutya.size() && nemVoltMeg; j++) // vegigmegyunk a jelenlegi kutya pontszamain
		{
			if (kutya.at(j) == minPontszamok.at(j))
				// megnezzuk hogy a jelenlegi kutya jelenlegi pontszama minimumpontszam-e
			{
				sorszamok.push_back(i + 1 - 2);
				// ha igen akkor beletesszuk a sorszamat a megoldas listaba ( i + 1 mert index, - 2 mert 2-vel elorebb indult a ciklus a min- és maxpontszamok miatt)

				nemVoltMeg = false; // a segedvaltozo megallitja a ciklust, ha egy kutya mar ert el minimalis pontszamot
			}
		}
	}

	return sorszamok;
}

void kiiratas(const vector<int>& sorszamok) // a fuggveny kiiratja a megoldas listat a standard kimenetre
{
	clog << "Az éppen minimális pontszámú kutyák száma: ";
	if (sorszamok.empty()) // ha nem volt egy eppen minimalis pontszamu kutya sem, akkor 0-at irunk ki
	{
		cout << "0" << endl;
		clog << endl;
	}
	else // egyebkent ...
	{
		cout << sorszamok.size() << " "; // kiiratjuk az eppen minimalis pontszamu kutyak szamat, majd
		clog << endl;
		clog << "Az éppen minimális pontszámú kutyák sorszámai növekvõ sorrendben: ";
		for (const int elem : sorszamok) // kiiratjuk az osszes ilyen kutya sorszamat
		{
			cout << elem << " ";
		}
		clog << endl;
	}

#ifndef BIRO
	clog << "\nA kilépéshez nyomjon le egy billentyût!" << endl;
	system("pause");
#endif
}

static void clearInput()
{
	cin.clear();
	cin.ignore(999, '\n');
}
