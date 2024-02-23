#include <iostream>
#include <string>
#include <regex>

static void clearInput();

class Complex
{
private:
    double x;
    double y;

public:
    Complex() = default;
	Complex(double a, double b);
    Complex(std::string expression);
    static Complex add(Complex a, Complex b);
    static Complex sub(Complex a, Complex b);
    static Complex mul(Complex a, Complex b);
    static Complex div(Complex a, Complex b);
    std::string toString();
};

Complex::Complex(double a, double b)
{
    x = a;
    y = b;
}

Complex::Complex(std::string expression)
{
    const std::regex regex ("-?[0-9]+.?[0-9]*[+-][0-9]+.?[0-9]*i");
	if(std::regex_match(expression, regex))
	{
        int treshold = -1; // length
        for(int i = 0; i < expression.length(); i++)
        {
	        if(expression[i] == '-' && i != 0 || expression[i] == '+' && i!=0)
	        {
                treshold = i;
                break;
	        }
        }
		x = stod(expression.substr(0, treshold));
        if(expression[treshold] == '+')
        {
            treshold++;
        }
        y = stod(expression.substr(treshold, expression.length()-(treshold+1)));

	}else
	{
        throw std::invalid_argument("Argument is in invalid syntax!");
	}
}

Complex Complex::add(Complex a, Complex b)
{
    Complex result = Complex(a.x, a.y);
    result.x += b.x;
    result.y += b.y;
    return result;
}

Complex Complex::sub(Complex a, Complex b)
{
    Complex result = Complex(a.x, a.y);
    result.x -= b.x;
    result.y -= b.y;
    return result;
}

Complex Complex::mul(Complex a, Complex b)
{
    Complex result = Complex(a.x, a.y);
    const double tempx = result.x;

    result.x = result.x * b.x - result.y * b.y;
    result.y = tempx * b.y + result.y * b.x;
    return result;
}

Complex Complex::div(Complex a, Complex b)
{
    Complex result = Complex(a.x, a.y);
    if(b.x == 0.0 || b.y == 0.0)
    {
        throw std::exception("Cant divide by 0!");
    }

    const double tempx = result.x;

    result.x = (result.x * b.x - result.y * b.y) / (pow(b.x, 2) + pow(b.y, 2));
    result.y = (result.y * b.x - tempx * b.y) / (pow(b.x, 2) + pow(b.y, 2));
    return result;
}

std::string Complex::toString()
{
	if(y < 0)
	{
        return std::to_string(x) + std::to_string(y) + "i";
	}else
	{
        return std::to_string(x) + "+" + std::to_string(y) + "i";
	}
}

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

void Menu::display()
{
    int menuPoint;
    do {
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
    int menuPoint = 0;

    bool hiba;

    std::cout << "\n****************************************\n\n";
    std::cout << "0. Kilepes\n";
    std::cout << "1. Ket komplex szam osszeadasa\n";
    std::cout << "2. Ket komplex szam kivonasa\n";
    std::cout << "3. Ket komplex szam szorzasa\n";
    std::cout << "4. Ket komplex szam osztasa\n";
    std::cout << "\n****************************************\n";

    do
    {
        hiba = false;
        std::cin >> menuPoint;
        if (std::cin.fail())
        {
            hiba = true;
            clearInput();
        }
    } while (hiba);

    return menuPoint;
}

void Menu::addComplex()
{
    std::cout << "\n****************************************\n\n";
    std::cout << "Kerem adja meg az osszeadast \"C1 + C2\" alakban, ahol C1 és C2 alakja \"x+yi\" \n";
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
    std::cout << "Kerem adja meg az kivonast \"C1 - C2\" alakban, ahol C1 és C2 alakja \"x+yi\" \n";
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
    std::cout << "Kerem adja meg a szorzast \"C1 * C2\" alakban, ahol C1 és C2 alakja \"x+yi\" \n";
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
    std::cout << "Kerem adja meg az osztast \"C1 / C2\" alakban, ahol C1 és C2 alakja \"x+yi\" \n";
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
    Complex quotient = Complex::div(firstComplex, secondComplex);
    std::cout << "\nAz eredmeny: " + quotient.toString() + "\nHa mar feljegyezte az eredmenyt es tovabb szeretne lepni, majd nyomjom meg egy gombot!\n";
    system("pause");
    system("CLS");
}

static void clearInput()
{
	std::cin.clear();
    std::cin.ignore(999, '\n');
}

int main()
{
    Menu menu = Menu();
    menu.display();

    return 0;
}
