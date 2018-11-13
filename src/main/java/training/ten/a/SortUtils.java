package training.ten.a;

public class SortUtils {
    static int count = 0;

    static void quickSort(int[] array, int left, int right) {
        int key = array[(left + right) / 2];
        int i = left;
        int j = right;
        do {
            while (array[i] < key) {
                i++;
                count++;
            }
            count++;
            while (array[j] > key) {
                j--;
                count++;
            }
            count++;
            if (i <= j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j) {
            quickSort(array, left, j);
        }
        if (i < right) {
            quickSort(array, i, right);
        }
    }

}
