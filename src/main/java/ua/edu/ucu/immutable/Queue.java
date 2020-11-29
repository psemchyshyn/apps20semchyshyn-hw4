package ua.edu.ucu.immutable;


import java.util.Arrays;
import java.util.Iterator;

public class Queue implements Iterable<String>{
    // The last element of the Queue is the first element of the storage
    private ImmutableLinkedList storage;

    public Queue() {
        storage = new ImmutableLinkedList();
    }

    public Queue(Object[] from) {
        storage = new ImmutableLinkedList(from);
    }

    public Object peek() {
        return storage.getLast();
    }

    public void enqueue(Object e) {
        storage = storage.addFirst(e);
    }

    public Object dequeue() {
        Object toRemove = peek();
        storage = storage.removeLast();
        return toRemove;
    }

    public Object[] toArray() {
        return storage.toArray();
    }

    @Override
    public String toString() {
        return storage.toString();
    }

    @Override
    public Iterator<String> iterator() {
        Object[] arr = toArray();
        String[] toIter = new String[arr.length];
        int cnt = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            toIter[cnt++] = (String) arr[i];
        }
        return Arrays.asList(toIter).iterator();
    }
}
