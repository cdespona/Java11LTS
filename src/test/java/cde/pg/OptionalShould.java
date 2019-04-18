package cde.pg;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionalShould {

    @Test
    void J10_raise_an_exception_when_no_value_present() {
        Integer actualInteger = List.of(1, 2, 3).stream()
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow();

        assertEquals(2, actualInteger);

        assertThrows(NoSuchElementException.class, this::thisIsGoingToFail);
    }

    @Test
    void J11_should_let_us_know_when_it_is_empty() {
        Optional<Object> optional = Optional.empty();

        assertTrue(optional.isEmpty());
    }

    private void thisIsGoingToFail() {
        List.of(1, 2, 3).stream()
                .filter(i -> i % 4 == 0)
                .findFirst()
                .orElseThrow();
    }
}
