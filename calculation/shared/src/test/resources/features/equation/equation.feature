@version:Release-1
@fast
Feature: Using equations
  In order to calculate different value components
  As the programmer
  I want a powerful calculation engine

  Scenario: Calculate an equation
    Given the following x values
      | value | units |
      | 10    | EUR   |
    When applying the following y values
      | value | units   |
      | 10    | JPY/EUR |
    And using the MULTIPLY equation
    Then I expect the following out values
      | value | units |
      | 100   | JPY   |

  Scenario: Calculate an equation
    Given the following x values
      | value | units |
      | 100   | JPY   |
    When applying the following y values
      | value | units   |
      | 10    | JPY/EUR |
    And using the DIVIDE equation
    Then I expect the following out values
      | value | units |
      | 10    | EUR   |

  Scenario: Calculate an equation
    Given the following x values
      | value | units |
      | 10    | EUR   |
    When applying the following y values
      | value | units   |
      | 10    | EUR/JPY |
    And using the MULTIPLY equation
    Then I expect the following exception message: units did not match [x=EUR, y=EUR/JPY]

  Scenario: Calculate an equation
    Given the following x values
      | value | units |
      | 10    | JPY   |
    When applying the following y values
      | value | units   |
      | 10    | EUR/JPY |
    And using the DIVIDE equation
    Then I expect the following exception message: units did not match [x=JPY, y=JPY/EUR]