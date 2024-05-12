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
    // * Time complexity: o (4**n * n) - This is because we have a total of 4**n combinations of parenthesis and 2n
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
}

