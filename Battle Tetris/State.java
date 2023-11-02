import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

// I am thinking that we can possibly make a class to hold the current state of the entire board in an ordered colleciton, this way
// if a row fills up then we can just remove and then its easier to move
public class State extends OrderedCollection{
    ReadImages image = new ReadImages();
    Node end;
    //length is backwards, indicates if there is less than 20
    int length;
    Pair Lastpos;
    boolean first=true; 

    int Player1Score;
    int Player2Score;

    // Constructor - Creates the first end, but then appends 20 rows - Important for instantiation of an initial blank State
    public State(){
        Player1Score = 0;
        Player2Score = 0;
        end = null;
        length = 20;
        Lastpos = new Pair(360, 30);
        for(int i =0; i<20; i++){
            append();
        }
    }
    ////////////////////////////////////////

    //append method, just adds a blank rows. Used only during initial instantiation or after a removal of a line. 
    public void append(){
        Node toAdd = new Node(length);
        toAdd.prev= end;
        end = toAdd;
        length--;
    }
    /////////////////////////////////////////
    
    /***********************************************************************/
    //StateON Iterates through the Structure to turn on a specific space
    public void SpaceON(double column, double row){
        Node n = end;
        while(n.rownum != row){
            n=n.prev;
        }
        n.rowstate[(int)column] = 1;
    }


    /************************************************************************* */
    //Same thing as SpaceOn, but rather turns of a specific state, used for deletion of original block after update
    public void SpaceOFF(double column, double row){
        Node n = end;
        while(n.rownum != row){
            n=n.prev;
        }
        
        n.rowstate[(int)column] = 0;

    }


    /********************************************************************** */
    //Checks a specific space and returns the value of that space, used for checking block collision
    public int checkSpace(double column, double row){
        Node n = end;
        while(n.rownum != row){
            n=n.prev;
        }
        return n.rowstate[(int)column];
    }


    /*************************************************************************************** */
    //Check left is used when moving the block left, indicates whether the spaces to the left of a block are occupied or out of bounds
    public boolean checkLeft(Pair[] block){
        boolean goleft =true;
        int currRow=(int) (Lastpos.y/30);
        // new column
        int currCol = (int) (((Lastpos.x/30))-7);
        //System.out.println("Current Column: "+ currCol+ "\n" + "Current Row: " + currRow); //Checks the current COlumn and row
        Pair center = new Pair((double) currCol,(double)currRow); 
        
        ////////This part of the method is to find the lowest blocks, basically the blocks that would be interacting with lower blocks
          
        LinkedList <Pair> Check= new LinkedList<Pair>();
        double biggesty = 0;
        double smallesty = 0;

        //Identify the bounds for the shape,
        for(Pair p: block){
            if(p.y>biggesty){
                biggesty = p.y;
            }
            if(p.y<smallesty){
                smallesty = p.y;
            }
        }
        //find the lowest point for each row
        for(double i = smallesty; i<biggesty+1; i++){
            Pair lowestPoint = new Pair(2, i);
            for(Pair p: block){
                if(p.y==lowestPoint.y && p.x<lowestPoint.x){
                    lowestPoint = p;
                }
            }
            Check.add(lowestPoint);
        }
        for(Pair p: Check){
            if(p.x+center.x<0 || checkSpace((center.x + p.x-1), center.y+p.y) ==1){
                goleft=false;
            }
        }
        return goleft;
    }




