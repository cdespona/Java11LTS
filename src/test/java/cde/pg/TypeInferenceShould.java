package cde.pg;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TypeInferenceShould {

    @Test
    void J10_infere_the_type_based_on_the_input() {
        var name = "David";
        assertEquals(String.class, name.getClass());
    }

    @Test
    void J10_infere_implementation_over_interface() {
        var list = new ArrayList<>();
        assertEquals(ArrayList.class, list.getClass());
    }

    @Test
    void J10_infere_anonymous_not_real_object() {
        var obj = new Object() {
        };
        assertNotEquals(obj.getClass(), Object.class);
        //obj = new Object(); // This is going to blow
    }

    @Test
    void J10_infere_what() {
        var something = System.in;

        var another_thing = IntStream.of(1, 2, 3)
                .asDoubleStream()
                .map(Objects::hashCode)
                .reduce((left, right) -> left)
                .orElseThrow(RuntimeException::new);
    }

    @Test
    void J10_let_me_know_var_is_not_a_key_word() {
        var var = "SomeVariable";
        //Var is not a key word, this maintains backwards compatibility
    }

    @Test
    void J10_WOW_lets_travel_to_the_past_with_var() {
        for (var i = 1; i < 3 ; i++){
            System.out.println(i);
        }
    }

    @Test
    void J10_not_let_us_use_it_wrongly() {
        //var notInitialized; //Cannot infere type

        //var nullValue = null; //Cannot infere type

        //var lambda = (String s) -> s.length() > 10; //lambda needs explicit type

        //var array =  {1, 2, 3}; //Array needs explicit type

        //var method = System.out::println; //explicit type
    }

    @Test
    void J11_type_inference_on_lambda() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Function<String, String> shouter = (@NotNull(message = "I said it cannot be null") var sentence) -> {
            validator.validate(sentence);
            return sentence + "!!";
        };

        Consumer<String> whatever = System.out::println;
        assertEquals("Hello!!", shouter.apply("Hello"));
        assertThrows(IllegalArgumentException.class, () -> shouter.apply(null), "I said it cannot be null");
    }

    @Test
    void J11_type_inference_on_lambda_fail_IDE() {
        Consumer<String> whatever = (@Deprecated var string) -> System.out.println(string);
//        Consumer<String> whatever = System.out::println;
    }

    @Test
    void J11_things_not_working() {
        //Function<String, String> function = (var s, x) -> s.concat(x);
        //Function<String, String> function = (var s, String x) -> s.concat(x);
        //Consumer<List> function = var s -> s.clear();
    }
}
