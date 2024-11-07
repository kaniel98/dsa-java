package misc.twopointer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixSum {
    public static void main(String[] args) {

    }

    // * Sub array Sum - Prefix approach
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public static List<Integer> subarraySum(List<Integer> arr, int target) {
        // WRITE YOUR BRILLIANT CODE HERE
        // 1. Initialize a hashmap
        // 2. Keep track of the total sum from 0 to the current pointer
        // 3. For each sum (The current prefix sum at the current pointer), minus it from the target
        // 4. Check if there is a "prefix sum" of that remainder in the list
        // 5. If yes, return the remainder "prefix sum" + 1 & the current pointer
        Map<Integer, Integer> pointerPrefixSumMap = new HashMap<>();
        int currentSum = 0;
        pointerPrefixSumMap.put(0, 0);
        for (int right = 0; right < arr.size(); right++) {
            currentSum += arr.get(right);
            int complement = currentSum - target;
            if (pointerPrefixSumMap.containsKey(complement)) {
                return List.of(pointerPrefixSumMap.get(complement), right + 1);
            }
            // Right pointer is starting from 0, we need to plus 1 to add it to the prefix array
            pointerPrefixSumMap.put(currentSum, right + 1);
        }
        return null;
    }

    // * Total number of sub arrays that sum up to target
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public static int subarraySumTotal(List<Integer> arr, int target) {
        // Map will keep track of the number of times a prefix sum appears instead
        Map<Integer, Integer> pointerPrefixMap = new HashMap<>();

        int currentSum = 0;
        int totalNumberOfSubArrays = 0;
        pointerPrefixMap.put(0, 1);

        for (int right = 0; right < arr.size(); right++) {
            currentSum += arr.get(right);

            if (pointerPrefixMap.containsKey(currentSum - target)) {
                // At this point in time, the complement prefix might have appeared a few times before the current
                // right pointer, thus providing us with a few possible combinations
                totalNumberOfSubArrays += pointerPrefixMap.get(currentSum - target);
            }

            pointerPrefixMap.put(currentSum, pointerPrefixMap.getOrDefault(currentSum, 0) + 1);
        }
        return totalNumberOfSubArrays;
    }
}
