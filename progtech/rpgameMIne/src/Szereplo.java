
/**
 *
 * @author jxn
 */
public abstract class Szereplo { 
    private int eletero;
    private String nev;
    private int tamadoero;

    public Szereplo(int eletero, String nev, int tamadoero) {
        this.eletero = eletero;
        this.nev = nev;
        this.tamadoero = tamadoero;
    }
    
    public boolean el(){
        return eletero > 0 ? true : false;
    }

    public int getEletero() {
        return eletero;
    }

    public void setEletero(int eletero) {
        this.eletero = eletero;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getTamadoero() {
        return tamadoero;
    }

    public void setTamadoero(int tamadoero) {
        this.tamadoero = tamadoero;
    }
    
    public abstract void sebzestKap (Szereplo sz);
}
