package be.gerard.calculation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

/**
 * Equation
 *
 * @author bartgerard
 * @version v0.0.1
 */
@FunctionalInterface
public interface Equation {

    Operator getOperator();

    default boolean hasInverse() {return Objects.nonNull(getInverse());}

    default Equation getInverse() {return null;}

    default Function<String[], String> getDescription() {return values -> "";}

    default Equation proportional() {return null;}

    default String asDescription(final String[] values) {return getDescription().apply(values);}

    @RequiredArgsConstructor(access = PRIVATE)
    enum Mode {
        ONE_TO_ONE() {
            @Override
            boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length == terms[Term.OUT].getValues().length;
            }

            @Override
            public void prepare(Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[terms[Term.X].getValues().length]);
            }
        },
        ONE_TO_MANY() {
            @Override
            boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length == 1 && terms[Term.Y].getValues().length >= 1
                        && terms[Term.OUT].getValues().length >= 1;
            }

            @Override
            public void prepare(Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[terms[Term.Y].getValues().length]);
            }
        },
        MANY_TO_ONE() {
            @Override
            boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length >= 1 && terms[Term.OUT].getValues().length == 1;
            }

            @Override
            public void prepare(Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[1]);
            }
        };

        void execute(final BigDecimal[][] values) {

        }

        public boolean isValid(final Term[] terms) {
            return terms.length == Term.Type.values().length && isValidSpecific(terms);
        }

        abstract boolean isValidSpecific(final Term[] terms);

        public abstract void prepare(final Term[] terms);

    }

    @RequiredArgsConstructor(access = PRIVATE)
    @Getter
    enum Description {
        ADD(values -> values[Term.X] + " + " + values[Term.Y] + " = " + values[Term.OUT]),
        SUBTRACT(values -> values[Term.X] + " - " + values[Term.Y] + " = " + values[Term.OUT]),
        MULTIPLY(values -> values[Term.X] + " * " + values[Term.Y] + " = " + values[Term.OUT]),
        DIVIDE(values -> values[Term.X] + " / " + values[Term.Y] + " = " + values[Term.OUT]);

        private final Function<String[], String> description;
    }

    @RequiredArgsConstructor(access = PRIVATE)
    @Getter
    enum Basic implements Equation {
        ADD(Operator.Basic.ADD),
        SUBTRACT(Operator.Basic.SUBTRACT),
        MULTIPLY(Operator.Basic.MULTIPLY),
        DIVIDE(Operator.Basic.DIVIDE);

        private static final Basic[] INVERSE = new Basic[]{
                SUBTRACT,
                ADD,
                DIVIDE,
                MULTIPLY
        };

        private final Operator operator;

        @Override
        public Equation getInverse() {
            return INVERSE[ordinal()];
        }

        @Override
        public Function<String[], String> getDescription() {
            return Description.values()[ordinal()].getDescription();
        }

        @Override
        public Equation proportional() {
            return Proportional.values()[ordinal()];
        }

    }

    @RequiredArgsConstructor(access = PRIVATE)
    @Getter
    enum Proportional implements Equation {
        ADD(Operator.Proportional.ADD),
        SUBTRACT(Operator.Proportional.SUBTRACT),
        MULTIPLY(Operator.Proportional.MULTIPLY),
        DIVIDE(Operator.Proportional.DIVIDE),

        INVERSE_ADD(Operator.Proportional.INVERSE_ADD) {
            @Override
            public Function<String[], String> getDescription() {
                return values -> values[Term.X] + " / (1 + " + values[Term.Y] + ") = " + values[Term.OUT];
            }

        },
        INVERSE_SUBTRACT(Operator.Proportional.INVERSE_SUBTRACT) {
            @Override
            public Function<String[], String> getDescription() {
                return values -> values[Term.X] + " / (1 - " + values[Term.Y] + ") = " + values[Term.OUT];
            }

        };

        private static final Proportional[] INVERSE = new Proportional[]{
                INVERSE_ADD,
                INVERSE_SUBTRACT,
                DIVIDE,
                MULTIPLY,

                ADD,
                SUBTRACT
        };

        private final Operator operator;

        @Override
        public Equation getInverse() {
            return INVERSE[ordinal()];
        }

        @Override
        public Function<String[], String> getDescription() {
            return Description.values()[ordinal()].getDescription();
        }

    }

}
