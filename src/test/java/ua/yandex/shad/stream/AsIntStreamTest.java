package ua.yandex.shad.stream;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Vladimir Fomenko
 */
public class AsIntStreamTest {
    private IntStream intStream;

    @Before
    public void setUp() {
        intStream = AsIntStream.of(-5, -3, -1, 1, 3, 5);
    }

    @Test
    public void testAverage() throws Exception {
        assertEquals(0, intStream.average(), 1e-9);
        assertEquals(1, intStream.map(x -> x + 1).average(), 1e-9);
        assertEquals(-1, intStream.map(x -> x - 1).average(), 1e-9);
    }

    @Test
    public void testMax() throws Exception {
        assertEquals(5, intStream.max());
        assertEquals(5, intStream.filter(x -> x < 0).map(x -> -x).max());
    }

    @Test
    public void testMin() throws Exception {
        assertEquals(-5, intStream.min());
        assertEquals(1, intStream.filter(x -> x > 0).min());
    }

    @Test
    public void testCount() throws Exception {
        assertEquals(6, intStream.count());
        assertEquals(2, intStream.filter(x -> x > -3 && x < 3).count());
    }

    @Test
    public void testFilter() throws Exception {
        assertEquals(2, intStream.filter(x -> x > -3 && x < 3).count());
        assertEquals(12, intStream.flatMap(x -> AsIntStream.of(x - 1, x, x + 1))
                .map(x -> (x > 0 ? x : -x))
                .filter(x -> x % 2 == 0)
                .count());
    }

    @Test
    public void testForEach() throws Exception {
        class Dummy {
            private int value;

            public void call(int x) {
                value = value * x + x;
            }

            public int getValue() {
                return value;
            }
        }
        Dummy dummy = new Dummy();
        intStream.forEach(dummy::call);
        assertEquals(-160, dummy.getValue());
    }

    @Test
    public void testMap() throws Exception {
        assertEquals(70, intStream.map(x -> x * x).sum());
    }

    @Test
    public void testReduce() throws Exception {
        assertEquals(Integer.valueOf(-1),
                Integer.valueOf(intStream.reduce(-5, (x, y) -> x * y + x + y)));
    }

    @Test
    public void testSum() throws Exception {
        assertEquals(-6, intStream.map(x -> x ^ 13).sum());
    }

    @Test
    public void testToArray() throws Exception {
        int[] array = intStream.toArray();
        int[] expected = new int[]{-5, -3, -1, 1, 3, 5};
        for (int i = 0; i < 6; ++i) {
            assertEquals(expected[i], array[i]);
        }
    }

    @Test
    public void testFlatMap() throws Exception {
        assertEquals(258, intStream.flatMap(
                (x) -> AsIntStream.of(3 * x * x + 2 * x + 5, 4 * x + 3)).sum());
    }
}
