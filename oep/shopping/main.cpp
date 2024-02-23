#include <iostream>
#include "store.h"
#include "customer.h"

using namespace std;

int main()
{
    try
    {
        Customer* c = new Customer("customer.txt");
        Store* s = new Store("food.txt", "tech.txt");

        c->shopping(s);

        delete s;
        delete c;
    }
    catch(...)
    {
        cerr << "Something went wrong!\n";
        return 1;
    }

    return 0;
}
