import java.util.ArrayList;
import java.util.Random;

public class TreapNode<P extends Comparable<P> & getP> {

	P p;

	TreapNode<P> right, left, parent;

	int priority;

	static Random rn = new Random(123);


	public TreapNode(P p, boolean isRoot) {
		this.p = p;
		if (isRoot) {
			priority = 0; // Set the root node's priority to 0
			System.out.println("This is the root and the priority is: " + priority);
		} else {
			priority = rn.nextInt(10000) + 1;
			System.out.println("This is not the root and the priority is: " + priority);
		}
	}


	public TreapNode<P> add(P newP) {
		int compare = p.compareTo(newP);
		if (compare == 0) {
			return this;
		}
		else if (compare < 0) {
			if (right == null) {
				right = new TreapNode<P>(newP, false);
				right.printMe();
				right.parent = this;	
			} 
			else {
				right = right.add(newP);
			}
			if (right.priority < this.priority) {
				rotateLeft();
				return this.parent;
			}
		} 
		else {
			if (left == null) {
				left = new TreapNode<P>(newP, false);
				left.printMe();
				left.parent = this;
			} 
			else {
				left = left.add(newP);
			}
			if (left.priority < this.priority) {
				rotateRight();
				return this.parent;
			}
		}
		return this;
	}

	
	private void rotateRight() {
		TreapNode<P> newParent = this.left;

		if (newParent == null) return;

		this.left = newParent.right;

		if (newParent.right != null) {
			newParent.right.parent = this;
		}

		newParent.parent = this.parent;

		if (this == this.parent.left) {
			this.parent.left = newParent;
		}
		else {
			this.parent.right = newParent;
		}

		newParent.right = this;

		this.parent = newParent;
	}
	
	private void rotateLeft() {
		TreapNode<P> newParent = this.right;

		if (newParent == null) return;

		this.right = newParent.left;

		if (newParent.left != null) {
			newParent.left.parent = this;
		}

		newParent.parent = this.parent;

		if (this == this.parent.left) {
			this.parent.left = newParent;
		}
		else {
			this.parent.right = newParent;
		}

		newParent.left = this;

		this.parent = newParent;
	}


	public int count() {
		int ret = 1;

		if (right != null) {
			ret = ret + right.count();
		}
		if (left != null) {
			ret = ret + left.count();
		}
		return ret;
	}


	public void printMe() {

		System.out.println(p);
		if (left != null) {
			System.out.println("On the left");
			left.printMe();
		}
		if (right != null) {
			System.out.println("On the right");
			right.printMe();
		}
	}


    // A
    public TreapNode<P> smallest() {
        if (left == null) {
            return this;
        }
        else {
            return left.smallest();
        }
    }



    //B
    public int countLeaves() {
        if (left == null && right == null) {
            return 1;
        }
        else {
            return left.countLeaves() + right.countLeaves();
        }
    }



    //C
    public int height() {
        if (left == null && right == null) {
            return 0;
        }

        int leftHeight = left.height();
        int rightHeight = right.height();

        return Math.max(leftHeight, rightHeight) + 1;
    }



    //D
    public boolean inBalance(int limit) {
        int leftHeight = left.height();
        int rightHeight = right.height();

        if (Math.abs(rightHeight - leftHeight) < limit) {
            return true;
        }
        else{
            return false;
        }
    }

	//E
	public void truncate(int limit) {
		if(limit <= 0){
			if (parent.left == this) {
				parent.left = null;
			}
			if (parent.right == this) {
				parent.right = null;
			}
		}

		if (left != null) {
			int a = limit - this.p.getP();
			left.truncate(a);
		}

		if (right != null) {
			int b = limit - this.p.getP();
			right.truncate(b);
		}
	}

	
	//F
	public int countG(P p) {
		int a = 0;
		if (this.p.compareTo(p) > 0) {
			a++;
			if (left != null) {
				a = a + left.countG(p);
			}
			if (right != null) {
				a = a + right.countG(p);
			}
		}

		else if (this.p.compareTo(p) <= 0 && right != null) {
			a =right.countG(p);
		}

		return a;
	}


	// G
	public void getNodesInOrder (ArrayList<TreapNode<P>> a) {
		if (left != null) {
			left.getNodesInOrder(a);
		}

		a.add(this);

		if (right != null) {
			right.getNodesInOrder(a);
		}
	}


	public TreapNode<P> findP(P target) {
		int compare = target.compareTo(this.p);
	
		if (compare == 0) {
			return this; 
		} else if (compare < 0 && left != null) {
			return left.findP(target); 
		} else if (compare > 0 && right != null) {
			return right.findP(target); 
		} else {
			return null; 
		}
	}

	public TreapNode<P> delete(P target) {
		// Step 1: Locate the node to delete
		TreapNode<P> nodeToDelete = findP(target);
	
		if (nodeToDelete == null) {
			// Node with the target value does not exist in the Treap.
			return this;
		}
	
		// Step 2: Rotate down until it becomes a leaf
		while (nodeToDelete.left != null || nodeToDelete.right != null) {
			if (nodeToDelete.left == null) {
				nodeToDelete.rotateLeft();
			} else if (nodeToDelete.right == null) {
				nodeToDelete.rotateRight();
			} else if (nodeToDelete.left.priority < nodeToDelete.right.priority) {
				nodeToDelete.rotateLeft();
			} else {
				nodeToDelete.rotateRight();
			}
		}
	
		// Step 3: Clip away the leaf
		if (nodeToDelete.parent != null) {
			if (nodeToDelete.parent.left == nodeToDelete) {
				nodeToDelete.parent.left = null;
			} else {
				nodeToDelete.parent.right = null;
			}
		} else {
			// If the nodeToDelete is the root node, set it to null.
			nodeToDelete = null;
		}
	
		return this; // Return the updated Treap.
	}

	// public TreapNode<P>[] split(P splitValue) {
    //     // Step 1: Find the node with the split value
    //     TreapNode<P> splitNode = findP(splitValue);

    //     if (splitNode == null) {
    //         // The split value is not in the Treap, return two empty sets.
    //         return new TreapNode[]{null, null};
    //     }

    //     // Step 2: Set the priority of the splitNode to a low value
    //     splitNode.priority = Integer.MIN_VALUE; // A very low priority value.

    //     // Step 3: Rotate the splitNode up to the root
    //     while (splitNode.parent != null) {
    //         if (splitNode == splitNode.parent.left) {
    //             rotateRight(); // Reuse the existing rotateRight method.
    //         } else {
    //             rotateLeft(); // Reuse the existing rotateLeft method.
    //         }
    //     }

    //     // The root of the Treap now represents the set of elements less than or equal to the split value.
    //     // The right child represents the set of elements greater than the split value.

    //     TreapNode<P>[] result = new TreapNode[]{this, this.right};
    //     return result;
    // }
}

