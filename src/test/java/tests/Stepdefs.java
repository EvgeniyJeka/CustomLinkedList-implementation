package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import Lists.CustomLinkedList;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {

	final int DEFAULT_AGE = 39;

	Integer numberStored;
	Person personStored;

	boolean flag;

	CustomLinkedList<Integer> numList;
	CustomLinkedList<Person> personList;

	List<Person> controlList;
	Collection<Person> tempCollection;

	@Given("^List of (\\d+) numbers$")
	public void list_of_numbers(int len) {

		this.numList = new CustomLinkedList<Integer>();
		for (int i = 0; i < len; i++)
			numList.add(i * 10);

	}

	@Given("^List of five Person objects$")
	public void list_of_five_Person_objects() {

		Person Eugene = new Person("Eugene", 34, 1012);
		Person Alex = new Person("Alex", 27, 1330);
		Person Paul = new Person("Paul", 39, 1801);
		Person Yuka = new Person("Yuka", 22, 1337);
		Person Vadim = new Person("Vadim", 45, 12332);

		this.personList = new CustomLinkedList<Person>(Arrays.asList(Eugene, Alex, Paul, Yuka, Vadim));
		this.controlList = new ArrayList<Person>(Arrays.asList(Eugene, Alex, Paul, Yuka, Vadim));

	}

	@When("^Saving the element with index (\\d+) of type (.*)$")
	public void saving_the_number_element_with_index(int index, String type) {
		if (type == "number")
			this.numberStored = this.numList.get(index);
		else if (type == "person")
			this.personStored = this.personList.get(index);

	}

	@Then("^Actual value of the saved element is identical to the (\\d+) (.*) value\\.$")
	public void actual_value_of_the_saved_element_is_identical_to_the_number_value(int expected, String type) {
		if (type == "number")
			assertEquals(Integer.valueOf(expected), this.numberStored);
		else if (type == "person")
			assertTrue(this.personStored.equals(controlList.get(expected)));

	}

	@When("^Adding new person with name \"([^\"]*)\" and ID (\\d+)$")
	public void adding_new_person_with_name_and_ID(String name, int id) {
		Person addik = new Person(name, DEFAULT_AGE, id);
		this.personList.add(addik);

	}

	@Then("^Expected list size is (\\d+)$")
	public void expected_list_size_is(int size) {
		assertEquals(size, this.personList.size());

	}

	@Then("^New element is added to the (\\d+)th place in the list - a person named \"([^\"]*)\" with ID (\\d+)$")
	public void new_element_is_added_to_the_th_place_in_the_list_a_person_named_with_ID(int place, String name,
			int id) {
		Person testAddedPerson = this.personList.get(place - 1);

		assertEquals(testAddedPerson.name, name);
		assertEquals(testAddedPerson.id, id);

	}

	@When("^Adding new person with name \"([^\"]*)\" and ID (\\d+) at index (\\d+)$")
	public void adding_new_person_with_name_and_ID_at_index(String name, int id, int index) {

		Person addedPerson = new Person(name, DEFAULT_AGE, id);
		this.personList.add(index, addedPerson);
		this.controlList.add(index, addedPerson);

	}

	@Then("^No unexpected modifications in list order or list elements$")
	public void no_unexpected_modifications_in_list_order_or_list_elements_following_the_addition_of_new_element() {
		for (int i = 0; i < this.personList.size(); i++)
			assertEquals(this.personList.get(i), this.controlList.get(i));

	}

	@When("^Checking if the list contains a Person named (.*) with ID (\\d+)$")
	public void checking_if_the_list_contains_a_Person_named_Eugene_with_ID(String name, int id) {

		Person testPerson = new Person(name, DEFAULT_AGE, id);

		this.flag = this.personList.contains(testPerson);

	}

	@Then("^Verify tested List method returns (.*)$")
	public void verify_tested_List_method_returns(String expected) {

		if (expected == "true")
			assertTrue(this.flag);

		else if (expected == "false")
			assertFalse(this.flag);

	}
	
	@When("^Checking if the list contains the provided collection$")
	public void checking_if_the_list_contains_the_provided_collection(){
	    
		this.flag = this.personList.containsAll(this.tempCollection);
	}

	@When("^Trying to add a new person with name \"([^\"]*)\" and ID (\\d+) at index (\\d+)$")
	public void trying_to_add_a_new_person_with_name_and_ID_at_index(String name, int id, int index) {

		this.flag = false;

		try {

			Person addedPerson = new Person(name, DEFAULT_AGE, id);
			this.personList.add(index, addedPerson);

		} catch (IndexOutOfBoundsException E) {
			this.flag = true;

		} catch (Exception E) {

			System.out.println("Log: incorrect expection was thrown - " + E.toString());
		}
	}

	@Then("^The correct exception is thrown$")
	public void the_correct_exception_is_thrown() {
		assertTrue(this.flag);
	}

	@When("^Checking for the index of Person named (.*) with ID (\\d+)$")
	public void checking_for_the_index_of_Person_named_Eugene_with_ID(String name, int id) {
		this.numberStored = this.personList.indexOf(new Person(name, DEFAULT_AGE, id));

	}

	@Then("^Getting the correct index (\\d+)$")
	public void getting_the_correct_index(int index) {
		assertTrue(this.numberStored.intValue() == index);
	}

	@When("^Checking for the last index of Person named \"([^\"]*)\" with ID (\\d+)$")
	public void checking_for_the_last_index_of_Person_named_with_ID(String name, int id) {

		this.numberStored = this.personList.lastIndexOf(new Person(name, DEFAULT_AGE, id));

	}

	@Then("^Verifying the last index of the added person equals to the index of the last element$")
	public void verifying_the_last_index_of_the_added_person_equals_to_the_index_of_the_last_element() {
		int lastElementIndex = this.personList.size() - 1;
		assertEquals(this.numberStored.intValue(), lastElementIndex);
	}

	@When("^The element at index (\\d+) is replaced with Person named \"([^\"]*)\" with ID (\\d+)$")
	public void the_element_at_index_is_replaced_with_Person_named_with_ID(int index, String name, int id) {

		Person replacedWith = new Person(name, DEFAULT_AGE, id);

		this.personStored = this.personList.set(index, replacedWith);
		this.controlList.set(index, replacedWith);

	}

	@Then("^The replaced element is returned, it is a Person named \"([^\"]*)\" with ID (\\d+)$")
	public void the_replaced_element_is_returned_it_is_a_Person_named_with_ID(String name, int id) {
		assertEquals(this.personStored.name, name);
		assertEquals(this.personStored.id, id);

	}

	@When("^Removing the element with index (\\d+)$")
	public void removing_the_element_with_index(int index) {
		this.personStored = this.personList.remove(index);
		this.controlList.remove(index);

	}

	@Then("^The list no longer contains the removed Person$")
	public void the_list_no_longer_contains_the_removed_Person() {
		assertFalse(this.personList.contains(this.personStored));
	}

	@Then("^New list size is (\\d+)$")
	public void new_list_size_is(int size) {
		System.out.println("*Size: " + this.personList.size());
		assertTrue(this.personList.size() == size);
	}

	@When("^Trying to remove an element with index (\\d+)$")
	public void trying_to_remove_an_element_with_index(int index) {
		this.flag = false;

		try {

			this.personList.remove(index);

		} catch (IndexOutOfBoundsException E) {
			this.flag = true;

		} catch (Exception E) {

			System.out.println("Log: incorrect expection was thrown - " + E.toString());
		}
	}

	@When("^Removing the person named \"([^\"]*)\" with ID (\\d+) from the list$")
	public void removing_the_person_named_with_ID_from_the_list(String name, int id) {

		Person removedPerson = new Person(name, DEFAULT_AGE, id);
		boolean result = this.personList.remove(removedPerson);
		assertTrue(result);

		this.controlList.remove(removedPerson);
	}

	@Given("^Secondary list that contains (\\d+) persons from the prime list\\.$")
	public void secondary_list_that_contains_persons_from_the_prime_list(int len) {

		this.tempCollection = new CustomLinkedList<Person>();

		for (int i = 0; i < len; i++)
			this.tempCollection.add(this.personList.get(i));

	}

	@When("^Removes from this list all elements that are contained in the secondary list$")
	public void removes_from_this_list_all_elements_that_are_contained_in_the_secondary_list() {
		this.personList.removeAll(tempCollection);
		this.controlList.removeAll(tempCollection);

	}

	@Then("^The list no longer contains the removed Persons$")
	public void the_list_no_longer_contains_the_removed_Persons() {
		boolean result = true;

		for (Person element : this.tempCollection) {
			if (this.personList.contains(element))
				result = false;
		}

		System.out.println("$$$Person list after modifications$$$: " + this.personList);

		assertTrue(result);

	}

	@When("^Trying to remove a non-existing person named \"([^\"]*)\" with ID (\\d+) from the list$")
	public void trying_to_remove_a_non_existing_person_named_with_ID_from_the_list(String name, int id) {

		Person removedPerson = new Person(name, DEFAULT_AGE, id);
		boolean result = this.personList.remove(removedPerson);
		assertFalse(result);

		this.controlList.remove(removedPerson);
	}

	@When("^All persons that are not contained in the secondary list are removed from the list$")
	public void all_persons_that_are_not_contained_in_the_secondary_list_are_removed_from_the_list() {
		this.personList.retainAll(this.tempCollection);
		this.controlList.retainAll(this.tempCollection);
	}

	@When("^The list contains only the persons that were on the secondary list$")
	public void the_list_contains_only_the_persons_that_were_on_the_secondary_list() {

		for (Person person : this.personList)
			assertTrue(this.tempCollection.contains(person));
	}

	@Given("^Secondary list of (\\d+) persons - (\\d+) of them are from the prime list$")
	public void secondary_list_of_persons_of_them_are_from_the_prime_list(int listSize, int commonElements) {

		this.tempCollection = new CustomLinkedList<Person>();

		for (int i = 0; i < commonElements; i++)
			this.tempCollection.add(this.personList.get(i));

		Integer base_id = 1;

		for (int i = 0; i < listSize - commonElements; i++) {
			this.tempCollection.add(new Person("Name_" + base_id.toString(), DEFAULT_AGE, base_id + 2000));
			base_id += 1;

		}
	}

	@When("^Iterating over the list$")
	public void iterating_over_the_list() {

		this.tempCollection = new CustomLinkedList<Person>();

		Iterator<Person> iterator = this.personList.iterator();

		while (iterator.hasNext()) {
			this.tempCollection.add(iterator.next());
		}

	}

	@Then("^All list elements are provided in a correct order$")
	public void all_list_elements_are_provided_in_a_correct_order() {

		assertTrue(this.tempCollection.equals(this.personList));

	}

	@Then("^All list elements placed after node (\\d+) are provided in a correct order$")
	public void all_list_elements_placed_after_node_are_provided_in_a_correct_order(int index) {

		List<Person> expectedList = this.personList.subList(index, this.personList.size());

		System.out.println(expectedList);
		System.out.println(this.tempCollection);

		assertTrue(this.tempCollection.equals(expectedList));

	}

	@Then("^All list elements placed before node (\\d+) are provided in a correct order$")
	public void all_list_elements_placed_before_node_are_provided_in_a_correct_order(int index) {

		List<Person> expectedList = this.personList.subList(0, this.personList.size() - index + 1);

		((CustomLinkedList<Person>) expectedList).reverse();

		System.out.println(expectedList);
		System.out.println(this.tempCollection);

		assertTrue(this.tempCollection.equals(expectedList));

	}

	@When("^Iterating over the list in for-each loop$")
	public void iterating_over_the_list_in_for_each_loop() {

		this.tempCollection = new CustomLinkedList<Person>();

		for (Person person : this.personList)
			this.tempCollection.add(person);
	}

	@When("^Iterating over the list in reverse$")
	public void iterating_over_the_list_in_reverse() {

		this.tempCollection = new CustomLinkedList<Person>();

		ListIterator<Person> listIterator = personList.listIterator();

		while (listIterator.hasNext())
			listIterator.next();

		while (listIterator.hasPrevious())
			this.tempCollection.add(listIterator.previous());

		((CustomLinkedList<Person>) this.tempCollection).reverse();

	}

	@When("^Iterating over the list from given node (\\d+)$")
	public void iterating_over_the_list_from_given_node(int index) {

		this.tempCollection = new CustomLinkedList<Person>();

		ListIterator<Person> listIterator = personList.listIterator(index);

		while (listIterator.hasNext()) {
			this.tempCollection.add(listIterator.next());
		}

	}

	@When("^Iterating over the list in reverse from given node (\\d+)$")
	public void iterating_over_the_list_in_reverse_from_given_node(int index) {

		this.tempCollection = new CustomLinkedList<Person>();

		ListIterator<Person> listIterator = personList.listIterator(index);

		while (listIterator.hasPrevious())
			this.tempCollection.add(listIterator.previous());

	}

	@When("^Creating a sublist from index (\\d+) to index (\\d+)$")
	public void creating_a_sublist_from_index_to_index(int fromIndex, int toIndex) {

		this.tempCollection = this.personList.subList(fromIndex, toIndex);

	}

	@Then("^Sublist size is (\\d+)$")
	public void sublist_size_is(int size) {

		assertTrue(this.tempCollection.size() == size);

	}

	@Then("^The person \"([^\"]*)\" is on the sublist at index (\\d+)$")
	public void the_person_is_on_the_sublist_at_index(String name, int index) {

		Person expectedPerson = ((CustomLinkedList<Person>) this.tempCollection).get(index);
		assertTrue(expectedPerson.name.equals(name));

	}

	@When("^Removing the person named \"([^\"]*)\" with ID (\\d+) from the sublist$")
	public void removing_the_person_named_with_ID_from_the_sublist(String name, int id) {

		Person removedPerson = new Person(name, DEFAULT_AGE, id);
		boolean result = this.tempCollection.remove(removedPerson);
		assertTrue(result);

		this.personStored = removedPerson;

		this.controlList.remove(removedPerson);

	}

	@When("^Removing the element with index (\\d+) from the sublist$")
	public void removing_the_element_with_index_from_the_sublist(int index) {

		Person removedPerson = ((CustomLinkedList<Person>) this.tempCollection).get(index);
		((CustomLinkedList<Person>) this.tempCollection).remove(index);

		this.controlList.remove(removedPerson);

	}

	@Then("^The sublist no longer contains the removed Person$")
	public void the_sublist_no_longer_contains_the_removed_Person() {

		assertFalse(this.tempCollection.contains(this.personStored));
	}

	@When("^Clearing the sublist$")
	public void clearing_the_sublist() {

		System.out.println("Temp collection: " + this.tempCollection);

		this.controlList.removeAll(this.tempCollection);
		this.tempCollection.clear();

		System.out.println("Control list: " + this.controlList);
		System.out.println("Person list: " + this.personList);
	}

	@When("^Removing a collection from the sublist that contains the first (\\d+) sublist elements$")
	public void removing_a_collection_from_the_sublist_that_contains_the_first_sublist_elements(int copyLen) {

		Collection<Person> removedCollection = CustomLinkedList
				.makeListOf(((CustomLinkedList<Person>) this.tempCollection).subList(0, copyLen));

		this.tempCollection.removeAll(removedCollection);
		this.controlList.removeAll(removedCollection);

	}

	@When("^Adding a person named \"([^\"]*)\" with ID (\\d+) at index (\\d+) to the sublist$")
	public void adding_a_person_named_with_ID_at_index_to_the_sublist(String name, int id, int index) {

		Person addedPerson = new Person(name, DEFAULT_AGE, id);

		((CustomLinkedList<Person>) this.tempCollection).add(index, addedPerson);
		this.controlList.add(index, addedPerson);

	}

	@When("^Adding a person named \"([^\"]*)\" with ID (\\d+) to the end of the sublist$")
	public void adding_a_person_named_with_ID_to_the_end_of_the_sublist(String name, int id) {

		Person addedPerson = new Person(name, DEFAULT_AGE, id);

		((CustomLinkedList<Person>) this.tempCollection).add(addedPerson);

	}

	@Then("^New sublist size is (\\d+)$")
	public void new_sublist_size_is(int size) {
		System.out.println("Actual size: " + this.tempCollection.size());

		assertTrue(this.tempCollection.size() == size);

	}

	@When("^Adding a collection of (\\d+) random persons to the sublist$")
	public void adding_a_collection_of_random_persons_to_the_sublist(int addedListSize) {

		Collection<Person> addedCollection = new CustomLinkedList<Person>();

		Integer base_id = 1;

		for (int i = 0; i < addedListSize; i++) {

			Person temp = new Person("Name_" + base_id.toString(), DEFAULT_AGE, base_id + 2000);

			addedCollection.add(temp);
			this.controlList.add(addedListSize + 1 + base_id, temp);

			base_id += 1;

		}

		this.tempCollection.addAll(addedCollection);

		System.out.println(this.controlList);
		System.out.println(this.personList);

	}

	@When("^Adding a collection of (\\d+) random persons to the sublist at index (\\d+)$")
	public void adding_a_collection_of_random_persons_to_the_sublist_at_index(int addedListSize, int index) {

		Collection<Person> addedCollection = new CustomLinkedList<Person>();

		Integer base_id = 1;

		for (int i = 0; i < addedListSize; i++) {

			Person temp = new Person("Name_" + base_id.toString(), DEFAULT_AGE, base_id + 2000);

			addedCollection.add(temp);
			base_id += 1;

		}

		this.controlList.addAll(index, addedCollection);
		((CustomLinkedList<Person>) this.tempCollection).addAll(index, addedCollection);

		System.out.println(this.controlList);
		System.out.println(this.personList);

	}
	
	@When("^Replacing the person at index (\\d+) in the sublist with a person named \"([^\"]*)\" with ID (\\d+)$")
	public void replacing_the_person_at_index_in_the_sublist_with_a_person_named_with_ID(int index, String name, int id){
	    
		Person addedPerson = new Person(name, DEFAULT_AGE, id);
		
		((CustomLinkedList<Person>) this.tempCollection).set(index, addedPerson);
		
		this.controlList.set(index, addedPerson);
		
	}
	
	@When("^All persons with ID above (\\d+) are removed$")
	public void all_persons_with_ID_above_are_removed(int maxId){
	    this.personList.removeIf(x->x.id>maxId);
	    this.controlList.removeIf(x->x.id>maxId);
		
	}
	
	@Then("^The person named \"([^\"]*)\" with ID (\\d+) is removed from the list$")
	public void the_person_named_with_ID_is_removed_from_the_list(String name, int id){
		
		boolean result = this.personList.contains(new Person(name, DEFAULT_AGE, id));
		assertFalse(result);
	    
		
	}
	

}
