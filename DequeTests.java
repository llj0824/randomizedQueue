import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DequeTests {
    private Integer item = 42;
    private Deque<Integer> deque;

    @Before
    public void setup() {
        deque = new Deque<>();
    }
    @Test
    public void whenGetSize_noItems_returnsZero() {
        deque = new Deque<>();
        assertEquals(0, deque.size());
    }
    @Test
    public void whenGetSize_oneItem_returnsOne() {
        deque.addFirst(42);
        assertEquals(1, deque.size());

        deque = new Deque<>();
        deque.addLast(42);
        assertEquals(1, deque.size());

    }

    @Test
    public void whenGetSize_twoItems_oneEachSide_returnsTwo() {
        deque.addFirst(42);
        deque.addLast(42);
        assertEquals(2, deque.size());

        deque = new Deque<>();
        deque.addFirst(42);
        deque.addLast(42);
        assertEquals(2, deque.size());
    }

    @Test
    public void whenAddOneItem_removeSameDirectionReturnsItem() {
        deque.addFirst(item);
        Integer result = deque.removeFirst();
        assertEquals(item, result);

        deque.addLast(item);
        Integer resultLast = deque.removeLast();
        assertEquals(item, resultLast);
    }

    @Test
    public void whenAddOneItem_removeOppositeDirectionReturnsItem() {
        deque.addFirst(item);
        Integer result = deque.removeLast();
        assertEquals(item, result);

        deque.addLast(item);
        Integer resultLast = deque.removeFirst();
        assertEquals(item, resultLast);
    }

}
