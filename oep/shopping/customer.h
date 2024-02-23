#ifndef CUSTOMER_H_INCLUDED
#define CUSTOMER_H_INCLUDED

#include "store.h"

class Customer
{
    public:
        class FileErrorException : public std::exception {};

        Customer(const std::string& path);
        ~Customer() { for(Product* p : _cart) delete p; }

        std::string getName() const { return _name; }

        void shopping(Store* store); //const Store& store

        std::vector<Product*> _cart;

    private:
        void purchase(Product* product, Department* department);
        bool search(const std::string& name, Department* department, Product* &product);
        bool minsearch(const std::string& name, Department* department, Product* &product);

        std::string _name;
        std::vector<std::string> _list;
};

#endif // CUSTOMER_H_INCLUDED
