#include <iostream>
using namespace std;
void clearInput();

int main()
{
	setlocale(LC_ALL, "hun");


	int t;
	int napDb;
	int db = 0;
	bool hiba = false;

	do
	{
		cout << "K�rem adja meg a t�rsas�g nagys�g�t!" << endl;
		cin >> t;
		if (cin.fail() || t <= 0)
		{
			hiba = true;
			clearInput();
		}else{
			hiba = false;
		}
	}
	while (hiba);


	do
	{
		cout << "K�rem adja meg a napok sz�m�t!" << endl;
		cin >> napDb;
		if (cin.fail() || napDb <= 0)
		{
			hiba = true;
			clearInput();
		}else{
			hiba = false;
		}
		
	}
	while (hiba);

	int* napok = new int [napDb];

	for (int i = 0; i < napDb; i++)
	{
		do
		{
			cout << "K�rem adja meg az " << i + 1 << ". napon elk�lt�tt �sszeget!" << endl;
			cin >> napok[i];
			if (cin.fail() || napok[i] < 0)
			{
				hiba = true;
				clearInput();
			}else{
				hiba = false;
			}
		} while (hiba);
	}

	for (int i = 0; i < napDb; i++)
	{
		if (napok[i] / (float)t > 100)
		{
			db++;
		}
	}

	cout << db << " olyan nap volt ahol fejenk�nt 100 eur�n�l t�bbet k�lt�ttek." << endl;

	return 0;
}

static void clearInput()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
