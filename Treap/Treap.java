import java.util.ArrayList;


public class Treap<P extends Comparable<P> & getP> {

    private TreapNode<P> root;

	
    public void add(P p) {

	if (root == null) {
	    root = new TreapNode<P>(p, true);
        root.printMe();
	    root.parent = null;
	}
	else root.add(p);
    }

    public void delete(P target) {
        if (root == null) {
            System.out.println("Treap is empty");
        }
        else {
            root.delete(target);
        }
    }
	
    public int count() {
	if (root == null) {
	    return 0;
	}
	else return root.count();
    }

	
	
    public void setRoot(TreapNode<P> r) {
	root = r;
    }

    public TreapNode<P> getRoot() {
	return root;
    }

    public void printMe() {
	if (root == null) {
	    System.out.println("Empty tree");
	}
	else root.printMe();
    }

    //A
    public TreapNode<P> smallest() {
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
    public void getNodesInOrder(ArrayList<TreapNode<P>> a) {
        root.getNodesInOrder(a);
        for (int x = 0; x < a.size(); x++) {
            System.out.println(a.get(x).p);
        }
    }
}

