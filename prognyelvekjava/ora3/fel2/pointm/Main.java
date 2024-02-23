package pointm;

// import point2d;


class Main {
    public static void main(String[] args) {
        point2d.Point p = new point2d.Point(); // new Point()

        p.x = 1;
        p.y = 2;
        p.move(3, 5);
        System.out.println("p x = " + p.x + ", y = " + p.y);

        point2d.Point p2 = new point2d.Point(); // new Point()
        p2.x = 4;
        p2.y = -1;
        System.out.println("p x = " + p2.x + ", y = " + p2.y);

        System.out.println("distance = " + p.distance(p2));
    }
}

