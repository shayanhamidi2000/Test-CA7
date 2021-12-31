package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetServiceFeaturesSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Map<String, Owner> owners = new HashMap<>();
	private Map<String, PetType> petTypes = new HashMap<>();
	private Owner defaultOwner;
	// for adding scenarios
	private PetType petType;
	private Owner owner;

	@Given("There is already a sample pet owner")
	public void thereIsAlreadyAPetOwner() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Mario");
		owner.setLastName("Maximiliano");
		owner.setAddress("andro - rickardo St.");
		owner.setCity("Rome");
		owner.setTelephone("9125459785");
		ownerRepository.save(owner);
	}

	@Given("There is some predefined pet types like {string}")
	public void thereIsSomePredefinedPetTypesLike(String petTypeName) {
		petType = new PetType();
		petType.setName(petTypeName);
		petTypeRepository.save(petType);
	}

	@When("He performs save pet service to add first pet with id {int} to his list")
	public void hePerformsSavePetService(int id) {
		Pet pet = new Pet();
		pet.setId(id);
		pet.setType(petType);
		petService.savePet(pet, owner);
	}

	@When("He performs save pet service to add second pet with id {int} to his list")
	public void hePerformsSavePetServiceForTheSecondTime(int id) {
		Pet pet = new Pet();
		pet.setId(id);
		pet.setType(petType);
		petService.savePet(pet, owner);
	}

	private boolean thePetIsSetCorrectlyCustomAssertion(Pet pet, int id){
		if(pet == null)
			return false;
		if(pet.getId() == id && pet.getOwner().getId().equals(owner.getId()))
			return true;
		return false;
	}

	@Then("A pet is saved successfully with id {int}")
	public void onePetIsSaved(int id) {
		Pet foundPet =  petService.findPet(id);
		assertTrue(thePetIsSetCorrectlyCustomAssertion(foundPet, id), "Inserting one pet was unsuccessful");
	}

	@Then("Two pets are saved successfully with ids {int} and {int}")
	public void twoPetsAreSaved(int firstId, int secondId) {
		Pet firstPet =  petService.findPet(firstId);
		Pet secondPet =  petService.findPet(secondId);
		assertTrue(thePetIsSetCorrectlyCustomAssertion(firstPet, firstId) && thePetIsSetCorrectlyCustomAssertion(secondPet, secondId),
			"Two Pets insertion was unsuccessful");
	}

	// For Update Scenarios
	@Given("There is already a pet saved with type {string} and id {int} and name {string} and birth date {string} and owner with full name {string} {string}")
	public void thereIsSomePredefinedPetWithSpecifiedTypeAndFullName(String petTypeName, int id, String name, String birthDate, String firstName, String lastName){
		Pet thePet = new Pet();
		thePet.setId(id);
		thePet.setName(name);
		thePet.setBirthDate(LocalDate.parse(birthDate));
		thePet.setType(petTypes.get(petTypeName));
		petService.savePet(thePet, owners.get(firstName + lastName));
	}

	@Given("There is a pet owner called with first name {string} and last name {string}")
	public void thereIsAPetOwnerCalled(String firstName, String lastName) {
		Owner newOwner = new Owner();
		newOwner.setFirstName(firstName);
		newOwner.setLastName(lastName);
		newOwner.setAddress("andro - rickardo St.");
		newOwner.setCity("Rome");
		newOwner.setTelephone("9125459785");
		owners.put(firstName + lastName, newOwner);
		ownerRepository.save(newOwner);
		defaultOwner = newOwner;
	}

	@Given("Add predefined pet type {string}")
	public void AddPredefinedPetType(String petTypeName) {
		PetType newPetType = new PetType();
		newPetType.setName(petTypeName);
		petTypes.put(petTypeName, newPetType);
		petTypeRepository.save(newPetType);
	}

	@When("We change pet with id {int} owner to owner with first name {string} and last name {string}")
	public void changeOwner(int petId, String firstName, String lastName){
		petService.savePet(petService.findPet(petId), owners.get(firstName+lastName));
	}

	@When("We change pet with id {int} pet type to pet type {string}")
	public void changePetType(int petId, String newPetType){
		Pet pet = petService.findPet(petId);
		pet.setType(petTypes.get(newPetType));
		petService.savePet(pet, defaultOwner);
	}

	@When("We change pet with id {int} birth date to {string}")
	public void changeBirthDate(int petId, String newBirthDate){
		Pet pet = petService.findPet(petId);
		pet.setBirthDate(LocalDate.parse(newBirthDate));
		petService.savePet(pet, defaultOwner);
	}

	@Then("The change on pet with id {int} for new owner with first name {string} and last name {string} must be applied")
	public void changeOwnerSuccessful(int petId, String firstName, String lastName){
		assertEquals(petService.findPet(petId).getOwner(), owners.get(firstName+lastName));
	}

	@Then("The change on pet with id {int} for new pet type {string} must be applied")
	public void changePetTypeSuccessful(int petId, String newPetType){
		assertEquals(petService.findPet(petId).getType(), petTypes.get(newPetType));
	}

	@Then("The change on pet with id {int} for new birth date {string} must be applied")
	public void changeBirthDateSuccessful(int petId, String newBirthDate){
		assertEquals(petService.findPet(petId).getBirthDate(), LocalDate.parse(newBirthDate));
	}

	@When("We change pet with id {int} name to {string}")
	public void changeName(int petId, String newName){
		Pet pet = petService.findPet(petId);
		pet.setName(newName);
		petService.savePet(pet, defaultOwner);
	}

	@Then("The change on pet with id {int} for new name {string} must be applied")
	public void changeNameSuccessful(int petId, String newName){
		assertEquals(petService.findPet(petId).getName(), newName);
	}
}
