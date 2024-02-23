#include <iostream>
#include <vector>
using namespace std;

typedef struct
{
	int gyarto;
	int fajta;
	int ar;
}szalon;


static int maxAr(int n, vector<szalon> szalonok, int gyarto);
int main()
{
	vector<szalon> szaloncukrok;
	vector<szalon> dragak;
	vector<int> gyartokDb;
	vector<int> egyedik;
	int n;
	int m;
	int k;
	cin >> n;
	cin >> m;
	cin >> k;
	gyartokDb.reserve(m);
	for (int i = 0; i < m; i++) {
		gyartokDb.push_back(0);
	}

	dragak.reserve(m);
	for (int i = 0; i < m; i++) {
		dragak.push_back(szalon{ 0,0,0 });
	}
	egyedik.reserve(k);
	for (int i = 0; i < k; i++) {
		egyedik.push_back(0);
	}

	for(int i = 0; i < n; i++)
	{
		int a;
		int b;
		int c;
		cin >> a;
		cin >> b;
		cin >> c;
		szaloncukrok.push_back(szalon{a,b,c});
	}

	int min = 0;

	for (int i = 0; i < n; i++)
	{
		if(szaloncukrok.at(i).ar<szaloncukrok.at(min).ar)
		{
			min = i;
		}
	}

	int legolcsobbGy = szaloncukrok.at(min).gyarto;
	int legolcsobbF = szaloncukrok.at(min).fajta;

	cout << legolcsobbGy << " " << legolcsobbF << endl;

	for (int i = 0; i < n; i++)
	{
		gyartokDb.at(szaloncukrok.at(i).gyarto-1)++;
	}

	int maxIndex = 0;
	int maxErtek = 0;

	for(int i = 0; i < gyartokDb.size(); i++)
	{
		if(gyartokDb.at(i) > maxErtek)
		{
			maxErtek = gyartokDb.at(i);
			maxIndex = i;
		}
	}

	cout << maxIndex+1 << endl;

	int gyartoDb = 0;
	for(int i = 0; i < m; i++)
	{
		if(gyartokDb.at(i) > 0)
		{
			dragak.at(gyartoDb).gyarto = i+1;
			dragak.at(gyartoDb).ar = maxAr(n, szaloncukrok, i+1);
			gyartoDb++;
		}
	}

	cout << gyartoDb << " ";
	for (szalon elem : dragak)
	{
		if(elem.ar!=0)
		{
			cout << elem.gyarto << " " << elem.ar << " ";
		}
		
	}
	cout << endl;

	int fajtakDb = gyartoDb;

	int egyediDb = 0;

	for(int i = 0; i < m; i++)
	{
		if(gyartokDb.at(i) == 1)
		{
			egyedik.push_back(i);
			egyediDb++;
		}
	}

	cout << fajtakDb << endl;

	cout << egyediDb << " ";

	for(int elem : egyedik)
	{
		if(elem != 0)
		{
			cout << elem << " ";
		}
		
	}

    return 0;
}

static int maxAr (int n, vector<szalon> szalonok, int gyarto)
{
	int max = -1;
	for(int i = 0; i < n; i++)
	{
		if(gyarto == szalonok.at(i).gyarto && szalonok.at(i).ar > max)
		{
			max = szalonok.at(i).ar;
		}
	}
	return max;
}