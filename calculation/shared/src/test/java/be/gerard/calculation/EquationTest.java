package be.gerard.calculation;

import be.gerard.calculation.model.Equation;
import be.gerard.calculation.model.Term;
import be.gerard.calculation.model.Value;
import org.junit.Test;

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

}
