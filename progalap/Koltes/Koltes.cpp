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
		cout << "Kérem adja meg a társaság nagyságát!" << endl;
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
		cout << "Kérem adja meg a napok számát!" << endl;
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
			cout << "Kérem adja meg az " << i + 1 << ". napon elköltött összeget!" << endl;
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

	cout << db << " olyan nap volt ahol fejenként 100 eurónál többet költöttek." << endl;

	return 0;
}

static void clearInput()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
