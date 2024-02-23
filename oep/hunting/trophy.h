#ifndef TROPHY_H_INCLUDED
#define TROPHY_H_INCLUDED

#include <string>
#include "animal.h"

class Trophy
{
    public:
        Trophy(Animal* a, const std::string& p, const std::string d):
            _animal(a), _place(p), _date(d) { }

        ~Trophy() { if(_animal != nullptr) delete _animal; }

        Animal* getAnimal() const { return _animal; }
        std::string getPlace() const { return _place; }
        std::string getDate() const { return _date; }

    private:
        Animal* _animal;
        std::string _place;
        std::string _date;
};

#endif // TROPHY_H_INCLUDED
