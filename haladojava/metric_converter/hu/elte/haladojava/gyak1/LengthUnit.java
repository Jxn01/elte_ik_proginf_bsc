package hu.elte.haladojava.gyak1;

public enum LengthUnit {

  MILLI_METER(1),
  CENTI_METER(10),
  METER(1_000),
  KILO_METER(1_000_000),
  INCH(25.4),
  YARD(914.4),
  MILE(1_609_344);

  private final double milliMeters;

  private LengthUnit(double milliMeters) {
    if (milliMeters <= 0) {
      throw new IllegalArgumentException("invalid quantity " + milliMeters);
    }
    this.milliMeters = milliMeters;
  }

  public double convertFrom(double quantity, LengthUnit originalUnit) {
    return originalUnit.milliMeters / milliMeters * quantity;
  }
}
