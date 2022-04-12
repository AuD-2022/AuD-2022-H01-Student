package h01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;

import static h01.ListTest.assertListOfListsEquals;
import static h01.ListTest.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleListOfListsProcessorTest {

    private ListItem<ListItem<Double>> listToPartition;

    private final double limit = 6.0;

    private final ListItem<ListItem<Double>> expectedRepartitionedList = toList(
        toList(1.0, 3.0),
        toList(5.0),
        toList(1.0, 2.0, 3.0),
        toList(4.0),
        toList(5.0)
    );

    private final ListItem<ListItem<Double>> listForReadWrite = toList(
        toList(1.0, 2.0),
        toList(),
        toList(1.0, 2.0, 3.0)
    );

    private final String contentForReadWrite =
        "1.0#2.0\n" +
        "\n" +
        "1.0#2.0#3.0#";

    @BeforeEach
    void setup() {
        listToPartition = toList(
            toList(1.0, 3.0, 5.0),
            toList(1.0, 2.0, 3.0, 4.0, 5.0)
        );
    }

    @Test
    void testPartitionListsAsCopyIteratively() {
        var actual = DoubleListOfListsProcessor
            .partitionListsAsCopyIteratively(listToPartition, limit);
        assertListOfListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsInPlaceIteratively() {
        var actual = DoubleListOfListsProcessor
            .partitionListsInPlaceIteratively(listToPartition, limit);
        assertListOfListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsAsCopyRecursively() {
        var actual = DoubleListOfListsProcessor
            .partitionListsAsCopyRecursively(listToPartition, limit);
        assertListOfListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsInPlaceRecursively() {
        var actual = DoubleListOfListsProcessor
            .partitionListsInPlaceRecursively(listToPartition, limit);
        assertListOfListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testWrite() {
        var writer = new StringWriter();

        DoubleListOfListsProcessor.write(writer, listForReadWrite);

        var actual = writer.toString();
        assertEquals(contentForReadWrite, actual);
    }

    @Test
    void testRead() {
        var reader = new BufferedReader(new StringReader(contentForReadWrite));

        var actual = DoubleListOfListsProcessor.read(reader);

        assertListOfListsEquals(listForReadWrite, actual);
    }
}
