#include <iostream>
#include "seqinfile.h"

using namespace std;

int main()
{
    try
    {
        SeqInFile file = SeqInFile("inp.txt");
        file.read();
        int db = 0;
        string legnagyobb = "";
        double max = 0.0;

        do
        {
            file.read();
        } while (file.current().kutyak.size() < 2);

        do
        {
            for (Kutya elem : file.current().kutyak)
            {
                if (elem.fogadhato && elem.suly > 10)
                {
                    db++;
                }

                if (!elem.fogadhato && max < elem.suly) {
                    legnagyobb = file.current().datum;
                    max = elem.suly;
                }
            }

            file.read();
	        
        }while (!file.end());

        cout << db << " " << legnagyobb << endl;



        file.close();
    }
    catch (SeqInFile::FileErrorException exc)
    {
        cerr << "A fajlt nem lehet megnyitni!" << endl;
        return 1;
    }
    return 0;
}
