#include <iostream>
#include <vector>
using namespace std;

class Sarok
{
public:
	int x;
	int y;

	Sarok(int x, int y)
		: x(x),
		  y(y)
	{
	}
};

class Territorium
{
public:
	Sarok balfelso;
	Sarok jobbfelso;
	Sarok balalso;
	Sarok jobbalso;
	int sorsz;
	int x;
	int y;
	int r;

	Territorium(const Sarok& balfelso, const Sarok& jobbfelso, const Sarok& balalso, const Sarok& jobbalso, int sorsz,
	            int x, int y, int r)
		: balfelso(balfelso),
		  jobbfelso(jobbfelso),
		  balalso(balalso),
		  jobbalso(jobbalso),
		  sorsz(sorsz),
		  x(x),
		  y(y),
		  r(r)
	{
	}
};

int main()
{
	vector<Territorium> territoriumok;
	vector<int> sorszamok;
	int m;
	int n;
	cin >> m;
	cin >> n;
	bool metszet = false;;

	for (int i = 0; i < m; i++)
	{
		int x;
		int y;
		int r;

		cin >> x;
		cin >> y;
		cin >> r;
		territoriumok.push_back(Territorium(Sarok(x - r, y + r), Sarok(x + r, y + r), Sarok(x - r, y - r),
		                                    Sarok(x + r, y - r), i + 1, x, y, r));
	}

	for (Territorium terr1 : territoriumok)
	{
		for (Territorium terr2 : territoriumok)
		{
			if (terr1.sorsz != terr2.sorsz && !metszet)
			{
				//bal alsó pont benne van
				if (terr2.balalso.x >= terr1.balalso.x && terr2.balalso.x <= terr1.jobbalso.x &&
					terr2.balalso.y >= terr1.balalso.y && terr2.balalso.y <= terr1.balfelso.y)
				{
					metszet = true;
				}
				//jobb alsó pont benne van
				else if (terr2.jobbalso.x >= terr1.balalso.x && terr2.jobbalso.x <= terr1.jobbalso.x &&
					terr2.jobbalso.y >= terr1.balalso.y && terr2.jobbalso.y <= terr1.balfelso.y)
				{
					metszet = true;
				}
				//bal felsõ pont benne van
				else if (terr2.balfelso.x >= terr1.balalso.x && terr2.balfelso.x <= terr1.jobbalso.x &&
					terr2.balfelso.y >= terr1.balalso.y && terr2.balfelso.y <= terr1.balfelso.y)
				{
					metszet = true;
				}
				//jobb felsõ pont benne van
				else if (terr2.jobbfelso.x >= terr1.balalso.x && terr2.jobbfelso.x <=
					terr1.jobbalso.x &&
					terr2.jobbfelso.y >= terr1.balalso.y && terr2.jobbfelso.y <=
					terr1.jobbfelso.y)
				{
					metszet = true;
				}
				//bal alsó pont benne van (inverz)
				else if (terr1.balalso.x >= terr2.balalso.x && terr1.balalso.x <= terr2.jobbalso.x &&
					terr1.balalso.y >= terr2.balalso.y && terr1.balalso.y <= terr2.balfelso.y)
				{
					metszet = true;
				}
				//jobb alsó pont benne van (inverz)
				else if (terr1.jobbalso.x >= terr2.balalso.x && terr1.jobbalso.x <= terr2.jobbalso.x &&
					terr1.jobbalso.y >= terr2.balalso.y && terr1.jobbalso.y <= terr2.balfelso.y)
				{
					metszet = true;
				}
				//bal felsõ pont benne van (inverz)
				else if (terr1.balfelso.x >= terr2.balalso.x && terr1.balfelso.x <= terr2.jobbalso.x
					&&
					terr1.balfelso.y >= terr2.balalso.y && terr1.balfelso.y <= terr2.balfelso.y)
				{
					metszet = true;
				}
				//jobb felsõ pont benne van (inverz)
				else if (terr1.jobbfelso.x >= terr2.balalso.x && terr1.jobbfelso.x <=
					terr2.jobbalso.x &&
					terr1.jobbfelso.y >= terr2.balalso.y && terr1.jobbfelso.y <=
					terr2.jobbfelso.y)
				{
					metszet = true;
				}
			}
		}
		if (!metszet)
		{
			sorszamok.push_back(terr1.sorsz);
		}
		metszet = false;
	}

	if(sorszamok.size()==0)
	{
		cout << 0;
	}else
	{
		for (int elem : sorszamok)
		{
			cout << elem << " ";
		}
	}

	


	return 0;
}
