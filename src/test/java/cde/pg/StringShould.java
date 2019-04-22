package cde.pg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringShould {

    @Test
    void J11_repeat_as_many_times_as_we_ask_for_it() {
        var expected = "Hold the door! Hold the door! Hold the door! HODOR!!!";
        assertEquals(expected, "Hold the door! ".repeat(3) + "HODOR!!!");
    }

    @Test
    void J11_get_rid_not_just_of_white_space_also_some_other_unicodes() {
        assertEquals("hello", "\n\t  hello   \u2005".strip());
        assertEquals("hello   \u2005", "\n\t  hello   \u2005".trim());
    }

    @Test
    void J11_validate_if_the_string_is_empty_or_contains_whitespaces() {
        assertTrue("\n\t\u2005  ".isBlank());
    }

    @Test
    void J11_split_lines_from_an_string() {
        String multilineStr = "This is\n \n a multiline\n string.";

        long lineCount = multilineStr.lines()
                .count();

        assertEquals(4L, lineCount);
    }
}
