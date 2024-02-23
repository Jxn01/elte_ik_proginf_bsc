#ifndef POINT_H
#define POINT_H


// pont osztaly
class Point
{
    // mindenki hozzaferhet
    public:
        // konstruktor
        Point(double a = 0, double b = 0): _x(a), _y(b) { }

        // getter metodusok
        double getX() const { return _x; }
        inline double getY() const { return _y; }

        // ket pont tavolsaga metodus
        double distance(const Point &q) const
        {
            return sqrt(pow(_x - q.getX(), 2) + pow(_y - q.getY(), 2));
        }

    // csak az adott osztaly ferhet hozza
    private:
        // adattagok
        double _x;
        double _y;
};

#endif // POINT_H
