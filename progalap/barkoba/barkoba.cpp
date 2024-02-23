
//Név: Oláh Norbert
//Neptun-kód: PST8RA
//E-mail: PST8RA@inf.elte.hu
//Feladat: Barkóba

/*Barkóba: Készítsünk programot, amiben ki kell
találni egy 1 és 100 közötti egész számot! A
felhasználó addig tippelhet, amíg ki nem találja a
program által „gondolt” számot. */


#include <iostream>
using namespace std;
int getInput();
void clearInput();
int randInt();

int main()
{
    setlocale(LC_ALL, "hun");

	int megoldas = randInt();
	int tipp;
	bool megoldott = false;
	bool hiba = false;


	do {
		try {
			tipp = getInput();
			hiba = false;

			if(tipp==megoldas)
			{
				cout << "OK" << endl;
				break;
			}

			if(tipp<megoldas)
			{
				cout << "nagyobb" << endl;
			}

			if(tipp>megoldas)
			{
				cout << "kissebb" << endl;
			}
		}
		catch (runtime_error) {
			cout << "\nCsak pozitív egész számokat adjon meg 1-től 100-ig!" << endl;
			hiba = true;
			clearInput();
		}
	} while (hiba||!megoldott);


}

static int getInput()
{
	setlocale(LC_ALL, "hun");
	int tipp;

	cout << "Tippeljen 1 és 100 között" <<	endl;
	cin >> tipp;

	if (cin.fail() || tipp < 1 || tipp > 100)
	{
		throw runtime_error("");
	}

	return tipp;
}

static void clearInput() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}

static int randInt()
{
	srand(time(NULL));
	return rand() % 100 + 1;
}