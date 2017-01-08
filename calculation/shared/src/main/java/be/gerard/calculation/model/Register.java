package be.gerard.calculation.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Register
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class Register<T extends Register.Registrable> {

    private static final String SEPARATOR = "/";

    private final IntFunction<T[]> generator;

    private final Map<String, T> VALUES = new HashMap<>();

    public Register(
            final IntFunction<T[]> generator,
            final T[]... values
    ) {
        this.generator = generator;

        for (T[] value : values) {
            register(value);
        }
    }

    private void register(final T... values) {
        for (T value : values) {
            VALUES.put(value.group() + value.name(), value);
        }
    }

    private T toUnit(final String value) {
        return VALUES.get(value);
    }

    public T[] toUnits(final String valuesAsString) {
        final String[] valueAsString = valuesAsString.split(SEPARATOR);
        final T[] values = generator.apply(valueAsString.length);

        for (int i = 0; i < valueAsString.length; i++) {
            values[i] = toUnit(valueAsString[i]);
        }

        if (values[0] == null) {
            return generator.apply(0);
        }

        return values;
    }

    @FunctionalInterface
    public interface Registrable {

        String name();

        default String group() {return "";}

    }

}
