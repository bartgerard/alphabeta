package features.equation.steps;

import be.gerard.calculation.TestComponent;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * EquationSteps
 *
 * @author bartgerard
 * @version v0.0.1
 */
public class EquationSteps {

    private static final Register<Value.Unit> UNITS = new Register<>(Value.Unit[]::new, Currency.values());
    private static final Register<Value.Component> COMPONENTS = new Register<>(Value.Component[]::new, TestComponent.values());

    private Term x, y;

    private Equation equation;

    private Equation.Mode mode;

    @Given("the following x values")
    public void x_values(final List<Input> data) {
        x = toTerm(data);
    }

    @When("applying the following y values")
    public void y_values(final List<Input> data) {
        y = toTerm(data);
    }

    @When("using the default (.+) equation")
    public void operator(final Equation.Basic equation) {
        this.equation = equation;
    }

    @When("using the proportional (.+) equation")
    public void proportional_operator(final Equation.Basic equation) {
        this.equation = equation.proportional();
    }

    @When("in mode (.+)")
    public void mode(final Equation.Mode mode) {
        this.mode = mode;
    }

    @Then("I expect the following out values")
    public void out_values(final List<Input> data) {
        final Term out = toTerm(data);

        final Term[] terms = Term.terms(x, y);
        equation.calculate(terms, new Value.Component[3], mode);

        for (int i = 0; i < terms[Term.OUT].getValues().length; i++) {
            final Value expectedValue = out.getValues()[i];
            final Value value = terms[Term.OUT].getValues()[i];
            assertThat(value.getValue()).isCloseTo(expectedValue.getValue(), Offset.offset(BigDecimal.valueOf(1, 5)));
            assertThat(value.getUnit()).isEqualTo(expectedValue.getUnit());
            assertThat(value.getComponent()).isEqualTo(expectedValue.getComponent());
        }
    }

    @Then("I expect the following exception message: (.+)")
    public void crash(final String message) {
        final Term[] terms = Term.terms(x, y);
        assertThatThrownBy(() -> equation.calculate(terms, new Value.Component[3], mode)).hasMessage(message);
    }

    private Term toTerm(List<Input> data) {
        return Term.of(data.stream()
                           .map(Input::toValue)
                           .toArray(Value[]::new));
    }

    @NoArgsConstructor
    public static class Input {
        private BigDecimal value;
        private String units;
        private String component;

        Value toValue() {
            return Value.builder()
                        .value(value)
                        .unit(UNITS.toValues(units))
                        .component(COMPONENTS.toValue(component))
                        .build();
        }

    }

}
