package ora1;

class A{

}

class Skip {
    public static void main(String args[]){

    }
}

class Name {
    public static void main(String args[]){
        if(args.length >= 1){
            System.out.println("Hello "+args[0]);
        }
        System.out.print("Add meg a nevedet: ");
        String nev = System.console().readLine();
        System.out.println("Hello "+nev);
    }
}


class Print {
    public static void main(String[] args) {
        int i = 4;
        for(i = 1; i <= 4; i++ ) {
            System.out.println((double)i/2);
        }
        System.out.println("Kiirtam " + (i-1) + " szamot");
    }
}

class InputMyWay{
    public static void main (String[] args){
        System.out.println("Kerem adja meg az elso szamot: ");
        int elso = Integer.parseInt(System.console().readLine());
        System.out.println("Kerem adja meg a masodik szamot: ");
        int masodik = Integer.parseInt(System.console().readLine());
        if(Math.abs(Math.abs(elso)-Math.abs(masodik))<=1){
            System.out.println("Koztuk levo egesz szamok szamanak fele (nyilt intervallum): 0");
        }else{
            System.out.println("Koztuk levo egesz szamok szamanak fele (nyilt intervallum): "+ (((Math.abs(elso-masodik))-1)/(double)2));
        }
    }
}

class Input{
    public static void main(String[] args){
        System.out.println("Kerem adja meg az elso szamot: ");
        int elso = Integer.parseInt(System.console().readLine());
        System.out.println("Kerem adja meg a masodik szamot: ");
        int masodik = Integer.parseInt(System.console().readLine());

        for(int i = elso; i <= masodik; i++){
            System.out.println((i/(double)2));
        }

        System.out.println("Kerem adja meg az n-t!");
        int n = Integer.parseInt(System.console().readLine());
        System.out.println("Faktorialis: "+fact(n));

    }

    static int fact(int n){
        int result = 1;
        for(int i = 1; i <= n;  i++){
            result*=i;
        }
        return result;
    }
}

class Hetes{
    public static void main (String[] args){
        if(args.length == 2){
            int elso = Integer.parseInt(args[0]);
            int masodik = Integer.parseInt(args[1]);
            System.out.println("Osszeg: "+(elso+masodik));
            System.out.println("Kulonbseg: "+(elso-masodik));
            System.out.println("Szorzat: "+(elso*masodik));
            if(masodik!=0){
                System.out.println("Hanyados: "+(elso/masodik));
                System.out.println("Osztasi maradek: "+(elso%masodik));
            }
        }
    }
}



