
package training.nine.one;

import java.util.Comparator;

public class ArraysUtils {

    <T> void sort(T[] array, int right, Comparator<T> comparator) {
        sort(array, 0, right, comparator);
    }

    private <T> void sort(T[] array, int left, int right, Comparator<T> comparator) {
        if (left >= right) return;
        int middle = partition(array, left, right, comparator);
        sort(array, left, middle - 1, comparator);
        sort(array, middle + 1, right, comparator);
    }

    private <T> int partition(T[] array, int left, int right, Comparator<T> comparator) {
        T pivot = array[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                T tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                i++;
            }
        }
        T tmp = array[right];
        array[right] = array[i];
        array[i] = tmp;
        return i;
    }

    <T> int deleteUniqueElements(T[] array, int right, Comparator<T> comparator) {
        int fillIndex = 0;
        int i = 0;
        while (i < right) {
            int count = 1;
            while (i < right && comparator.compare(array[i], array[i + 1]) == 0) {
                array[fillIndex++] = array[i];
                i++;
                count++;
            }
            if (count > 1) {
                array[fillIndex++] = array[i];
            }
            i++;
        }
        return fillIndex - 1;
    }

}
