@version:Release-1
@fast
Feature: Using equations
  In order to calculate different value components
  As the programmer
  I want a powerful calculation engine

  Scenario: Addition
    Given the following x values
      | value | units | component       |
      | 10    | EUR   | PRODUCTION_COST |
    When applying the following y values
      | value | units | component     |
      | 10    | EUR   | SHIPPING_COST |
      | 90    | EUR   | SHIPPING_COST |
    And using the default ADD equation
    Then I expect the following out values
      | value | units |
      | 20    | EUR   |
      | 100   | EUR   |

  Scenario: Multiplication
    Given the following x values
      | value | units | component |
      | 10    | EUR   | MSRP      |
    When applying the following y values
      | value | units   | component     |
      | 1000  | JPY/EUR | EXCHANGE_RATE |
      | 10    | GBP/EUR | EXCHANGE_RATE |
    And using the default MULTIPLY equation
    Then I expect the following out values
      | value | units |
      | 10000 | JPY   |
      | 100   | GBP   |

  Scenario: Division
    Given the following x values
      | value | units | component |
      | 100   | JPY   | MSRP      |
    When applying the following y values
      | value | units   | component     |
      | 10    | JPY/EUR | EXCHANGE_RATE |
    And using the default DIVIDE equation
    Then I expect the following out values
      | value | units |
      | 10    | EUR   |

  Scenario: Multiplication with incorrect units
    Given the following x values
      | value | units | component |
      | 10    | EUR   | MSRP      |
    When applying the following y values
      | value | units   | component     |
      | 10    | EUR/JPY | EXCHANGE_RATE |
    And using the default MULTIPLY equation
    Then I expect the following exception message: units did not match [x=EUR, y=EUR/JPY]

  Scenario: Division with incorrect units
    Given the following x values
      | value | units | component |
      | 10    | JPY   | MSRP      |
    When applying the following y values
      | value | units   | component     |
      | 10    | EUR/JPY | EXCHANGE_RATE |
    And using the default DIVIDE equation
    Then I expect the following exception message: units did not match [x=JPY, y=JPY/EUR]

  Scenario: Proportional addition
    Given the following x values
      | value | units | component       |
      | 10    | EUR   | PRODUCTION_COST |
    When applying the following y values
      | value | units | component |
      | 10    |       |           |
    And using the proportional ADD equation
    Then I expect the following out values
      | value | units |
      | 11    | EUR   |

  Scenario: Proportional multiplication
    Given the following x values
      | value | units | component       |
      | 10    | EUR   | PRODUCTION_COST |
    When applying the following y values
      | value | units | component |
      | 10    |       |           |
    And using the proportional MULTIPLY equation
    Then I expect the following out values
      | value | units |
      | 1     | EUR   |

  Scenario: Proportional division
    Given the following x values
      | value | units | component       |
      | 10    | EUR   | PRODUCTION_COST |
    When applying the following y values
      | value | units | component |
      | 10    |       |           |
    And using the proportional DIVIDE equation
    Then I expect the following out values
      | value | units |
      | 100   | EUR   |