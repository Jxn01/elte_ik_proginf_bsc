
//Név: Oláh Norbert
//Neptun-kód: PST8RA
//E-mail: PST8RA@inf.elte.hu
//Feladat: Pénz

/*„Mennyi pénzt költhetsz el szórakozásra  ?
Elhatározod, hogy ha 5000 Ft-nál kevesebbet
keresel (azaz készpénzt kapsz), akkor azt
azonnal elköltöd – ennél nagyobb összeg
esetében azonban 5000 Ft egész számú
többszörösét a számládra utalod és csak a
maradékot költöd el.” */


#include <iostream>
using namespace std;
int getInput();
void clearInput();

int main()
{
	setlocale(LC_ALL, "hun");

	int kereset = 0;
	int maradek = 0;
	bool hiba = false;

	do{
		try {
			kereset = getInput();
			hiba = false;
		}
		catch (runtime_error) {
			cout << "\nCsak pozitív egész számokat adjon meg!\nA legkisebb címlet : 5 Ft" << endl;
			hiba = true;
			clearInput();
		}
	} while (hiba);

	


	if (kereset < 5000){
		cout << "\nElköltött pénz: " << kereset << " Ft" << endl;
	}else{
		maradek = kereset % 5000;
		cout << "\nElköltött pénz: " << maradek << " Ft" << endl;
		cout << "Elutalt pénz: " << kereset - maradek << " Ft" << endl;
	}
}

static int getInput()
{
	setlocale(LC_ALL, "hun");

	int kereset;

	cout << "Kérem adja meg a keresetét!" << endl;
	cout << "Kereset: ";
	cin >> kereset;

	if(cin.fail() || kereset < 0 || kereset % 5 != 0)
	{
		throw runtime_error("");
	}

	return kereset;
}

static void clearInput() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
