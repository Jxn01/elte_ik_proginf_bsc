package matrix;
import java.util.Random;

public class Matrix {
    public static void main (String [] args){
        elso();
        masodik();
        harmadik();
        negyedik();
    }

    static void elso(){
        Random rnd = new Random();
        for (int i = 0; i < 7; i ++) {
            for (int j = 0; j < 7; j ++) {
                if ( i == j ){
                // Escape_character [ < <code > >m << output text > >
                // ASCII
                System.out.print("\033[32m \t"+rnd.nextInt(100));
                }else{
                    // UTF-16 Encoding
                    System.out.print("\u001B[0m \t"+rnd.nextInt(100));
                }
            }
            System.out.println("\033[0m \t");
        }

        System.out.println("");
        System.out.println("");
    }

    static void masodik(){
        Random rnd = new Random();
        for (int i = 0; i < 7; i ++) {
            for (int j = 0; j < 7; j ++) {
                if ( i == j ){
                // Escape_character [ < <code > >m << output text > >
                // ASCII
                System.out.print("\033[32m \t"+rnd.nextInt(100));
                }
                if(i-1 < j){

                }else{
                    // UTF-16 Encoding
                    System.out.print("\u001B[0m \t"+rnd.nextInt(100));
                }
            }
            System.out.println("\033[0m \t");
        }
        System.out.println("");
        System.out.println("");
    }

    static void harmadik(){
        Random rnd = new Random();
        for (int i = 0; i < 7; i ++) {
            for (int j = 0; j < 7; j ++) {
                if ( i+j==6 ){
                // Escape_character [ < <code > >m << output text > >
                // ASCII
                System.out.print("\033[32m \t"+rnd.nextInt(100));
                }else{
                    // UTF-16 Encoding
                    System.out.print("\u001B[0m \t"+rnd.nextInt(100));
                }
            }
            System.out.println("\033[0m \t");
        }
        System.out.println("");
        System.out.println("");
    }

    static void negyedik(){
        Random rnd = new Random();
        for (int i = 0; i < 7; i ++) {
            for (int j = 0; j < 7; j ++) {
                if ( i == j ){
                // Escape_character [ < <code > >m << output text > >
                // ASCII
                System.out.print("\033[32m \t"+rnd.nextInt(100));
                }else if(i+j==6){
                    System.out.print("\033[32m \t"+rnd.nextInt(100));
                }else{
                    // UTF-16 Encoding
                    System.out.print("\u001B[0m \t"+rnd.nextInt(100));
                }
            }
            System.out.println("\033[0m \t");
        }
        System.out.println("");
        System.out.println("");
    }
}
