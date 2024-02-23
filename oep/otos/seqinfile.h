#ifndef SEQINFILE_H_INCLUDED
#define SEQINFILE_H_INCLUDED
using namespace std;

#include <fstream>
#include <sstream>

struct Hospital{
    string name;
    int people;
    int machine;

    friend istream& operator>> (istream& is, Hospital& h){
        is >> h.name >> h.people >> h.machine;
        return is;
    }
};

struct Day{
    string date;
    int infections;
    int sum;

    friend istream& operator>> (istream& is, Day& d){
        is >> d.date >> d.infections;
        string line;
        getline(is, line);
        istringstream s(line);

        Hospital h;
        d.sum = 0;
        while(s >> h){
            d.sum += h.people;
        }

        return is;
    }
};

class SeqInFile{
    public:
        class FileErrorException : public exception {};

        SeqInFile(string path){
            x.open(path);
            if(x.fail()){
                throw FileErrorException();
            }
        }

        Day current(){
            return day;
        }

        bool end(){
            return st == abnorm;
        }

        void read(){
            st = (x >> day) ? norm : abnorm;
        }

    private:
        enum Status{abnorm, norm};
        ifstream x;
        Day day;
        Status st;


};

#endif //SEQINFILE_H_INCLUDED