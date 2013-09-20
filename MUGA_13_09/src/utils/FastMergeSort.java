///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author ZULU
 */
public class FastMergeSort {

    @SuppressWarnings({"unchecked"})
    private static void mergeSort(Object src[], Object dest[], int low, int high, int off,
            Comparator comparator) {
        int length = high - low;
        // use insertion sort on smallest arrays
        if (length < 13) {
            for (int i = low; i < high; i++) {
                for (int j = i; j > low && comparator.compare(dest[j - 1], dest[j]) > 0; j--) {
                    Object temp = dest[j];
                    dest[j] = dest[j - 1];
                    dest[j - 1] = temp;
                }
            }
            return;
        }
        // recursively sort halves of dest into src
        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = (low + high) >> 1;
        mergeSort(dest, src, low, mid, -off, comparator);
        mergeSort(dest, src, mid, high, -off, comparator);
        // is list already sorted?
        if (comparator.compare(src[mid - 1], src[mid]) <= 0) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        // merge sorted halves from src into dest
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && comparator.compare(src[p], src[q]) <= 0) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    public void sort(Object[] a, Comparator c) {
        Object aux[] = a.clone();
        mergeSort(aux, a, 0, a.length, 0, c);
    }

    public void sort(Comparable[] a) {
        Object aux[] = a.clone();
        mergeSort(aux, a, 0, a.length, 0, AscendingComparator.INSTANCE);
    }

    // ---------------------------------------------------------------- static
    public static void doSort(Object[] a, Comparator c) {
        Object aux[] = a.clone();
        mergeSort(aux, a, 0, a.length, 0, c);
    }

    public static void doSort(Object[] a) {
        Object aux[] = a.clone();
        mergeSort(aux, a, 0, a.length, 0, AscendingComparator.INSTANCE);
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Object[] a = list.toArray();
        doSort(a);
        ListIterator<T> i = list.listIterator();
        for (int j = 0; j < a.length; j++) {
            i.next();
            i.set((T) a[j]);
        }
    }
    
    
     public static void descendingSort(Object[] a) {
        Object aux[] = a.clone();
        mergeSort(aux, a, 0, a.length, 0, DescendingComparator.INSTANCE);
    }

    public static <T extends Comparable<? super T>> void descendingSort(List<T> list) {
        Object[] a = list.toArray();
        descendingSort(a);
        ListIterator<T> i = list.listIterator();
        for (int j = 0; j < a.length; j++) {
            i.next();
            i.set((T) a[j]);
        }
    }
}

// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

/**
 * Comparator that adapts
 * <code>Comparables</code> to the
 * <code>Comparator</code> interface.
 */
class AscendingComparator<T extends Comparable<T>> implements Comparator<T> {

    /**
     * Cached instance.
     */
    public static final AscendingComparator INSTANCE = new AscendingComparator();

    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
}

class DescendingComparator<T extends Comparable<T>> implements Comparator<T> {

    /**
     * Cached instance.
     */
    public static final DescendingComparator INSTANCE = new DescendingComparator();

    @Override
    public int compare(T o1, T o2) {
        return -o1.compareTo(o2);
    }
}