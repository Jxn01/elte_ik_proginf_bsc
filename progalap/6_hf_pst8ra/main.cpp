#include <iostream>
using namespace std;

typedef struct
{
	int reggel;
	int delutan;
} THom;

static void clearInputBuffer();
static void input(int& result, const string inputMessage = "", const string errorMessage = "",
                  const string inputReply = "", const int minErt = _I8_MIN, const int maxErt = _I8_MAX);
static void input(THom result[100][100], const int db1, const int db2, const string inputMessage1 = "",
                  const string inputMessage2 = "", const string errorMessage = "", const string inputReply1 = "",
                  const string inputReply2 = "", const int minErt = _I32_MIN, const int maxErt = _I32_MAX);

int main()
{
	setlocale(LC_ALL, "hun");
	//adatszerkezetek, változók

	int varosDb = 0;
	int napDb = 0;
	int dbKisebb = 0;
	int maxVaros = 0;
	const int minHom = -89;
	const int maxHom = 58;
	const int maxVarosDb = 100;
	const int maxNapDb = 100;
	double reggeliAtl[maxVarosDb];

	input(varosDb, "Kérem adja meg a városok számát!", "Rossz adatot adott meg, próbálja újra!", "A városok száma: ",
	      0);
	input(napDb, "Kérem adja meg a napok számát!", "Rossz adatot adott meg, próbálja újra!", "A napok száma: ", 0);
	THom homersekletek[maxVarosDb][maxNapDb];
	input(homersekletek, varosDb, napDb, "Írd be a reggeli hõmérsékletet!", "Írd be a délutáni hõmérsékletet!",
	      "Rossz adatot adtál meg!\nA hõmérsékletnek -89 és 58 C fok között kell lennie!", "Reggeli hõmérséklet: ",
	      "Délutáni hõmérséklet: ", minHom, maxHom);

	for (int i = 0; i < varosDb; i++)
	{
		for(int j = 0; j < napDb; j++)
		{
			if(homersekletek[i][j].delutan<homersekletek[i][j].reggel)
			{
				dbKisebb++;
			}
			reggeliAtl[i] += homersekletek[i][j].reggel;
		}
		reggeliAtl[i] = reggeliAtl[i] / napDb;
	}

	for(int i = 0; i < varosDb; i++)
	{
		if(reggeliAtl[i]<=reggeliAtl[maxVaros])
		{
			maxVaros=i;
		}
	}

	cout << dbKisebb << " alkalommal volt kisebb a délutáni hõmérséklet, mint a reggeli." << endl;
	cout << maxVaros << ". város rendelkezik a legnagyobb reggeli átlaghõmérséklettel." << endl;

	return 0;
}

static void input(THom result[100][100], const int db1, const int db2, const string inputMessage1,
                  const string inputMessage2, const string errorMessage, const string inputReply1,
                  const string inputReply2, const int minErt, const int maxErt)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db1; i++)
	{
		for (int j = 0; j < db2; j++)
		{
			int reggel = 0;
			int delutan = 0;
			input(reggel, inputMessage1, errorMessage, inputReply1, minErt, maxErt);
			input(delutan, inputMessage2, errorMessage, inputReply2, minErt, maxErt);
			result[i][j].reggel = reggel;
			result[i][j].delutan = delutan;
		}
	}
}

static void input(int& result, const string inputMessage, const string errorMessage, const string inputReply,
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
}

static void clearInputBuffer()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
