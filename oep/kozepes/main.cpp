#include <iostream>
#include "seqinfile.h"

using namespace std;

int main()
{
    try
    {
        SeqInFile file("inp.txt");

        string when;
        bool l = false;
        unsigned int max = 0;

        file.read();
        while(!file.end())
        {
            Day e = file.current();
            l = l || (e.infections > 5000);
            if(e.sum > max)
            {
                max = e.sum;
                when = e.date;
            }
            file.read();
        }

        cout << (l ? "yes" : "no") << " " << when;
    }
    catch(const SeqInFile::FileErrorException& ex)
    {
        cerr << "Hiba a fajl megnyitasa kozben!\n";
        return 1;
    }

    return 0;
}
