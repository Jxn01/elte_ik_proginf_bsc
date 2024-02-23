package ora4;

public class F1 {
    public static void main (String [] args){
        System.out.println("Kerem adja meg a tomb hosszat!");
        int length = Integer.parseInt(System.console().readLine());

        double [] numbers = new double[length];

        for(int i=0; i<length; i++){
            System.out.println("Kerem adja meg a "+(i+1)+". elemet!");
            numbers[i]=Double.parseDouble(System.console().readLine());
        }

        double sum = 0.0;
        double avr = 0.0;

        for(int i=0; i<length;i++){
            sum+=numbers[i];
        }

        avr=sum/length;

        double min = Double.MAX_VALUE;
        int ind = 0;

        for(int i=0;i<length;i++){
            if(Math.abs(numbers[i]-avr)<min){
                min=Math.abs(numbers[i]-avr);
                ind = i;
            }
        }

        System.out.println();
        System.out.println("A legkozelebb a "+avr+" atlaghoz a "+(ind+1)+" indexen levo "+numbers[ind]+" szam van");

        //----------------------------------------------------------------------------------------------------------------------

        System.out.println();
        Point p = new Point(0.0, 0.0);
        System.out.println(p.toString());

        System.out.println("Kerem adja meg a pontok szamat");
        int n = Integer.parseInt(System.console().readLine());
        Point[] points2 = new Point[n];

        for(int i=0;i<n;i++){
            System.out.println("Adja meg az "+(i+1)+". pont x koordinatajat!");
            double x = Double.parseDouble(System.console().readLine());
            System.out.println("Adja meg az "+(i+1)+". pont y koordinatajat!");
            double y = Double.parseDouble(System.console().readLine());
            points2[i] = new Point(x,y);
        }

        System.out.println(Point.tomegkozeppont(points2));
    }
}

class Point {
    int id;
    static int current_id = 1;
    double x, y;

    static Point tomegkozeppont(Point[] points){
        Point result = new Point();
        result.id = 100;

        for(int i = 0; i < points.length; i++){
            result.x +=points[i].x; 
            result.y +=points[i].y; 
        }

        result.x /= (double)points.length;
        result.y /= (double)points.length;

        return result;
    }

    void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    void mirror(double cx, double cy) {
        x = 2 * cx - x;
        y = 2 * cy - y;
    }

    double distance(Point that) {
        double dx = x - that.x;
        double dy = y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        id = current_id++;
    }

    @Override
    public String toString() {
        return "ID"+id+":("+x+","+y+")";
    }
}
