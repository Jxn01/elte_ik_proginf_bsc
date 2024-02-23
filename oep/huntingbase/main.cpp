#include <iostream>
#include <fstream>
#include <sstream>
#include "animal.h"
#include "trophy.h"
#include "hunter.h"

using namespace std;

int main()
{
    ifstream file("input.txt");
    if(file.fail()) { cout << "File open error!\n"; return 1; }

    Hunter* h = new Hunter("Jack", 62);

    string line, place, date, species, gender;
    double weight, right, left, horn;
    Animal* animal;

    while(getline(file, line))
    {
        istringstream is(line);

        is >> place >> date >> species >> weight >> gender;

        if(species == "lion")
        {
            animal = new Lion(weight, gender == "male");
        }
        else if(species == "rhino")
        {
            is >> horn;
            animal = new Rhino(weight, horn);
        }
        else if(species == "elephant")
        {
            is >> right >> left;
            animal = new Elephant(weight, right, left);
        }

        h->shot(animal, place, date);
    }

    cout << "Number of the male lions: " << h->countMaleLions() << endl;

    double rate;
    if(h->maxHornWeigthRate(rate))
        cout << "The most horn-weight rate among the rhinos: " << rate << endl;
    else
        cout << "There are no rhinos!\n";

    if(h->searchEqualTusks())
        cout << "There exists an elephant with same length tusks.\n";
    else
        cout << "There is no elephant with same length tusks.\n";

    delete h;

    return 0;
}
