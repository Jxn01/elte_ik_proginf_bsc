#include <iostream>
#include <vector>
#include <any>

using namespace std;

class Halmaz
{
private:
	vector<int> halmaz;

public:
	Halmaz()
	{
		const vector<int> halm;
		halmaz = halm;
	}

	void beolvasas(int x)
	{
		halmaz.push_back(x);
	}

	void hozzaAd(int x)
	{
		if(!benneVanE(x))
		{
			halmaz.push_back(x);
		}
	}

	bool benneVanE(int x)
	{
		bool benneVan = false;
		for(int elem: halmaz)
		{
			if(elem==x)
			{
				benneVan = true;
			}
		}

		return benneVan;
	}

	int holVan(int x)
	{
		if(benneVanE(x))
		{
			for(int i = 0; i < meret(); i++)
			{
				if(halmaz.at(i)==x)
				{
					return i;
				}
			}
		}else
		{
			return NULL;
		}
	}

	int index(int i)
	{
		if(i<meret()&&i>=0)
		{
			return halmaz.at(i);
		}else
		{
			return NULL;
		}
	}

	void elvesz(int x)
	{
		if(benneVanE(x))
		{
			for (int i = 0; i < meret(); i++)
			{
				if (halmaz.at(i) == x)
				{
					if(meret()==1||meret()==0)
					{
						halmaz.clear();
					}else
					{
						halmaz.at(i) = halmaz.at(meret()-1);
						halmaz.pop_back();
					}
				}
			}
		}
	}

	void legyenEkkora(int x)
	{
		halmaz.reserve(x);
	}

	int meret() const
	{
		return halmaz.size();
	}

	void kiiras()
	{
		for(int elem:halmaz)
		{
			cout << elem << " ";
		}
		cout << endl;
	}

	bool ures()
	{
		if(meret()==0)
		{
			return true;
		}else
		{
			return false;
		}
	}

	Halmaz unio(Halmaz masikHalmaz)
	{
		Halmaz unio = Halmaz();
		unio.legyenEkkora(meret() + masikHalmaz.meret());
		for(int elem: halmaz)
		{
			unio.beolvasas(elem);
		}

		for(int i=0; i<masikHalmaz.meret();i++)
		{
			unio.hozzaAd(masikHalmaz.index(i));
		}

		return unio;
	}

	Halmaz metszet(Halmaz masikHalmaz)
	{
		Halmaz metszet = Halmaz();
		metszet.legyenEkkora(meret() + masikHalmaz.meret());
		for(int elem: halmaz)
		{
			bool benneVan = false;
			for(int i=0; i < masikHalmaz.meret() && !benneVan; i++)
			{
				if (masikHalmaz.benneVanE(elem))
				{
					metszet.beolvasas(elem);
					benneVan = true;
				}
			}
		}

		return metszet;
	}

	bool resze(Halmaz masikHalmaz)
	{
		if(meret()>masikHalmaz.meret())
		{
			return false;
		}

		if(metszet(masikHalmaz).meret() == meret())
		{
			return true;
		}else
		{
			return false;
		}

		return true;
	}

};

int main()
{
	setlocale(LC_ALL, "hun");
	Halmaz halmaz = Halmaz();
	halmaz.legyenEkkora(10);

	cout << "Kérem adjon meg számokat, mondjuk 10-et!" << endl;


	for (int i = 0; i < 10; i++)

	{
		cout << "Kérem adja meg az " << i + 1 << ".edik számot!" << endl;
		bool hiba = false;
		do
		{
			hiba = false;
			int x;
			cin >> x;

			if (cin.fail() || cin.peek() != '\n')
			{
				hiba = true;
				cout << "Nem számot adott meg, adja meg újból!" << endl;
			}
			else
			{
				halmaz.hozzaAd(x);
			}
			cin.clear();
			cin.ignore(999, '\n');

		} while (hiba);
	}

	Halmaz halmaz2 = Halmaz();
	halmaz2.beolvasas(1);
	halmaz2.beolvasas(2);
	halmaz2.beolvasas(3);
	halmaz2.beolvasas(4);
	halmaz2.beolvasas(5);

	cout << "Az eredeti halmaz:" << endl;
	halmaz.kiiras();
	cout << endl;

	cout << "A halmaz2 halmaz:" << endl;
	halmaz2.kiiras();
	cout << endl;

	cout << "A két halmaz uniója: " << endl;
	halmaz.unio(halmaz2).kiiras();
	cout << endl;

	cout << "A két halmaz metszete: " << endl;
	halmaz.metszet(halmaz2).kiiras();
	cout << endl;

	cout << "Az elsõ halmaz része a másodiknak: ";
	if(halmaz.resze(halmaz2))
	{
		cout << "igen" << endl;
		cout << endl;
	}else
	{
		cout << "nem" << endl;
		cout << endl;
	}

	cout << "Az második halmaz része az elsõnek: ";
	if (halmaz2.resze(halmaz))
	{
		cout << "igen" << endl;
		cout << endl;
	}
	else
	{
		cout << "nem" << endl;
		cout << endl;
	}
	


	return 0;
}
