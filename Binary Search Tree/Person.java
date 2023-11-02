public class Person implements Comparable<Person>, getP{
    private String lastName;
    private String firstName;

    Person(String l, String f) {
	this.lastName = l;
	this.firstName = f;
    }

    public String toString() {
	return this.lastName+","+this.firstName;
    }

    public int compareTo(Person p)
    {
	int lastComp = lastName.compareTo(p.lastName);
	if (lastComp != 0)
	    return lastComp;
	int firstComp = firstName.compareTo(p.firstName);
	if (firstComp != 0)
	    return firstComp;
	return 0;
    }

	@Override
	public int getP() {
		
		return (lastName+firstName).length();
	}
}
