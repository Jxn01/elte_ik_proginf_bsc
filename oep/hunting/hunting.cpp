#include <iostream>
#include <fstream>
#include <sstream>
#include "animal.h"
#include "trophy.h"
#include "hunter.h"

using namespace std;

void Hunter::shot(Animal* animal, const std::string &place, const std::string &date)
{
    _trophies.push_back(new Trophy(animal, place, date));
}

int Hunter::countMaleLions() const
{
    int c = 0;
    for(Trophy* t : _trophies)
    {
        if(t->getAnimal()->isLion())
        {
            if( ((Lion*)(t->getAnimal()))->getMale() ) ++c;
        }
    }
    return c;
}

bool Hunter::maxHornWeigthRate(double& rate) const
{
    /// TODO: MaxAgyarTomeg, ami MaxSzarvTomeg metodus
    int maxint = 0;
    for(Trophy* elem: _trophies)
    {
	    if(elem->getAnimal()->isRhino())
	    {
            if(maxint < ((Rhino*)elem)->getHorn())
            {
                maxint = ((Rhino*)elem)->getHorn();
            }
            
	    }
    }

    return maxint;

}

bool Hunter::searchEqualTusks() const
{
    /// TODO: EgyezoAgyarhossz metodus
    for(Trophy* elem : _trophies)
    {
	    if(elem->getAnimal()->isElephant())
	    {
		    if(((Elephant*)elem)->getLeft() == ((Elephant*)elem)->getRight())
		    {
                return true;
		    }
	    }
    }

    return false;

}

int main()
{
    ifstream file("input.txt");
    if (file.fail()) { cout << "File open error!\n"; return 1; }

    Hunter* h = new Hunter("Jack", 62);

    string line, place, date, species, gender;
    double weight, right, left, horn;
    Animal* animal{};

    while (getline(file, line))
    {
        istringstream is(line);

        is >> place >> date >> species >> weight >> gender;

        if (species == "lion")
        {
            animal = new Lion(weight, gender == "male");
        }
        else if (species == "rhino")
        {
            is >> horn;
            animal = new Rhino(weight, horn);
        }
        else if (species == "elephant")
        {
            is >> right >> left;
            animal = new Elephant(weight, right, left);
        }

        h->shot(animal, place, date);
    }

    cout << "Number of the male lions: " << h->countMaleLions() << endl;

    double rate;
    if (h->maxHornWeigthRate(rate))
        cout << "The most horn-weight rate among the rhinos: " << rate << endl;
    else
        cout << "There are no rhinos!\n";

    if (h->searchEqualTusks())
        cout << "There exists an elephant with same length tusks.\n";
    else
        cout << "There is no elephant with same length tusks.\n";

    delete h;

    return 0;
}

