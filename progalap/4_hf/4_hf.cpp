#include <iostream>
#include <vector>
using namespace std;
static void clearBuffer();

template <class T>
class Szam
{
public:
	T szam;

	explicit Szam(const T& szam)
		: szam(szam)
	{
	}
};
template <typename T>
static void IO();

int main()
{

	IO<double>();

	return 0;
}

static void clearBuffer(){
	cin.clear();
	cin.ignore(999, '\n');
}

template <typename T>
static void IO()
{
	setlocale(LC_ALL, "hun");
	int vDb;
	int maxP;
	bool van = false;
	bool hiba = false;
	int sorsz = 1;

	cout << "K�rem adja meg a tesztek sz�m�t!" << endl;
	do
	{
		hiba = false;
		cin >> vDb;
		if (cin.fail() || vDb < 0)
		{
			cout << "Rossz adatot adott meg, pr�b�lja meg �jb�l!" << endl;
			hiba = true;
			clearBuffer();
		}

	} while (hiba);

	vector<Szam<T>> tesztek;

	cout << "K�rem adja meg a maxim�lis pontsz�mot!" << endl;

	do
	{
		hiba = false;
		cin >> maxP;
		if (cin.fail() || maxP <= 0)
		{
			cout << "Rossz adatot adott meg, pr�b�lja meg �jb�l!" << endl;
			hiba = true;
			clearBuffer();
		}

	} while (hiba);

	for (int i = 0; i < vDb; i++)
	{
		T tmp;
		cout << "K�rem adja meg a(z) " << i + 1 << ". teszt pontsz�m�t!" << endl;
		do
		{
			hiba = false;
			cin >> tmp;
			if (cin.fail() || tmp<0 || tmp>maxP)
			{
				cout << "Rossz adatot adott meg, pr�b�lja meg �jb�l!" << endl;
				hiba = true;
				clearBuffer();
			}

		} while (hiba);
		tesztek.push_back(Szam<T>(tmp));
	}

	while (!van && sorsz != vDb + 1)
	{
		if (tesztek[sorsz].szam == tesztek[sorsz - 1].szam)
		{
			van = true;
		}
		sorsz++;
	}

	if (van)
	{
		cout << "Van olyan vizsg�z�, aki ugyanannyi pontot kapott, mint valamelyik szomsz�dja!" << endl;
		cout << "A sorsz�ma: " << sorsz << "." << endl;
	}
	else
	{
		cout << "Nincs olyan vizsg�z�, aki ugyanannyi pontot kapott, mint valamelyik szomsz�dja!" << endl;
	}
}

