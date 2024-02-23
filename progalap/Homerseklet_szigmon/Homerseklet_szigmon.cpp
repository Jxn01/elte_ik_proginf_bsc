#include <iostream>
using namespace std;

void clearInput();

int main()
{
	setlocale(LC_ALL, "hun");

	int homDb;
	const int minH = -89;
	const int maxH = 58;
	bool szigmon = false;
	bool hiba = false;

	do
	{
		cout << "Kérem adja meg a hõmérsékletek számát!" << endl;
		cin >> homDb;
		if (cin.fail() || homDb < 2)
		{
			hiba = true;
			clearInput();
		}
		else {
			hiba = false;
		}
	} 	while (hiba);

	int* homk = new int[homDb];

	for (int i = 0; i < homDb; i++)
	{
		do
		{
			cout << "Kérem adja meg az " << i+1 << ". hõmérsékletet!" << endl;
			cin >> homk[i];
			if (cin.fail() || homk[i] < -89 || homk[i] > 58)
			{
				hiba = true;
				clearInput();
			}
			else {
				hiba = false;
			}
		} while (hiba);
	}

	int i = 2;

	while(i<=homDb && homk[i-1] > homk[i-2])
	{
		i++;
	}

	if(i>homDb)
	{
		szigmon = true;
		cout << "A hõmérséklet sorozat szigorúan monoton növekvõ!" << endl;
	}else
	{
		szigmon = false;
		cout << "A hõmérséklet sorozat NEM szigorúan monoton növekvõ!" << endl;
	}

	return 0;
}

static void clearInput()
{
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
}