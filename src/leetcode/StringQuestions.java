package leetcode;

public class StringQuestions {
    public static void main(String[] args) {

    }

    // * 1957. Delete Characters to Make Fancy String
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public String makeFancyString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i < 2) {
                sb.append(s.charAt(i));
                continue; // Skip
            }
            if (s.charAt(i) == s.charAt(i - 1) && s.charAt(i) == s.charAt(i - 2)) {
                continue; // Skip this
            }
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }
}
