#include <iostream>
#include "Menu.h"
#include "Complex.h"


void Menu::display()
{
    int menuPoint;
    do {
        std::cout << "\n****************************************\n";
        std::cout << "---------KOMPLEX SZAM KALKULATOR--------\n\n";
        std::cout << "   A kezdeshez nyomjon le egy entert!\n";
        std::cout << "****************************************\n";
        menuPoint = getMenuPoint();
        system("CLS");
        switch (menuPoint) {
        case 1:
            addComplex();
            break;
        case 2:
            subComplex();
            break;
        case 3:
            mulComplex();
            break;
        case 4:
            divComplex();
            break;
        default:
            std::cout << "\nViszlat!\n";
            exit(0);
            break;
        }
    } while (menuPoint != 0);
}

int Menu::getMenuPoint()
{
    clearInput();
    int menuPoint = 0;
    std::string menuPointString = "";
    bool hiba = false;
    do
    {
        if(hiba){
            std::cout << "\n****************************************\n";
            std::cout << "---------KOMPLEX SZAM KALKULATOR--------\n\n";
            std::cout << "   A kezdeshez nyomjon le egy entert!\n";
            std::cout << "****************************************\n";
        }

        std::cout << "\n****************************************\n\n";
        std::cout << "0. Kilepes\n";
        std::cout << "1. Ket komplex szam osszeadasa\n";
        std::cout << "2. Ket komplex szam kivonasa\n";
        std::cout << "3. Ket komplex szam szorzasa\n";
        std::cout << "4. Ket komplex szam osztasa\n";
        std::cout << "\n****************************************\n";
        std::cout << "\nKerem adja meg a kivant menupont szamat! (0-4)";
        std::cout << "\nMenupont szama: ";

        hiba = false;

        const std::regex regex ("[0-4]{1}");
        getline(std::cin, menuPointString);

        if(!std::regex_match(menuPointString, regex))
        {
            std::cout << "\nErvenytelen bemenet, kerem egy szamot adjon meg 0-tol 4-ig a menupontok kivalasztasahoz!" << std::endl;
            std::cout << "A bemenet ujboli megadasahoz nyomjon meg egy billentyut!" << std::endl;
            hiba = true;
            system("pause");
            system("CLS");
        }else{
            menuPoint = std::stoi(menuPointString);
        }
    } while (hiba);
    return menuPoint;
}

void Menu::addComplex()
{
    std::cout << "\n****************************************\n\n";
    std::cout << "Kerem adja meg az osszeadast \"C1 + C2\" alakban, ahol C1 es C2 alakja \"x+yi\" \n";
    std::cout << "Pelda: 1-3i + 3+8i \n";
    std::string first;
    std::string second;
    Complex firstComplex;
    Complex secondComplex;
    bool hiba;
    do
    {
        std::cout << "\nAdja meg az osszeadas elso tagjat: ";
        hiba = false;
        std::cin >> first;
        try
        {
            firstComplex = Complex(first);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);

    do
    {
        std::cout << "\nAdja meg az osszeadas masodik tagjat: ";
        hiba = false;
        std::cin >> second;
        try
        {
            secondComplex = Complex(second);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);

    Complex sum = Complex::add(firstComplex, secondComplex);
    std::cout << "\nAz eredmeny: " + sum.toString()+"\nHa mar feljegyezte az eredmenyt es tovabb szeretne lepni, majd nyomjom meg egy gombot!\n";
    system("pause");
    system("CLS");
}

void Menu::subComplex()
{
    std::cout << "\n****************************************\n\n";
    std::cout << "Kerem adja meg az kivonast \"C1 - C2\" alakban, ahol C1 es C2 alakja \"x+yi\" \n";
    std::cout << "Pelda: 1-3i - 3+8i \n";
    std::string first;
    std::string second;
    Complex firstComplex;
    Complex secondComplex;
    bool hiba;
    do
    {
        std::cout << "\nAdja meg az kivonas elso tagjat: ";
        hiba = false;
        std::cin >> first;
        try
        {
            firstComplex = Complex(first);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);

    do
    {
        std::cout << "\nAdja meg az kivonas masodik tagjat: ";
        hiba = false;
        std::cin >> second;
        try
        {
            secondComplex = Complex(second);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);
    Complex difference = Complex::sub(firstComplex, secondComplex);
    std::cout << "\nAz eredmeny: " + difference.toString() + "\nHa mar feljegyezte az eredmenyt es tovabb szeretne lepni, majd nyomjom meg egy gombot!\n";
    system("pause");
    system("CLS");
}

void Menu::mulComplex()
{
    std::cout << "\n****************************************\n\n";
    std::cout << "Kerem adja meg a szorzast \"C1 * C2\" alakban, ahol C1 es C2 alakja \"x+yi\" \n";
    std::cout << "Pelda: 1-3i * 3+8i \n";
    std::string first;
    std::string second;
    Complex firstComplex;
    Complex secondComplex;
    bool hiba;
    do
    {
        std::cout << "\nAdja meg a szorzas elso tagjat: ";
        hiba = false;
        std::cin >> first;
        try
        {
            firstComplex = Complex(first);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);

    do
    {
        std::cout << "\nAdja meg a szorzas masodik tagjat: ";
        hiba = false;
        std::cin >> second;
        try
        {
            secondComplex = Complex(second);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);
    Complex product = Complex::mul(firstComplex, secondComplex);
    std::cout << "\nAz eredmeny: " + product.toString() + "\nHa mar feljegyezte az eredmenyt es tovabb szeretne lepni, majd nyomjom meg egy gombot!\n";
    system("pause");
    system("CLS");
}

void Menu::divComplex()
{
    std::cout << "\n****************************************\n\n";
    std::cout << "Kerem adja meg az osztast \"C1 / C2\" alakban, ahol C1 es C2 alakja \"x+yi\" \n";
    std::cout << "FIGYELEM: A nevezo nem lehet nulla! \n";
    std::cout << "Pelda: 1-3i / 3+8i \n";
    std::string first;
    std::string second;
    Complex firstComplex;
    Complex secondComplex;
    bool hiba;
    do
    {
        std::cout << "\nAdja meg az osztas elso tagjat: ";
        hiba = false;
        std::cin >> first;
        try
        {
            firstComplex = Complex(first);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);

    do
    {
        std::cout << "\nAdja meg az osztas masodik tagjat: ";
        hiba = false;
        std::cin >> second;
        try
        {
            secondComplex = Complex(second);
        }
        catch (std::invalid_argument exc)
        {
            hiba = true;
            std::cout << "\nHibas a szintaxis, a komplex szamot \"x+yi\" alakban adja meg!\n";
            std::cout << "Persze ha a komplex szam \"-x+yi\" vagy hasonlo alakban van, ugy is megadhatja, ne zarojelezzen!\n";
            clearInput();
        }
    } while (hiba);
    bool nulla = false;
    Complex quotient;
    try{
        quotient = Complex::div(firstComplex, secondComplex);
    }catch(std::logic_error exc){
        std::cout << "\nNullaval nem lehet osztani!\n";
        nulla = true;
    }
    
    if(!nulla){
        std::cout << "\nAz eredmeny: " + quotient.toString() + "\nHa mar feljegyezte az eredmenyt es tovabb szeretne lepni, majd nyomjom meg egy gombot!\n";
    }else{
        std::cout << "\nNincs eredmeny.\nA tovabblepeshez nyomjon meg egy gombot!\n";
    }
    system("pause");
    system("CLS");
}

static void clearInput()
{
	std::cin.clear();
    std::cin.ignore(999, '\n');
}