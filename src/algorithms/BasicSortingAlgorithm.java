package algorithms;

import java.util.ArrayList;
import java.util.List;

public class BasicSortingAlgorithm {
    public static void main(String[] args) {
        System.out.println(bubbleSort(new ArrayList<>(List.of(1, 32, 4, 5))));
    }

    public static List<Integer> insertionSort(List<Integer> unsortedList) {
        for (int i = 0; i < unsortedList.size(); i++) {
            int current = i;
            // Go through the following
            while (current > 0 && unsortedList.get(current) < unsortedList.get(current - 1)) {
                int temp = unsortedList.get(current);
                unsortedList.set(current, unsortedList.get(current - 1));
                unsortedList.set(current - 1, temp);
                current--;
            }
        }
        return unsortedList;
    }

    public static List<Integer> selectionSort(List<Integer> unsortedList) {
        int n = unsortedList.size();
        // Loop through the array
        for (int i = 0; i < n; i++) {
            // Assume the minimum is at the start or where i is currently at
            int minimumIndex = i;
            for (int j = i; j < n; j++) {
                if (unsortedList.get(j) < unsortedList.get(minimumIndex)) {
                    minimumIndex = j;
                }
            }
            int temp = unsortedList.get(i);
            unsortedList.set(i, unsortedList.get(minimumIndex));
            unsortedList.set(minimumIndex, temp);
        }
        return unsortedList;
    }

    public static List<Integer> bubbleSort(List<Integer> unsortedList) {
        int n = unsortedList.size();
        // Iterate through the array from the back - Assuming the biggest will always be at the end
        for (int i = n - 1; i >= 0; i--) {
            // Keeps track if any swapping was done
            boolean swapped = false;
            for (int j = 0; j < i; j++) {
                if (unsortedList.get(j) > unsortedList.get(j + 1)) {
                    int temp = unsortedList.get(j);
                    unsortedList.set(j, unsortedList.get(j + 1));
                    unsortedList.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) return unsortedList;
        }
        return unsortedList;
    }
}
