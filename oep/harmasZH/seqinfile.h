#ifndef SEQINFILE_H_INCLUDED
#define SEQINFILE_H_INCLUDED

#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

struct Dog
{
    string name;
    double weight;
    bool adoptable;
};

struct Day
{
    string date;
    vector<Dog> dogs;
};



class SeqInFile
{
public:
    class FileErrorException : public exception {};

    SeqInFile(string name)
    {
        file.open(name);

        if (file.fail())
        {
            throw FileErrorException();
        }
    }

    void close()
    {
        file.close();
    }

    Day current()
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
        if(st==norm)
        {
            istringstream s(line);
            string date;
            //st = () ? norm : abnorm;
            s >> date;
            vector<Dog> dogsTemp;
            vector<string> datatemp;
            for(string data; s >> data;)
            {
                datatemp.push_back(data);
	            
            }

            for(int i = 0; i < datatemp.size()-1; i+=3)
            {
                Dog tempdog;
                tempdog.name = datatemp.at(i);
                if(datatemp.at(i+2)=="true")
                {
                    tempdog.adoptable = true;
                }else
                {
                    tempdog.adoptable = false;
                }
                tempdog.weight = stod(datatemp.at(i + 1));
                dogsTemp.push_back(tempdog);
            }
            currentd.date = date;
            currentd.dogs = dogsTemp;
        }

    }

private:
    enum Status {norm, abnorm};

    ifstream file;

    Day currentd;

    Status st;
};

#endif // SEQINFILE_H_INCLUDED