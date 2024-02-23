#include <iostream>
#include "seqinfile.h"

using namespace std;

int osszkis(vector<Dog> kutyak);
int forgalmas(vector<Dog> kutyak);

int main()
{
    try
    {
        SeqInFile file = SeqInFile("inp.txt");
        int s = 0;
        int hany = 0;
        string mikor;
        file.read();
        while(!file.end())
        {
            hany += osszkis(file.current().dogs);
            s = forgalmas(file.current().dogs);
            if(s >= 2)
            {
                mikor = file.current().date;
            }
            file.read();
        }

        cout << mikor << " " << hany << endl;



        file.close();
    }catch(SeqInFile::FileErrorException exc)
    {
        cerr << "A fajlt nem lehet megnyitni!" << endl;
        return 1;
    }
    return 0;
}

int forgalmas(vector<Dog> kutyak)
{
    int s = 0;
    for (int i = 0; i < kutyak.size(); i++)
    {
        s++;
    }
    return s;
}

int osszkis(vector<Dog> kutyak)
{
    int sum = 0;
    for(int i=0; i<kutyak.size(); i++)
    {
	    if(kutyak.at(i).weight < 5)
	    {
            sum++;
	    }
    }
    return sum;
}