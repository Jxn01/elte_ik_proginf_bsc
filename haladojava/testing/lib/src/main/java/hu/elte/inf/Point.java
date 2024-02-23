package hu.elte.inf;

import java.util.Objects;

/**
 * Point can only be from the non negative quadrant
 * @author zebalu
 *
 */
public class Point {

	private final int x;
	private final int y;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @throws IllegalArgumentException if any parameter is less then 0
	 */
	public Point(int x, int y) {
		if(x<0 || y<0) {
			throw new IllegalArgumentException("no coordinate can be smaler than zero");
		}
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public double distanceFrom(Point point) {
		if(x==point.x) {
			return Math.abs(y-point.y);
		} else if(y == point.y) {
			return Math.abs(x-point.x);
		}
		int xDif = x-point.x;
		int yDif = y-point.y;
		return Math.sqrt(xDif*xDif+yDif*yDif);
	}
	
	
	
}
