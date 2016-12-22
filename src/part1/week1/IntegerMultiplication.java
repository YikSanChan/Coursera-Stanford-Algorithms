package part1.week1;

import java.math.BigInteger;

/**
 * Created by CYS on 2016/12/22.
 */
public class IntegerMultiplication {

    public static void main(String[] args)
    {
        String x = "3141592653589793238462643383279502884197169399375105820974944592";
        String y = "2718281828459045235360287471352662497757247093699959574966967627";

        BigInteger res1 =bigIntegerMultiply(new BigInteger(x), new BigInteger(y));
        String     res2 = thirdGrade(x, y);
        BigInteger res3 = karatsubaBinary(new BigInteger(x), new BigInteger(y));

        assert res1.equals(new BigInteger(res2));
        assert res3.equals(new BigInteger(res2));

        System.out.println(res3);
    }

    /**
     * Directly apply BigInteger's multiply method.
     */
    public static BigInteger bigIntegerMultiply(BigInteger x, BigInteger y)
    {
        return x.multiply(y);
    }

    /**
     * Third grade O(n^2) integer multiplication
     * n = digits of x or y (x and y shares same length)
     */
    public static String thirdGrade(String x, String y)
    {
        assert x.length() == y.length();

        int n = x.length();
        int[] res = new int[2 * n];

        // pass when meet 0
        for (int i = n - 1; x.charAt(i) != '0' && i >= 0; i--)
        {
            for (int j = n - 1; y.charAt(j) != '0' && j >= 0; j--)
            {
                // single digit a multiplies single digit b
                // result has at most 2 digits
                // place its 1st digit at res[p1], second at res[p2]
                int mul = (x.charAt(i) - '0') * (y.charAt(j) - '0');
                int p1 = i + j, p2 = p1 + 1;
                int sum = mul + res[p2];
                res[p1] += sum / 10;
                res[p2] = sum % 10;
            }
        }

        // output as a string
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (res[i] == 0) i++;
        for (; i < res.length; i++) sb.append(res[i]);
        return sb.toString();
    }

    /**
     * Karatsuba implementation with BigInteger,
     * Only difference: use binary instead of decimal
     * http://introcs.cs.princeton.edu/java/99crypto/Karatsuba.java.html
     */
    public static BigInteger karatsubaBinary(BigInteger x, BigInteger y)
    {
        int n = Math.max(x.bitLength(), y.bitLength());
        if (n <= 2000) return x.multiply(y);
        n = n / 2 + n % 2;

        // x = a * 2^n + b, y = c * 2^n + d
        BigInteger a = x.shiftRight(n);
        BigInteger b = x.subtract(a.shiftLeft(n));
        BigInteger c = y.shiftRight(n);
        BigInteger d = y.subtract(c.shiftLeft(n));

        BigInteger ac = karatsubaBinary(a,c);
        BigInteger bd = karatsubaBinary(b,d);
        BigInteger abcd = karatsubaBinary(a.add(b), b.add(c));

        // (ac + (ad + bc) * 2^n) * 2^n + bd
        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(n)).shiftLeft(n).add(bd);
    }

    public static String karatsubaDecimal(String x, String y)
    {
        return null;
    }
}
