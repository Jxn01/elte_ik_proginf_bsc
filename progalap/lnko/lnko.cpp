

#include <iostream>

using namespace std;


int main()
{
	setlocale(LC_ALL, "hun");

	int a = 0;
	int b = 0;
	int x = 0;
	int temp = 0;
	int r = 0;

	cout << "K�rem adjon meg 2 eg�sz pozit�v sz�mot!" << endl;
	cin >> a;
	cin >> b;

	while (cin.fail() || a <= 0 || b <= 0 || cin.peek()!= '\n'){
		cin.clear();
		cin.ignore(999,'\n');
		cout << "Hib�s �rt�keket adott meg, k�rem adja meg �ket �jb�l!" << endl;
		cin >> a;
		cin >> b;
	}

	if(a<b)
	{
		temp = a;
		a = b;
		b = temp;
	}

	r = a % b;

	while(r>0)
	{
		a = b;
		b = r;
		r = a % b;
	}

	x = b;

	cout << "A legnagyobb k�z�s oszt�: " << x << endl;

	

	return 0;
}

