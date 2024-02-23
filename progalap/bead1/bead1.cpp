#include <iostream>
using namespace std;
int main()
{
	int versenyzokSz;
	int pontokSz;
	int pont;
	cin >> versenyzokSz;
	cin >> pontokSz;
	int* pontok = new int[pontokSz];
	int db = 0;

	for(int i=0; i<pontokSz;i++)
	{
		pontok[i] = 0;
	}

	for(int i=0; i<versenyzokSz; i++)
	{
		db = 0;
		cin >> pont;
		pontok[pont]++;
		for(int j=pont-1;j>0;j--)
		{
			if(j!=-1)
			{
				db += pontok[j];
			}
			
		}

		cout << db << "\t";
	}

	return 0;
}
