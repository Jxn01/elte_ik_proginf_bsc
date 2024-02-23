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

class Rectangle{
    private double x;
    private double y;
    private double width;
    private double height;

    public Point bottomLeft(){
        Point p = new Point(x,y);

        if(width < 0){
            p.x+=width;
        }

        if(height < 0){
            p.y+=height;
        }
        
        return p;
    }

    public Point bottomRight(){
        Point p = new Point(x,y);

        if(width > 0){
            p.x+=width;
        }
        
        if(height < 0){
            p.y+=height;
        }

        return p;
    }

    public Point topLeft(){
        Point p = new Point(x,y);

        if(width < 0){
            p.x+=width;
        }
        
        if(height > 0){
            p.y+=height;
        }

        return p;
    }

    public Point topRight(){
        Point p = new Point(x,y);

        if(width > 0){
            p.x+=width;
        }
        
        if(height > 0){
            p.y+=height;
        }

        return p;
    }

    public Rectangle() {

    }

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "{" +
            " x='" + x + "'" +
            ", y='" + y + "'" +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            "}";
    }

}

public class Gyakorlo4 {
    public static void main (String [] args){
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for(int i = 0; i < args.length; i+=4){
            rectangles.add(new Rectangle(Double.parseDouble(args[i]),Double.parseDouble(args[i+1]),Double.parseDouble(args[i+2]),Double.parseDouble(args[i+3])));
        }

        Point bottomLeft = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        Point topRight = new Point(Double.MIN_VALUE, Double.MIN_VALUE);

        for(Rectangle r : rectangles){
            if(r.bottomLeft().x <= bottomLeft.x && r.bottomLeft().y <= bottomLeft.y){
                bottomLeft.x = r.bottomLeft().x;
                bottomLeft.y = r.bottomLeft().y;
            }

            if(r.topRight().x >= topRight.x && r.topRight().y >= topRight.y){
                topRight.x = r.topRight().x;
                topRight.y = r.topRight().y;
            }
        }

        System.out.println("Bounding rectangle: " + bottomLeft.x+";"+bottomLeft.y+" - "+topRight.x+";"+topRight.y);
    }
}
