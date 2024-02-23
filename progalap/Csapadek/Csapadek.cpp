#include <iostream>
#include <vector>
using namespace std;

class Intervallum
{
public:
    int kezdet;
    int veg;


    Intervallum(int kezdet, int veg)
	    : kezdet(kezdet),
	      veg(veg)
    {
    }

    Intervallum() = default;

    int hossz()
    {
        return veg - kezdet;
    }
};

int main()
{
    int n;
    cin >> n;

    vector<Intervallum> intervallumok;
    vector<vector<int>> hetek;

    for(int i = 0; i < n;i++)
    {
        vector<int> het;
	    for(int j = 0; j < 7; j++)
	    {
            int nap;
            cin >> nap;
            het.push_back(nap);
	    }
        hetek.push_back(het);
    }

    int jelenlegiMax = 0;
    int kezdoIndex = -1;
    int vegIndex = -1;
    bool jo = false;


    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < 7; j++)
        {
            jelenlegiMax += hetek[i][j];
        }

        if(jelenlegiMax<=10)
        {
            if(!jo)
            {
                jo = true;
                kezdoIndex = i;
            }
            vegIndex = i;
        }else
        {
            jo = false;
            if(kezdoIndex!=-1&&vegIndex!=-1)
            {
                intervallumok.push_back(Intervallum(kezdoIndex, vegIndex));
            }
            kezdoIndex = -1;
            vegIndex = -1;
        }
        jelenlegiMax = 0;
    }

    if(intervallumok.size()==0)
    {
        cout << 0 << endl;
    }else
    {
        int max = 0;
        Intervallum minElem = intervallumok.at(0);

        for (Intervallum elem : intervallumok)
        {
            if (elem.hossz() > max)
            {
                max = elem.hossz();
                minElem = elem;
            }
        }

        cout << minElem.kezdet + 1 << " " << minElem.veg + 1 << endl;
    }



    return 0;
}
