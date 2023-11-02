
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class World {
    int height;
    int width;

    ReadImages image = new ReadImages();

    Random rand;
    
    int currentBlockKey;
    int nextBlockKey;

    /////////////////////////
    int count;
    ///////////////////////////

    int row, column;
    int size = Main.BLOCKSIZE;//takes static variable from Main.java named BLOCKSIZE
    Block currentBlock;
    Block nextBlock;

    State currentState;

    ArrayList<Block> blockss = new ArrayList<Block>();//initializing the ArrayList of Blocks

    ArrayList<Block> nextBlocks = new ArrayList<Block>();//initializing the ArrayList of next Block

    public World(int initWidth, int initHeight) {//constructor for the world class
        width = initWidth;
        height = initHeight;

        this.rand = new Random();
        
        this.currentState = new State();

        //generates what the first block will be in the sequence
        currentBlockKey = rand.nextInt(0,7);

        //generates the second block according to the key
        nextBlockKey = rand.nextInt(0,7);
        System.out.println(currentBlockKey +" " +nextBlockKey);
        currentBlock = new Block(currentState, currentBlockKey);
        nextBlock = new Block(nextBlockKey);

        nextBlock.isFalling = false;
    
    }

    public void addBlock() {
       

        if (currentBlock.isFalling == false) {

            //updates the currentBlockKey to nextBlockKey
            currentBlockKey = nextBlockKey;

            nextBlockKey = rand.nextInt(0,7);
            nextBlock = new Block(nextBlockKey);


            currentBlock = new Block(currentState, currentBlockKey);
            currentBlock.position = new Pair(360,30);
            currentBlock.isFalling=true;

            
        }
    }


    private static int generateKey(){
        Random randomKey = new Random();
        int r = randomKey.nextInt();
        return r;
    }


    public void drawBoard(Graphics g) {//
       
        int boardWidth = 10 * size; // width of the board
        int boardHeight = 20 * size; // height of the board
        int boardX = 7 * size; // x-coordinate of the board's top-left corner
        int boardY = 1 * size; // y-coordinate of the board's top-left corner
        
        currentState.drawState(g, image.blockImage, boardX, boardY, size);
        drawPlayer(g);

    }
    public void drawPlayer(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Player 1 Score: " + currentState.Player1Score, 250, 15);
        g.drawString("Player 2 Score: " + currentState.Player2Score, 250, 45);
        if (Main.counter % 2 == 0) {
            g.drawString("CURRENT PLAYER: Player 2", 70, 15);
        }
        else {
            g.drawString("CURRENT PLAYER: Player 1", 70, 15);
        }
    }

    public void moveLeft() {
        currentBlock.moveLeft(currentState);
    }

    public void moveRight() {
        currentBlock.moveRight(currentState);
    }


    public void updateBlocks(double time) {
        addBlock();
        currentBlock.update(this, time,currentState);
    }

    public void drawGameOver(Graphics g){

    }

}
