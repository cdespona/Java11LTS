package cde.pg;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionalShould {

    @Test
    void raise_an_exception_when_no_value_present() {
        Integer actualInteger = List.of(1, 2, 3).stream()
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow();

        assertEquals(2, actualInteger);

        assertThrows(NoSuchElementException.class, this::thisIsGoingToFail);
    }

    private void thisIsGoingToFail() {
        List.of(1, 2, 3).stream()
                .filter(i -> i % 4 == 0)
                .findFirst()
                .orElseThrow();
    }
}
