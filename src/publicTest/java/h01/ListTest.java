package h01;

import static org.junit.jupiter.api.Assertions.*;

public class ListTest {

    private ListTest() {}

    public static <T> void assertListOfListsEquals(ListItem<ListItem<T>> expected, ListItem<ListItem<T>> actual) {
        assertListOfListsEquals0(expected,  actual, 0);
    }

    private static <T> void assertListOfListsEquals0(ListItem<ListItem<T>> expected, ListItem<ListItem<T>> actual, int i) {
        if (expected == null && actual == null) {
            return;
        }

        assertNotNull(
            expected,
            "Actual outer list is longer than the expected one"
        );

        assertNotNull(
            actual,
    "Actual outer list is shorter than the expected one"
        );

        assertListEquals(expected.key, actual.key, i, 0);
        assertListOfListsEquals0(expected.next, actual.next, i+1);
    }

    private static <T> void assertListEquals(ListItem<T> expected, ListItem<T> actual, int i, int j) {
        if (expected == null && actual == null) {
            return;
        }

        assertNotNull(
            expected,
            String.format("Actual inner list at index %d is longer than the expected one", i)
        );

        assertNotNull(
            actual,
            String.format("Actual inner list at index %d is shorter than the expected one", i)
        );

        assertEquals(
            expected.key,
            actual.key,
            String.format("Elements at indices (%d, %d) are not equal", i, j)
        );

        assertListEquals(expected.next, actual.next, i, j+1);
    }

    @SafeVarargs
    public static <T> ListItem<T> toList(T... elements) {
        ListItem<T> head = null;
        ListItem<T> tail = null;

        for (var e : elements) {
            if (head == null) {
                head = tail = new ListItem<>();
            } else {
                tail = tail.next = new ListItem<>();
            }

            tail.key = e;
        }

        return head;
    }
}
