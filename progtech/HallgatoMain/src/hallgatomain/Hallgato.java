package hallgatomain;

/**
 *
 * @author jxn
 */
public class Hallgato {
    private String name;
    private String nationality;
    private double avg;

    public Hallgato(String name, String nationality, double avg) {
        this.name = name;
        this.nationality = nationality;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
    
    
}
