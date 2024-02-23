#include <iostream>
#include "SeqInFile.h"
#include <cstring>

SeqInFile::SeqInFile(string filePath){
    x.open(filePath);
    if(x.fail()){
        throw SeqInFile::FileErrorException();
    }
}

void SeqInFile::first(){
    SeqInFile::next();
}

void SeqInFile::next(){
    do{
        x.get(tempChar);
        st = x.fail() ? abnorm : norm;
    }while(st == norm && tempChar == ' ');
    read();
}

void SeqInFile::read(){
    x.get(current, 100, ' ');
    st = x.fail() ? abnorm : norm;
    if(st == norm){
        string temp = "";
        temp+=tempChar;
        temp+=current;
        std::cout << "|" << temp << "|" << std::endl;
        e = strtod(temp.c_str(), NULL);
    }
}

bool SeqInFile::end(){
    if(st == abnorm){
        return true;
    }else{
        return false;
    }
}

void SeqInFile::close(){
    x.close();
}
