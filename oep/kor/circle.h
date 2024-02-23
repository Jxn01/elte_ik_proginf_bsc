#ifndef CIRCLE_H
#define CIRCLE_H

#include "point.h"

class Circle
{
    public:
        // sajat kivetel felvetele, az std::exception osztalybol szarmazik le
        class WrongRadiusException : public std::exception {};

        // konstruktorok
        Circle(){ }

        /// TODO: Konstruktor; a fenti kivetelt dobja, ha hibas a sugar
        Circle( Point a, double b ):
            if(b <= 0)
            {
                throw WrongRadiusException();
            }else
            {
                p(a);
                r(b);
            }

		{ }


        /// TODO: Tartalmaz metodus
        bool contains(Point p) {
            Point tmp = Point(0, 0);
            if (p.distance(tmp) <= r) {

            }
          
        }

    private:
        Point p;
        double r;

};

#endif // CIRCLE_H
