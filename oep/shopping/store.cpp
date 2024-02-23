#include "store.h"

Store::Store(const std::string& fName, const std::string& tName)
{
    _food = new Department(fName);
    _tech = new Department(tName);
}
