package hu.elte.haladojava.gyak1;

public class MetricConverter {

  public static void main(String[] args) {
    System.out.println("2cm = " + LengthUnit.INCH.convertFrom(2, LengthUnit.CENTI_METER) + " inch");
    System.out.println("1m = " + LengthUnit.MILLI_METER.convertFrom(1, LengthUnit.METER) + " mm");
  }

}
