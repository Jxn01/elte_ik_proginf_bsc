#include <iostream>
#include <sstream>

using namespace std;

// sajat exception osztalyok
class Nulldenominator : public std::exception { };
class Nulldivision    : public std::exception { };

/* *****************************************
        Rational1 a(3,-2), b(-20,3), c;
        c = a.add(b);
        c = a.sub(b);
        c = a.mul(b);
        c = a.div(b);
   ***************************************** */

class Rational1
{
    public:
        Rational1(int x = 0, int y = 1): n(x), d(y)
        {
            if(y == 0) throw Nulldenominator();
        }

        Rational1 add(const Rational1& b) const;
        Rational1 sub(const Rational1& b) const;
        Rational1 mul(const Rational1& b) const;
        Rational1 div(const Rational1& b) const;

        string toString() const;

    private:
        int n;
        int d;
};

Rational1 Rational1::add(const Rational1& b) const
{
    return Rational1(this->n * b.d + this->d * b.n, this->d * b.d);
}

Rational1 Rational1::sub(const Rational1& b) const
{
    return Rational1(n * b.d - d * b.n, d * b.d);
}

Rational1 Rational1::mul(const Rational1& b) const
{
    return Rational1(n * b.n, d * b.d);
}

Rational1 Rational1::div(const Rational1& b) const
{
    if(b.n == 0) throw Nulldivision();
    return Rational1(n * b.d, d * b.n);
}

string Rational1::toString() const
{
    ostringstream os;
    os << "(" << n << "/" << d << ")";
    return os.str();
}

/* *****************************************
        Rational2 a(3,-2), b(-20,3), c;
        c = add(a,b);
        c = sub(a,b);
        c = mul(a,b);
        c = div(a,b);
   ***************************************** */

class Rational2
{
    public:
        Rational2(int x = 0, int y = 1): n(x), d(y)
        {
            if(y == 0) throw Nulldenominator();
        }

        // hozzafer a privat adattagokhoz, deklaralas az osztalyon belul, definialas az osztalyon kivul
        friend Rational2 add(const Rational2& a, const Rational2& b);
        friend Rational2 sub(const Rational2& a, const Rational2& b);
        friend Rational2 mul(const Rational2& a, const Rational2& b);
        friend Rational2 div(const Rational2& a, const Rational2& b);

        string toString() const;

    private:
        int n;
        int d;
};

Rational2 add(const Rational2& a, const Rational2& b)
{
    return Rational2(a.n * b.d + a.d * b.n, a.d * b.d);
}

Rational2 sub(const Rational2& a, const Rational2& b)
{
    return Rational2(a.n * b.d - a.d * b.n, a.d * b.d);
}

Rational2 mul(const Rational2& a, const Rational2& b)
{
    return Rational2(a.n * b.n, a.d * b.d);
}

Rational2 div(const Rational2& a, const Rational2& b)
{
    if(b.n == 0) throw Nulldivision();
    return Rational2(a.n * b.d, a.d * b.n);
}

string Rational2::toString() const
{
    ostringstream os;
    os << "(" << n << "/" << d << ")";
    return os.str();
}

/* ****************************************
        Rational a(3,-2), b(-20,3), c;
        c = a + b;
        c = a - b;
        c = a * b;
        c = a / b;
    *************************************** */

class Rational3
{
    public:
        Rational3(int x = 0, int y = 1): n(x), d(y)
        {
            if(y == 0) throw Nulldenominator();
        }

        friend Rational3 operator+(const Rational3& a, const Rational3& b);
        friend Rational3 operator-(const Rational3& a, const Rational3& b);
        friend Rational3 operator*(const Rational3& a, const Rational3& b);
        friend Rational3 operator/(const Rational3& a, const Rational3& b);

        string toString() const;
        friend ostream& operator<<(ostream& o, const Rational3& a);

    private:
        int n;
        int d;
};

Rational3 operator+(const Rational3& a, const Rational3& b)
{
    return Rational3(a.n * b.d + a.d * b.n, a.d * b.d);
}

Rational3 operator-(const Rational3& a, const Rational3& b)
{
    return Rational3(a.n * b.d - a.d * b.n, a.d * b.d);
}

Rational3 operator*(const Rational3& a, const Rational3& b)
{
    return Rational3(a.n * b.n, a.d * b.d);
}

Rational3 operator/(const Rational3& a, const Rational3& b)
{
    if(b.n == 0) throw Nulldivision();
    return Rational3(a.n * b.d, a.d * b.n);
}

string Rational3::toString() const
{
    ostringstream os;
    os << "(" << n << "/" << d << ")";
    return os.str();
}

ostream& operator<<(ostream& o, const Rational3& a)
{
    o << a.toString();
    return o;
}

int main()
{
    cout << "Vart eredmenyek: (-49/6), (31/6), (10/1), (9/40)" << endl;

    cout << "\nRational1" << endl;
    Rational1 a1(3,-2), b1(-20,3), c1;
    c1 = a1.add(b1); cout << "a + b = " << c1.toString() << endl;
    c1 = a1.sub(b1); cout << "a - b = " << c1.toString() << endl;
    c1 = a1.mul(b1); cout << "a * b = " << c1.toString() << endl;
    c1 = a1.div(b1); cout << "a / b = " << c1.toString() << endl;

    cout << "\nRational2" << endl;
    Rational2 a2(3,-2), b2(-20,3), c2;
    c2 = add(a2,b2); cout << "a + b = " << c2.toString() << endl;
    c2 = sub(a2,b2); cout << "a - b = " << c2.toString() << endl;
    c2 = mul(a2,b2); cout << "a * b = " << c2.toString() << endl;
    c2 = div(a2,b2); cout << "a / b = " << c2.toString() << endl;

    cout << "\nRational3" << endl;
    Rational3 a3(3,-2), b3(-20,3), c3;
    c3 = a3 + b3; cout << "a + b = " << c3 << endl;
    c3 = a3 - b3; cout << "a - b = " << c3 << endl;
    c3 = a3 * b3; cout << "a * b = " << c3 << endl;
    c3 = a3 / b3; cout << "a / b = " << c3 << endl;

    return 0;
}
