Feature: Custom Linked List

#.get(x)
Scenario Outline: Get element by index - numbers
Given List of 5 numbers
When Saving the element with index <index> of type <type> 
Then Actual value of the saved element is identical to the <expected> <type> value.
	
  Examples:
  |index|expected|type	|
  |0    |0			 |number|
  |2    |20			 |number|
  |4    |40			 |number|
  
  #.get(x)
  Scenario Outline: Get element by index - custom objects
  Given List of five Person objects
  When Saving the element with index <index> of type <type> 
  Then Actual value of the saved element is identical to the <expected> <type> value.
  
 Examples:
  |index|expected|type	|
  |0    |0			 |person|
  |2    |2		   |person|
  |4    |4		   |person|
  
  #.size()
  Scenario: Data provided on list size is correct. 
  Given List of five Person objects
  Then Expected list size is 5
  
  
  #.add(x)
  Scenario: Add new element of type Person to the end of the list
  Given List of five Person objects
  When Adding new person with name "Adik" and ID 8200
  Then New element is added to the 6th place in the list - a person named "Adik" with ID 8200 
  And Expected list size is 6
  
  #.add(index,x)
  Scenario: Add new element of type Person in the middle of the list
  Given List of five Person objects
  When Adding new person with name "Adik" and ID 8200 at index 3
  Then New element is added to the 4th place in the list - a person named "Adik" with ID 8200
  And Expected list size is 6
  *   No unexpected modifications in list order or list elements  
  
  #.add(index,x)
  Scenario: NEGATIVE - exception 'Index Out Of Bounds' is raised when trying to add new element with invalid index
  Given List of five Person objects
  When Trying to add a new person with name "Adik" and ID 8200 at index 10
  Then The correct exception is thrown
  But No unexpected modifications in list order or list elements  
  
  #.add(0,x)
  Scenario: Add new element of type Person to the beginning of the list 
  Given List of five Person objects 
  When Adding new person with name "Adik" and ID 8200 at index 0
  Then New element is added to the 1th place in the list - a person named "Adik" with ID 8200
  And Expected list size is 6
  *   No unexpected modifications in list order or list elements  
  
  #.contains(x)
  Scenario Outline: List method "contains" returns "true" if the list contains the provided object
  Given List of five Person objects
  When Checking if the list contains a Person named <name> with ID <id>
  Then Verify tested List method returns <result>.
  
  Examples:
  |name	 |id	|result|
  |Eugene|1012|true  |
  |Alex  |1330|true  |
  |Ev    |8908|false |
  
  
  #.contains(Collection<x>)
  Scenario: List method "containsAll" returns "true" if the list contains all the elements of the provided collection
  Given List of five Person objects
    *   Secondary list that contains 2 persons from the prime list.
  When  Checking if the list contains the provided collection
  Then  Verify tested List method returns true.
  
  #.contains(Collection<x>)
  Scenario: NEGATIVE - List method "containsAll" returns "false" if the list does not contain all the elements of the provided collection
  Given List of five Person objects
    *   Secondary list of 4 persons - 2 of them are from the prime list
  When  Checking if the list contains the provided collection
  Then  Verify tested List method returns false.
  
  
  #.indexOf(x)
  Scenario Outline: Index of each list element is available
  Given List of five Person objects
  When Checking for the index of Person named <name> with ID <id>
  Then Getting the correct index <index_expected>
  
  Examples:
  |name  |id   |index_expected|
  |Eugene|1012 |0             |
  |Alex  |1330 |1             |
  |Paul  |1801 |2             |
  |Vadim |12332|4							|
  
  #.lastIndexOf(x)
  Scenario: The last index of each element is available.
  Given List of five Person objects
  * Adding new person with name "Eugene" and ID 1012
  When Checking for the last index of Person named "Eugene" with ID 1012
  Then Verifying the last index of the added person equals to the index of the last element 
  
  #.set(index,x)
  Scenario: Method 'Set' replaces an element with another element of the same type, replaced element is returned.
  Given List of five Person objects
  When The element at index 4 is replaced with Person named "Vlad" with ID 1920
  Then The replaced element is returned, it is a Person named "Vadim" with ID 12332
  *    No unexpected modifications in list order or list elements
  
  #.remove(0,x)
  Scenario: Remove the first element in the list
  Given List of five Person objects 
  When Removing the element with index 0
  Then The list no longer contains the removed Person
  *    New list size is 4
  *    No unexpected modifications in list order or list elements
   
  #.remove(index,x)
  Scenario: Remove an element from the middle of the list
  Given List of five Person objects 
  When Removing the element with index 3
  Then The list no longer contains the removed Person
  *    New list size is 4
  *    No unexpected modifications in list order or list elements
  
  #.remove(index,x)
  Scenario: NEGATIVE - exception 'Index Out Of Bounds' is raised when trying to remove element with invalid index
  Given List of five Person objects 
  When Trying to remove an element with index 33
  Then The correct exception is thrown
  But No unexpected modifications in list order or list elements
  
  #.remove(x)
  Scenario: Remove the selected element from the list
  Given List of five Person objects 
  When Removing the person named "Paul" with ID 1801 from the list
  Then The list no longer contains the removed Person
  *    New list size is 4
  *    No unexpected modifications in list order or list elements
  
  #.remove(x)
  Scenario: NEGATIVE - trying to remove a non existing element from the list
  Given List of five Person objects 
  When Trying to remove a non-existing person named "Raul" with ID 5804 from the list
  Then    No unexpected modifications in list order or list elements
  
  #.removeAll(Collection<x>)
  Scenario: Removes from this list all of its elements that are contained in thespecified collection 
  Given List of five Person objects 
  *     Secondary list that contains 2 persons from the prime list.
  When  Removes from this list all elements that are contained in the secondary list         
  Then  The list no longer contains the removed Persons
  *    New list size is 3 
  *    No unexpected modifications in list order or list elements
  
  #.removeIf(condition)
  Scenario: Removes all of the elements of this list that answer the given condition
  Given List of five Person objects
  When  All persons with ID above 10000 are removed
  Then  The person named "Vadim" with ID 12332 is removed from the list 
    *   No unexpected modifications in list order or list elements
     
  
  
  #.retainAll(Collection<x>)
  Scenario: Removes from this list all of its elements that are not contained in the specified collection
  Given List of five Person objects 
    *   Secondary list that contains 2 persons from the prime list.
  When  All persons that are not contained in the secondary list are removed from the list
    *   The list contains only the persons that were on the secondary list
    *   New list size is 2 
    *   No unexpected modifications in list order or list elements
     
  #.retainAll(Collection<x>)
  Scenario: Removes from this list all of its elements that are not contained in the specified collection.
  The specified collection contains other elements that are not on the list.
   
  Given List of five Person objects 
    *   Secondary list of 4 persons - 2 of them are from the prime list
  When  All persons that are not contained in the secondary list are removed from the list
  Then  The list contains only the persons that were on the secondary list
    *   New list size is 2 
    *   No unexpected modifications in list order or list elements
     
  #.retainAll(Collection<x>)
  Scenario: The specified collection contains only elements that are not on the list, therefore
  all the elements shall be removed
  
	 Given List of five Person objects 
	   *   Secondary list of 4 persons - 0 of them are from the prime list
	 When  All persons that are not contained in the secondary list are removed from the list
	 Then   New list size is 0 
	 
	 #.iterator()
	 Scenario: The list is iterable
	 Given List of five Person objects
	 When Iterating over the list
	 Then All list elements are provided in a correct order
	 
	 #.iterator()
   Scenario: For-each iteration is supported. Iterating over the list in for-each loop.
   Given List of five Person objects
	 When Iterating over the list in for-each loop
	 Then All list elements are provided in a correct order 
	 
	 
	 #.listIterator()
	 Scenario: Reverse iteration is supported
	 Given List of five Person objects
	 When Iterating over the list in reverse 
	 Then All list elements are provided in a correct order
	 
	 
	 #.listIterator(index)
	 Scenario Outline: Iteration is supported from given node
	 Given List of five Person objects
	 When Iterating over the list from given node <node_index>
	 Then All list elements placed after node <node_index> are provided in a correct order
	 
	 Examples:
	 |node_index|
	 |	0       |
   |  1       |
   |  2       |
   |  4       |
   
   #.listIterator(index)
   Scenario: Reverse iteration is supported from given node
   Given List of five Person objects
   When Iterating over the list in reverse from given node 3
   Then All list elements placed before node 3 are provided in a correct order
   
   #.subList(from,to)
   Scenario: A sub list is created - it contains the first 2 elements of the original list
   Given List of five Person objects
   When Creating a sublist from index 0 to index 2
   Then Sublist size is 2
    *   The person "Eugene" is on the sublist at index 0
    *   The person "Alex" is on the sublist at index 1
    
   #.subList(from,to)
   Scenario: A sub list is created - it contains the last 2 elements of the original list
   Given List of five Person objects
   When Creating a sublist from index 3 to index 5
   Then Sublist size is 2
    *   The person "Yuka" is on the sublist at index 0
    *   The person "Vadim" is on the sublist at index 1
    
    #.subList(from,to).remove(x)
    Scenario: Elements removed from the sub list are removed from the original list as well
  	Given List of five Person objects
  	When Creating a sublist from index 0 to index 3
  	 *   Removing the person named "Paul" with ID 1801 from the sublist
  	Then The list no longer contains the removed Person
  	 *   New list size is 4
     *   No unexpected modifications in list order or list elements
     
    #.subList(from,to).remove(index)
    Scenario Outline: Removing an element with given index from the sublist - verifying the original list is updated accordingly.
    Given List of five Person objects
    When Creating a sublist from index 0 to index 3
     *   Removing the element with index <index> from the sublist
    Then The sublist no longer contains the removed Person
     *   The list no longer contains the removed Person
     
    Examples:
    |index|
    | 0   |
    | 1   |
    | 2   |
     
    #.subList(from,to).clear() 
    Scenario Outline: Clearing the sublist - verifying the original list is updated accordingly.
    Given List of five Person objects
    When Creating a sublist from index <fromIndex> to index <toIndex>
     *   Clearing the sublist
    Then New list size is 2
     *   No unexpected modifications in list order or list elements
     
     Examples:
     |fromIndex|toIndex|
     |    0    |   3   |
     |    1    |   4   |
     |    2    |   5   |
  	
		#.subList(from,to).removeAll(Collection<x>)
		Scenario: Removing a collection from the sublist - verifying the original list is updated accordingly.
		Given List of five Person objects
		When Creating a sublist from index 0 to index 4
		 *   Removing a collection from the sublist that contains the first 2 sublist elements 
		Then New list size is 3
		 *   No unexpected modifications in list order or list elements
		 
		
		#.subList(from,to).add(index,x)
		Scenario Outline: Adding an element to the sublist at given index - verifying the original list is updated accordingly. 
		Given List of five Person objects 
		When Creating a sublist from index 0 to index 4
		 *   Adding a person named "Jack" with ID 3016 at index <index> to the sublist
		Then New list size is 6
		 *   No unexpected modifications in list order or list elements
		
	  Examples:
	  |index|
	  |0    |
	  |1    |
	  |2    |
	  |3    |
	  
	  #.subList(from,to).add(x)
	  Scenario: Adding an element to the end of the sublist - verifying the original list is updated accordingly. 
	  Given List of five Person objects
	  When Creating a sublist from index 0 to index 4
	   *   Adding a person named "Jack" with ID 3016 to the end of the sublist
	  Then New sublist size is 5
	  And  Checking for the index of Person named Jack with ID 3016
  	 *   Getting the correct index 4 
  	 
  	#.subList(from,to).add(Collection<x>)
  	Scenario: Adding a collection to the end of the sublist - verifying the original list is updated accordingly. 
  	Given List of five Person objects
  	When Creating a sublist from index 0 to index 4
  	 *   Adding a collection of 2 random persons to the sublist
  	Then New list size is 7
  	 *   No unexpected modifications in list order or list elements
  	 
  	#.subList(from,to).add(index, Collection<x>)
  	Scenario: Adding a collection to the sublist at given index - verifying the original list is updated accordingly. 
  	Given List of five Person objects
  	When Creating a sublist from index 0 to index 4
  	 *   Adding a collection of 2 random persons to the sublist at index 2
  	Then New list size is 7
  	 *   No unexpected modifications in list order or list elements
  	 
  	#.subList(from,to).set(index,element)
  	Scenario: Changing the value of one of the sublist elements and verifying the original list is updated accordingly. 
  	Given List of five Person objects
  	When Creating a sublist from index 0 to index 4
  	 *   Replacing the person at index 2 in the sublist with a person named "Jack" with ID 3016 
  	 *   No unexpected modifications in list order or list elements
  	 
  	 
   #test exp_marker
	  
	  
	   
		
  
  
  
  
