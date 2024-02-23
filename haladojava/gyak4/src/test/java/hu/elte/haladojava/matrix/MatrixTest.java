package hu.elte.haladojava.matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

  @Test
  public void testConstructor() { // also testing getters
    Matrix matrix = new Matrix(1, 2);

    Assert.assertEquals(1, matrix.getHeight());
    Assert.assertEquals(2, matrix.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_negativeHeight() {
    new Matrix(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_negativeWidth() {
    new Matrix(1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_bothHeightAndWidthNegative() {
    new Matrix(-1, -2);
  }

  @Test
  public void testAdd() {
    // GIVEN
    Matrix matrixA = new Matrix(2, 3);
    Matrix matrixB = new Matrix(2, 3);

    matrixA.setElement(-1, 0, 1);
    matrixA.setElement(2, 1, 2);
    matrixB.setElement(3, 0, 1);
    matrixB.setElement(4, 1, 1);

    // WHEN
    matrixA.add(matrixB);

    // THEN
    Assert.assertEquals(0, matrixA.getElement(0, 0)); // would be better to override equals/hashCode in Matrix
    Assert.assertEquals(2, matrixA.getElement(0, 1)); // -1 + 3
    Assert.assertEquals(0, matrixA.getElement(0, 2));
    Assert.assertEquals(0, matrixA.getElement(1, 0));
    Assert.assertEquals(4, matrixA.getElement(1, 1));
    Assert.assertEquals(2, matrixA.getElement(1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdd_incompatibleMatrices() {
    // GIVEN
    Matrix matrixA = new Matrix(1, 2);
    Matrix matrixB = new Matrix(2, 2);

    // WHEN
    matrixA.add(matrixB);
  }

  @Test
  public void testMultiply() {
    // GIVEN
    Matrix matrixA = new Matrix(2, 3);
    Matrix matrixB = new Matrix(3, 4);

    matrixA.setElement(2, 0, 0);
    matrixA.setElement(6, 0, 1);
    matrixA.setElement(3, 0, 2);
    matrixA.setElement(1, 1, 0);
    matrixA.setElement(0, 1, 1);
    matrixA.setElement(4, 1, 2);

    matrixB.setElement(5, 0, 0);
    matrixB.setElement(6, 0, 1);
    matrixB.setElement(2, 0, 2);
    matrixB.setElement(-3, 0, 3);
    matrixB.setElement(3, 1, 0);
    matrixB.setElement(7, 1, 1);
    matrixB.setElement(-2, 1, 2);
    matrixB.setElement(4, 1, 3);
    matrixB.setElement(8, 2, 0);
    matrixB.setElement(1, 2, 1);
    matrixB.setElement(0, 2, 2);
    matrixB.setElement(-4, 2, 3);

    // WHEN
    Matrix result = Matrix.multiply(matrixA, matrixB);

    // THEN
    Assert.assertEquals(52, result.getElement(0, 0)); // would be better to override equals/hashCode in Matrix
    Assert.assertEquals(57, result.getElement(0, 1));
    Assert.assertEquals(-8, result.getElement(0, 2));
    Assert.assertEquals(6, result.getElement(0, 3));
    Assert.assertEquals(37, result.getElement(1, 0));
    Assert.assertEquals(10, result.getElement(1, 1));
    Assert.assertEquals(2, result.getElement(1, 2));
    Assert.assertEquals(-19, result.getElement(1, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiply_incompatibleMatrices() {
    // GIVEN
    Matrix matrixA = new Matrix(1, 2);
    Matrix matrixB = new Matrix(3, 2);

    // WHEN
    Matrix.multiply(matrixA, matrixB);
  }

  @Test
  public void testClone() throws CloneNotSupportedException {
    // GIVEN
    Matrix originalMatrix = new Matrix(2, 1);
    originalMatrix.setElement(1, 0, 0);
    originalMatrix.setElement(2, 1, 0);

    // WHEN
    Matrix clonedMatrix = originalMatrix.clone();
    originalMatrix.setElement(-1, 1, 0); // cloned one should not be affected!

    // THEN
    Assert.assertEquals(1, clonedMatrix.getElement(0, 0));
    Assert.assertEquals(2, clonedMatrix.getElement(1, 0));
  }

  @Test
  public void testToString_1x1() {
    // GIVEN
    Matrix matrix = new Matrix(1, 1);
    matrix.setElement(5, 0, 0);

    // WHEN
    String matrixAsString = matrix.toString();

    // THEN
    Assert.assertEquals("[ 5 ]", matrixAsString);
  }

  @Test
  public void testToString_2x3() {
    // GIVEN
    Matrix matrix = new Matrix(2, 3);
    matrix.setElement(0, 0, 0);
    matrix.setElement(1, 0, 1);
    matrix.setElement(2, 0, 2);
    matrix.setElement(3, 1, 0);
    matrix.setElement(4, 1, 1);
    matrix.setElement(5, 1, 2);

    // WHEN
    String matrixAsString = matrix.toString();

    // THEN
    Assert.assertEquals("[ 0, 1, 2 | 3, 4, 5 ]", matrixAsString);
  }
}
