public class blockList {
    BlockNode end;
    public blockList(){//constructor that sets null to end
		end = null;//sets the base for the pointer i.e. what's it pointing to at first when it gets created. 
    }
    public void append (Block toAppend){
        BlockNode toAdd = new BlockNode(toAppend);
        toAdd.prev = end;//end is set to previous node
        end = toAdd;//end is set to new node (toAdd)
    }

    public Block peek(){
        return end.brick;
    }

    public int length(){
        BlockNode n=end;//gets the end from the list and makes it point
        int counter =0;
        if (n==null){
            return 0;
        }
        while(n!=null){//while the value of the node != null 
            n=n.prev;
            counter++;
            //System.out.println(counter);//it's getting into the loop, but the loop is not breaking.
        }
        return counter;
    }

    
}

class BlockNode{
    BlockNode prev;
    Block brick;
    BlockNode(Block bricks){
        this.brick=bricks;
    }
}
