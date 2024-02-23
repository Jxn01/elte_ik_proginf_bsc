#include <iostream>
#include <vector>
using namespace std;


int main()
{
    int n;
    cin >> n;
    vector<int> mennyiseg;
    vector<int> ar;
    vector<int> nemvoltmar;

    for(int i=0;i<n*2;i++)
    {
	    if(i==0||i%2==0)
	    {
            int mennyisegTemp;
            cin >> mennyisegTemp;
            mennyiseg.push_back(mennyisegTemp);
	    }else
	    {
            int arTemp;
            cin >> arTemp;
            ar.push_back(arTemp);
	    }
    }


    
    for(int i=0;i<n;i++)
    {
        bool voltemar = false;
	    for(int j=i; j<n;j++)
	    {
		   if(ar.at(i)==ar.at(j)&&i!=j)
		   {
               voltemar = true;
		   }
	    }

        if(!voltemar)
        {
            nemvoltmar.push_back(ar.at(i));
        }
    }


    cout << nemvoltmar.size() << endl;

    return 0;
}
