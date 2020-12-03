package ua.edu.ucu.tries;

import lombok.Getter;
import lombok.Setter;
import ua.edu.ucu.immutable.Queue;


public class RWayTrie implements Trie {
    private Node root = new Node();
    private static final int R = 26;
    private static final int shift = 97;
    private int size;

    private static class Node{
        @Setter @Getter
        private int val;
        public Node[] next;

        public Node() {
            val = -1;
            next = new Node[R];
        }

    }

    @Override
    public void add(Tuple t) {
        String term = t.term;
        int weight = t.weight;
        Node temp = root;
        for (int i = 0; i < term.length(); i++) {
            if (temp.next[term.charAt(i) - shift] == null)
                temp.next[term.charAt(i) - shift] = new Node();
            temp = temp.next[term.charAt(i) - shift];

        }
        size = temp.getVal() == -1 ? size: size + 1; // if the word exists, don't change size
        temp.setVal(weight);
    }


    private Node get(Node start, String word) {
        Node temp = start;
        for (int i = 0; i < word.length(); i++) {
            temp = temp.next[word.charAt(i) - shift];
            if (temp == null) {
                return null;
            }
        }
        return temp;
    }

    @Override
    public boolean contains(String word) {
        Node temp = get(root, word);
        return temp != null && temp.getVal() != -1;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            size--;
            Node temp = root;
            Node prev = temp;
            for (int i = 0; i < word.length(); i++) {
                prev = temp;
                temp = temp.next[word.charAt(i) - shift];
            }
            temp.setVal(-1);
            for (int i = 0; i < R; i++) {
                if (temp.next[i] != null) return true;
            }
            prev.next[word.charAt(word.length() - 1) - shift] = null;
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s), s, q);
        return q;
    }

    // from book
    private void collect(Node x, String pre, Queue q) {
        if (x == null) return;
        if (x.getVal() != -1) q.enqueue(pre);
        for (int c = 0; c < R; c++)
            collect(x.next[c], pre + (char) (c + shift), q);
    }

    @Override
    public int size() {
        return size;
    }
}
