package tests;



@SuppressWarnings("unused")
public class Person {

	String name;
	int id;
	int age;


	public Person(String name, int age, int id) {

		this.name = name;
		this.id = id;
		this.age = age;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person))
			return false;
		if (!(obj.getClass() == Person.class))
			return false;

		Person testedPerson = (Person) obj;

		return this.id == testedPerson.id && this.name.contentEquals(testedPerson.name);

	}

	@Override
	public String toString() {

		return this.name + ", " + this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unused")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


}
