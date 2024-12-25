package leetcode;

import java.util.*;

public class StackQuestions {
    public static void main(String[] args) {
        StackQuestions test = new StackQuestions();
        System.out.println(test.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"
        }));
    }


    // * 20. Valid parentheses
    // * Time complexity - O(N)
    // * Space complexity - O(N)
    public boolean isValid(String s) {
        // Hashmap to maintain the combinations first
        Map<Character, Character> store = new HashMap<>();
        store.put('{', '}');
        store.put('[', ']');
        store.put('(', ')');
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            Character currentChar = s.charAt(i);
            if (store.containsKey(currentChar)) {
                stack.push(store.get(currentChar));
            } else if (stack.isEmpty()) {
                return false;
            } else {
                Character closingChar = stack.pop();
                if (closingChar != currentChar) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // * 155. Min Stack
    class MinStack {
        private ArrayDeque<Node> stack;

        class Node {
            private int value;
            private int currentMinValue;

            public Node(int value, int currentMinValue) {
                this.value = value;
                this.currentMinValue = currentMinValue;
            }
        }

        public MinStack() {
            this.stack = new ArrayDeque<>();
        }

        public void push(int val) {
            int currentMin = Math.min(this.getMin(), val);
            this.stack.push(new Node(val, currentMin));
        }

        public void pop() {
            this.stack.pop();
        }

        public int top() {
            return this.stack.peek().value;
        }

        public int getMin() {
            if (this.stack.isEmpty()) {
                return Integer.MAX_VALUE;
            }
            return this.stack.peek().currentMinValue;
        }
    }

    // * 150. Evaluate Reverse Polish Notation
    public int evalRPN(String[] tokens) {
        Set<String> validOperators = new HashSet<>(List.of("+", "-", "*", "/"));
        ArrayDeque<Integer> store = new ArrayDeque<>();
        int num1;
        int num2;
        for (String token : tokens) {
            // Handle the operators first
            if (validOperators.contains(token)) {
                num1 = store.pop();
                num2 = store.pop();
                switch (token) {
                    case "+" -> store.push(num2 + num1);
                    case "-" -> store.push(num2 - num1);
                    case "*" -> store.push(num2 * num1);
                    case "/" -> store.push(num2 / num1);
                }
            } else {
                store.push(Integer.parseInt(token));
            }
        }
        return store.pop();
    }

    // * 22. Generate Parenthesis
    // * Time complexity: o (2**n * n) - This is because we have a total of 4**n combinations of parenthesis and 2n
    // length
    // * Space complexity: o(4**n * n) - Same as above.
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesisDfs(result, new ArrayList<>(), n, 0, 0);
        return result;
    }

    private void generateParenthesisDfs(List<String> result, List<String> currentPath, int n, int openingCount, int closingCount) {
        if (currentPath.size() == n * 2) {
            result.add(String.join("", currentPath));
            return;
        }
        // Conditions to execute path
        if (openingCount < n) {
            currentPath.add("(");
            generateParenthesisDfs(result, currentPath, n, openingCount + 1, closingCount);
            currentPath.removeLast();
        }
        if (closingCount < openingCount) {
            currentPath.add(")");
            generateParenthesisDfs(result, currentPath, n, openingCount, closingCount + 1);
            currentPath.removeLast();
        }
    }

    // * 739. Daily Temperature
    // * Time complexity - o (N)
    // * Space complexity - o(N)
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int currDay = 0; currDay < temperatures.length; currDay++) {
            while (!stack.isEmpty() && temperatures[currDay] > temperatures[stack.peek()]) {
                int prevDay = stack.pop();
                result[prevDay] = currDay - prevDay;
            }
            stack.add(currDay);
        }
        return result;
    }

    // * 2390. Removing stars from a string
    // * Time complexity - o(n) (Length of string)
    // * Space complexity - o(n) (Length of string)
    public String removeStars(String s) {
        List<Character> stack = new ArrayList<>(s.length());

        for (Character chr : s.toCharArray()) {
            // Add to the stack if it isnt "*"
            if (chr != '*') {
                stack.add(chr);
            }

            // Else, pop the stack and remove the last character
            else if (!stack.isEmpty()) {
                stack.removeLast();
            }
        }

        // Compile it back into a string and return it
        StringBuilder stringBuilder = new StringBuilder();
        for (Character remainingChr : stack) {
            stringBuilder.append(remainingChr);
        }
        return stringBuilder.toString();
    }

    // * 921. Minimum Add to Make Parentheses Valid
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int minAddToMakeValid(String s) {
        // Original solution is to use a stack to keep track of open
        // But in the event that it overshots, it will not be able to keep track
        // Use two variables to keep track of open and close
        int openSurplus = 0;
        int closeSurplus = 0;

        for (char chr : s.toCharArray()) {
            if (chr == '(') {
                openSurplus += 1;
            }

            if (chr == ')') {
                if (openSurplus > 0) {
                    openSurplus--;
                    continue;
                }
                closeSurplus++;
            }
        }
        return openSurplus + closeSurplus;
    }

    // * 946. Validate Stack Sequences
    // * Time complexity: o(2n)
    // * Space complexity: o(n)
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // End goal is that the stack should be empty at the end and that both pushed and popped have been iterated to the end

        int poppedPointer = 0;
        ArrayList<Integer> stack = new ArrayList<>();

        for (int pushedPointer = 0; pushedPointer < pushed.length; pushedPointer++) {
            // Case 1. Stack's last element matches popped
            // After adding the prev element, pop all elements that matches the sequence in popped
            while (!stack.isEmpty() && stack.getLast() == popped[poppedPointer]) {
                stack.removeLast();
                poppedPointer++;
            }

            stack.add(pushed[pushedPointer]);
        }

        // At the end, do another pop again
        if (poppedPointer != pushed.length) {
            while (!stack.isEmpty() && stack.getLast() == popped[poppedPointer]) {
                stack.removeLast();
                poppedPointer++;
            }
        }

        // Need to make sure that all is popped
        return (stack.isEmpty() && poppedPointer == popped.length);
    }

    // * 1190. Reverse Substrings Between Each Pair of Parentheses
    // * Time Complexity: o(n)
    // * Space Complexity: o(n)
    public String reverseParentheses(String s) {
        // 1. Keep track of each pair
        // Builds on the concept of undirected graphs, maintaining a pointer to each other
        Map<Integer, Integer> parenthesisMap = new HashMap<>();
        ArrayList<Integer> stack = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.add(i);
            }
            if (s.charAt(i) == ')') {
                parenthesisMap.put(stack.getLast(), i);
                parenthesisMap.put(i, stack.getLast());
                stack.removeLast();
            }
        }

        int count = 0;
        boolean isRightDirection = true;
        StringBuilder sb = new StringBuilder();
        while (count < s.length()) {
            if (s.charAt(count) == '(' || s.charAt(count) == ')') {
                // Move it to the other end of the parenthesis
                count = parenthesisMap.get(count);
                // Reverse the direction that the pointer is moving in
                isRightDirection = !isRightDirection;
            } else {
                // if it isnt, we will just append it to the list
                sb.append(s.charAt(count));
            }

            // Proceed to either increase or decrease the pointer based on direction
            // This works out because it will always increase until the last parenthesis
            if (isRightDirection) {
                count++;
            } else {
                count--;
            }
        }

        return sb.toString();
    }

    //  * 1475. Final Prices With a Special Discount in a Shop
    // *  Time complexity: o(n)
    // *  Space complexity: o(n)
    public int[] finalPrices(int[] prices) {
        List<int[]> stack = new ArrayList<>();

        for (int i = 0; i < prices.length; i++) {
            int currPrice = prices[i];
            if (stack.isEmpty()) {
                // [price, index]
                stack.add(new int[]{prices[i], i});
                continue;
            }

            // Else check if the current price is smaller than the previous price;
            int[] prev = stack.getLast();
            while (prev[0] >= currPrice) {
                // While the previous price is bigger or equal to the curr price
                // We will keep popping from the stack and modifying it in the prices
                prices[prev[1]] = prev[0] - currPrice;
                stack.removeLast();
                if (stack.isEmpty()) {
                    break;
                }
                prev = stack.getLast();
            }
            stack.add(new int[]{currPrice, i});
        }

        return prices; // Modifications are done in place
    }
}

