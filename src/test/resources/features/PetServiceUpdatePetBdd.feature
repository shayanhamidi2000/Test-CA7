Feature: Pet Service Behavior Driven Development Tests focusing on savePet updating pets functionality

  Background: Sample General Preconditions Explanation
    Given Add predefined pet type "dog"
    Given There is a pet owner called with first name "Hassan" and last name "Kachal"
    Given There is already a pet saved with type "dog" and id 1 and name "Lily" and birth date "2020-09-09" and owner with full name "Hassan" "Kachal"

  Scenario: Changing pet owner
    Given There is a pet owner called with first name "Mammad" and last name "Chakhan"
    When We change pet with id 1 owner to owner with first name "Mammad" and last name "Chakhan"
    Then The change on pet with id 1 for new owner with first name "Mammad" and last name "Chakhan" must be applied

  Scenario: Changing pet type
    Given Add predefined pet type "cat"
    When We change pet with id 1 pet type to pet type "cat"
    Then The change on pet with id 1 for new pet type "cat" must be applied

  Scenario: Changing pet's birth date
    When We change pet with id 1 birth date to "2021-01-01"
    Then The change on pet with id 1 for new birth date "2021-01-01" must be applied

  Scenario: Changing pet's name
    When We change pet with id 1 name to "Kiki"
    Then The change on pet with id 1 for new name "Kiki" must be applied
