package classes;
class Circle{
    double x;
    double y;
    double radius;

    public void enlarge(double f){
        radius *= f;
    }

    public double getArea(){
        return radius*radius*Math.PI;
    }

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

}

public class CircleMain {
    public static void main(String [] args){
        Circle c = new Circle(4.2, 5.5, 6.2);
        System.out.println(c.getArea());
        c.enlarge(3.4);
        System.out.println(c.getArea());

    }    
}
