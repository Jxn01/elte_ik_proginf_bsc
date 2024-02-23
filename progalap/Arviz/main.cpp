#include <iostream>

using namespace std;
typedef struct
{
	int kezd;
	int veg;
	int hossz;
} szakasz;
static void input(int& result, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
static void input(int*& result, const int& db, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
static void output(szakasz*& szakaszok, int& szakaszDb);

int main()
{
	setlocale(LC_ALL, "hun");
	//adatszerkezetek, változók
	int mDb;
	input(mDb);
	int *meresek = new int[mDb];
	input(meresek, mDb);
	int szakaszDb = 0;
	szakasz* szakaszok = new szakasz[mDb];

	for (int i = 0; i < mDb; i++)
	{
		if(meresek[i]>800)
		{
			szakaszok[szakaszDb].kezd = i+1;
			int j;
			for (j = i + 1; j <= mDb && meresek[j] > 800; j++)
			i = j;
			szakaszok[szakaszDb].veg = i+1;
			szakaszok[szakaszDb].hossz = szakaszok[szakaszDb].veg - szakaszok[szakaszDb].kezd + 1;
			szakaszDb++;
		}
	}

	output(szakaszok, szakaszDb);
	return 0;
}

static void output(szakasz*& szakaszok, int& szakaszDb)
{
	setlocale(LC_ALL, "hun");
	
	if(szakaszDb==0)
	{
		cout << 0 << endl;
	}else
	{
		cout << szakaszDb << endl;
		for (int i = 0; i < szakaszDb; i++)
		{
			cout << szakaszok[i].kezd << " ";
			cout << szakaszok[i].veg << " ";
			
		}
		cout << endl;

		for (int i = 0; i < szakaszDb; i++)
		{
			cout << szakaszok[i].hossz << " ";
			
		}
		cout << endl;
	}

	
}

static void input(int& result, const string& inputMessage, const string& errorMessage, const string& inputReply)
{
	cin >> result;
}

static void input(int*& result, const int& db, const string& inputMessage, const string& errorMessage, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db; i++)
	{
		cin >> result[i];
	}
}