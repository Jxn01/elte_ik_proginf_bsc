package bead9;

public class Main{
    public static void main (String [] args){
        Square square = new Square(5);
        Rectangle rectangle = new Rectangle(2, 3);
        Circle circle = new Circle(5);
        System.out.println("Square(5) perimeter: "+square.getPerimeter());
        System.out.println("Square(5) area: "+square.getArea());
        System.out.println("Rectangle(2, 3) perimeter: "+rectangle.getPerimeter());
        System.out.println("Rectangle(2, 3) area: "+rectangle.getArea());
        System.out.println("Circle(5) perimeter: "+circle.getPerimeter());
        System.out.println("Circle(5) area: "+circle.getArea());
    }
}

interface Shape {
    double getPerimeter();
    double getArea();
}

class Square implements Shape{
    double a;

    public Square(double a) {
        this.a = a;
    }

    @Override
    public double getPerimeter(){
        return 4*a;
    }

    @Override
    public double getArea(){
        return a*a;
    }


}

class Rectangle implements Shape{
    double a;
    double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double getPerimeter(){
        return 2*a+2*b;
    }

    @Override
    public double getArea(){
        return a*b;
    }

}

class Circle implements Shape{
    double r;

    public Circle(double r) {
        this.r = r;
    }

    @Override
    public double getPerimeter(){
        return 2*r*Math.PI;
    }

    @Override
    public double getArea(){
        return Math.PI*r*r;
    }

}