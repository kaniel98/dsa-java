package leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieQuestions {
    // * TrieNode
    static class TrieNode {
        // Assuming the implementation is only for the 26 small letters
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
        boolean isWord = false;
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

    // * 648. Replace Words
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public String replaceWords(List<String> dictionary, String sentence) {
        ReplaceWordTrie dictTrie = new ReplaceWordTrie();
        for (String str : dictionary) {
            dictTrie.insert(str);
        }

        // Proceed to check for each string
        String[] sentenceList = sentence.split(" ");
        for (int i = 0; i < sentenceList.length; i++) {
            sentenceList[i] = dictTrie.getReplacement(sentenceList[i]);
        }

        return String.join(" ", sentenceList);
    }

    static class ReplaceWordTrie { // Note the implementation of this trie includes the unique getReplacement method
        TrieNode root;

        public ReplaceWordTrie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = this.root;
            for (char chr : word.toCharArray()) {
                node.children.putIfAbsent(chr, new TrieNode());
                node = node.children.get(chr);
            }
            node.isWord = true;
        }

        public String getReplacement(String word) {
            TrieNode node = this.root;
            for (int i = 0; i < word.length(); i++) {
                char chr = word.charAt(i);
                if (!node.children.containsKey(chr)) {
                    return word; // No replacement will be made, immediately return the word
                }
                node = node.children.get(chr);

                // But if word is the end, we will return it
                if (node.isWord) {
                    return word.substring(0, i + 1);
                }
            }
            return word;
        }
    }
}
