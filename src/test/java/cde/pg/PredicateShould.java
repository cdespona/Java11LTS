package cde.pg;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;

public class PredicateShould {

    @Test
    void J11_make_a_lot_cleaner_to_negate() {
        List<String> letters = Stream.of("a", "b", "", "c")
                .filter(string -> !string.isBlank())
                .filter(((Predicate<String>) String::isBlank).negate())
                .filter(not(String::isBlank))
                .collect(Collectors.toUnmodifiableList());

        assertThat(letters).containsExactly("a", "b", "c");
    }

    @Test
    void J11_allow_us_to_use_regexp_as_predicate() {
        var nonWordCharacter = Pattern.compile("\\W");
        var containsNonWordCharacter = Pattern.compile("\\w*\\W\\w*");
        var bandNames = List.of("Metallica", "Motörhead");

        Set<String> findConstainsNonWord = bandNames.stream()
                .filter(containsNonWordCharacter.asPredicate())
                .collect(toUnmodifiableSet());

        assertThat(findConstainsNonWord).containsExactly("Motörhead");

        Set<String> matchContainsNonWord = bandNames.stream()
                .filter(containsNonWordCharacter.asMatchPredicate())
                .collect(toUnmodifiableSet());

        assertThat(matchContainsNonWord).containsExactly("Motörhead");

        Set<String> findNonWord = bandNames.stream()
                .filter(nonWordCharacter.asPredicate())
                .collect(toUnmodifiableSet());

        assertThat(findNonWord).containsExactly("Motörhead");

        Set<String> matchNonWord = bandNames.stream()
                .filter(nonWordCharacter.asMatchPredicate())
                .collect(toUnmodifiableSet());

        assertThat(matchNonWord).isEmpty();
    }
}
