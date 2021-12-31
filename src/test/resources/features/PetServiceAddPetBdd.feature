Feature: Pet Service Behavior Driven Development Tests focusing on savePet adding pets functionality

  Background: Initial type and owner are set up
    Given There is some predefined pet types like "dog"
    Given There is already a sample pet owner

  Scenario: Adding one pet and observing results
    When He performs save pet service to add first pet with id 1 to his list
    Then A pet is saved successfully with id 1

  Scenario: Adding Two pets and observing results
    When He performs save pet service to add second pet with id 2 to his list
    Then Two pets are saved successfully with ids 1 and 2
