package ora3.fel1.point2d;


class Point {
    double x, y;

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
}