      /*************************************************************************************** */
    //Check left is used when moving the block right, indicates whether the spaces to the right of a block are occupied or out of bounds
    public boolean checkRight(Pair[] block){
        boolean goRight =true;
        int currRow=(int) (Lastpos.y/30);
        // new column
        int currCol = (int) (((Lastpos.x/30))-7);
        Pair center = new Pair((double) currCol,(double)currRow); 
        
         /******************************************************************
          * This part of the method is to find the lowest blocks, basically the blocks that would be interacting with lower blocks
          */
        LinkedList <Pair> Check= new LinkedList<Pair>();
        double biggesty = 0;
        double smallesty = 0;

        //Identify the bounds for the shape,
        for(Pair p: block){
            if(p.y>biggesty){
                biggesty = p.y;
            }
            if(p.y<smallesty){
                smallesty = p.y;
            }
        }
        //find the lowest point for each row
        for(double i = smallesty; i<biggesty+1; i++){
            Pair rightPoint = new Pair(-2, i);
            for(Pair p: block){
                if(p.y==rightPoint.y && p.x>rightPoint.x){
                    rightPoint = p;
                }
            }
            Check.add(rightPoint);
        }
        for(Pair p: Check){
            if(p.x+center.x>9 || checkSpace((center.x + p.x+1), center.y+p.y) ==1){
                goRight=false;
            }
        }
        return goRight;
    }



/********************************************************************** */
    //Returns the first rowstate
    public int[] peek(){

        return end.rowstate;

    }



/******************************************************************************************
 * Removes a node at a specific index, used primarily in conjunctino with checkComplete to see if a row is full and then remove
 */
    public int[] remove(int index){
        int[] toReturn;
        Node removed;
        Node n = end;

        //Different process for removing the end
        if(index == 1){
            toReturn = n.rowstate;
            length=-1;
            pop();
        }else{
        //Go through datastructure until the one we want to remove + 1
            while(n.prev.rownum != index){
                n=n.prev;
            }
            toReturn = n.prev.rowstate;
        //
            removed = n.prev;
        //In order to remove it we set the previous to the previous of the one before
            //System.out.println("Removed Row: " + removed.rownum);


            n.prev = n.prev.prev;
        }


        //Readjusts numbering for the rows, so that when append adds node number 1 to the beginning
        n = end;
        //now to update rownumbers
        if(index==20){
            while(n.prev!= null){
                n.rownum++;
                n = n.prev;
            }
            n.rownum++;
            length++;

        }else{
            while(n.rownum <= index-1){
                n.rownum++;
                n = n.prev;
            }
            length++;
        }
        
        append();

        return toReturn;

    }

/*************************************************************************
 * Returns the last node,
 */
    public int[] pop(){
        int[] toReturn = end.rowstate;
        end = end.prev;
        length++;
        return toReturn;
    }
    /**********************************************************************
     * ToString method, allows to print the entire state
     */
    public String toString(){
        String toReturn = "";
        Node n = end;
        while(n!=null){
            for (int j =0; j<1; j++){//why is this a nested for loop if you're only doing it for 1 row?
                for( int i =0; i<10; i++){
                    toReturn =toReturn + " " + n.rowstate[i] + " ";
                }
                toReturn = toReturn + "Row: " + n.rownum + "\n";
            }

            n=n.prev;
        }
        return toReturn;
    }

/************************************************************************************
 * Rotates the block
 */
    public void rotateBlock(Pair[] origBlock, Pair[] newBlock, Pair Pos){
        
            //First need to erase Where the block originally was
            Pair Center = new Pair((Pos.x/30)-7, (Pos.y/30)); 
            for(Pair p : origBlock){
                //System.out.println("Turning Off: " + (p.x+Center.x)+ ", "+ (p.y + Center.y));
                SpaceOFF(p.x+Center.x, p.y+Center.y);
            }

            //Now turn on the spaces where the New Block is 
            for(Pair p: newBlock){
                SpaceON(Center.x+p.x, Center.y+p.y);
            }
   

    }

/*********************************************************************************
 * Checks through each row to see if the row is full and removes any that are full
 */
    public void checkComplete(){
        Node n = end;
        LinkedList <Integer> complete = new LinkedList<>();
        while(n!=null){
            int count = 0;
            for( int i =0; i<10; i++){
                if (n.rowstate[i] == 1){
                    count++;
                }
                if(count==10){
                    complete.add(n.rownum);
                }
            }
            n=n.prev;
        }
        for(int p: complete){
            if (Main.counter % 2 == 1) {
                Player1Score = Player1Score + 100;
                System.out.println(Player1Score);
            }
            else {
                Player2Score = Player2Score + 100;
                System.out.println(Player2Score);
            }
            remove(p);
        }
    }
    
/***********************************************************************************
 * Updates the position of the block, erases the old one, draws the new one, allows for movemnt of a block
 */
    public void updatePos(Pair[] block, Pair newPos){
       // System.out.println("Last pos: " + Lastpos.x + ", " + Lastpos.y + "\n" + "New Pos: " + newPos.x + ", " + newPos.y);
        //mew row is this
        int newrow = (int) (newPos.y/30);

        // new column
        int newcol = ((int) (newPos.x/30))-7;

        Pair center = new Pair((double)newrow,(double) newcol);
        int origrow = (int)(Lastpos.y/30);
        int origcol = (int)((Lastpos.x/30)-(newPos.x/30)) + newcol;

        for(Pair p: block){
            // space off takes column then row as parameters
            SpaceOFF(origcol + p.x, origrow + p.y);
        }

        for(Pair p: block){
            SpaceON(newcol+p.x, newrow + p.y);
        }
        this.Lastpos = new Pair(newPos.x, newPos.y);

        ///!!! COOL TIPPET!!!! if you uncomment the line below you can actually play terminal tetris!
        System.out.println(this);

    }
    
/****************************************************************************************************/
/////Check Collision
    public boolean checkCollision(boolean isFalling, Pair[] block){
        int currRow=(int) (Lastpos.y/30);
        // new column
        int currCol = (int) (((Lastpos.x/30))-7);
        Pair center = new Pair((double) currCol,(double)currRow); 
        
         /******************************************************************
          * This part of the method is to find the lowest blocks, basically the blocks that would be interacting with lower blocks
          */
        LinkedList <Pair> Check= new LinkedList<Pair>();
        double biggestx = 0;
        double smallestx= 0;

        //Identify the bounds for the shape,
        for(Pair p: block){
            if(p.x>biggestx){
                biggestx = p.x;
            }
            if(p.x<smallestx){
                smallestx = p.x;
            }
        }
        //find the lowest point for each row
        for(double i = smallestx; i<biggestx+1; i++){
            Pair lowestPoint = new Pair(i, -2);
            for(Pair p: block){
                if(p.x==lowestPoint.x && p.y>lowestPoint.y){
                    lowestPoint = p;
                }
            }
            Check.add(lowestPoint);
        }
        /******************************************************************* */

        //Look at the Row below each bottom block in check, if it is greater than 20, the maximum row, then stop falling. if It is occupied by a one stop
        for(Pair p: Check){
            if((center.y+p.y+1)>20 || checkSpace((center.x + p.x), center.y+p.y+1) ==1){
                isFalling=false;
            }
        }


        return isFalling;
    }
    /**************************************************************************************************** */

