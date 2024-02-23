#include "classNames.hpp"
#include "foldterulet.hpp"
#include "idojaras.hpp"

#include <vector>
#include <iostream>
#include <string>
#include <fstream>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>

using namespace std;

bool Ugyanaz(vector<MostaniTerulet*> foldteruletek){
    if(foldteruletek.size() == 0){
        return true;
    }
    string elsoFajta = foldteruletek.at(0)->getFajta();
    for(unsigned int i = 0; i < foldteruletek.size(); i++){
        if(foldteruletek.at(i)->getFajta() != elsoFajta){
            return false;
        }
    }
    return true;
}

void Beolvas(vector<MostaniTerulet*> &foldteruletek, string filename, Idojaras* &ido){
    ifstream be(filename);

    if(be.fail()){
        cerr << "Error at opening file for reading.\n";
        exit(1);
    }

    int db;
    be >> db;
    for(int i = 0; i < db; i++){
        string mName;
        string mFajta;
        int mViz;

        be >> mName >> mFajta >> mViz;

        if(mFajta == "p"){
            foldteruletek.push_back(new MostaniTerulet(new Puszta(mName,mViz)));
        }else{
            if(mFajta == "z"){
                foldteruletek.push_back(new MostaniTerulet(new Zold(mName,mViz)));
            }else{
                foldteruletek.push_back(new MostaniTerulet(new Tavas(mName,mViz)));
            }
        }
    }
    int mostaniPara;
    be >> mostaniPara;
    ido = Idojaras::create(mostaniPara);
}

void egyNap(int round, vector<MostaniTerulet*> &foldteruletek, Idojaras* &ido){
    if(round != 1){
        ido = Idojaras::create(ido->getParatartalom());
    }
    cout << ido->getName() << " " << ido->getParatartalom() << " " << round << ".kor: \n";
    for(unsigned int i = 0; i < foldteruletek.size(); i++){
        foldteruletek.at(i)->VizValt(ido);
        foldteruletek.at(i)->ValtSzazalek(ido);
    }
        
    for(unsigned int i = 0; i < foldteruletek.size(); i++){
        cout << foldteruletek.at(i)->getName() << " " << foldteruletek.at(i)->getFajta() << " " << foldteruletek.at(i)->getViz() << '\n';
    }
}

void deleteAll(Idojaras* &ido, vector<MostaniTerulet*> &foldteruletek){
    delete ido;
    Napos::destroy();
    Esos::destroy();
    Felhos::destroy();
    for(unsigned int i = 0; i < foldteruletek.size(); i++){
        delete foldteruletek.at(i);
    }
}
// Ezzel lehet a manualis es a unit teszt mod kozott valtogatni
#define NORMAL_MODE
#ifdef NORMAL_MODE

int main(){
    srand( (unsigned)time(NULL) );
    vector<MostaniTerulet*> foldteruletek;
    cout << "Add meg a file nevet!" << '\n';
    string filename;
    cin >> filename;
    Idojaras* ido = nullptr;
    Beolvas(foldteruletek,filename,ido);
    int round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    deleteAll(ido,foldteruletek);
    return 0;
}

#else
#define CATCH_CONFIG_MAIN
#include "catch.hpp"

TEST_CASE("1", "inp1*.txt")
{
    srand( (unsigned)time(NULL) );
    vector<Foldterulet*> foldteruletek;
    Idojaras* ido = nullptr;

    Beolvas(foldteruletek, "inp11.txt", ido);
    int round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr;

    Beolvas(foldteruletek, "inp12.txt", ido);
    round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr;

    Beolvas(foldteruletek, "inp13.txt", ido);
    round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr;
    deleteAll(ido,foldteruletek);
}

TEST_CASE("2", "inp2*.txt")
{
    srand( (unsigned)time(NULL) );
    vector<Foldterulet*> foldteruletek;
    Idojaras* ido = nullptr;

    Beolvas(foldteruletek, "inp21.txt", ido);
    int round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr;

    Beolvas(foldteruletek, "inp22.txt", ido);
    round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr;

    Beolvas(foldteruletek, "inp23.txt", ido);
    round = 0;
    while(!Ugyanaz(foldteruletek)){
        round++;
        egyNap(round, foldteruletek, ido);
    }
    CHECK(Ugyanaz(foldteruletek));
    foldteruletek.clear();
    ido = nullptr; 

    deleteAll(ido,foldteruletek);
}

TEST_CASE("3", "") {
    srand( (unsigned)time(NULL) );
    vector<Foldterulet*> foldteruletek;
    Idojaras* ido = Idojaras::create(20);

    foldteruletek.push_back(Foldterulet::create("Teszt",0,10));
    egyNap(1,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 7);

    foldteruletek.clear();

    ido = Idojaras::create(20);
    foldteruletek.push_back(Foldterulet::create("Teszt",2,40));
    egyNap(1,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 34);
    foldteruletek.clear();

    ido = Idojaras::create(20);
    foldteruletek.push_back(Foldterulet::create("Teszt",1,60));
    egyNap(0,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 50);
    foldteruletek.clear();

    ido = Idojaras::create(50);
    foldteruletek.push_back(Foldterulet::create("Teszt",0,10));
    egyNap(1,foldteruletek,ido);
    while(ido->getName() != "felhos"){
        foldteruletek.clear();

        ido = Idojaras::create(50);
        foldteruletek.push_back(Foldterulet::create("Teszt",0,10));
        egyNap(0,foldteruletek,ido);
    }
    
    CHECK(foldteruletek.at(0)->getViz() == 11);
    foldteruletek.clear();

    ido = Idojaras::create(50);
    foldteruletek.push_back(Foldterulet::create("Teszt",2,40));
    egyNap(1,foldteruletek,ido);
    while(ido->getName() != "felhos"){
        foldteruletek.clear();

        ido = Idojaras::create(50);
        foldteruletek.push_back(Foldterulet::create("Teszt",2,40));
        egyNap(1,foldteruletek,ido);
    }
    CHECK(foldteruletek.at(0)->getViz() == 42);
    foldteruletek.clear();

    ido = Idojaras::create(50);
    foldteruletek.push_back(Foldterulet::create("Teszt",1,60));
    egyNap(1,foldteruletek,ido);
    while(ido->getName() != "felhos"){
        foldteruletek.clear();

        ido = Idojaras::create(50);
        foldteruletek.push_back(Foldterulet::create("Teszt",1,60));
        egyNap(1,foldteruletek,ido);
    }
    CHECK(foldteruletek.at(0)->getViz() == 63);
    foldteruletek.clear();

    ido = Idojaras::create(95);
    foldteruletek.push_back(Foldterulet::create("Teszt",0,10));
    egyNap(1,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 15);
    foldteruletek.clear();

    ido = Idojaras::create(95);

    foldteruletek.push_back(Foldterulet::create("Teszt",2,40));
    egyNap(1,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 50);
    foldteruletek.clear();

    ido = Idojaras::create(95);

    foldteruletek.push_back(Foldterulet::create("Teszt",1,60));
    egyNap(1,foldteruletek,ido);
    CHECK(foldteruletek.at(0)->getViz() == 75);
    foldteruletek.clear();

    deleteAll(ido,foldteruletek);
}

#endif // NORMAL_MODE
