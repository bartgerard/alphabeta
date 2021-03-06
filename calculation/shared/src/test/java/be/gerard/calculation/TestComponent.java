package be.gerard.calculation;

import be.gerard.calculation.model.Value;

/**
 * TestComponent
 *
 * @author bartgerard
 * @version v0.0.1
 */
public enum TestComponent implements Value.Component {
    PRODUCTION_COST,
    SHIPPING_COST,
    MARGIN,
    MSRP, // Merchant Suggested Retail Price
    EXCHANGE_RATE;
}
