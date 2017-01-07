package be.gerard.calculation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

/**
 * Value
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RequiredArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class Value {

    private final BigDecimal value;

    private final Component component;

    private final Unit[] unit;

    public interface Component {}

    @FunctionalInterface
    public interface Unit {

        Unit PERCENT = () -> "%";

        String getName();

        default String getDescription() {
            return "";
        }

    }

}
