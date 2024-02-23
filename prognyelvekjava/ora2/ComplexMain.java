class Complex{
    int re;
    int im;

    public double abs(){
        return Math.sqrt(re*re + im*im);
    }

    public void add (Complex c){
        re += c.re;
        im += c.im;
    }

    public void sub (Complex c){
        re -= c.re;
        im -= c.im;
    }

    public void mul (Complex c){
        int tmpRe = re;
        re = (re * c.re) + ((im * c.im)*-1);
        im = (im * c.re) + (tmpRe * c.im);
    }

    public Complex(int re, int im) {
        this.re = re;
        this.im = im;
    }

}

class ComplexMain {
    public static void main(String [] args){
        Complex alpha = new Complex(3,2);
        System.out.println(alpha.re + " " + alpha.im+"i");
        Complex beta = new Complex(1,2);
        System.out.println(beta.re + " " + beta.im+"i");

        System.out.println(alpha.abs());

        alpha.add(beta);
        System.out.println(alpha.re + " " + alpha.im+"i");

        alpha.mul(beta);
        System.out.println(alpha.re + " " + alpha.im+"i");

        alpha.sub(beta);
        System.out.println(alpha.re + " " + alpha.im+"i");
    }
}
