package be.gerard.calculation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * Term
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RequiredArgsConstructor(access = PROTECTED, staticName = "of")
@Getter
public class Term {

    public static final int X = Type.X.ordinal();
    public static final int Y = Type.Y.ordinal();
    public static final int OUT = Type.OUT.ordinal();

    private final Value[] values;

    public static Term term(final Value... values) {
        return Term.of(values);
    }

    private static Term[] terms() {
        return new Term[Term.Type.values().length];
    }

    public static Term[] terms(
            final Value[] x,
            final Value[] y
    ) {
        return terms(Term.of(x), Term.of(y));
    }

    public static Term[] terms(
            final Term x,
            final Term y
    ) {
        final Term[] terms = terms();

        terms[X] = x;
        terms[Y] = y;

        return terms;
    }

    public static <T> T[] inverse(final T[] values) {
        final T x = values[X];
        values[X] = values[OUT];
        values[OUT] = x;

        return values;
    }

    /**
     * X (operator) Y = OUT
     */
    public enum Type {
        X,
        Y,
        OUT
    }

}
