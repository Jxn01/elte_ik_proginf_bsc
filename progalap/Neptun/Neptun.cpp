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

	input(zhdb, "K�rem adja meg azon tanul�k sz�m�t, akik norm�l ZH-t �rtak!", "0-n�l nagyobb sz�mot adjon meg!",
	      "Tanul�k sz�ma: ", 0);
	input(potdb, "K�rem adja meg azon tanul�k sz�m�t, akik p�t ZH-t �rtak!", "0-n�l nagyobb sz�mot adjon meg!",
	      "Tanul�k sz�ma: ", 0);

	Tanulo* zhk = new Tanulo[zhdb];
	Tanulo* potzhk = new Tanulo[potdb];

	input(zhdb, zhk, "K�rem adja meg azon tanul�k neptun k�dj�t �s a jegyeit, akik norm�l ZH-t �rtak! (p�lda form�tum: A1A2A3;4)");
	input(potdb, potzhk, "K�rem adja meg azon tanul�k neptun k�dj�t �s a jegyeit, akik p�t ZH-t �rtak! (p�lda form�tum: A1A2A3;4)");

	int db = 0;

	Tanulo* mind2zhk = metszet(zhk, potzhk, zhdb, potdb, db);

	cout << db << " db tanul� volt norm�l ZH-n �s p�t ZH-n is." << endl;

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
			cout << i+1 << ". tanul� k�dja �s jegye: ";
			getline(cin, sor, '\n');
			if (!regex_match(sor, regex("[A-Z0-9]{6};[0-9]{1}")))
			{
				error = true;
				clearInputBuffer();
				cout << "Val�sz�n�leg elrontotta a form�tumot, a helyes form�tum a k�vetkez�:\n\t-A neptun k�d 6 karakter hossz�, csupa nagybet�\n\t-A jegy 1 �s 5 k�z�tt kell hogy legyen\n\t-A neptun k�dot �s a jegyet egyetlen \";\" karakterrel kell elv�lasztani" << endl;
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
					cout << "Ez a tanul� m�r �rt ZH-t, ne adja meg m�g egyszer!" << endl;
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
