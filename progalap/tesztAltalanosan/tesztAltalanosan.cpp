#include <iostream>
#include <vector>
using namespace std;
static void clearBuffer();

template <class T>
class Szam {
public:
	T szam;

	explicit Szam(const T& szam)
		: szam(szam)
	{
	}
};

int main()
{
	setlocale(LC_ALL,"hun");

	bool van = false;
	int vDb = 0;

	int sorsz=1;

	vector<T> tesztek = input(&vDb);

	while(!van&&sorsz!=vDb+1)
	{
		if(tesztek[sorsz]==tesztek[sorsz-1])
		{
			van = true;
		}
		sorsz++;
	}

	if(van)
	{
		cout << "Van olyan vizsg�z�, aki ugyanannyi pontot kapott, mint valamelyik szomsz�dja!" << endl;
		cout << "A sorsz�ma: " << sorsz << "." << endl;
	}else
	{
		cout << "Nincs olyan vizsg�z�, aki ugyanannyi pontot kapott, mint valamelyik szomsz�dja!" << endl;
	}

	return 0;
}

static void clearBuffer(){
	cin.clear();
	cin.ignore(999, '\n');
}

template <typename T>
void input(int *vDb, vector<T> tesztek)
{
	int maxP;
	bool hiba = false;
	cout << "K�rem adja meg a tesztek sz�m�t!" << endl;
	do
	{
		hiba = false;
		cin >> *vDb;
		if (cin.fail() || *vDb < 0)
		{
			cout << "Rossz adatot adott meg, pr�b�lja meg �jb�l!" << endl;
			hiba = true;
			clearBuffer();
		}

	} while (hiba);

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

	for (int i = 0; i < *vDb; i++)
	{
		cout << "K�rem adja meg a(z) " << i + 1 << ". teszt pontsz�m�t!" << endl;
		do
		{
			hiba = false;
			T tmp;
			cin >> tmp;
			if (cin.fail() || tmp<0 || tmp>maxP)
			{
				cout << "Rossz adatot adott meg, pr�b�lja meg �jb�l!" << endl;
				hiba = true;
				clearBuffer();
			}

			tesztek.push_back(tmp);

		} while (hiba);
	}
}
