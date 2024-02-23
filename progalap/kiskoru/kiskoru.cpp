#include <iostream>
#include <array>
using namespace std;
void clearInput();

int main()
{
	setlocale(LC_ALL, "hun");

	int db = 0;
	bool voltKiskoru = false;
	bool hiba = false;

	do
	{
		cout << "Kérem adja meg hogy hány résztvevõ volt jelen!" << endl;
		cout << "Résztvevõk száma: ";
		cin >> db;
		if (cin.fail() || cin.peek() != '\n' || db < 0)
		{
			hiba = true;
			clearInput();
			cout << "Hibás adatot adott meg, adja meg újra" << endl;
		}
		else
		{
			hiba = false;
		}
	}
	while (hiba);

	auto resztvevok = new int[db];


	for (int i = 0; i < db; i++)
	{
		int eletkor = 0;
		do
		{
			cout << "Kérem adja meg az " << i+1 <<". résztvevõ életkorát!" << endl;
			cout << "Résztvevõ életkora: ";
			cin >> eletkor;
			if (cin.fail() || cin.peek() != '\n' || eletkor < 10 || eletkor > 100)
			{
				hiba = true;
				clearInput();
				cout << "Hibás adatot adott meg, adja meg újra" << endl;
			}
			else
			{
				hiba = false;
				resztvevok[i] = eletkor;
			}
		} while (hiba);
	}

	for(int i = 0; i < sizeof(resztvevok); i++)
	{
		if(resztvevok[i]<18)
		{
			voltKiskoru = true;
		}
	}

	if(voltKiskoru && db!=0)
	{
		cout << "Volt kiskorú!" << endl;
	}else
	{
		cout << "Nem volt kiskorú!" << endl;
	}
}

static void clearInput()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
