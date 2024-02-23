package hu.elte.haladojava.matrix;

public class Matrix implements Cloneable {

  private int[] elements;
  private final int height;
  private final int width;

  public Matrix(int height, int width) {
    if (height < 1) {
      throw new IllegalArgumentException("invalid height: " + height);
    }
    if (width < 1) {
      throw new IllegalArgumentException("invalid width: " + width);
    }
    this.elements = new int[height * width];
    this.height = height;
    this.width = width;
  }

  public void add(Matrix other) {
    if (height != other.height) {
      throw new IllegalArgumentException(
          "matrices are incompatible, height values are different (" + height + " and " + other.height + ")");
    }
    if (width != other.width) {
      throw new IllegalArgumentException(
          "matrices are incompatible, width values are different (" + width + " and " + other.width + ")");
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        elements[index(i, j)] += other.elements[index(i, j)];
      }
    }
  }

  public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
    if (matrixA.width != matrixB.height) {
      throw new IllegalArgumentException(
          String.format("matrices (%dx%d and %dx%d) are incompatible with regards to multiplication",
              matrixA.height, matrixA.width, matrixB.height, matrixB.width));
    }
    Matrix result = new Matrix(matrixA.height, matrixB.width);
    for (int i = 0; i < result.height; i++) {
      for (int j = 0; j < result.width; j++) {
        int s = 0;
        for (int k = 0; k < matrixA.width; k++) {
          s += matrixA.getElement(i, k) * matrixB.getElement(k, j);
        }
        result.setElement(s, i, j);
      }
    }
    return result;
  }

  public int getElement(int row, int column) {
    return elements[index(row, column)];
  }

  public void setElement(int element, int row, int column) {
    elements[index(row, column)] = element;
  }

  private int index(int row, int column) {
    return width * row + column;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public Matrix clone() throws CloneNotSupportedException {
    Matrix clone = (Matrix) super.clone();
    clone.elements = this.elements.clone();
    return clone;
  }

  /**
   * Renders as a matrix as [ r1a, r1b, ..., r1X | r2a, r2b, ..., r2X | rNa, rNb, ..., rNX ] on one line.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(elements[index(i, j)]);
        if (j < width - 1) {
          sb.append(", ");
        }
      }
      if (i < height - 1) {
        sb.append(" | ");
      }
    }
    return sb.append(" ]").toString();
  }

}