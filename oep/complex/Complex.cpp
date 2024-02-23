#include "Complex.h"

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
        int threshold = -1; // length
        for(int i = 0; i < expression.length(); i++)
        {
	        if(expression[i] == '-' && i != 0 || expression[i] == '+' && i!=0)
	        {
                threshold = i;
                break;
	        }
        }
		x = stod(expression.substr(0, threshold));
        if(expression[threshold] == '+')
        {
            threshold++;
        }
        y = stod(expression.substr(threshold, expression.length()-(threshold+1)));

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
    if(b.x == 0.0 && b.y == 0.0)
    {
        throw std::logic_error("Cant divide by 0!");
    }

    result.x = (a.x * b.x + a.y * b.y) / (pow(b.x, 2) + pow(b.y, 2));
    result.y = (a.y * b.x - a.x * b.y) / (pow(b.x, 2) + pow(b.y, 2));
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