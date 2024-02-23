#ifndef MENU_H_INCLUDED
#define MENU_H_INCLUDED
#include <string>
#include <iostream>
#include <regex>

class Menu
{
private:
    int getMenuPoint();
    void addComplex();
    void subComplex();
    void mulComplex();
    void divComplex();
public:
    Menu() = default;
    void display();
};

static void clearInput();

#endif //MENU_H_INCLUDED

