class PointMain {
    public static void main(String [] args){
        Point p = new Point(3,4);
        p.move(3,4);
        System.out.println(p.x + " " + p.y);
        p.mirror(0,0);
        System.out.println(p.x + " 1" + p.y);
        //Point origin = new Point(0,0);
        
    }
}

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

