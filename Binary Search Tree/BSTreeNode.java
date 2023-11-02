import java.util.ArrayList;

public class BSTreeNode<P extends Comparable<P> & getP> {

	P p;

	BSTreeNode<P> right, left, parent;

	public BSTreeNode(P p){
		this.p = p;
	}


	public BSTreeNode<P> add(P newP) {

		// we found it - don't add it

		int compare = p.compareTo(newP);
		if (compare == 0)
			return this;
		if (compare < 0) {
			if (right == null) {
				right = new BSTreeNode<P>(newP);
				right.parent = this;
				return right;
			}
			else {
				return right.add(newP);
			}
		}
		else {
			if (left == null) {
				left = new BSTreeNode<P>(newP);
				left.parent = this;
				return left;
			}
			else {
				return left.add(newP);
			}
		}
	}


	public int count() {
		int ret = 1;

		if (right != null)
			ret = ret + right.count();
		if (left != null)
			ret = ret + left.count();

		return ret;
	}


	public void printMe() {

		System.out.println(p);
		if (left != null)
			left.printMe();
		if (right != null)
			right.printMe();

	}


    // A
    public BSTreeNode<P> smallest() {
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
	public void getNodesInOrder (ArrayList<BSTreeNode<P>> a) {
		if (left != null) {
			left.getNodesInOrder(a);
		}

		a.add(this);

		if (right != null) {
			right.getNodesInOrder(a);
		}
	}


	public BSTreeNode<P> findP(P target) {
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

	// H
	public void delete (P p) {
		BSTreeNode<P> node1 = findP(p);

		if (node1 != null) {
			if (node1.left == null && node1.right == null) {
				if (node1.parent.left == node1) {
					node1.parent.left = null;
				}
				else {
					node1.parent.right = null;
				}
			}

			else if(node1.left != null && node1.right != null) {
				BSTreeNode<P> rsmallest = rsmallest(node1.right);
				node1.p = rsmallest.p;
				rsmallest.right.delete(rsmallest.p);
			}

			else {
				BSTreeNode<P> node2;
				if (node1.left != null) {
					node2 = node1.left;
				}
				else{
					node2 = node1.right;
				}
				if(node2.parent != null) {
					node2.parent = node1.parent;
					if (node1.parent.left == node1) {
						node1.parent.left = node2;
					}
					else {
						node1.parent.right = node2;
					}
				}
			}
		}
	}
}

