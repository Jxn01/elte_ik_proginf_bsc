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

#endif // SEQINFILE_H_INCLUDED