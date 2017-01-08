package be.gerard.calculation;

import be.gerard.calculation.model.Currency;
import be.gerard.calculation.model.Equation;
import be.gerard.calculation.model.Term;
import be.gerard.calculation.model.Value;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EquationTest
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class EquationTest {

    @Test
    public void basic_test() {
        assertThat(Equation.Basic.ADD.getInverse()).isEqualTo(Equation.Basic.SUBTRACT);
        assertThat(Equation.Basic.SUBTRACT.getInverse()).isEqualTo(Equation.Basic.ADD);
        assertThat(Equation.Basic.MULTIPLY.getInverse()).isEqualTo(Equation.Basic.DIVIDE);
        assertThat(Equation.Basic.DIVIDE.getInverse()).isEqualTo(Equation.Basic.MULTIPLY);
    }

    @Test
    public void proportional_test() {
        assertThat(Equation.Basic.ADD.proportional()
                                     .getInverse()).isEqualTo(Equation.Proportional.INVERSE_ADD);
        assertThat(Equation.Basic.SUBTRACT.proportional()
                                          .getInverse()).isEqualTo(Equation.Proportional.INVERSE_SUBTRACT);
        assertThat(Equation.Basic.MULTIPLY.proportional()
                                          .getInverse()).isEqualTo(Equation.Proportional.DIVIDE);
        assertThat(Equation.Basic.DIVIDE.proportional()
                                        .getInverse()).isEqualTo(Equation.Proportional.MULTIPLY);
    }

    @Test
    public void description_test() {
        final String[] values = new String[]{"x", "y", "z"};

        assertThat(Equation.Basic.ADD.asDescription(values)).isEqualTo("x + y = z");
        assertThat(Equation.Basic.SUBTRACT.asDescription(values)).isEqualTo("x - y = z");
        assertThat(Equation.Basic.MULTIPLY.asDescription(values)).isEqualTo("x * y = z");
        assertThat(Equation.Basic.DIVIDE.asDescription(values)).isEqualTo("x / y = z");

        assertThat(Equation.Basic.ADD.proportional()
                                     .asDescription(values)).isEqualTo("x + y = z");
        assertThat(Equation.Basic.SUBTRACT.proportional()
                                          .asDescription(values)).isEqualTo("x - y = z");
        assertThat(Equation.Basic.MULTIPLY.proportional()
                                          .asDescription(values)).isEqualTo("x * y = z");
        assertThat(Equation.Basic.DIVIDE.proportional()
                                        .asDescription(values)).isEqualTo("x / y = z");

        assertThat(Equation.Basic.ADD.proportional()
                                     .getInverse()
                                     .asDescription(values)).isEqualTo("x / (1 + y) = z");
        assertThat(Equation.Basic.SUBTRACT.proportional()
                                          .getInverse()
                                          .asDescription(values)).isEqualTo("x / (1 - y) = z");
    }

    @Test
    public void mode_test() {
        final Term[] terms1 = Term.terms(
                Term.term(
                        Value.builder()
                             .build(),
                        Value.builder()
                             .build()
                ),
                Term.term(
                        Value.builder()
                             .build()
                )
        );

        Equation.Mode.ONE_TO_ONE.prepare(terms1);
        assertThat(terms1[Term.OUT].getValues()).hasSize(2);

        Equation.Mode.ONE_TO_MANY.prepare(terms1);
        assertThat(terms1[Term.OUT].getValues()).hasSize(1);

        Equation.Mode.MANY_TO_ONE.prepare(terms1);
        assertThat(terms1[Term.OUT].getValues()).hasSize(1);

        final Term[] terms2 = Term.terms(
                Term.term(
                        Value.builder()
                             .build()
                ),
                Term.term(
                        Value.builder()
                             .build(),
                        Value.builder()
                             .build()
                )
        );

        Equation.Mode.ONE_TO_ONE.prepare(terms2);
        assertThat(terms2[Term.OUT].getValues()).hasSize(1);

        Equation.Mode.ONE_TO_MANY.prepare(terms2);
        assertThat(terms2[Term.OUT].getValues()).hasSize(2);

        Equation.Mode.MANY_TO_ONE.prepare(terms2);
        assertThat(terms2[Term.OUT].getValues()).hasSize(1);
    }

    @Test
    public void unit_test() { // Tadadum Tss! -> Joke!
        final Term[] terms = Term.terms(
                Term.term(
                        Value.builder()
                             .value(BigDecimal.TEN)
                             .unit(new Value.Unit[]{Currency.EUR})
                             .build()
                ),
                Term.term(
                        Value.builder()
                             .value(BigDecimal.TEN)
                             .unit(new Value.Unit[]{Currency.JPY, Currency.EUR})
                             .build()
                )
        );

        Equation.Basic.MULTIPLY.calculate(terms, new Value.Component[]{TestComponent.MSRP, TestComponent.EXCHANGE_RATE, TestComponent.MSRP}, Equation.Mode.ONE_TO_ONE);

        assertThat(terms[Term.OUT].getValues()[0]).isEqualTo(Value.builder()
                                                                  .value(BigDecimal.valueOf(100L))
                                                                  .unit(new Value.Unit[]{Currency.JPY})
                                                                  .component(TestComponent.MSRP)
                                                                  .build());
    }

}
