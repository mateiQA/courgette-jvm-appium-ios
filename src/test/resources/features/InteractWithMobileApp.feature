
Feature: Test iOS application

  @ios
  Scenario: Verify application alert on device
    Given I show the alert
    Then I verify the alert shows this alert is so cool.
    And I accept the alert