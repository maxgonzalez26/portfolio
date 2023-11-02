public class Scoreboard {
    scoreNode start; // first node in the list
    scoreNode end; // last node in linked list
    int length; // length of linked list

    public Scoreboard() { // constructor for scoreboard linked list.
        start = null;
        end = null;
        length = 0;
    }

    public void printDLL() {//print doubly linked list starting from low end
        scoreNode current = start;
        while (current != null) {
            System.out.print(current.score + " ");
            current = current.next;
        }
        System.out.println();
    }

    public void printTop5(){//print the 5 highest numbers currently in the doubly linked list 
        scoreNode current = end;
        int number = 1;
        while(current!= null && number<6){
            System.out.print(number + ". " + current.score + "   ");
            current=current.prev;
            number++;
        }
    }
    public void append(String name, int score) { // append method
        scoreNode toAppend = new scoreNode(name, score);
    if (start == null){//if there's currently no items in the list
        start=end=toAppend;
        toAppend.next=null;
        toAppend.prev=null;
        System.out.println("Start was null, added: " + toAppend.score);
    } else if (start.score>toAppend.score) {//if node being added is less than start node
        //System.out.println("added node < start so located " + toAppend.score + " at beginning of list");
        toAppend.next=start;//append.next points to start
        start.prev=toAppend;
        start = toAppend;//toappend is the new start of dataset
    }


    else{
        scoreNode current = start;//current node starts at beginning of dLL
        while(current.next!=null && current.score < toAppend.score) {//traverse thru linked list to find where new node should be added

            current=current.next;//walks current until the point where current scoreNode is at the node one past the value of toAppend.score
        }
        if(current.next==null && current.score<toAppend.score){//if you've walked all the way to the end of the list
            System.out.println("walked to the end of the list trying to add" + toAppend.score);

                current.next = toAppend;
                toAppend.prev = current;
                toAppend.next=null;
                end=toAppend;//to append becomes the end of the list
                System.out.println("walked to the end of the list to add: " + toAppend.score);

        }
        else {
            if (current.prev != null) {
                current = current.prev;//regress so that current node is the node prior to where we want to to insert stuff
            }


            System.out.println("node before toadd: " + current.score + " node being added: " + toAppend.score);
            // System.out.println("current value of end: " + end.score);
            toAppend.next = current.next;//sets to append.next pointer to the next node (the one the while loop determined to be its successor)


            if (current.next != null) {//if you're not at the end of the list, set the node after toAppend to have its prev pointer point to to append instead of current
                toAppend.next.prev = toAppend;
            }
            current.next = toAppend;//current node points forward to toAppend
            toAppend.prev = current;//Append's prev pointer points to current node.
        }
    }

        length++;
        }//append

}//Class Scoreboard

class scoreNode {
    scoreNode next;//holds address in memory of next scoreNode in the linked list. 
    scoreNode prev;

    String name;
    int score;
    
    scoreNode(String name, int score) {//constructor for scoreNode class
        this.name = name;
        this.score = score;
        this.next = null;
    }
}//class Scorenode
