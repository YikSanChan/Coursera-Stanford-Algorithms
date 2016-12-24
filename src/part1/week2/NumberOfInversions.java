package part1.week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by CYS on 2016/12/24.
 */
public class NumberOfInversions {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // Aside from merge, also count number of inversions
    private static long merge(Comparable[] a, Comparable[] aux, int lo, int hi, int mid)
    {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        // copy a to aux
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);

        // count inversions
        long inversions = 0;

        // copy from aux back to a
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
        {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else                          {a[k] = aux[j++];inversions += (mid - i + 1);}
        }

        assert isSorted(a, lo, hi);
        return inversions;
    }
    private static long sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (hi <= lo) return 0;
        int mid = lo + (hi - lo) / 2;
        return sort(a, aux, lo, mid) + sort(a, aux, mid + 1, hi) + merge(a, aux, lo, hi, mid);
    }

    public static long sort(Comparable[] a)
    {
        Comparable[] aux = new Comparable[a.length];
        return sort(a, aux, 0, a.length - 1);
    }

    public static void main(String[] args)
    {
        Integer[] a = null;
        /*
        a = new Integer[]{1,2,3,4,5}; System.out.println(sort(a));
        a = new Integer[]{5,4,3,2,1}; System.out.println(sort(a));
        a = new Integer[]{1,2,3,5,4}; System.out.println(sort(a));
        */
        try (Scanner input = new Scanner(new File("resource/IntegerArray.txt"))) {
            a = new Integer[100000];
            int i = 0;
            while (input.hasNext())
            {
                a[i++] = input.nextInt();
            }
            System.out.println(sort(a)); // 2407905288
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
