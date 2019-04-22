package cde.pg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.List.copyOf;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionsShould {

    @Test
    void J10_create_an_unmodifiable_list_from_an_already_existing() {
        var unmodifiableList = copyOf(List.of("one", "two", "three"));
        var emptyCopy = copyOf(Collections.emptyList());

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("four"));

        var unmodifiableListFromSet = copyOf(Set.of("one", "two", "three"));

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableListFromSet.add("four"));

        List<Integer> integers = Arrays.asList(null, 1, 2);
        assertThrows(NullPointerException.class, () -> copyOf(integers));
    }

    @Test
    void J10_collect_stream_output_into_unmodifiable_list() {
        var collection = List.of(1, 2, 3, 4, 5).stream()
                .collect(toUnmodifiableList());

        assertThrows(UnsupportedOperationException.class, () -> collection.add(4));
    }

    @Test
    void J11_make_easy_to_transform_collections_to_arrays() {
        var letters = Set.of("A", "B", "C", "D");

        String[] array = letters.toArray(String[]::new);

        assertEquals("[A, B, C, D]", Arrays.toString(array));
    }
}
