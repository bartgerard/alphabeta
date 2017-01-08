package be.gerard.calculation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

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
            final Value.Unit xUnitNominator = Value.Unit.nominator(xUnits);
            final Value.Unit yUnitDenominator = Value.Unit.denominator(yUnits, isRateInverse());
            Assert.isTrue(xUnitNominator == yUnitDenominator, "units did not match [x=" + Value.Unit.asText(xUnits) + ", y=" + Value.Unit.asText(yUnits, isRateInverse()) + "]");

            outUnits[Term.X] = Value.Unit.nominator(yUnits, isRateInverse());
        }

        return outUnits;
    }

    default void calculate(
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

            mode.calculate(this, xs, ys, outs, components[Term.OUT]);
        } else if (terms[Term.OUT].getValues().length > 0) {
            getInverse().calculate(Term.inverse(terms), Term.inverse(components), mode.getInverse());
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
                terms[Term.OUT] = Term.of(GENERATOR.apply(terms[Term.X].getValues().length));
            }

            @Override
            void calculate(
                    final Equation equation,
                    final Value[] xs,
                    final Value[] ys,
                    final Value[] outs,
                    final Value.Component component
            ) {
                for (int i = 0; i < xs.length; i++) {
                    final Value x = xs[i];
                    final Value y = ys[ys.length < 2 ? 0 : i];

                    outs[i] = calculate(equation, x, y, component);
                }
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
                terms[Term.OUT] = Term.of(GENERATOR.apply(terms[Term.Y].getValues().length));
            }

            @Override
            void calculate(
                    final Equation equation,
                    final Value[] xs,
                    final Value[] ys,
                    final Value[] outs,
                    final Value.Component component
            ) {
                for (int i = 0; i < xs.length; i++) {
                    final Value x = xs[i];

                    for (int j = 0; j < ys.length; j++) {
                        final Value y = ys[j];

                        outs[i * xs.length + j] = calculate(equation, x, y, component);
                    }
                }
            }
        },
        MANY_TO_ONE() {
            @Override
            protected boolean isValidSpecific(final Term[] terms) {
                return terms[Term.X].getValues().length >= 1 && terms[Term.OUT].getValues().length == 1;
            }

            @Override
            public void prepare(final Term[] terms) {
                terms[Term.OUT] = Term.of(GENERATOR.apply(1));
            }

            @Override
            void calculate(
                    final Equation equation,
                    final Value[] xs,
                    final Value[] ys,
                    final Value[] outs,
                    final Value.Component component
            ) {
                outs[0] = calculate(equation, xs[0], ys[0], component);
            }
        };

        private static final IntFunction<Value[]> GENERATOR = Value[]::new;

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

        abstract void calculate(
                final Equation equation,
                final Value[] xs,
                final Value[] ys,
                final Value[] outs,
                final Value.Component component
        );

        static Value calculate(
                final Equation equation,
                final Value x,
                final Value y,
                final Value.Component component
        ) {
            final BigDecimal value = equation.getOperator()
                                             .function()
                                             .apply(x.getValue(), y.getValue());

            return Value.builder()
                        .value(value)
                        .unit(equation.unit(x.getUnit(), y.getUnit()))
                        .component(component)
                        .build();
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
