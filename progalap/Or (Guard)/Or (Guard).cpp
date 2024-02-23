#include <iostream>
using namespace std;

int main()
{

	int m;
	int n;

	cin >> m;

	cin >> n;

	if (m == 0)
	{
		cout << 1 << endl;
	}else
	{
		bool* orNapok = new bool[m + 1];

		for (int i = 0; i < n && m != 0; i++)
		{
			int orElso;
			int orUtolso;
			cin >> orElso;
			cin >> orUtolso;

			for (int j = orElso; j <= orUtolso; j++)
			{
				orNapok[j] = true;
			}

		}

		bool volt = true;
		int melyik = 0;

		for (int i = 1; i <= m && volt; i++)
		{
			if (!orNapok[i])
			{
				volt = false;
				melyik = i;
			}
		}

		cout << melyik << endl;
	}

	return 0;
}

