/*  MINTA INPUT
 *
 *             //A program úgy van megírva, hogy minden névhez
 *             //csak egy jegy tartozhat!
 *
 *  3          //1. sor: tanulók száma (n)
 *  Jóska      //Utána lévő n sor: tanulók nevei sorrendben
 *  Pista
 *  Béla
 *  1          //Nevek utáni n sor: tanulók jegyei sorrendben, párosítva, tehát:
 *  2          //Jóska jegye egyes
 *  3          //Pista jegye kettes
 *             //Béla jegye hármas
 */

#include <iostream>
using namespace std;

int main()
{
	setlocale(LC_ALL, "hun");
	int tanulokDb;
	bool hiba = false;
	do
	{
		cout << "Kérem adja meg a tanulók számát!" << endl;
		cout << "Tanulók száma: ";
		cin >> tanulokDb;
		cout << endl;
		if (cin.fail() || tanulokDb < 0)
		{
			hiba = true;
			cout << "Hibás adatot adott meg (minimum 0 tanulót adjon meg)! \nKérem adja meg újra!" << endl;
			cin.clear();
			cin.ignore(999, '\n');
		}
		else
		{
			hiba = false;
		}
	}
	while (hiba);

	if (tanulokDb == 0)
	{
		cout << "Ebben az iskolában nincsenek tanulók! " << endl;
	}
	else
	{
		string* tanuloNevek = new string[tanulokDb];
		int* tanuloJegyek = new int[tanulokDb];
		
		for (int i = 0; i < tanulokDb; i++)
		{
			cout << "Kérem adja meg az " << i + 1 << ". tanuló nevét!" << endl;
			cout << i + 1 << ". tanuló neve: ";
			cin >> tanuloNevek[i];
			cout << endl;
		}

		cout << endl;

		cout << "Kérem adja meg a tanulók érdemjegyeit!" << endl;

		for (int i = 0; i < tanulokDb; i++)
		{
			do
			{
				cout << tanuloNevek[i] << " érdemjegye: ";
				cin >> tanuloJegyek[i];
				int jegy = tanuloJegyek[i];
				cout << endl;
				if (cin.fail() || jegy > 5 || jegy < 1)
				{
					hiba = true;
					cout << "Hibás adatot adott meg, a jegynek 1 és 5 között kell lennie! \nKérem adja meg újra!" <<
						endl;
					cin.clear();
					cin.ignore(999, '\n');
				}
				else
				{
					hiba = false;
				}
			}
			while (hiba);
		}

		int db1 = 0;
		int db2 = 0;
		int db3 = 0;
		int db4 = 0;
		int db5 = 0;

		for (int i = 0; i < tanulokDb; i++)
		{
			if (tanuloJegyek[i] == 1)
			{
				db1++;
			}
			else if (tanuloJegyek[i] == 2)
			{
				db2++;
			}
			else if (tanuloJegyek[i] == 3)
			{
				db3++;
			}
			else if (tanuloJegyek[i] == 4)
			{
				db4++;
			}
			else if (tanuloJegyek[i] == 5)
			{
				db5++;
			}
		}

		string* egyesTanulok = new string[db1];
		string* kettesTanulok = new string[db2];
		string* harmasTanulok = new string[db3];
		string* negyesTanulok = new string[db4];
		string* otosTanulok = new string[db5];

		db1 = 0;
		db2 = 0;
		db3 = 0;
		db4 = 0;
		db5 = 0;

		for (int i = 0; i < tanulokDb; i++)
		{
			if (tanuloJegyek[i] == 1)
			{
				egyesTanulok[db1] = tanuloNevek[i];
				db1++;
			}
			else if (tanuloJegyek[i] == 2)
			{
				kettesTanulok[db2] = tanuloNevek[i];
				db2++;
			}
			else if (tanuloJegyek[i] == 3)
			{
				harmasTanulok[db3] = tanuloNevek[i];
				db3++;
			}
			else if (tanuloJegyek[i] == 4)
			{
				negyesTanulok[db4] = tanuloNevek[i];
				db4++;
			}
			else if (tanuloJegyek[i] == 5)
			{
				otosTanulok[db5] = tanuloNevek[i];
				db5++;
			}
		}

		cout << endl;

		cout << "A tanulók érdemjegyek szerint:" << endl;
		cout << endl;

		cout << "Egyes érdemjeggyel rendelkező tanulók:" << endl;
		if (db1 == 0)
		{
			cout << "\t -Nincs ilyen tanuló" << endl;
		}
		else
		{
			for (int i = 0; i < db1; i++)
			{
				cout << "\t -" << egyesTanulok[i] << endl;
			}
		}
		cout << endl;

		cout << "Kettes érdemjeggyel rendelkező tanulók:" << endl;
		if (db2 == 0)
		{
			cout << "\t -Nincs ilyen tanuló" << endl;
		}
		else
		{
			for (int i = 0; i < db2; i++)
			{
				cout << "\t -" << kettesTanulok[i] << endl;
			}
		}
		cout << endl;

		cout << "Hármas érdemjeggyel rendelkező tanulók:" << endl;
		if (db3 == 0)
		{
			cout << "\t -Nincs ilyen tanuló" << endl;
		}
		else
		{
			for (int i = 0; i < db3; i++)
			{
				cout << "\t -" << harmasTanulok[i] << endl;
			}
		}
		cout << endl;

		cout << "Négyes érdemjeggyel rendelkező tanulók:" << endl;
		if (db4 == 0)
		{
			cout << "\t -Nincs ilyen tanuló" << endl;
		}
		else
		{
			for (int i = 0; i < db4; i++)
			{
				cout << "\t -" << negyesTanulok[i] << endl;
			}
		}
		cout << endl;

		cout << "Ötös érdemjeggyel rendelkező tanulók:" << endl;
		if (db5 == 0)
		{
			cout << "\t -Nincs ilyen tanuló" << endl;
		}
		else
		{
			for (int i = 0; i < db5; i++)
			{
				cout << "\t -" << otosTanulok[i] << endl;
			}
		}
		cout << endl;
	}
	return 0;
}
