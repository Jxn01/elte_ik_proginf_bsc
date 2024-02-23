package ora6;

class Calculator {
    public static void main(String[] args){
        try {
            if (args.length != 3){
                throw new IllegalArgumentException("Too few arguments provided.");
            } else {
                double a = Double.parseDouble(args[0]);
                char m = args[1].charAt(0);
                double b = Double.parseDouble(args[2]);

                System.out.print("" + a + m + b + " = ");
                switch (m) {
                    case '+':
                        System.out.println(a + b);
                        break;
                    case '-':
                        System.out.println(a - b);
                        break;
                    case '*':
                        System.out.println(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero.");
                        }
                        System.out.println(a / b);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operation.");
                }
            }
        }
        catch (IllegalArgumentException | ArithmeticException e) {
            if(e instanceof NumberFormatException){
                System.out.println("Invalid number format.");
            }
            if(e instanceof IllegalArgumentException){
                System.out.println("Invalid program arguments provided.");
            }
            if(e instanceof ArithmeticException){
                System.out.println("Arithmetic error occured.");
            }
            System.out.println(e.getMessage());
        }
    }
}
