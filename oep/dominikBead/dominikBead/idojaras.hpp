#pragma once

#include "classNames.hpp"
#include "foldterulet.hpp"
#include <string>

using namespace std;
class Idojaras{
    protected:
        string name;
        int paratartalom;
    public:
        static Idojaras* create(int para);
        virtual ~Idojaras(){}
        virtual Foldterulet* Atalakit(Puszta* f) = 0;
        virtual Foldterulet* Atalakit(Zold* f) = 0;
        virtual Foldterulet* Atalakit(Tavas* f) = 0;
        string getName(){return name;};
        int getParatartalom(){return paratartalom;};
        void setParatartalom(int ujPara);
        //void NapKezdes();
};

class Napos: public Idojaras{
    public:
        static Napos* instance(int para);
        Foldterulet* Atalakit(Puszta* f) override;
        Foldterulet* Atalakit(Zold* f) override;
        Foldterulet* Atalakit(Tavas* f) override;
        void static destroy() { if ( nullptr!=_instance) delete _instance;  _instance = nullptr;};
    private:
        Napos(int para){this->paratartalom = para; this->name = "napos";};
        static Napos* _instance;
};

class Felhos: public Idojaras{
    public:
        static Felhos* instance(int para);
        Foldterulet* Atalakit(Puszta* f) override;
        Foldterulet* Atalakit(Zold* f) override;
        Foldterulet* Atalakit(Tavas* f) override;
        void static destroy() { if ( nullptr!=_instance) delete _instance;  _instance = nullptr;};
    private:
        Felhos(int para){this->paratartalom = para; this->name = "felhos";};
        static Felhos* _instance;
};

class Esos: public Idojaras{
    public:
        static Esos* instance(int para);
        Foldterulet* Atalakit(Puszta* f) override;
        Foldterulet* Atalakit(Zold* f) override;
        Foldterulet* Atalakit(Tavas* f) override;
        void static destroy() { if ( nullptr!=_instance) delete _instance;  _instance = nullptr;};
    private:
        Esos(int para){this->paratartalom = para; this->name = "esos";};
        static Esos* _instance;
};