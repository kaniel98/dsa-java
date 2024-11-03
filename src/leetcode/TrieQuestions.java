package leetcode;

import java.util.HashMap;
import java.util.Map;

public class TrieQuestions {
    // * TrieNode
    static class TrieNode {
        // Assuming the implementation is only for the 26 small letters
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
    }

    // * 208. Implement Trie (Prefix Tree)
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode(); // Root is definitely not the end
        }

        public void insert(String word) {
            TrieNode node = this.root;
            for (char chr : word.toCharArray()) {
                node.children.putIfAbsent(chr, new TrieNode());
                node = node.children.get(chr);
            }
            // Towards the end, set this node to be end = true
            node.isEnd = true;
        }

        public boolean search(String word) {
            TrieNode node = this.root;
            for (char chr : word.toCharArray()) {
                if (!node.children.containsKey(chr)) {
                    return false;
                }
                node = node.children.get(chr);
            }
            return node.isEnd;
        }

        public boolean startsWith(String prefix) {
            TrieNode node = this.root;
            for (char chr : prefix.toCharArray()) {
                if (!node.children.containsKey(chr)) {
                    return false;
                }
                node = node.children.get(chr);
            }
            return true;
        }
    }


}
