package be.gerard.calculation;

import be.gerard.calculation.model.Equation;
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

}
