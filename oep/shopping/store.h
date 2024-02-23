#ifndef STORE_H_INCLUDED
#define STORE_H_INCLUDED

#include "department.h"

class Store
{
    public:
        Store(const std::string& fName, const std::string& tName);
        ~Store() { delete _food; delete _tech; }

        Department* _food;
        Department* _tech;
};

#endif // STORE_H_INCLUDED
