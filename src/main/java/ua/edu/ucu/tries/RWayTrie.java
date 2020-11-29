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
        private char sym;
        public Node[] next;

        public Node() {
            val = -1;
            next = new Node[R];
        }
    }

    @Override
    public void add(Tuple t) {

        root = add_helper(root, t.term, t.weight, 0);
        size++;
    }

    private Node add_helper(Node to, String term, int weight, int index) {
        if (to == null) to = new Node();
        if (term.length() == index) {
            to.setVal(weight);
            return to;
        }
        int next = term.charAt(index) - shift;
        to.next[next] = add_helper(to.next[next], term, weight, index + 1);
        return to;
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
        return get(root, word) != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            size--;
            return true;
        }
        return false;
    }

    private Node delete(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length())
            x.setVal(-1);
        else
        {
            int c = key.charAt(d);
            x.next[c - shift] = delete(x.next[c - shift], key, d+1);
        }
        if (x.getVal() != -1) return x;
        for (int i = 0; i < R; i++)
            if (x.next[i] != null) return x;
        return null;
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

    private void collect(Node x, String pre,
                         Queue q)
    {
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
