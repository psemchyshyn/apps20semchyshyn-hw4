package ua.edu.ucu.immutable;

public final class ImmutableLinkedList implements ImmutableList{
    private static class Node {
        public Node next;
        public Object value;
        public Node(Object value) {
            this.next = null;
            this.value = value;
        }

    }
    private int currSize;
    private Node head;

    public ImmutableLinkedList() {
        head = null;
    }

    public ImmutableLinkedList(Object[] from) {
        ImmutableList list = new ImmutableLinkedList();
        list = list.addAll(from);
        head = ((ImmutableLinkedList) list).getHead();
        currSize = list.size();
    }

    private ImmutableLinkedList(Node head, int size) {
        this.head = head;
        currSize = size;
    }

    private ImmutableLinkedList internalCopy() {
        if (head != null) {
            Node headCopy = new Node(head.value);
            Node tempCopy = headCopy;
            Node temp = head.next;
            while (temp != null) {
                tempCopy.next = new Node(temp.value);
                temp = temp.next;
                tempCopy = tempCopy.next;
            }
            return new ImmutableLinkedList(headCopy, currSize);
        }
        return new ImmutableLinkedList();
    }

    private Node getHead() {
        return head;
    }

    private void checkOutOfBounds(int index) {
        if (index < 0 || index >= currSize) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public Node getPrevNode(int index) {
        checkOutOfBounds(--index);
        Node temp = head;
        while (index-- != 0) {
            temp = temp.next;
        }
        return temp;
    }

    public ImmutableLinkedList removeFirst() {
        checkOutOfBounds(0);
        ImmutableLinkedList copy = internalCopy();
        Node temp = copy.head;
        copy.head = copy.head.next;
        temp.next = null;
        copy.currSize--;
        return copy;
    }

    public ImmutableLinkedList removeLast() {
        return (ImmutableLinkedList) remove(currSize - 1);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(currSize - 1);
    }

    public ImmutableLinkedList addFirst(Object e) {
        ImmutableLinkedList copy = internalCopy();
        Node newNode = new Node(e);
        newNode.next = copy.head;
        copy.head = newNode;
        copy.currSize++;
        return copy;
    }

    public ImmutableLinkedList addLast(Object e) {
        return (ImmutableLinkedList) add(e);
    }

    public ImmutableList add(Object e) {
        return add(currSize, e);
    }

    public ImmutableList add(int index, Object e) {
        if (index == 0) {
            return addFirst(e);
        } else {
            ImmutableLinkedList copy = internalCopy();
            Node temp  = copy.getPrevNode(index);
            Node newNode = new Node(e);
            newNode.next = temp.next;
            temp.next = newNode;
            copy.currSize++;
            return copy;
        }
    }

    public ImmutableList addAll(Object[] c) {
        return addAll(currSize, c);
    }

    public ImmutableList addAll(int index, Object[] c) {
        ImmutableList copy = internalCopy();
        for (int i = 0; i < c.length; i++) {
            copy = copy.add(index + i, c[i]);
        }
        return copy;
    }

    public Object get(int index) {
        checkOutOfBounds(index);
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    public ImmutableList remove(int index) {
        checkOutOfBounds(index);
        ImmutableLinkedList copy = internalCopy();
        if (index == 0) {
            return removeFirst();
        } else {
            Node temp = copy.getPrevNode(index);
            temp.next = temp.next.next;
        }
        copy.currSize--;
        return copy;
    }

    public ImmutableList set(int index, Object e) {
        checkOutOfBounds(index);
        ImmutableLinkedList copy = internalCopy();
        if (index == 0) {
            copy.head.value = e;
        } else {
            Node temp = copy.getPrevNode(index);
            temp.next.value = e;
        }
        return copy;
    }

    public int indexOf(Object e) {
        for (int i = 0; i < currSize; i++) {
            if (get(i).equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return currSize;
    }

    public ImmutableList clear() {
        return new ImmutableLinkedList();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Object[] toArray() {
        Object[] arr =  new Object[currSize];
        for (int i = 0; i < currSize; i++) {
            arr[i] = get(i);
        }
        return arr;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer("[ ");
        for (int i = 0; i < currSize; i++) {
            bf.append((Object) get(i));
            bf.append(", ");
        }
        if (bf.length() > 3) {
            bf = bf.delete(bf.length() - 2, bf.length());
        }
        bf.append(" ]");
        return bf.toString();
    }
}
