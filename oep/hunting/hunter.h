#ifndef HUNTER_H_INCLUDED
#define HUNTER_H_INCLUDED

#include <vector>
#include "trophy.h"

class Hunter
{
    public:
        Hunter(const std::string& n, int a): _name(n), _age(a) { }
        ~Hunter() { for(Trophy* t : _trophies) delete t; }

        void shot(Animal* animal, const std::string &place, const std::string &date);

        int countMaleLions() const;
        bool maxHornWeigthRate(double& rate) const;
        bool searchEqualTusks() const;

        std::string getName() const { return _name; }
        int getAge() const { return _age; }
        std::vector<Trophy*> getTrophies() const { return _trophies; }

    private:
        std::string _name;
        int _age;
        std::vector<Trophy*> _trophies;
};

#endif // HUNTER_H_INCLUDED
