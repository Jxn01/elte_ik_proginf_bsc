package hu.elte.haladojava.gyak1;

import java.util.Arrays;

public class MetricConverter {

  public static void main(String[] args) {
    if (args.length != 3) {
      exit("number of parameters were %d, expected usage (3 parameters): [quantity] [unit from] [unit to] ", args.length);
    }

    try {
      double quantity = Double.parseDouble(args[0]);
      LengthUnit unitFrom = LengthUnit.valueOf(args[1]);
      LengthUnit unitTo = LengthUnit.valueOf(args[2]);
      System.out.printf("%f %s = %f %s", quantity, unitFrom, unitTo.convertFrom(quantity, unitFrom), unitTo);

    } catch (NumberFormatException e) {
      exit("invalid number: %s", args[0]);
    } catch (IllegalArgumentException e) {
      exit("invalid unit in %s or %s, accepted values are %s", args[1], args[2], Arrays.toString(LengthUnit.values()));
    }
  }

  private static void exit(String format, Object... args) {
    System.err.printf(format + "%n", args);
    System.exit(1);
  }

}
