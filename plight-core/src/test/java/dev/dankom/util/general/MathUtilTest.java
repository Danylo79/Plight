package dev.dankom.util.general;

import junit.framework.TestCase;

public class MathUtilTest extends TestCase {
    public void testClamp() {
        assertEquals(10, Math.round(MathUtil.clamp(1.0F, 10.0F, 11.0F)));
        assertEquals(10, Math.round(MathUtil.clamp(1.0D, 10.0D, 11.0D)));
        assertEquals(10, MathUtil.clamp(1, 10, 11));

        assertEquals(0, Math.round(MathUtil.clamp(-1F, 0F, 1F)));
        assertEquals(0, Math.round(MathUtil.clamp(-1D, 0D, 1D)));
        assertEquals(0, MathUtil.clamp(-1, 0, 1));
    }

    public void testConvertDoubleToThousand() {
        assertEquals(1000.0, MathUtil.convertDoubleToThousand(1));
    }

    public void testConvertDoubleToMillion() {
        assertEquals(1000000.0, MathUtil.convertDoubleToMillion(1));
    }

    public void testConvertDoubleToBillion() {
        assertEquals(1000000000.0, MathUtil.convertDoubleToBillion(1));
    }

    public void testFormatLargeDouble() {
        assertEquals("100,000,000,000.123", MathUtil.formatLargeDouble(100000000000.123));
    }

    public void testGetPercent() {
        assertEquals(1, MathUtil.getPercent(100, 1));
        assertEquals(20, MathUtil.getPercent(100, 20));
        assertEquals(100, MathUtil.getPercent(1000, 10));
    }

    public void testPercOf() {
        assertEquals(1.0, MathUtil.percOf(100, 1));
        assertEquals(20.0, MathUtil.percOf(100, 20));
        assertEquals(100.0, MathUtil.percOf(1000, 10));
    }

    public void testTestPercOf() {
        assertEquals(1.0, MathUtil.percOf(100, 1));
        assertEquals(20.0, MathUtil.percOf(100, 20));
        assertEquals(100.0, MathUtil.percOf(1000, 10));
    }

    public void testIsPrime() {
        assertEquals(MathUtil.isPrime(7), true);
    }

    public void testNumberOfDigits() {
        assertEquals(1, MathUtil.numberOfDigits(1));
        assertEquals(3, MathUtil.numberOfDigits(230));
    }

    public void testNumberOfDigitsFast() {
        assertEquals(1, MathUtil.numberOfDigitsFast(1));
        assertEquals(3, MathUtil.numberOfDigitsFast(230));
    }

    public void testNumberOfDigitsFaster() {
        assertEquals(1, MathUtil.numberOfDigitsFaster(1));
        assertEquals(3, MathUtil.numberOfDigitsFaster(230));
    }

    public void testNumberOfDigitsRecursion() {
        assertEquals(1, MathUtil.numberOfDigitsRecursion(1));
        assertEquals(3, MathUtil.numberOfDigitsRecursion(230));
    }
}