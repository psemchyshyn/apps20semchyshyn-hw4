package ua.edu.ucu;

import ua.edu.ucu.autocomplete.PrefixMatches;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        try {
//            Scanner in = new Scanner(new File("rnd.txt"));
//        } catch (FileNotFoundException e) {
//            System.out.println("Wrong");
//        }
        Scanner in = new Scanner(new File("rnd.txt"));
        Trie trie = new RWayTrie();
        List<String> lst = new ArrayList<>();
        String next;
        while (in.hasNext()) {
            next= in.nextLine();
            trie.add(new Tuple(next, next.length()));
        }
        PrefixMatches prefixMatches = new PrefixMatches(trie);
        for (String s: prefixMatches.wordsWithPrefix("ab", 3)) {
            prefixMatches.delete(s);
            System.out.println(s);
        }
        System.out.println("\n");
        for (String s: prefixMatches.wordsWithPrefix("ab")) {
            System.out.println(s);
        }
    }
}
