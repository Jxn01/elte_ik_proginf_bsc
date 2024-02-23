#include "department.h"
#include <fstream>

Department::Department(const std::string& path)
{
    std::ifstream file(path);
    if(file.fail()) throw FileErrorException();

    std::string name;
    int price;
    while(file >> name >> price)
        _stock.push_back(new Product(name, price));
}

void Department::takeOutFromStock(Product* product)
{
    bool l = false;
    int ind;
    for(unsigned int i = 0; !l && i < _stock.size(); ++i)
    {
        l = (_stock[i] == product);
        ind = i;
    }

    if(l) _stock.erase(_stock.begin() + ind);
}
