import java.util.Iterator;
import java.util.NoSuchElementException;

//Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
//Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
//Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
//Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    int size;

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // head -> (newItem) head <-> preHead
        Node prevHead = head;
        head = new Node(item);
        head.next = prevHead;
        if (prevHead != null) {
            prevHead.prev = prevHead;
        }

        // tail
        if (tail == null) {
            tail = head;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // tail -> prevTail <-> tail (newItem)
        Node prevTail = tail;
        tail = new Node(item);
        tail.prev = prevTail;
        if (prevTail != null) {
            prevTail.next = tail;
        }

        // head
        if (head == null) {
            head = tail;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // head
        // oldHead <-> head
        Node prevHead = head;
        head = prevHead.next;
        prevHead.next = null;
        if (head != null) {
            head.prev = null;
        }

        //tail
        if (tail == prevHead) {
            tail = head;
        }

        size--;

        return prevHead.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // tail
        // newTail <-> prevTail
        Node prevTail = tail;
        tail = prevTail.prev;
        prevTail.prev = null;
        if (tail != null) {
            tail.next = null;
        }

        //head
        if (head == prevTail) {
            head = tail;
        }

        size--;

        return prevTail.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new NodeIterator<>(head);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque iterDeque = new Deque<>();
        iterDeque.addFirst(3);
        iterDeque.addFirst(2);
        iterDeque.addFirst(1);
        Iterator iter = iterDeque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        Deque deque = new Deque();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        System.out.println(String.format("Deque: Expected:%s, Actual:%s",
                1, deque.removeFirst()));
        System.out.println(String.format("Deque: Expected:%s, Actual:%s",
                3, deque.removeLast()));
        System.out.println(String.format("Deque: Expected:%s, Actual:%s",
                2, deque.removeFirst()));

        System.out.println(String.format("Deque: Expected:%s, Actual:%s",
                0, deque.size()));
        System.out.println(String.format("Deque: Expected:%s, Actual:%s",
                true, deque.isEmpty()));
    }

    public class Node {
        Node prev;
        Node next;
        Item val;
        public Node(final Item val) {
            this.val = val;
        }
    }

    public class NodeIterator<Item> implements Iterator<Item> {
        private Node iterHead;

        public NodeIterator(Node head) {
            this.iterHead = head;
        }

        @Override
        public boolean hasNext() {
            return iterHead == null;
        }

        @Override
        public Item next() {
            Item item = (Item) iterHead.val;
            iterHead = iterHead.next;
            return item;
        }
    }

}
