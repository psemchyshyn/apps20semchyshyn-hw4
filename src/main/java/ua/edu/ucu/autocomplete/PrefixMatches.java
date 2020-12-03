package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.*;


public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        if (strings.length == 1) {
            strings = strings[0].split("\\s+");
        }
        for (String s: strings) {
            trie.add(new Tuple(s, s.length()));
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            throw new IndexOutOfBoundsException("Should be more than 2");
        }
        int start = pref.length() == 2 ? 3 : pref.length();
        HashSet<Integer> temp = new HashSet<>();
        List<String> store = new ArrayList<>();
        for (String s: wordsWithPrefix(pref)) {
            if (s.length() >= start && (temp.contains(s.length()) || temp.size() < k)) {
                temp.add(s.length());
                store.add(s);
            }
        }
        return store;
    }

    public int size() {
        return trie.size();
    }
}
