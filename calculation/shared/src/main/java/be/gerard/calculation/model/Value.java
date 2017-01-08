package be.gerard.calculation.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

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
    public interface Component extends Register.Registrable {

        default int precision() {return 2;}

    }

    @FunctionalInterface
    public interface Unit extends Register.Registrable {

        int NOMINATOR = 0;
        int DENOMINATOR = 1;

        Unit PERCENT = () -> "%";

        default String getDescription() {return "";}

        static Unit nominator(final Unit[] units) {
            return nominator(units, false);
        }

        static Unit nominator(
                final Unit[] units,
                final boolean inverse
        ) {
            return units[inverse ? DENOMINATOR : NOMINATOR];
        }

        static Unit denominator(
                final Unit[] units,
                final boolean inverse
        ) {
            return units[inverse ? NOMINATOR : DENOMINATOR];
        }

        static String asText(final Unit[] units) {
            return asText(units, false);
        }

        static String asText(
                final Unit[] units,
                final boolean inverse
        ) {
            if (units.length == 1) {
                return Objects.toString(units[0]);
            } else if (units.length == 2) {
                return inverse ? units[1] + "/" + units[0] : units[0] + "/" + units[1];
            }

            return "";
        }

    }

}
