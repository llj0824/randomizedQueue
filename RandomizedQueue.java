import com.sun.org.apache.regexp.internal.RE;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/*
Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;
    private static final Object REMOVED = null;
    private Item[] a;         // array of items
    private int n;            // number of elements on stack

    // construct an empty deque
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public RandomizedQueue(Item[] a, int n) {
        this.a = a;
        this.n = n;
    }

    public RandomizedQueue RandomizedQueueCopy(RandomizedQueue baseRandomizedQueue) {
        final Item[] originalItems = (Item[]) baseRandomizedQueue.getItems();
        final int n = originalItems.length;
        Item[] copyOfItems = (Item[]) new Object[n];
        for (int i = 0; i < n; i++) {
            copyOfItems[i] = originalItems[i];
        }
        return new RandomizedQueue(copyOfItems, n);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        int copyIndex = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == REMOVED) {
                continue;
            }
            copy[copyIndex] = a[i];
            copyIndex++;
        }
        a = copy;
    }


    // add the item
    public void enqueue(Item item) {
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n] = item;                            // add item
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");

        int randomIndex;
        Item returnItem = (Item) REMOVED;
        do {
            randomIndex = StdRandom.uniform(0, a.length);
            returnItem = a[randomIndex];
        } while (returnItem == REMOVED);

        // remove item
        a[randomIndex] = (Item) REMOVED;                              // to avoid loitering
        n--;

        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return returnItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item returnItem = (Item) REMOVED;
        do {
            int randomIndex = StdRandom.uniform(0, a.length);
            returnItem = a[randomIndex];
        } while (returnItem == REMOVED);

        return returnItem;
    }

    public Item[] getItems() {
        return this.a;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new RandomIterator<Item>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
        final RandomizedQueue<String> stack = new RandomizedQueue<>();
        final String[] input = "1,2,3,4,5,6,7,8".split(",");
        for (int i = 0; i < input.length; i++) {
            final String item = input[i];
            stack.enqueue(item);
        }
        while (!stack.isEmpty()) {
            StdOut.print(stack.dequeue() + " ");
            StdOut.println("(" + stack.size() + " left on stack)");
        }
        StdOut.println("Done!");
    }

    private class RandomIterator<Item> implements Iterator<Item> {
        private Item[] arr;
        private int size;
        private int curIndex;

        public RandomIterator(RandomizedQueue<Item> queue) {
            final RandomizedQueue copyOfQueue = RandomizedQueueCopy(queue);
            this.arr = (Item[]) new Object[copyOfQueue.size()];

            this.size = 0;
            this.curIndex = 0;
            while (!copyOfQueue.isEmpty()) {
                Item copyOfItem = (Item) copyOfQueue.dequeue();
                arr[this.size] = copyOfItem;
                this.size++;
            }
        }

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public Item next() {
            return arr[curIndex++];
        }
    }
}
