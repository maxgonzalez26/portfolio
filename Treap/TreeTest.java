public class TreeTest {

    public static void main(String[] args) {

	new TreeTest().process();
    }

    public void process() {
	Treap<Person> sampleTree = new Treap<Person>();

	sampleTree.printMe();

	sampleTree.add(new Person("H","Test"));

	sampleTree.add(new Person("D","Test"));
	sampleTree.add(new Person("B","Test"));
	sampleTree.add(new Person("C","Test"));

	sampleTree.add(new Person("G","Test"));
	sampleTree.add(new Person("A","Test"));

	sampleTree.add(new Person("E","Test"));
	sampleTree.add(new Person("F","Test"));

		
	System.out.println(sampleTree.count());
	sampleTree.printMe();

	sampleTree.delete(new Person("B", "Test")); 

	System.out.println("B should be deleted");
	System.out.println(sampleTree.count());
	sampleTree.printMe();
    }

}