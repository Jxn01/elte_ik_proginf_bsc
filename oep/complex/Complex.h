#ifndef COMPLEX_H_INCLUDED
#define COMPLEX_H_INCLUDED
#include <string>
#include <regex>
#include <math.h>

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

#endif //COMPLEX_H_INCLUDED