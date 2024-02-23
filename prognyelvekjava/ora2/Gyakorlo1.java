class Complex{
    double re;
    double im;

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
        double tmpRe = re;
        re = (re * c.re) + ((im * c.im)*-1);
        im = (im * c.re) + (tmpRe * c.im);
    }

    public void conjugate(){
        im*=-1;
    }

    public void reciprocate(){
        if(re==0 && im==0){
            throw new ArithmeticException("Zero doesn't have a reciprocate!");
        }else{
            double tempRe = re;
            re = re / ((re*re) + (im*im));
            im = (im / ((tempRe*tempRe) + (im*im)))*-1; 
        }
    }

    public void div(Complex c){
        Complex numerator = new Complex(this.re, this.im);
        Complex denominator = new Complex(c.re, c.im);
        Complex conjugated = new Complex(c.re, c.im);

        conjugated.conjugate(); // c konjugaltja

        numerator.mul(conjugated); // szamlalo: this
        denominator.mul(conjugated); // nevezo: c
        this.im = numerator.im / denominator.re;
        this.re = numerator.re / denominator.re;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public String toString() {
        String imaginary = "";
        if(this.im < 0){
            imaginary = " "+this.im;
        }else{
            imaginary = " +"+this.im;
        }
        return this.re+imaginary+"i";
    }
}

public class Gyakorlo1 {
    public static void main (String [] args){
        Complex komplex1 = new Complex(1,-2);
        Complex komplex2 = new Complex(1,3);
        Complex komplex3 = new Complex(0,0);

        komplex1.div(komplex2);
        System.out.println(komplex1.toString());
        komplex2.reciprocate();
        System.out.println(komplex2.toString());
        //komplex3.reciprocate(); //throws an exception
    }
}
