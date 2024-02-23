#include "foldterulet.hpp"
#include "idojaras.hpp"

#include <string>

using namespace std;

Puszta::Puszta(string name, int viz){
    this->name = name;
    this->fajta = "p";
    this->viz = viz;
}

Foldterulet* Puszta::VizValt(Idojaras* i){
    return i->Atalakit(this);
}

Foldterulet* Puszta::Valtozik(){
    /*
    if(i->getName() == "napos"){
        this->viz = this->viz - 3;
    }else{
        if(i->getName() == "felhos"){
            this->viz = this->viz + 1;
        }else{
            this->viz = this->viz + 5;
        }
    }
    */
    if(this->viz > 15){
        string name = this->name;
        int viz = this->viz;
        delete this;
        return new Zold(name,viz);
    }
    string name = this->name;
    int viz = this->viz;
    delete this;
    return new Puszta(name,viz);
}

void Puszta::ValtSzazalek(Idojaras* i){
    i->setParatartalom(i->getParatartalom() + 3);
}

Tavas::Tavas(string name, int viz){
    this->name = name;
    this->fajta = "t";
    this->viz = viz;
}

Foldterulet* Tavas::VizValt(Idojaras* i){
    return i->Atalakit(this);
}

Foldterulet* Tavas::Valtozik(){
    /*
    if(i->getName() == "napos"){
        this->viz = this->viz - 10;
    }else{
        if(i->getName() == "felhos"){
            this->viz = this->viz + 3;
        }else{
            this->viz = this->viz + 15;
        }
    }
    */
    if(this->viz < 51){
        string name = this->name;
        int viz = this->viz;
        delete this;
        return new Tavas(name,viz);
    }
    string name = this->name;
    int viz = this->viz;
    delete this;
    return new Zold(name,viz);
}

void Tavas::ValtSzazalek(Idojaras* i){
    i->setParatartalom(i->getParatartalom() + 10);
}


Zold::Zold(string name, int viz){
    this->name = name;
    this->fajta = "z";
    this->viz = viz;
}

Foldterulet* Zold::VizValt(Idojaras* i){
    return i->Atalakit(this);
}

Foldterulet* Zold::Valtozik(){
    /*
    if(i->getName() == "napos"){
        this->viz = this->viz - 6;
    }else{
        if(i->getName() == "felhos"){
            this->viz = this->viz + 2;
        }else{
            this->viz = this->viz + 10;
        }
    }
    */

    
    if(this->viz > 50){
        string name = this->name;
        int viz = this->viz;
        delete this;
        return new Zold(name,viz);
    }
    if(this->viz < 16){
        string name = this->name;
        int viz = this->viz;
        delete this;
        return new Puszta(name,viz);
    }
    string name = this->name;
    int viz = this->viz;
    delete this;
    return new Tavas(name,viz);
}

void Zold::ValtSzazalek(Idojaras* i){
    i->setParatartalom(i->getParatartalom() + 7);
}

