package hu.elte.haladojava.gyak1;

public enum LengthUnit {

  MILLI_METER(1, "mm"),
  CENTI_METER(10, "cm"),
  METER(1_000, "m"),
  KILO_METER(1_000_000, "km"),
  INCH(25.4, "in"),
  YARD(914.4, "yd"),
  MILE(1_609_344, "mi");

  private final double milliMeters;
  private final String shortName;

  private LengthUnit(double milliMeters, String shortName) {
    if (milliMeters <= 0) {
      throw new IllegalArgumentException("invalid quantity " + milliMeters);
    }
    this.milliMeters = milliMeters;
    this.shortName = shortName;
  }

  public double convertFrom(double quantity, LengthUnit originalUnit) {
    return originalUnit.milliMeters / milliMeters * quantity;
  }

  // we could override toString() but then we could not use valueOf() which expects the original enum names
  public String shortName() {
    return shortName;
  }

}
