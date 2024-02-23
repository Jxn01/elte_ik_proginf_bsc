#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

class Point
{
    // mindenki hozzaferhet
public:
    // konstruktor
    Point(double a = 0, double b = 0) : _x(a), _y(b) { }

    // getter metodusok
    double getX() const { return _x; }
    inline double getY() const { return _y; }

    // ket pont tavolsaga metodus
    double distance(const Point& q) const
    {
        return sqrt(pow(_x - q.getX(), 2) + pow(_y - q.getY(), 2));
    }

    // csak az adott osztaly ferhet hozza
private:
    // adattagok
    double _x;
    double _y;
};

class Circle
{
public:
    // sajat kivetel felvetele, az std::exception osztalybol szarmazik le
    class WrongRadiusException : public std::exception {};

    // konstruktorok
    Circle() { }

    /// TODO: Konstruktor; a fenti kivetelt dobja, ha hibas a sugar
    Circle(Point a, double b) {
        if (b <= 0)
        {
            throw WrongRadiusException();
        }
        else
        {
            p = a;
            r = b;
        }
    }


    /// TODO: Tartalmaz metodus
    bool contains(Point p) {
        Point tmp = Point(0, 0);
        if(p.distance(tmp)<=r)
        {
            return true;
        }else
        {
            return false;
        }
    }

private:
    Point p;
    double r;

};

using namespace std;

// fuggvenyek deklaralasa
int count_points(const vector<Point>&, const Circle&);
bool search_point(const vector<Point>&, const Circle&, unsigned int&);

int main()
{
    // input fajl megnyitasa
    ifstream file("input.txt");

    // ha hiba tortent a megnyitaskor, akkor lepjunk ki
    if (file.fail())
    {
        cerr << "Hiba a fajl megnyitasa kozben!" << endl;
        return 1;
    }

    // probaljuk meg elvegezni a feladatot
    try
    {
        // a kor adatainak beolvasasa
        double x, y, r;
        file >> x >> y >> r;

        // a kor objektum peldanyositasa
        Circle c(Point(x, y), r);

        // a pontok beolvasasa es eltarolasa
        vector<Point> p;
        while (file >> x >> y)
        {
            p.push_back(Point(x, y));
        }

        // szamlalas elvegzese
        int result = count_points(p, c);
        cout << "A korlemezen levo pontok szama: " << result << endl;

        /*
        // linearis kereses pelda
        unsigned int ind;
        if(search_point(p, c, ind))
            cout << "A korlemezen levo elso pont: (" << p[ind].getX() << "," << p[ind].getY() << ")" << endl;
        else
            cout << "Egyik pont sem esik a korlemezbe." << endl;
        */
    }
    catch (const Circle::WrongRadiusException& e)
    {
        cerr << "Tul kicsi sugar!" << endl;
        return 1;
    }
    catch (...)
    {
        cerr << "Valamilyen hiba tortent!" << endl;
        return 1;
    }

    return 0;
}

// fuggvenyek definialasa

int count_points(vector<Point>& points, Circle& circle)
{
    int result = 0;

    for(Point elem : points)
    {
	    if(circle.contains(elem))
	    {
            result++;
	    }
    }

	return result;

}

bool search_point(const vector<Point>& points, Circle& circle, unsigned int& ind)
{
    bool l = false;
    for (unsigned int i = 0; !l && i < points.size(); ++i)
    {
        l = circle.contains(points[i]);
        ind = i;
    }
    return l;
}

// reference vs pointer
/*
    int value = 6;
    int* pointerToValue = &value;
    int& aliasOfValue = value;

    cout << "Values before changing:" << endl;
    cout << "value = " << value << endl;
    cout << "pointerToValue = " << pointerToValue << endl;
    cout << "*pointerToValue = " << *pointerToValue << endl;
    cout << "aliasOfValue = " << aliasOfValue << endl << endl;

    *pointerToValue = 66;

    cout << "Values after changing *pointerToValue:" << endl;
    cout << "value = " << value << endl;
    cout << "pointerToValue = " << pointerToValue << endl;
    cout << "*pointerToValue = " << *pointerToValue << endl;
    cout << "aliasOfValue = " << aliasOfValue << endl << endl;

    aliasOfValue = 666;

    cout << "Values after changing aliasOfValue:" << endl;
    cout << "value = " << value << endl;
    cout << "pointerToValue = " << pointerToValue << endl;
    cout << "*pointerToValue = " << *pointerToValue << endl;
    cout << "aliasOfValue = " << aliasOfValue << endl << endl;
*/
