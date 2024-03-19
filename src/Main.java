import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(coinChange(List.of(1, 2, 5), 11));
    }

    public static List<Integer> coinChange(List<Integer> coins, int amount) {
        return coinChangeDfs(coins, amount, 0, new ArrayList<>(), new HashMap<>());
    }

    public static List<Integer> coinChangeDfs(List<Integer> coins, Integer amount, Integer sum, List<Integer> current, Map<Integer, List<Integer>> memoMap) {
        if (amount.equals(sum)) {
            return new ArrayList<>(current); // Leaf node
        }
        if (sum > amount) {
            return null; // Out of bound, return null to invalidate
        }
        if (memoMap.getOrDefault(sum, null) != null) {
            return memoMap.get(sum);
        }

        // Else proceed to go down each node
        int minSize = Integer.MAX_VALUE; // Initialize minSize
        List<Integer> ans = null;
        for (Integer coin : coins) {
            current.add(coin);
            List<Integer> result = coinChangeDfs(coins, amount, sum + coin, current, memoMap);
            current.remove(current.size() - 1);
            if (result == null) {
                continue;
            }
            if (result.size() <= minSize) {
                minSize = result.size();
                ans = result;
            }
        }
        memoMap.put(sum, ans);
        return ans;
    }
}