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
		cout << "K�rem adja meg hogy h�ny r�sztvev� volt jelen!" << endl;
		cout << "R�sztvev�k sz�ma: ";
		cin >> db;
		if (cin.fail() || cin.peek() != '\n' || db < 0)
		{
			hiba = true;
			clearInput();
			cout << "Hib�s adatot adott meg, adja meg �jra" << endl;
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
			cout << "K�rem adja meg az " << i+1 <<". r�sztvev� �letkor�t!" << endl;
			cout << "R�sztvev� �letkora: ";
			cin >> eletkor;
			if (cin.fail() || cin.peek() != '\n' || eletkor < 10 || eletkor > 100)
			{
				hiba = true;
				clearInput();
				cout << "Hib�s adatot adott meg, adja meg �jra" << endl;
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
		cout << "Volt kiskor�!" << endl;
	}else
	{
		cout << "Nem volt kiskor�!" << endl;
	}
}

static void clearInput()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
