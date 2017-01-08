package be.gerard.calculation.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Value {

    private final BigDecimal value;

    private final Component component;

    private final Unit[] unit;

    @FunctionalInterface
    public interface Component extends Register.Registrable {}

    @FunctionalInterface
    public interface Unit extends Register.Registrable {

        Unit PERCENT = () -> "%";

        default String getDescription() {return "";}

    }

}
