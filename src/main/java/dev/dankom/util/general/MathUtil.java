package dev.dankom.util.general;

import dev.dankom.math.vector.d.Vector2D;
import dev.dankom.math.vector.f.Vector2F;

import java.text.DecimalFormat;
import java.util.Random;

public class MathUtil {
    public static double randDouble(int min, int max) {
        Random r = new Random();
        return (min - 1) + ((max - 1) - (min - 1)) * r.nextDouble();
    }

    public static int randInt(int min, int max) {
        Random r = new Random();
        return (min - 1) + ((max - 1) - (min - 1)) * r.nextInt();
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double convertDoubleToThousand(double num) {
        return num * 1000;
    }

    public static double convertDoubleToMillion(double num) {
        return num * 1000000;
    }

    public static double convertDoubleToBillion(double num) {
        return num * 1000000000;
    }

    public static String formatLargeDouble(double Double) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.###");
        String out = decimalFormat.format(Double);

        return out;
    }

    public static int getPercent(int num1, float perc) {
        return Math.round((perc * num1) / 100f);
    }

    public static Double percOf(Integer number1, Integer prec) {
        return number1 * prec / 100.0;
    }

    public static Double percOf(Double number1, Integer prec) {
        return number1 * prec / 100.0;
    }

    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }
        for (int i = 3, limit = (int) Math.sqrt(n); i <= limit; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the number of digits in a number.
     *
     * @param number number to find
     * @return number of digits of given number
     */
    public static int numberOfDigits(int number) {
        int digits = 0;
        do {
            digits++;
            number /= 10;
        } while (number != 0);
        return digits;
    }

    /**
     * Find the number of digits in a number fast version.
     *
     * @param number number to find
     * @return number of digits of given number
     */
    public static int numberOfDigitsFast(int number) {
        return number == 0 ? 1 : (int) Math.floor(Math.log10(Math.abs(number)) + 1);
    }

    /**
     * Find the number of digits in a number faster version.
     *
     * @param number number to find
     * @return number of digits of given number
     */
    public static int numberOfDigitsFaster(int number) {
        return number < 0 ? (-number + "").length() : (number + "").length();
    }

    /**
     * Find the number of digits in a number using recursion.
     *
     * @param number number to find
     * @return number of digits of given number
     */
    public static int numberOfDigitsRecursion(int number) {
        return number / 10 == 0 ? 1 : 1 + numberOfDigitsRecursion(number / 10);
    }

    /**
     * get greatest common divisor
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return gcd
     */
    public static int gcd(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new ArithmeticException();
        }

        if (num1 == 0 || num2 == 0) {
            return Math.abs(num1 - num2);
        }

        while (num1 % num2 != 0) {
            int remainder = num1 % num2;
            num1 = num2;
            num2 = remainder;
        }
        return num2;
    }

    /**
     * get greatest common divisor in array
     *
     * @param number contains number
     * @return gcd
     */
    public static int gcd(int[] number) {
        int result = number[0];
        for (int i = 1; i < number.length; i++)
            // call gcd function (input two value)
            result = gcd(result, number[i]);

        return result;
    }

    /**
     * Check if a number is perfect square number
     *
     * @param number the number to be checked
     * @return <tt>true</tt> if {@code number} is perfect square, otherwise <tt>false</tt>
     */
    public static boolean isPerfectSquare(int number) {
        int sqrt = (int) Math.sqrt(number);
        return sqrt * sqrt == number;
    }

    /**
     * Check if a number is fibonacci number This is true if and only if at least one of 5x^2+4 or
     * 5x^2-4 is a perfect square
     *
     * @param number the number
     * @return <tt>true</tt> if {@code number} is fibonacci number, otherwise <tt>false</tt>
     * @link https://en.wikipedia.org/wiki/Fibonacci_number#Identification
     */
    public static boolean isFibonacciNumber(int number) {
        return isPerfectSquare(5 * number * number + 4) || isPerfectSquare(5 * number * number - 4);
    }

    /**
     * Returns the largest (closest to positive infinity)
     *
     * @param number the number
     * @return the largest (closest to positive infinity) of given {@code number}
     */
    public static double floor(double number) {
        if (number - (int) number == 0) {
            return number;
        } else if (number - (int) number > 0) {
            return (int) number;
        } else {
            return (int) number - 1;
        }
    }

    /**
     * Returns the smallest (closest to negative infinity)
     *
     * @param number the number
     * @return the smallest (closest to negative infinity) of given {@code number}
     */
    public static double ceil(double number) {
        if (number - (int) number == 0) {
            return number;
        } else if (number - (int) number > 0) {
            return (int) (number + 1);
        } else {
            return (int) number;
        }
    }

    /**
     * Calculate average of a list of numbers
     *
     * @param numbers array to store numbers
     * @return mean of given numbers
     */
    public static double average(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    /**
     * find average value of int array
     *
     * @param array the array contains element and the sum does not excess long value limit
     * @return average value
     */
    public static int average(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; ++i) {
            sum += array[i];
        }
        return (int) (sum / array.length);
    }

    /**
     * If value is less than zero, make value positive.
     *
     * @param value a number
     * @return the absolute value of a number
     */
    public static int absVal(int value) {
        return value < 0 ? -value : value;
    }

    /**
     * get the value, returns the absolute min value min
     *
     * @param numbers contains elements
     * @return the absolute min value
     */
    public static int absMin(int[] numbers) {
        int absMinValue = numbers[0];
        for (int i = 1, length = numbers.length; i < length; ++i) {
            if (Math.abs(numbers[i]) < Math.abs(absMinValue)) {
                absMinValue = numbers[i];
            }
        }
        return absMinValue;
    }

    /**
     * Calculate the surface area of a cube.
     *
     * @param sideLength side length of cube
     * @return surface area of given cube
     */
    public static double surfaceAreaCube(double sideLength) {
        return 6 * sideLength * sideLength;
    }

    /**
     * Calculate the surface area of a sphere.
     *
     * @param radius radius of sphere
     * @return surface area of given sphere
     */
    public static double surfaceAreaSphere(double radius) {
        return 4 * Math.PI * radius * radius;
    }

    /**
     * Calculate the area of a rectangle
     *
     * @param length length of rectangle
     * @param width width of rectangle
     * @return area of given rectangle
     */
    public static double surfaceAreaRectangle(double length, double width) {
        return length * width;
    }

    /**
     * Calculate the area of a square
     *
     * @param sideLength side length of square
     * @return area of given square
     */
    public static double surfaceAreaSquare(double sideLength) {
        return sideLength * sideLength;
    }

    /**
     * Calculate the area of a triangle
     *
     * @param base base of triangle
     * @param height height of triangle
     * @return area of given triangle
     */
    public static double surfaceAreaTriangle(double base, double height) {
        return base * height / 2;
    }

    /**
     * Calculate the area of a parallelogram
     *
     * @param base base of parallelogram
     * @param height height of parallelogram
     * @return area of given parallelogram
     */
    public static double surfaceAreaParallelogram(double base, double height) {
        return base * height;
    }

    /**
     * Calculate the area of a trapezium
     *
     * @param base1 upper base of trapezium
     * @param base2 bottom base of trapezium
     * @param height height of trapezium
     * @return area of given trapezium
     */
    public static double surfaceAreaTrapezium(double base1, double base2, double height) {
        return (base1 + base2) * height / 2;
    }

    /**
     * Calculate the area of a circle
     *
     * @param radius radius of circle
     * @return area of given circle
     */
    public static double surfaceAreaCircle(double radius) {
        return Math.PI * radius * radius;
    }

    /**
     * find max of array
     *
     * @param array the array contains element
     * @return max value of given array
     */
    public static int findMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Returns the greater of two {@code int} values. That is, the result is the argument closer to
     * the value of {@link Integer#MAX_VALUE}. If the arguments have the same value, the result is
     * that same value.
     *
     * @param a an argument.
     * @param b another argument.
     * @return the larger of {@code a} and {@code b}.
     */
    public static int max(int a, int b) {
        return a >= b ? a : b;
    }

    /**
     * Finds the aliquot sum of an integer number
     *
     * @param number a positive integer
     * @return aliquot sum of given {@code number}
     */
    public static int aliquotSum(int number) {
        int sum = 0;
        for (int i = 1, limit = number / 2; i <= limit; ++i) {
            if (number % i == 0) {
                sum += i;
            }
        }
        return sum;
    }

    /**
     * Check if a number is perfect cube or not
     *
     * @param number number to check
     * @return {@code true} if {@code number} is perfect cube, otherwise {@code false}
     */
    public static boolean isPerfectCube(int number) {
        int a = (int) Math.pow(number, 1.0 / 3);
        return a * a * a == number;
    }

    /**
     * Calculate the sum of digits of a number
     *
     * @param number the number contains digits
     * @return sum of digits of given {@code number}
     */
    public static int sumOfDigits(int number) {
        number = number < 0 ? -number : number; /* calculate abs value */
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    /**
     * Calculate the sum of digits of a number using recursion
     *
     * @param number the number contains digits
     * @return sum of digits of given {@code number}
     */
    public static int sumOfDigitsRecursion(int number) {
        number = number < 0 ? -number : number; /* calculate abs value */
        return number < 10 ? number : number % 10 + sumOfDigitsRecursion(number / 10);
    }

    /**
     * Calculate the sum of digits of a number using char array
     *
     * @param number the number contains digits
     * @return sum of digits of given {@code number}
     */
    public static int sumOfDigitsFast(int number) {
        number = number < 0 ? -number : number; /* calculate abs value */
        char[] digits = (number + "").toCharArray();
        int sum = 0;
        for (int i = 0; i < digits.length; ++i) {
            sum += digits[i] - '0';
        }
        return sum;
    }

    public static Vector2F getCenterOfTriangle(Vector2F v1, Vector2F v2, Vector2F v3) {
        return new Vector2F((int) Math.round((v1.getX() + v2.getX() + v3.getX()) / 3.0), (int) Math.round((v1.getY() + v2.getY() + v3.getY()) / 3.0));
    }

    public static Vector2D getCenterOfTriangle(Vector2D v1, Vector2D v2, Vector2D v3) {
        return new Vector2D((int) Math.round((v1.getX() + v2.getX() + v3.getX()) / 3.0), (int) Math.round((v1.getY() + v2.getY() + v3.getY()) / 3.0));
    }
}
