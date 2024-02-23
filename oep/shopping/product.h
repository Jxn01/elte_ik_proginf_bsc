#ifndef PRODUCT_H_INCLUDED
#define PRODUCT_H_INCLUDED

#include <string>

class Product
{
    public:
        Product(std::string n, int p): _name(n), _price(p) { }

        std::string getName() const { return _name; }
        int getPrice() const { return _price; }

    private:
        std::string _name;
        int _price;
};

#endif // PRODUCT_H_INCLUDED
