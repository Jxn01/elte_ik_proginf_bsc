#include "SeqInFile.h"
#include <iostream>

using namespace std;

int main()
{
    try{
        double s = 0.0;
        int db = 0;
        SeqInFile f = SeqInFile("input.txt");

        for(f.first();!f.end() && f.e > 0;f.next()){
            s+=f.e;
            db++;
        }

        double a = s / db;
        bool l = true;
        double min = f.e;

        while(!f.end()){
            l = l && f.e<0;
            if(min > f.e){
                min = f.e;
            }
            f.next();
        }

        cout << "Atlag: " << a << endl;

        if(l){
            cout << "Az elso fagypont alatti nap utani napokon minden nap fagypont alatt maradt a homerseklet." << endl;
        }else{
            cout << "Az elso fagypont alatti nap utani napokon NEM maradt minden nap fagypont alatt a homerseklet." << endl;
        }

        cout << "A legalacsonyabb homerseklet: " << min << endl;

        f.close();

        return 0;
    }catch(SeqInFile::FileErrorException exc){
        std::cout << "A fajlt nem lehet megnyitni!" << std::endl;
        return 1;
    }
    
}