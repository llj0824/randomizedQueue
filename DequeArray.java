import java.util.Iterator;
import java.util.NoSuchElementException;

// restart - use Nodes...
public class DequeArray<Item> implements Iterable<Item> {
    private enum OpType {
        ADD,
        REMOVE
    }

    private static final Integer UNINITIALIZED = -1;
    private static final int INT_CAPACITY = 10;
    private ItemContainer[] list;
    private int headIndex; //points at the object
    private int tailIndex;

    // construct an empty deque
    public DequeArray() {
        this.list = new ItemContainer[INT_CAPACITY];
        this.headIndex = UNINITIALIZED;
        this.tailIndex = UNINITIALIZED;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (headIndex == 0 && tailIndex == list.length - 1) {
            return true;
        } else if (headIndex == tailIndex) {
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size() {
        // empty start
        if (headIndex == UNINITIALIZED && tailIndex == UNINITIALIZED) {
            return 0;
        } else if (headIndex == UNINITIALIZED || tailIndex == UNINITIALIZED) {
            int idxDiff = headIndex == UNINITIALIZED ? headIndex : tailIndex;
            return idxDiff + 1;
        }

        return Math.abs(headIndex - tailIndex) + 1;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // check size constraints
        if (headIndex == getMaxIndex()) {
            resize(OpType.ADD);
        }

        // head
        headIndex++;
        list[headIndex] = new ItemContainer(item);

        // tail
        tailIndex = Math.max(headIndex, tailIndex); // update tailindex, if head passed it by.
    }

    // add the item to the back
    public void addLast(Item item) {
        // check size constraints
        if (headIndex == getMaxIndex()) {
            resize(OpType.ADD);
        }

        // tail
        tailIndex++;
        list[tailIndex] = new ItemContainer(item);

        // head - nothing
    }

    // remove and return the item from the front
    public Item removeFirst() {
        // check size constraints
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // head
        ItemContainer item = list[headIndex];
        headIndex++;

        // tail
        tailIndex = Math.max(headIndex, tailIndex); // update tailindex, if head passed it by.

        return null;
    }

    // remove and return the item from the back
    public Item removeLast() {
        return null;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return null;
    }

    /* removes elements already dequed from list array */
    private void garbageCollect() {

    }

    private int getMaxIndex() {
        return list.length - 1;
    }

    /* resize current list array */
    private void resize(OpType optype) {
        garbageCollect();

        if (OpType.ADD.equals(optype)) {
            final int newCapacity = list.length * 2;
            final Integer[] newArr = new Integer[newCapacity];
        }

        if (OpType.REMOVE.equals(optype)) {

        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}

class ItemContainer<Item> {
    private Item item;

    public ItemContainer(Item i) {
        super();
        this.item = i;
    }

    public Item getItem() {
        return item;
    }
}
