package tests;



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
		if(!(obj instanceof Person))
			return false;
		if(!(obj.getClass()==Person.class))
			return false;
		
		Person testedPerson = (Person) obj;
		
		if(this.id != testedPerson.id || !this.name.contentEquals(testedPerson.name))
			return false;
		
		return true;
		
	}
	
	@Override
	public String toString() {
		
		return this.name+", "+this.id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	
	public static void main(String[] args) {
		
	}

	

}
