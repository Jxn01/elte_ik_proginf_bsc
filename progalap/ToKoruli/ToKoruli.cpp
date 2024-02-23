#include <iostream>
#include <vector>
using namespace std;

int main()
{
	int n;
	cin >> n;
	vector<vector<int>> matrix;
	vector<int> megoldas;

	for(int i = 0;i<n; i++)
	{
		vector<int> ertekek;
		for(int j = 0; j<n;j++)
		{
			int ertek;
			cin >> ertek;
			ertekek.push_back(ertek);
		}
		matrix.push_back(ertekek);
	}

	int sor = 0;
	int min = 10001;

	for (int j = 0; j < n; j++)
	{
		matrix[j][sor] = 0;
	}

	for(int i = 0; i<n; i++)
	{
		int min = 10001;
		int index = 0;
		for(int j = 0; j<n; j++)
		{
			if(matrix[sor][j]!=0&&matrix[sor][j]<min)
			{
				min = matrix[sor][j];
				index = j;
			}
		}

		cout << index + 1 << " ";

		for(int j = 0; j<n;j++)
		{
			matrix[j][sor] = 0;
		}

		sor = index;
	}
	return 0;
}