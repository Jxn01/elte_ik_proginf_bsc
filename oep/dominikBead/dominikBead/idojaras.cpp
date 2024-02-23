#include "foldterulet.hpp"
#include "idojaras.hpp"

#include <stdlib.h>
#include <time.h>
#include <stdio.h>

using namespace std;

Idojaras* Idojaras::create(int para){
    if(para > 70){
        return Esos::instance(30);
        //this->name = "esos";
        //this->paratartalom = 30;
    }else{
        if(para < 40){
            return Napos::instance(para);
            //this->name = "napos";
        }else{
            double n = rand() % 101;
            if(n < (para - 40) * 3.3){
                return Esos::instance(30);
                //this->name = "esos";
            }else{
                return Felhos::instance(para);
                //this->name = "felhos";
            }
        }
    }
}

void Idojaras::setParatartalom(int ujPara){
    if(ujPara > 100){
        ujPara = 100;
    }
    if(ujPara < 0){
        ujPara = 0;
    }
    this->paratartalom = ujPara;
}

/*
void Idojaras::NapKezdes(){
    if(this->paratartalom > 70){
        this->name = "esos";
        this->paratartalom = 30;
    }else{
        if(this->paratartalom < 40){
            this->name = "napos";
        }else{
            double n = rand() % 101;
            if(n < (this->paratartalom - 40) * 3.3){
                this->name = "esos";
            }else{
                this->name = "felhos";
            }
        }
    }
}
*/

Napos* Napos::_instance = nullptr;
Napos* Napos::instance(int para){
    if(_instance == nullptr){
        _instance = new Napos(para);
    }
    _instance->setParatartalom(para);
    return _instance;
}

Foldterulet* Napos::Atalakit(Puszta* p){
    p->setViz(p->getViz() - 3);
    return p->Valtozik();
}

Foldterulet* Napos::Atalakit(Tavas* p){
    p->setViz(p->getViz() - 10);
    return p->Valtozik();
}

Foldterulet* Napos::Atalakit(Zold* p){
    p->setViz(p->getViz() - 6);
    return p->Valtozik();
}

Felhos* Felhos::_instance = nullptr;
Felhos* Felhos::instance(int para){
    if(_instance == nullptr){
        _instance = new Felhos(para);
    }
    _instance->setParatartalom(para);
    return _instance;
}

Foldterulet* Felhos::Atalakit(Puszta* p){
    p->setViz(p->getViz() + 1);
    return p->Valtozik();
}

Foldterulet* Felhos::Atalakit(Tavas* p){
    p->setViz(p->getViz() + 3);
    return p->Valtozik();
}

Foldterulet* Felhos::Atalakit(Zold* p){
    p->setViz(p->getViz() + 2);
    return p->Valtozik();
}

Esos* Esos::_instance = nullptr;
Esos* Esos::instance(int para){
    if(_instance == nullptr){
        _instance = new Esos(para);
    }
    _instance->setParatartalom(para);
    return _instance;
}

Foldterulet* Esos::Atalakit(Puszta* p){
    p->setViz(p->getViz() + 5);
    return p->Valtozik();
}

Foldterulet* Esos::Atalakit(Tavas* p){
    p->setViz(p->getViz() + 15);
    return p->Valtozik();
}

Foldterulet* Esos::Atalakit(Zold* p){
    p->setViz(p->getViz() + 10);
    return p->Valtozik();
}