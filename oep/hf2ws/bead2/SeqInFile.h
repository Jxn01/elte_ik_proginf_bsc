#ifndef SEQINFILE_H_INCLUDED
#define SEQINFILE_H_INCLUDED

#include <fstream>
using namespace std;

class SeqInFile {
private:
    ifstream x;
    enum Status { norm, abnorm };
    Status st;
    char current;
    void readChar();

public:
    class FileErrorException : public std::exception {};
    SeqInFile(string filePath);
    void read();
    bool end();
    void close();
    double e;
};

SeqInFile::SeqInFile(string filePath) {
    x.open(filePath);
    if (x.fail()) {
        throw SeqInFile::FileErrorException();
    }
}

void SeqInFile::read() {
    do
    {
        readChar();
    } while (st == norm && current == ' ');
    string temp = "";
    temp += current;
    do
    {
        readChar();
        if (st == norm && current != ' ')
        {
            temp += current;
        }
    } while (st == norm && current != ' ');
    e = strtod(temp.c_str(), NULL);

}

void SeqInFile::readChar()
{
    x.get(current);
    st = x.fail() ? abnorm : norm;
}

bool SeqInFile::end() {
    if (st == abnorm) {
        return true;
    }
    else {
        return false;
    }
}

void SeqInFile::close() {
    x.close();
}

#endif // SEQINFILE_H_INCLUDED