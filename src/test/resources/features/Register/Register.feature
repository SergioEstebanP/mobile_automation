@register
Feature: As an android user I want to load the Wallbox application and register myself as a user

  @android @run
  Scenario Outline: User register in the system Successfully
    Given user opens the app
    And taps on register button and see the register window
    And types the name "<name>"
    And types the surname "<surname>"
    And types the email "<email>"
    And types the password "<password>"
    And types the repeated password "<repeatedPassword>"
    And checks the terms and conditions checkbox
    And tap on accepts button

    Examples:
      | name    | surname | email | password    | repeatedPassword |
      | Testing | Bot     | valid | Testing1234 | Testing1234      |
