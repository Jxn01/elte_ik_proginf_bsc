#include <iostream>
#include <math.h>

using namespace std;
static void clearInputBuffer();
static void input(int& result, int also, int felso, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
int main()
{
	setlocale(LC_ALL, "hun");
	//adatszerkezetek, változók
	int n;
	int m;
	 
	input(n, 2, 1000, "Kérem adja meg a csapatok számát!", "Csak számot adjon meg, a csapatok száma minimum 2, maximum 1000 lehet!", "Csapatok száma: ");
	input(m, 1, 10000, "Kérem adja meg a mérkõzések számát!", "Csak számot adjon meg, a mérkõzések száma minimum 1, maximum 10000 lehet!", "Mérkõzések száma: ");

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