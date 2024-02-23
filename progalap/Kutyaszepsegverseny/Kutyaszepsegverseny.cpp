#include <iostream>
#include <vector>
using namespace std;

vector<int> minKutya(vector<vector<int>> kutyak, int n, int m);
int main()
{
	setlocale(LC_ALL, "hun");
	vector<int> kutya;
	vector<vector<int>> kutyak; //N*M
	vector<int> maxpontszamok;
	vector<int> minpontszamok;
	vector<int> eddigiMax;

	int n; // resztvevok
	int m; // szempontok szama
	cin >> n;
	cin >> m;

	if (n == 1)
	{
		cout << "NINCS" << endl;
	}
	else
	{
		for (int i = 0; i < m; i++)
		{
			int v;
			cin >> v;
			maxpontszamok.push_back(v);

		}

		for (int i = 0; i < m; i++)
		{
			int v;
			cin >> v;
			minpontszamok.push_back(v);
		}

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				int v;
				cin >> v;
				kutyak.push_back(kutya);
				kutyak.at(i).push_back(v);
			}
		}

		eddigiMax = minKutya(kutyak, n, m);
		bool van = false;
		bool nagyobb = false;

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				nagyobb = (kutyak.at(i).at(j) > eddigiMax.at(j)) ? true : false;

				if (!nagyobb)
				{
					break;
				}

			}

			if (nagyobb)
			{
				eddigiMax = kutyak.at(i);
			}
		}

		for (int i = 0; i < n && !van; i++)
		{
			for (int k = 0; k < m; k++)
			{
				van = (eddigiMax.at(k) > kutyak.at(i).at(k)) ? true : false;
				if (van)
				{
					break;
				}

			}

		}

		if (van)
		{
			cout << "VAN" << endl;
		}
		else
		{
			cout << "NINCS" << endl;
		}

	}

	return 0;
}

vector<int> minKutya(vector<vector<int>> kutyak, int n, int m)
{
	vector<int> kutya;
	bool kisebb = false;

	for (int i = 0; i < m; i++)
	{
		kutya.push_back(INT32_MAX);
	}

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < m; j++)
		{
			kisebb = (kutyak.at(i).at(j) <= kutya.at(j)) ? true : false;

			if (!kisebb) {
				break;
			}
		}

		if (kisebb)
		{
			kutya = kutyak.at(i);
		}

	}

	return kutya;
}