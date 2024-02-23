#include <functional>
#include <iostream>
#include <regex>
#include <sstream>
#include <string>
#include <vector>

using namespace std;
static void clearInputBuffer();
static void input(int& result, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
static void input(int*& result, const int& db, const string& inputMessage = "", const string& errorMessage = "", const string& inputReply = "");
static vector<string> split(string s, string del);

class Ember
{
	string vezeteknev;
	string keresztnev;
	string lakohely;
	int pontszam;

public:
	Ember(const string& vezeteknev, const string& keresztnev, const string& lakohely, int pontszam)
		: vezeteknev(vezeteknev),
		  keresztnev(keresztnev),
		  lakohely(lakohely),
		  pontszam(pontszam)
	{
	}

	string vezeteknev1() const
	{
		return vezeteknev;
	}

	string keresztnev1() const
	{
		return keresztnev;
	}

	string lakohely1() const
	{
		return lakohely;
	}

	int pontszam1() const
	{
		return pontszam;
	}
};

int main()
{
	setlocale(LC_ALL, "hun");
	//adatszerkezetek, változók
	vector<Ember> emberek;


	int edb = 0;

	input(edb, "Kérem adja meg az emberek számát!", "Rossz adatot adott meg (0 és 100 között)!", "Emberek száma: ");
	string sor;
	bool hiba = false;
	for (int i = 0; i < edb; i++)
	{
		cout << "Kérem adja meg az " << i + 1 << ". ember adatait! (Vezetéknév Keresztnév;Város;Pontszám)!" << endl;
		cout << "Az " << i + 1 << ". ember adatai: ";
		getline(cin, sor, '\n');
		clearInputBuffer();
		vector<string> adatok = split(sor, ";");
		string keresztnev;
		string vezeteknev;
		string varos;
		int pont;
		if(split(adatok.at(0), " ").size()>2)
		{
			vezeteknev = split(adatok.at(0), " ").at(0);
			keresztnev = split(adatok.at(0), " ").at(1) + " " + split(adatok.at(0), " ").at(2);
		}else
		{
			vezeteknev = split(adatok.at(0), " ").at(0);
			keresztnev = split(adatok.at(0), " ").at(1);
		}
		varos = adatok.at(1);
		pont = atoi(adatok.at(2).c_str());

		emberek.push_back(Ember(keresztnev, vezeteknev, varos, pont));
	}

	cout << "A budapestiek adatai: " << endl;

	for(Ember elem : emberek)
	{
		if(elem.lakohely1()=="Budapest")
		{
			cout << "Név: " << elem.vezeteknev1() << " " << elem.keresztnev1() << endl;
			cout << "Pontszám: " << elem.pontszam1() << endl;
		}
	}

	return 0;
}

static vector<string> split(string s, string del = " ")
{
	vector<string> adatok;
	int start = 0;
	int end = s.find(del);
	while (end != -1) {
		adatok.push_back(s.substr(start, end - start));
		start = end + del.size();
		end = s.find(del, start);
	}
	adatok.push_back(s.substr(start, end - start));
	return adatok;
}

static void input(int& result, const string& inputMessage, const string& errorMessage, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || result < 0 || result > 100)
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

static void input(int*& result, const int& db, const string& inputMessage, const string& errorMessage, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db; i++)
	{
		bool error = false;
		do
		{
			cout << inputMessage << endl;
			cout << inputReply;
			cin >> result[i];
			cout << endl;
			if (cin.fail() || cin.peek() != '\n')
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
}

static void clearInputBuffer() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}