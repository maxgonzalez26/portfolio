public class TreeTest {

    public static void main(String[] args) {

	new TreeTest().process();
    }

    public void process() {
	BinarySearchTree<Person> sampleTree = new BinarySearchTree<Person>();

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

    }

}
