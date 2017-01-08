package features.equation.steps;

import be.gerard.calculation.model.Currency;
import be.gerard.calculation.model.Equation;
import be.gerard.calculation.model.Register;
import be.gerard.calculation.model.Term;
import be.gerard.calculation.model.Value;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.NoArgsConstructor;
import org.assertj.core.data.Offset;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EquationSteps
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class EquationSteps {

    private static final Register<Value.Unit> UNITS = new Register<>(Value.Unit[]::new, Currency.values());

    private Term x, y;

    private Equation equation;

    private Equation.Mode mode = Equation.Mode.ONE_TO_ONE;

    @Given("the following x values")
    public void x_values(final List<Input> data) {
        x = Term.of(data.stream()
                        .map(Input::toValue)
                        .toArray(Value[]::new));
    }

    @When("applying the following y values")
    public void y_values(final List<Input> data) {
        y = Term.of(data.stream()
                        .map(Input::toValue)
                        .toArray(Value[]::new));
    }

    @When("using the (.+) equation")
    public void operator(final Equation.Basic equation) {
        this.equation = equation;
    }

    @When("using the proportional (.+) equation")
    public void proportional_operator(final Equation.Basic equation) {
        this.equation = equation.proportional();
    }

    @Then("I expect the following out values")
    public void out_values(final List<Input> data) {
        final Term out = Term.of(data.stream()
                                     .map(Input::toValue)
                                     .toArray(Value[]::new));

        final Term[] terms = Term.terms(x, y);
        equation.execute(terms, new Value.Component[3], mode);

        for (int i = 0; i < terms[Term.OUT].getValues().length; i++) {
            final Value expectedValue = out.getValues()[i];
            final Value value = terms[Term.OUT].getValues()[i];
            assertThat(value.getValue()).isCloseTo(expectedValue.getValue(), Offset.offset(BigDecimal.valueOf(1, 5)));
            assertThat(value.getUnit()).isEqualTo(expectedValue.getUnit());
        }
    }

    @NoArgsConstructor
    public static class Input {
        private BigDecimal value;
        private String units;

        Value toValue() {
            return Value.builder()
                        .value(value)
                        .unit(UNITS.toUnits(units))
                        .build();
        }

    }

}
