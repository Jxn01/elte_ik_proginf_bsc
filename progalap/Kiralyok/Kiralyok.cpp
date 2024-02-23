#include <iostream>
#include <string>
#include <vector>
using namespace std;


class Kiraly
{
public:
    string nev;
	int utodSzam;
	bool voltApa;

	Kiraly(const string& nev, int utodSzam, bool voltApa)
		: nev(nev),
		  utodSzam(utodSzam),
	      voltApa(voltApa)
	{
	}

	Kiraly() = default;
};

int main()
{
	setlocale(LC_ALL, "hun");
	vector<Kiraly> kiralyok;
    int n;
	int m;
    cin >> n;
	cin.ignore(999, '\n');
	for(int i = 0; i<n;i++)
	{
		string nev;
		getline(cin, nev, '\n');
		int charCounter = 0;
		int index = 0;
		for(char c : nev)
		{
			if(c == ' ')
			{
				charCounter++;
			}

			if(charCounter==2)
			{
				break;
			}

			index++;
		}

		nev = nev.substr(index+1, nev.back());
		kiralyok.push_back(Kiraly(nev, 0, false));
	}

	cin >> m;
	cin.ignore(999, '\n');

	for(int i = 0; i < m; i++)
	{
		string kinek;
		string ki; // ...utodja

		getline(cin, kinek, '\n');
		getline(cin, ki, '\n');


		bool apja = false;
		bool utod = false;

		Kiraly* kiraly2 = nullptr;
		for(Kiraly &kiraly : kiralyok)
		{
			if(kiraly.nev==kinek)
			{
				apja = true;
				kiraly.voltApa = true;
				kiraly2 = &kiraly;
			}

			if(kiraly.nev==ki)
			{
				utod = true;
			}

			if(apja&&utod)
			{
				kiraly2->utodSzam++;
				apja = false;
				utod = false;
			}

		}
		
	}
	cin.ignore(999, '\n');


	int eredmeny = 0;

	for(Kiraly &kiraly : kiralyok)
	{
		if(kiraly.utodSzam==0&&kiraly.voltApa)
		{
			eredmeny++;
		}
	}

	cout << eredmeny << endl;




    return 0;
}
