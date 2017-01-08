package be.gerard.calculation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
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

    default boolean isRate() {return false;}

    default boolean isRateInverse() {return false;}

    default boolean hasInverse() {return Objects.nonNull(getInverse());}

    default Equation getInverse() {return null;}

    default Function<String[], String> getDescription() {return values -> "";}

    default Equation proportional() {return null;}

    default String asDescription(final String[] values) {return getDescription().apply(values);}

    default Value.Unit[] unit(
            final Value.Unit[] xUnits,
            final Value.Unit[] yUnits
    ) {
        final Value.Unit[] outUnits = Arrays.copyOf(xUnits, xUnits.length);

        if (isRate() && yUnits.length == 2) {
            if (isRateInverse()) {
                Assert.isTrue(xUnits[Term.X] == yUnits[Term.X], "Do not compare apples with pears! :-)");
                outUnits[Term.X] = yUnits[Term.Y];
            } else {
                Assert.isTrue(xUnits[Term.X] == yUnits[Term.Y], "Do not compare apples with pears! :-)");
                outUnits[Term.X] = yUnits[Term.X];
            }
        }

        return outUnits;
    }

    default void execute(
            final Term[] terms,
            final Value.Component[] components,
            final Mode mode
    ) {
        Assert.notNull(mode, "mode is invalid");

        if (terms[Term.Y].getValues().length == 0) {
            return;
        }

        if (terms[Term.X].getValues().length > 0) {
            mode.prepare(terms);

            final Value[] xs = terms[Term.X].getValues();
            final Value[] ys = terms[Term.Y].getValues();
            final Value[] outs = terms[Term.OUT].getValues();

            for (int i = 0; i < xs.length; i++) {
                final Value x = xs[i];

                for (int j = 0; j < xs.length; j++) {
                    final Value y = ys[j];

                    final BigDecimal value = getOperator().function()
                                                          .apply(x.getValue(), y.getValue());

                    outs[i * xs.length + j] = Value.builder()
                                                   .value(value)
                                                   .unit(unit(x.getUnit(), y.getUnit()))
                                                   .component(components[Term.OUT])
                                                   .build();
                }
            }
        } else if (terms[Term.OUT].getValues().length > 0) {
            getInverse().execute(Term.inverse(terms), Term.inverse(components), mode.getInverse());
        }
    }

    @RequiredArgsConstructor(access = PRIVATE)
    enum Mode {
        ONE_TO_ONE() {
            @Override
            protected boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length == terms[Term.OUT].getValues().length;
            }

            @Override
            public void prepare(final Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[terms[Term.X].getValues().length]);
            }
        },
        ONE_TO_MANY() {
            @Override
            protected boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length == 1 && terms[Term.Y].getValues().length >= 1
                        && terms[Term.OUT].getValues().length >= 1;
            }

            @Override
            public void prepare(final Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[terms[Term.Y].getValues().length]);
            }
        },
        MANY_TO_ONE() {
            @Override
            protected boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length >= 1 && terms[Term.OUT].getValues().length == 1;
            }

            @Override
            public void prepare(final Term[] terms) {
                terms[Term.OUT] = Term.of(new Value[1]);
            }
        };

        private static final Mode[] INVERSE = new Mode[]{
                ONE_TO_ONE,
                MANY_TO_ONE,
                ONE_TO_MANY
        };

        public boolean isValid(final Term[] terms) {
            return terms.length == Term.Type.values().length && isValidSpecific(terms);
        }

        protected abstract boolean isValidSpecific(final Term[] terms);

        public abstract void prepare(final Term[] terms);

        private Mode getInverse() {
            return INVERSE[ordinal()];
        }

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
        ADD(Operator.Basic.ADD, false),
        SUBTRACT(Operator.Basic.SUBTRACT, false),
        MULTIPLY(Operator.Basic.MULTIPLY, true),
        DIVIDE(Operator.Basic.DIVIDE, true) {
            @Override
            public boolean isRateInverse() {
                return true;
            }
        };

        private static final Basic[] INVERSE = new Basic[]{
                SUBTRACT,
                ADD,
                DIVIDE,
                MULTIPLY
        };

        private final Operator operator;

        private final boolean rate;

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
