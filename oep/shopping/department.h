#ifndef DEPARTMENT_H_INCLUDED
#define DEPARTMENT_H_INCLUDED

#include "product.h"
#include <vector>

class Department
{
    public:
        class FileErrorException : public std::exception {};

        Department(const std::string& path);
        ~Department() { for(Product* p : _stock) delete p; }

        void takeOutFromStock(Product* product);

        std::vector<Product*> _stock;
};

#endif // DEPARTMENT_H_INCLUDED
