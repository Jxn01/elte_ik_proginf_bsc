package game;
import game.utils.*;

public class Main {
    
    public static void main (String [] args){
        Vehicle vehicle1 = new Vehicle(1, "Test1", 255, 255);
        Vehicle vehicle2 = new Vehicle(2, "Test2", 0, 255);
        Vehicle vehicle3 = new Vehicle(3, "Test3", 255, 0);

        Player player1 = new Player("Janos", "192.168.0.1", 100, null);
        Player player2 = new Player("Bela", "192.168.0.2", 50, vehicle1);

        System.out.println(player1.toString());
        System.out.println();
        System.out.println(player2.toString());
    }

}
