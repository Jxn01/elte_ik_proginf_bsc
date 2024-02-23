#include <iostream>
#include <math.h>

using namespace std;
static void clearInputBuffer();
static void input(int& result, int also, int felso, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
int main()
{
	setlocale(LC_ALL, "hun");
	//adatszerkezetek, v�ltoz�k
	int n;
	int m;
	 
	input(n, 2, 1000, "K�rem adja meg a csapatok sz�m�t!", "Csak sz�mot adjon meg, a csapatok sz�ma minimum 2, maximum 1000 lehet!", "Csapatok sz�ma: ");
	input(m, 1, 10000, "K�rem adja meg a m�rk�z�sek sz�m�t!", "Csak sz�mot adjon meg, a m�rk�z�sek sz�ma minimum 1, maximum 10000 lehet!", "M�rk�z�sek sz�ma: ");

	int *gy = new int[n];
	int *v = new int[n];

	for(int i = 0; i < n; i++)
	{
		input(gy[i], 0, floor(log2(gy[i])));
	}
	




	return 0;
}

static void input(int& result, int also, int felso,  const string& inputMessage, const string& errorMessage, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	int also;
	int felso;

	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || result > felso || result < also)
		{
			error = true;
			cout << errorMessage << endl;
			clearInputBuffer();
		}
		else
		{
			error = false;
		}
	} while (error);
}

static void clearInputBuffer() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}