    /****************************************************************************************************
     * check for lose
     */
    public void checkLose(Pair[] block, Pair pos, boolean first){

        //Check for lose by comparing last pos to new pos
        boolean checker=false; 
        if(pos.x==Lastpos.x && Lastpos.y==pos.y){
            checker =true;
        }if(first){
            System.out.println("Got here first time");
        }
        else if (checker){
            System.out.println("Made Game Over");
            Main.gameover=true;

        }
    }


    /*****************************************************************************************************
     * Pseudo-Constructor for block, instantiates the block into the state
     */

    public void newblock(Pair[] block, Pair pos){
        Pair lastposi = Lastpos;

        int top = 0;
        for(Pair p : block){
            if(p.y<top){
                top = (int)p.y;
            }
        }
        Pair center = new Pair(5, top +pos.y/30);

        for(Pair p: block){
            SpaceON(p.x+center.x, p.y+center.y);
        }
        Lastpos=new Pair(360, 30);
        checkLose(block, lastposi, first);
        first = false;
        System.out.println(this);
        Main.counter++;
        System.out.println(Main.counter);
        System.out.println(Player1Score);
        System.out.println(Player2Score);
    }

/**********************************************************
 * returns length
 */
    public int length(){
        return length;
    }


/**********************************************************************************
 * Draws the whole board
 */
    public void drawState(Graphics g, Image blockImage, int boardX, int boardY, int size){
        Node n = end;
        Graphics2D g2d = (Graphics2D)g;
        
        for(int p =0; p<20; p++){
            for(int i =0; i<10; i++){
                g.drawImage(blockImage, boardX + i * size, boardY + (n.rownum+1) * size, size, size, null);
                if(n.rowstate[i]==1){
                    if (Main.counter % 2 == 0) {
                        g2d.drawImage(image.blockImage1, (int) i * size + 210, (int) n.rownum*size+30, 30, 30, null);
                    }
                    if (Main.counter % 2 == 1) {
                        g2d.drawImage(image.blockImage2, (int) i * size + 210, (int) n.rownum*size+30, 30, 30, null);
                    }
                    
                }
            }
            n=n.prev;
        }

        //System.out.println("Counter" + Main.counter);
    }


}





class Node{
    //each node will hold the state of the row in an array
    int rownum;
    int[] rowstate;
    Node prev;
    public Node(int num){
        rowstate = new int[10];
        this.rownum = num;

        //Test to see if this is working and creating rows properly/
        // System.out.println("Row: " + rownum);




    }
}