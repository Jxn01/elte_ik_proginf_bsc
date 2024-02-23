#ifndef INPUT_H
#define INPUT_H
#include <iostream>
using namespace std;

//input usage:
//result, (db1), (db2), (inputMsg), (errorMsg), (condition), (replyMsg)
//possible inputs:
//int, string, double, int array, string array, double array, int matrix

static void clearInputBuffer();
static void input(int& result, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(double& result, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(string& result, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(int*& result, const int& db, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(int**& result, const int& db1, const int& db2, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(string*& result, const int& db, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");
static void input(double*& result, const int& db, const string& inputMessage = "", const string& errorMessage = "", const bool& cond = NULL, const string& inputReply = "");

static void input(int& result, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || cond)
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

static void input(string& result, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || cond)
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

static void input(int*& result, const int& db, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db; i++)
	{
		int temp;
		input(temp, inputMessage, errorMessage, cond, inputReply);
		result[i] = temp;
	}
}
static void input(int**& result, int& db1, int& db2, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db1; i++)
	{
		for (int j = 0; j < db2; j++)
		{
			int temp;
			input(temp, inputMessage, errorMessage, cond, inputReply);
			result[i][j] = temp;
		}
	}
}

static void input(string*& result, const int& db, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db; i++)
	{
		string temp;
		input(temp, inputMessage, errorMessage, cond, inputReply);
		result[i] = temp;
	}
}

static void input(double& result, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	bool error = false;
	do
	{
		cout << inputMessage << endl;
		cout << inputReply;
		cin >> result;
		cout << endl;
		if (cin.fail() || cin.peek() != '\n' || cond)
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

static void input(double*& result, const int& db, const string& inputMessage, const string& errorMessage, const bool& cond, const string& inputReply)
{
	setlocale(LC_ALL, "hun");
	for (int i = 0; i < db; i++)
	{
		double temp;
		input(temp, inputMessage, errorMessage, cond, inputReply);
		result[i] = temp;
	}
}

static void clearInputBuffer() {
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}
#endif // INPUT_H