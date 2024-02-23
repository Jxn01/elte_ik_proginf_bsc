#include <iostream>
#include <regex>
using namespace std;

typedef struct Tanulo
{
	string neptun;
	int jegy;

	Tanulo(string neptun, int jegy)
		: neptun(neptun),
		  jegy(jegy)
	{
	}

	Tanulo() = default;
};

static void clearInputBuffer();
static int input(int& result, const string inputMessage = "", const string errorMessage = "",
                 const string inputReply = "", const int minErt = INT32_MIN, const int = INT32_MAX);
static string input(string& result, const string inputMessage = "", const string errorMessage = "",
                    const string inputReply = "");
static void input(int zhdb, Tanulo* zhk, const string inputMessage="");
static void constructor(Tanulo* zhk, string sor, int iteration);
static Tanulo* metszet(Tanulo* zhk, Tanulo* potzhk, int zhdb, int potdb, int& db);

int main()
{
	setlocale(LC_ALL, "hun");
	int zhdb; // > 0
	int potdb; // > 0

	input(zhdb, "Kérem adja meg azon tanulók számát, akik normál ZH-t írtak!", "0-nál nagyobb számot adjon meg!",
	      "Tanulók száma: ", 0);
	input(potdb, "Kérem adja meg azon tanulók számát, akik pót ZH-t írtak!", "0-nál nagyobb számot adjon meg!",
	      "Tanulók száma: ", 0);

	Tanulo* zhk = new Tanulo[zhdb];
	Tanulo* potzhk = new Tanulo[potdb];

	input(zhdb, zhk, "Kérem adja meg azon tanulók neptun kódját és a jegyeit, akik normál ZH-t írtak! (példa formátum: A1A2A3;4)");
	input(potdb, potzhk, "Kérem adja meg azon tanulók neptun kódját és a jegyeit, akik pót ZH-t írtak! (példa formátum: A1A2A3;4)");

	int db = 0;

	Tanulo* mind2zhk = metszet(zhk, potzhk, zhdb, potdb, db);

	cout << db << " db tanuló volt normál ZH-n és pót ZH-n is." << endl;

	return 0;
}

static Tanulo* metszet(Tanulo* zhk, Tanulo* potzhk, int zhdb, int potdb, int& db)
{
	int hossz = zhdb + potdb;
	Tanulo* mind2zhk = new Tanulo[hossz];
	bool voltemar = false;
	for(int i = 0; i < zhdb;i++)
	{
		for(int j=0; j < potdb; j++)
		{
			if(zhk[i].neptun==potzhk[j].neptun)
			{
				for(int k = 0; k < db;k++)
				{
					if(zhk[i].neptun==mind2zhk[k].neptun)
					{
						voltemar = true;
					}
				}

				if(!voltemar)
				{
					mind2zhk[db] = zhk[i];
					db++;
				}

			}
		}
	}

	return mind2zhk;
}

static void constructor(Tanulo* zh, string sor, int iteration)
{
	string neptun = sor.substr(0, 6);
	int jegy = atoi(sor.substr(6,7).c_str());
	zh[iteration] = Tanulo(neptun, jegy);
}

static void input(int zhdb, Tanulo* zhk, const string inputMessage)
{
	clearInputBuffer();
	cout << inputMessage << endl;
	string sor = "";
	int db = 0;
	for (int i = 0; i < zhdb; i++)
	{
		bool error = false;
		do
		{
			cout << i+1 << ". tanuló kódja és jegye: ";
			getline(cin, sor, '\n');
			if (!regex_match(sor, regex("[A-Z0-9]{6};[0-9]{1}")))
			{
				error = true;
				clearInputBuffer();
				cout << "Valószínûleg elrontotta a formátumot, a helyes formátum a következõ:\n\t-A neptun kód 6 karakter hosszú, csupa nagybetû\n\t-A jegy 1 és 5 között kell hogy legyen\n\t-A neptun kódot és a jegyet egyetlen \";\" karakterrel kell elválasztani" << endl;
			}
			else
			{
				error = false;
			}
			for (int i = 0; i < db; i++)
			{
				if (zhk[i].neptun == sor.substr(0, 6))
				{
					error = true;
					cout << "Ez a tanuló már írt ZH-t, ne adja meg még egyszer!" << endl;
					clearInputBuffer();
				}
				
			}
		}
		while (error);
		constructor(zhk, sor, i);
		db++;
	}
}

static int input(int& result, const string inputMessage, const string errorMessage, const string inputReply,
                 const int minErt, const int maxErt)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || result <= minErt || result >= maxErt)
		{
			error = true;
			cout << errorMessage << endl;
			clearInputBuffer();
		}
		else
		{
			error = false;
		}
	}
	while (error);

	return result;
}

static void clearInputBuffer()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
