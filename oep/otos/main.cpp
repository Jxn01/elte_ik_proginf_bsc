#include <iostream>
#include "seqinfile.h"
using namespace std;

int main(){
    try{
        SeqInFile file ("inp.txt");

        file.read();
        while(!file.end() && file.current().sum <= 1000){
            file.read();
        }

        int count = 0;
        int max = file.current().sum;
        string when = file.current().date;

        file.read();

        while(!file.end()){
            Day e = file.current();
            count+=e.infections;
            if(e.sum > max){
                max = e.sum;
                when = e.date;
            }
            file.read();
        }

        cout << count << " " << when << " " << max;


    }catch(SeqInFile::FileErrorException e){
        cerr << "Hiba" << endl;
        return 1;
    }
    return 0;
}