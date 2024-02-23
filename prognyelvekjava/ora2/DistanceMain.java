import java.util.ArrayList;

class Point {

    public double x;
    public double y;

    public void move(double dx, double dy){
        x += dx;
        y += dy;
    }

    public void mirror(double cx, double cy){
        x = 2*cx - x;
        y = 2*cy - y;
    }

    public void mirror(Point center){
        x = 2*center.x-x;
        y = 2*center.y-y;
    }

    public double distance(Point to){
        double dx = x - to.x;
        double dy = y - to.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

}

public class DistanceMain {
    public static void main(String [] args){
        ArrayList<Point> points = new ArrayList<>();

        for(int i = 0; i < args.length; i+=2){
            points.add(new Point(Double.parseDouble(args[i]),Double.parseDouble(args[i+1])));
        }

        double distanceSum = 0;

        for(int i = 0; i < points.size()-1; i++){
            distanceSum += points.get(i).distance(points.get(i+1));
        }

        System.out.println(distanceSum);
    }
}
