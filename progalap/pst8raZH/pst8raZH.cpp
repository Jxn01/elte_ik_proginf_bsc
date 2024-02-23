#include <iostream>
#include <vector>
#include <set>

using namespace std;

typedef struct
{
	string gyarto;
	string fajta;
	int ar;
}szaloncukor;

typedef struct
{
	int gyartoDb;
	string fajtaNev;
}fajta;

typedef struct
{
	string gyartoNev;
	set<string> fajtak;
}gyarto;

typedef struct
{
	string fajtaNev;
	int max;
}maximum;

typedef struct
{
	string fajtaNev;
	int ar;
}fajtaAr;

typedef struct
{
	string gyartoNev;
	vector<fajtaAr> arak;
}gyartoAr;

int main()
{
	vector<szaloncukor> szaloncukrok;
	vector<fajta> fajtak;
	vector<gyarto> gyartok;
	vector<maximum> maximumFajtak;
	vector<gyartoAr> gyartoArak;

	int n;
	cin >> n;

	szaloncukrok.reserve(n);

	for(int i=0; i < n; i++)
	{
		szaloncukor temp = szaloncukor{};
		cin >> temp.gyarto;
		cin >> temp.fajta;
		cin >> temp.ar;

		szaloncukrok.push_back(temp);
	}

	cout << "#" << endl;

	int legolcsobb = 20001;
	string legolcsobbGyarto;
	string legolcsobbFajta;

	for(szaloncukor elem : szaloncukrok)
	{
		if(elem.ar<legolcsobb)
		{
			legolcsobb = elem.ar;
			legolcsobbGyarto = elem.gyarto;
			legolcsobbFajta = elem.fajta;
		}
	}

	cout << legolcsobbFajta << " " << legolcsobbGyarto << endl;

	cout << "#" << endl;

	bool vanCsakEgyGyart = false;
	string csakEgyGyart;

	for(int i = 0; i < n; i++)
	{
		bool benneVan = false;
		for(int j=0; j<fajtak.size();j++)
		{
			if(szaloncukrok.at(i).fajta == fajtak.at(j).fajtaNev){
				fajtak.at(j).gyartoDb++;
				benneVan = true;
			}
		}

		if(!benneVan)
		{
			fajtak.push_back(fajta{ 1, szaloncukrok.at(i).fajta });
		}
	}

	for(int i = 0; i < fajtak.size() && !vanCsakEgyGyart; i++)
	{
		if(fajtak.at(i).gyartoDb==1)
		{
			vanCsakEgyGyart = true;
			csakEgyGyart = fajtak.at(i).fajtaNev;
		}
	}

	if(vanCsakEgyGyart)
	{
		cout << csakEgyGyart << endl;
	}else
	{
		cout << "NINCS" << endl;
	}

	cout << "#" << endl;

	cout << fajtak.size() << endl;

	for(fajta elem : fajtak)
	{
		cout << elem.fajtaNev << endl;
	}

	cout << "#" << endl;

	for(szaloncukor elem : szaloncukrok)
	{
		bool benneVan1 = false;
		bool benneVan2 = false;
		for(int i = 0; i < gyartok.size(); i++)
		{
			if(elem.gyarto == gyartok.at(i).gyartoNev)
			{
				gyartok.at(i).fajtak.insert(elem.fajta);
				benneVan1 = true;
			}

			if(elem.gyarto == gyartoArak.at(i).gyartoNev)
			{
				benneVan2 = true;
				fajtaAr tmp;
				tmp.ar = elem.ar;
				tmp.fajtaNev = elem.fajta;
				gyartoArak.at(i).arak.push_back(tmp);
			}
		}

		if(!benneVan2)
		{
			fajtaAr tmp;
			tmp.ar = elem.ar;
			tmp.fajtaNev = elem.fajta;
			vector<fajtaAr> tmpp;
			tmpp.push_back(tmp);
			gyartoArak.push_back(gyartoAr{ elem.gyarto, tmpp });
		}

		if (!benneVan1)
		{
			set<string> tmp;
			tmp.insert(elem.fajta);
			gyartok.push_back(gyarto{ elem.gyarto, tmp });
		}
	}

	string legtobbetGyarto;
	int max = 0;
	for(gyarto elem : gyartok)
	{
		if(elem.fajtak.size()> max)
		{
			max = elem.fajtak.size();
			legtobbetGyarto = elem.gyartoNev;
		}
	}

	cout << legtobbetGyarto << endl;

	cout << "#" << endl;

	bool vanDragaCukor = false;
	string dragaCukorGyarto;

	for(szaloncukor elem : szaloncukrok)
	{
		bool benneVan = false;
		for(int i = 0; i < maximumFajtak.size(); i++)
		{
			maximum jelenlegi = maximumFajtak.at(i);

			if(elem.fajta == jelenlegi.fajtaNev)
			{
				benneVan = true;
				if(elem.ar > jelenlegi.max)
				{
					jelenlegi.max = elem.ar;
				}
			}
		}

		if(!benneVan)
		{
			maximumFajtak.push_back(maximum{ elem.fajta, elem.ar });
		}
	}

	for(gyartoAr gyarto: gyartoArak)
	{
		bool tempJo = false;

		for(fajtaAr fajta: gyarto.arak)
		{
			for(maximum maximumFajta: maximumFajtak)
			{
				if(fajta.fajtaNev == maximumFajta.fajtaNev)
				{
					if(fajta.ar < maximumFajta.max)
					{
						tempJo = true;
					}else
					{
						tempJo = false;
						break;
					}
				}
			}
		}

		if(tempJo)
		{
			dragaCukorGyarto = gyarto.gyartoNev;
			vanDragaCukor = true;
			break;
		}
	}

	if(vanDragaCukor)
	{
		cout << dragaCukorGyarto << endl;
	}else
	{
		cout << "NINCS" << endl;
	}

	return 0;
}