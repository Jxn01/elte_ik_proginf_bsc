#ifndef SEQINFILE_H_INCLUDED
#define SEQINFILE_H_INCLUDED

#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

struct Kutya
{
    string nev;
    double suly;
    bool fogadhato;
};

struct Nap
{
    string datum;
    vector<Kutya> kutyak;
};

class SeqInFile
{
public:
    class FileErrorException : public exception {};

    SeqInFile(string nev)
    {
        file.open(nev);

        if (file.fail())
        {
            throw FileErrorException();
        }
    }

    void close()
    {
        file.close();
    }

    Nap current()
    {
        return currentd;
    }

    bool end()
    {
        return st == abnorm;
    }

    void read()
    {
        string line;
        st = (getline(file, line)) ? norm : abnorm;
        if (st == norm)
        {
            istringstream s(line);
            string datum;
            s >> datum;
            vector<Kutya> kutyak;
            vector<string> adatok;
            for (string adat; s >> adat;)
            {
                adatok.push_back(adat);

            }

            for (int i = 0; i < adatok.size() - 1; i += 3)
            {
                Kutya kutya;
                kutya.nev = adatok.at(i);
                kutya.suly = stod(adatok.at(i + 1));

                if (adatok.at(i + 2) == "true")
                {
                    kutya.fogadhato = true;
                }
                else
                {
                    kutya.fogadhato = false;
                }
                
                kutyak.push_back(kutya);
            }
            currentd.datum = datum;
            currentd.kutyak = kutyak;
        }

    }

private:
    enum Status { norm, abnorm };

    ifstream file;

    Nap currentd;

    Status st;
};

#endif // SEQINFILE_H_INCLUDED