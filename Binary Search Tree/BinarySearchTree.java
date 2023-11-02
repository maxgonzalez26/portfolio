import java.util.ArrayList;


public class BinarySearchTree<P extends Comparable<P> & getP> {

    private BSTreeNode<P> root;

	
    public void add(P p) {

	if (root == null) {
	    root = new BSTreeNode<P>(p);
	    root.parent = null;
	}
	else root.add(p);
    }
	
    public int count() {
	if (root == null) {
	    return 0;
	}
	else return root.count();
    }

	
	
    public void setRoot(BSTreeNode<P> r) {
	root = r;
    }

    public BSTreeNode<P> getRoot() {
	return root;
    }

    public void printMe() {
	if (root == null) {
	    System.out.println("Empty tree");
	}
	else root.printMe();
    }

    //A
    public BSTreeNode<P> smallest() {
        if (root == null) {
            System.out.println("Empty tree");
            return null;
        }
        else {
            return root.smallest();
        }
    }



    //B
    public int countLeaves() {
        if (root == null) {
            System.out.println("Empty tree");
            return 0;
        }
        else {
            return root.countLeaves();
        }
    }



    //C
    public int height() {
        if (root == null) {
            System.out.println("Empty tree");
            return -1;
        }
        else {
            return root.height();
        }
    }



    //D
    public boolean inBalance(int limit) {
        if (root == null) {
            System.out.println("Empty tree");
            return true;
        }
        else {
            return inBalance(limit);
        }
    }


    //E
    public void truncate(int limit) {
        System.out.println("Empty tree");
        root.truncate(limit);
    }


    // F
    public int countG(P p) {
        if (root == null) {
            System.out.println("Empty tree");
            return 0;
        }
        else {
            return root.countG(p);
        }
    }

    // G
    public void getNodesInOrder(ArrayList<BSTreeNode<P>> a) {
        root.getNodesInOrder(a);
        for (int x = 0; x < a.size(); x++) {
            System.out.println(a.get(x).p);
        }
    }

    // H
    public void delete(P p) {
        root.delete(p);
    }
}

