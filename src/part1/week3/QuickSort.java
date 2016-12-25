package part1.week3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by CYS on 2016/12/25.
 */
public class QuickSort {

    private static int comparisons;

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
    private static boolean less(Comparable v, Comparable w)
    {
        return v.compareTo(w) < 0;
    }
    private static void exch(Object[] a, int from, int to)
    {
        Object tmp = a[from];
        a[from] = a[to];
        a[to] = tmp;
    }

    // 1st element as the pivot
    private static int partition(Comparable[] a, int lo, int hi)
    {
        // exchange the pivot element with the first element.

        //exch(a, lo, hi);
        //exch(a, lo, medianOf3(a, lo, hi));

        comparisons += hi - lo;
        int i = lo + 1;
        for (int j = i; j <= hi; j++)
            if (less(a[j], a[lo]))
                exch(a, j, i++);
        exch(a, lo, i - 1);
        return i - 1;
    }

    private static int medianOf3(Comparable[] a, int i, int j)
    {
        int k = i + (j - i) / 2;
        if (less(a[j], a[i]) && less(a[k], a[i])) return less(a[j], a[k]) ? k : j;
        else if (less(a[k], a[j]))                return less(a[i], a[k]) ? k : i;
        else                                      return less(a[i], a[j]) ? j : i;
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static void sort(Comparable[] a)
    {
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args)
    {
        Integer[] a = new Integer[10000];
        try (Scanner input = new Scanner(new File("resource/QuickSort.txt"))) {
            int i = 0;
            while (input.hasNext())
            {
                a[i++] = input.nextInt();
            }
            sort(a);
            System.out.println(comparisons);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
