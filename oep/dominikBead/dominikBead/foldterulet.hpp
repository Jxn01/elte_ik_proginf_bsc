#pragma once

#include "idojaras.hpp"
#include <string>

using namespace std;

class Foldterulet {
    protected:
        string name;
        string fajta;
        int viz;
    public:
        virtual Foldterulet* VizValt(Idojaras* i) = 0;
        virtual Foldterulet* Valtozik() = 0;
        virtual void ValtSzazalek(Idojaras* i)  = 0;
        string getName(){return name;};
        string getFajta(){return fajta;};
        int getViz(){return viz;};
        void setViz(int newViz){this->viz = newViz;};
        virtual ~Foldterulet() {}
};

class MostaniTerulet {
    private:
        Foldterulet* _foldterulet;
    public:
        MostaniTerulet(Foldterulet* f = nullptr):_foldterulet(f) {};
        ~MostaniTerulet() {delete this->_foldterulet;};
        void setFoldterulet(Foldterulet* f){
            //delete this->_foldterulet;
            this->_foldterulet = f;
        }
        void VizValt(Idojaras* i){
            this->setFoldterulet(this->_foldterulet->VizValt(i));
        }
        Foldterulet* Valtozik(){
            return (this->_foldterulet->Valtozik());
        }
        void ValtSzazalek(Idojaras* i){
            this->_foldterulet->ValtSzazalek(i);
        }
        string getName(){return (this->_foldterulet->getName());};
        int getViz(){return (this->_foldterulet->getViz());};
        void setViz(int newViz){this->_foldterulet->setViz(newViz);};
        string getFajta(){return (this->_foldterulet->getFajta());};
};

class Puszta : public Foldterulet {
    public:
        Foldterulet* VizValt(Idojaras* i) override;
        Foldterulet* Valtozik() override;
        void ValtSzazalek(Idojaras* i) override;
        Puszta(string name, int viz);
        ~Puszta() = default;
};

class Tavas : public Foldterulet {
    public:
        Foldterulet* VizValt(Idojaras* i) override;
        Foldterulet* Valtozik() override;
        void ValtSzazalek(Idojaras* i) override;
        Tavas(string name, int viz);
        ~Tavas() = default;
};

class Zold : public Foldterulet {
    public:
        Foldterulet* VizValt(Idojaras* i) override;
        Foldterulet* Valtozik() override;
        void ValtSzazalek(Idojaras* i) override;
        Zold(string name, int viz);
        ~Zold() = default;
};