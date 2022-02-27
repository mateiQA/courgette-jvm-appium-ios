Feature: Test iOS application in parallel

  Scenario: Verify application alert on device test one
    Given I show the alert
    Then I verify the alert shows this alert is so cool.
    And I accept the alert

  Scenario: Verify application alert on device test two
    Given I show the alert
    Then I verify the alert shows this alert is so cool.
    And I accept the alert