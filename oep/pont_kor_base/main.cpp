#include <iostream>
#include <vector>
#include <fstream>
#include "point.h"
#include "circle.h"

using namespace std;

// fuggvenyek deklaralasa
int count_points(const vector<Point>&, const Circle&);
bool search_point(const vector<Point>&, const Circle&, unsigned int&);

int main()
{
    // input fajl megnyitasa
    ifstream file("input.txt");

    // ha hiba tortent a megnyitaskor, akkor lepjunk ki
    if(file.fail())
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
        while(file >> x >> y)
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
    catch(const Circle::WrongRadiusException &e)
    {
        cerr << "Tul kicsi sugar!" << endl;
        return 1;
    }
    catch(...)
    {
        cerr << "Valamilyen hiba tortent!" << endl;
        return 1;
    }

    return 0;
}

// fuggvenyek definialasa

int count_points(const vector<Point> &points, const Circle &circle)
{
    int result;

    circle.

    return result;

}

bool search_point(const vector<Point>& points, const Circle& circle, unsigned int& ind)
{
    bool l = false;
    for(unsigned int i = 0; !l && i < points.size(); ++i)
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
