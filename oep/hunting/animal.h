#ifndef ANIMAL_H_INCLUDED
#define ANIMAL_H_INCLUDED

class Animal
{
    public:
            Animal(double w): _weight(w) { }
            virtual ~Animal() { }

            virtual bool isElephant() const { return false; }
            virtual bool isRhino() const { return false; }
            virtual bool isLion() const { return false; }

            double getWeight() const { return _weight; }

    protected:
        double _weight;
};

class Elephant : public Animal
{
    public:
        Elephant(double w, double r, double l): Animal(w), _right(r), _left(l) { }

        bool isElephant() const override { return true; }

        double getRight() const { return _right; }
        double getLeft() const { return _left; }

    private:
        double _right;
        double _left;
};

class Rhino : public Animal
{
    public:
        Rhino(double w, double h): Animal(w), _horn(h) { }

        bool isRhino() const override { return true; }

        double getHorn() const { return _horn; }

    private:
        double _horn;
};

class Lion : public Animal
{
private:
    bool male;
public:
    Lion(double w, bool m) : Animal(w), male(m) { }
    bool getMale() { return male; }
};

#endif // ANIMAL_H_INCLUDED
