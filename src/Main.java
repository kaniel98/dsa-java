import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(getTopKBitDistances(List.of(4, 10, 13, 5, 18), 3));

    }

    public static int raisingPower(List<Integer> arr) {
        // Write your code here
        double highestValue = 0;
        int highestValueIndex = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            double currValue = Math.pow(arr.get(i), arr.get(i + 1)) % (Math.pow(10, 9) + 7);
            if (currValue > highestValue) {
                highestValue = currValue;
                highestValueIndex = i;
            }

            System.out.println(currValue);
            System.out.println(i);

        }
        return highestValueIndex;
    }

    public static List<Integer> getTopKBitDistances(List<Integer> numbers, int k) {
        // 1. Convert into a hashmap - num : number of times it appear

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int maxBitDistance = -1;
        for (Integer number : numbers) {
            int bitDistance = findMaxDistanceBetweenOnes(number);
            if (!map.containsKey(bitDistance)) {
                map.put(bitDistance, new ArrayList<>());
            }
            map.get(bitDistance).add(number);
            maxBitDistance = Math.max(bitDistance, maxBitDistance);
        }

        // 2. Convert it into an array
        List<Integer>[] tempStore = new List[maxBitDistance + 1];
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int count = entry.getKey();
            if (entry.getKey() != -1 && tempStore[count] == null) {
                tempStore[count] = entry.getValue();
            }
        }

        // 3. Iterate from the back and add it to the result
        List<Integer> result = new ArrayList<>();
        int index = 0;
        for (int i = tempStore.length - 1; i >= 0; i--) {
            if (tempStore[i] != null) {
                for (int num : tempStore[i]) {
                    if (index < k) {
                        result.add(num);
                        index++;
                    }
                }
            }
        }
        return result;

    }

    public static int findMaxDistanceBetweenOnes(int num) {
        int distance = 0;
        int lastOneIndex = -1;  // Keeps track of the index of the last encountered 1
        int numberOfOnes = 0;

        // Iterate through each bit
        int bitPosition = 0;
        while (num > 0) {
            int currentBit = num & 1;
            if (currentBit == 1) {
                // Update distance if there was a previous 1 and calculate the difference
                if (lastOneIndex != -1) {
                    distance = Math.max(distance, bitPosition - lastOneIndex - 1);
                }
                lastOneIndex = bitPosition;
                numberOfOnes++;
            }
            num >>= 1;  // Right shift to check the next bit
            bitPosition++;
        }

        // Check if there are less than two 1s
        if (numberOfOnes < 2) {
            return -1;
        }
        return distance;
    }
}