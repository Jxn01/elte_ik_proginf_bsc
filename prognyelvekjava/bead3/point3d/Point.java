package bead3.point3d;
import java.util.ArrayList;

public class Point {
    private int id;
    private static int current_id = 1;
    private double x, y, z;
    private double mass;

    public static Point tomegkozeppont(ArrayList<Point> points){
        Point result = new Point();
        result.id = 100;

        for(int i = 0; i < points.size(); i++){
            result.x +=points.get(i).x*points.get(i).mass; 
            result.y +=points.get(i).y*points.get(i).mass; 
            result.z +=points.get(i).z*points.get(i).mass; 
        }

        result.x /= (double)points.size();
        result.y /= (double)points.size();
        result.z /= (double)points.size();

        return result;
    }

    public static Point tomegtolLegtavolabbi(ArrayList<Point> points){
        Point tomegkozeppont = tomegkozeppont(points);
        Point result = new Point();
        double maxDistance=0.0;
        for(Point elem: points){
            double currDistance = elem.distance(tomegkozeppont);
            if(maxDistance < currDistance){
                maxDistance = currDistance;
                result = elem;
            }
        }
        return result;
    }

    static double distanceFromOrigo(Point that) {
        double dx = 0 - that.x;
        double dy = 0 - that.y;
        double dz = 0 - that.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    double distance(Point that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        double dz = this.z - that.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public Point() {
    }

    public Point(double x, double y, double z, double mass){
        setX(x);
        setY(y);
        setZ(z);
        if(mass > 10 || mass < 0){
            throw new IllegalArgumentException("Mass should be between 0 and 10!");
        }else{
            setMass(mass);
        }
        this.id = current_id++;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    @Override
    public String toString() {
        return "ID:\tP00"+id+"\nx:\t"+x+" cm\ny:\t"+y+" cm\nz:\t"+z+" cm\nmass:\t"+mass+" kg";
    }
}
