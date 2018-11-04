package com.amazon;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class MyListTest {
    private MyList<Integer> myList;

    @Before
    public void init() {
        myList = new MyList<>(1, 2, 3, 4);
    }

    @Test
    public void reverse() {
        assertArrayEquals(new MyList<>(4, 3, 2, 1).getData(), myList.reverse().getData());
    }

    @Test
    public void filter() {
        Predicate<Integer> pred = integer -> integer % 2 == 0;
        assertArrayEquals(new MyList<>(2, 4).getData(), myList.filter(pred).getData());
        assertArrayEquals(new MyList<>(2, 4, 6, 8, 10).getData(), new MyList<>(2, 4, 6, 8, 10).filter(pred).getData());
        assertArrayEquals(new MyList<>().getData(), new MyList<Integer>().filter(pred).getData());
    }

    @Test
    public void map() {
        MyList<String> mapped = new MyList<>("foo", "bar",
                "baz").map(String::toUpperCase);
        assertEqualsDeep(new MyList<>("FOO", "BAR", "BAZ"), mapped);
    }

    @Test
    public void fold() {
        Integer cummulativeSize = new MyList<>("foo", "bar", "baz", "boom").foldLeft(0, (String a, Integer acc) -> a.length() + acc);
        assertEquals(13, cummulativeSize.intValue());

        //no operation test
        String concat = new MyList<>("foo", "bar", "baz", "boom").foldLeft("", (String a, String acc) -> acc + a);
                assertEquals("foobarbazboom", concat);
    }

    void assertEqualsDeep(MyList a, MyList b) {
        if (a == null && b != null) {
            fail("First arg is null!");
        }
        if (a != null && b == null) {
            fail("Second arg is null!");
        }
        if (a.getData().length != b.getData().length) {
            fail("Different sizes!");
        }
// iterate over the data
        Object[] dataa = a.getData();
        Object[] datab = b.getData();
        for (int i = 0; i < a.getData().length; i++) {
            assertEquals(dataa[i], datab[i]);
        }
    }
}