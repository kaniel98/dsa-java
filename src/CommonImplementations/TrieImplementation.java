package CommonImplementations;

import java.util.HashMap;

public class TrieImplementation {
    HashMap<Character, TrieImplementation> children;
    int freq;

    TrieImplementation() {
        children = new HashMap<>();
        freq = 0;
    }

    void insert(String word) {
        TrieImplementation node = this;
        for (char c : word.toCharArray()) {
            // If the character does not exist in the trie, add it
            node.children.putIfAbsent(c, new TrieImplementation());
            node = node.children.get(c);
            // Increase frequency for this prefix
            node.freq++; // Increase frequency for this prefix
        }
    }

    int query(String prefix) {
        TrieImplementation node = this;
        for (char c : prefix.toCharArray()) {
            // If prefix does not exist in trie, return 0
            if (!node.children.containsKey(c)) {
                return 0;
            }
            // Move to the next node (Character) - Iterating down the trie
            node = node.children.get(c);
        }
        return node.freq; // Return the frequency of this prefix
    }
}
