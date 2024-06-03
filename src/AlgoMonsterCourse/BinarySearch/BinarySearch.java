package AlgoMonsterCourse.BinarySearch;

import java.util.Collections;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        System.out.println(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
    }

    public static int binarySearch(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (arr.get(middle) == target) {
                return middle;
            } else if (arr.get(middle) < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    public static int findBoundary(List<Boolean> arr) {
        int left = 0, right = arr.size() - 1;
        int boundaryIndex = -1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (arr.get(middle)) {
                boundaryIndex = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return boundaryIndex;
    }

    public static int firstNotSmaller(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        int boundaryIndex = -1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (arr.get(middle) >= target) {
                boundaryIndex = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return boundaryIndex;
    }

    public static int findFirstOccurrence(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        int boundaryIndex = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (arr.get(middle) == target) {
                boundaryIndex = middle;
                right = middle - 1;
            } else if (arr.get(middle) < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return boundaryIndex;
    }

    public static int squareRoot(int n) {
        if (n == 0) return n;

        int left = 1, right = n;
        int result = -1;
        // View it as a last false problem
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (middle == n / middle) {
                return middle;
            } else if (middle > n / middle) {
                result = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return result + 1;
    }

    // e.g., Input: [3, 5, 7, 11, 13, 17, 19, 2]
    public static int findMinRotated(List<Integer> arr) {
        int left = 0;
        int right = arr.size() - 1;
        int boundary = -1;
        while (left <= right) {
            // Condition is if the middle is lesser than the end -> Smallest number is in the lower half
            int middle = left + (right - left) / 2;
            if (arr.get(middle) <= arr.get(arr.size() - 1)) {
                boundary = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return boundary;
    }

    public static int peakOfMountainArray(List<Integer> arr) {
        int left = 0;
        int right = arr.size() - 1;
        int boundary = -1;
        while (left <= right) {
            // Finding the first true
            // Return the first instance where x > y.
            int middle = left + (right - left) / 2;
            // Need to also cater for edge case where it is out of bound
            if (middle == arr.size() - 1 || arr.get(middle) > arr.get(middle + 1)) {
                boundary = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return boundary;
    }

    // Newspaper question
    public static int newspapersSplit(List<Integer> newspapersReadTimes, int numCoworkers) {
        // Shortest amount of time - Max time to read a single news paper
        int left = Collections.max(newspapersReadTimes);
        // Longest amount of time - Total time to read all news paper - One coworker
        int right = 0;
        int ans = -1;
        for (int newspaperReadTime : newspapersReadTimes) {
            right += newspaperReadTime;
        }
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (feasible(newspapersReadTimes, numCoworkers, middle)) {
                ans = middle;
                // Move down to see if it can be reduced further
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return ans;
    }

    public static boolean feasible(List<Integer> newsPaperReadTimes, int numCoWorkers, int timeLimit) {
        int time = 0, numWorkers = 0;
        for (int readTime : newsPaperReadTimes) {
            if (time + readTime > timeLimit) {
                time = 0;
                numWorkers++;
            }
            time += readTime;
        }
        if (time != 0) {
            numWorkers++;
        }
        return numWorkers <= numCoWorkers;
    }

    public static int[] searchRange(int[] nums, int target) {
        // Search for the first position
        int left = 0, right = nums.length - 1, firstPosition = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                firstPosition = middle;
                right = middle - 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        left = 0;
        right = nums.length - 1;
        int lastPosition = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                lastPosition = middle;
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        System.out.println(firstPosition + " " + lastPosition);
        return new int[]{firstPosition, lastPosition};
    }

}
