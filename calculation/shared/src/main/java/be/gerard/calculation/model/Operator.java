package be.gerard.calculation.model;

import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 * Operator
 *
 * @author bartgerard
 * @version v0.0.1
 */
@FunctionalInterface
public interface Operator {

    int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    int PRECISION = 5;

    BiFunction<BigDecimal, BigDecimal, BigDecimal> function();

    interface Basic extends Operator {
        Basic ADD = () -> BigDecimal::add;
        Basic SUBTRACT = () -> BigDecimal::subtract;
        Basic MULTIPLY = () -> BigDecimal::multiply;
        Basic DIVIDE = () -> (x, y) -> x.divide(y, PRECISION, ROUNDING_MODE);
    }

    interface Proportional extends Operator {
        Basic ADD = () -> (x, y) -> BigDecimal.valueOf(100)
                                              .add(y)
                                              .multiply(x)
                                              .multiply(BigDecimal.valueOf(0.01));
        Basic SUBTRACT = () -> (x, y) -> ADD.function()
                                            .apply(x, y.negate());
        Basic MULTIPLY = () -> (x, y) -> x.multiply(y)
                                          .multiply(BigDecimal.valueOf(1, 2));
        Basic DIVIDE = () -> (x, y) -> x.multiply(BigDecimal.valueOf(1, -2))
                                        .divide(y, PRECISION, ROUNDING_MODE);

        Basic INVERSE_ADD = () -> (x, y) -> x.multiply(BigDecimal.valueOf(100))
                                             .divide(BigDecimal.valueOf(100)
                                                               .add(y), PRECISION, ROUNDING_MODE);
        Basic INVERSE_SUBTRACT = () -> (x, y) -> INVERSE_ADD.function()
                                                            .apply(x, y.negate());
    }

}
