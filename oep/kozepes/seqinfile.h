#ifndef SEQINFILE_H_INCLUDED
#define SEQINFILE_H_INCLUDED

#include <fstream>
#include <sstream>

struct Hospital
{
    std::string name;
    unsigned int people;
    unsigned int machine;

    friend std::istream& operator>>(std::istream& is, Hospital& h)
    {
        is >> h.name >> h.people >> h.machine;
        return is;
    }
};

struct Day
{
    std::string date;
    unsigned int infections;
    unsigned int sum;

    friend std::istream& operator>>(std::istream& is, Day& d)
    {
        is >> d.date >> d.infections;

        std::string line;
        getline(is, line);
        std::istringstream s(line);

        Hospital h;
        d.sum = 0;
        while(s >> h)
        {
            d.sum += h.people;
        }

        return is;
    }
};

class SeqInFile
{
    public:
        class FileErrorException : public std::exception {};

        SeqInFile(const std::string& path)
        {
            _x.open(path);
            if(_x.fail()) throw FileErrorException();
        }

        Day current() const { return _day; }
        bool end() const { return _st == abnorm; }

        void read()
        {
            _st = (_x >> _day) ? norm : abnorm;
        }

    private:
        enum Status{ abnorm, norm };

        std::ifstream _x;
        Day _day;
        Status _st;
};

#endif // SEQINFILE_H_INCLUDED
