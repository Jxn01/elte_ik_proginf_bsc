#include "customer.h"
#include <fstream>
#include <iostream>

Customer::Customer(const std::string& path)
{
    std::ifstream file(path);
    if(file.fail()) throw FileErrorException();

    file >> _name;
    std::string item;
    while(file >> item) _list.push_back(item);
}

void Customer::shopping(Store* store)
{
    std::cout << _name << " purchased these items:\n";

    for(std::string productName : _list)
    {
        Product* product;
        if(search(productName, store->_food, product))
        {
            purchase(product, store->_food);
            std::cout << product->getName() << " " << product->getPrice() << std::endl;
        }
    }

    for(std::string productName : _list)
    {
        Product* product;
        if(minsearch(productName, store->_tech, product))
        {
            purchase(product, store->_tech);
            std::cout << product->getName() << " " << product->getPrice() << std::endl;
        }
    }
}

void Customer::purchase(Product* product, Department* department)
{
    /// TODO: Vesz metodus
    department->takeOutFromStock(product);
    _cart.push_back(product);
}

bool Customer::search(const std::string& name, Department* department, Product* &product)
{
    /// TODO: Keres metodus
    bool l = false;
    for(Product* p : department->_stock)
    {
        if((l = (p->getName() == name)))
        {
            product = p;
            break;
        }
    }
    return l;
}

bool Customer::minsearch(const std::string& name, Department* department, Product* &product)
{
    /// TODO: OlcsotKeres metodus
    bool l = false;
    int min;
    for(Product* p : department->_stock)
    {
        if(p->getName() != name) continue;
        else if(l && p->getName() == name)
        {
            if(p->getPrice() < min)
            {
                min = p->getPrice();
                product = p;
            }
        }
        else if(!l && p->getName() == name)
        {
            l = true;
            min = p->getPrice();
            product = p;
        }
    }
    return l;
}
