import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JPanel implements KeyListener, MouseListener {
    public static ReadImages i = new ReadImages();
    public static final int WIDTH = 24;
    public static final int HEIGHT = 22;
    public static final int BLOCKSIZE = 30;
    public static int FPS = 2;
    World world;
    public static int counter;
    boolean highlight = false;
    static int page = 0;
    boolean showStart;
    String player;
    static boolean gameover;
    static Scoreboard highScores;

    class Runner implements Runnable {//Runnable interface

        public void run() {
            while (true) {
                if(page==2){//while we are on the game screen, run the game
                    world.updateBlocks(1.0 / (double) FPS);
                }

                repaint();
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
                
            }
          
            
            
        }


    }

    /*INFO for mouselistener found @ https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html */

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println("Mouse clicked " + mouseX + "  " + mouseY);
        if(mouseX>270 && mouseX<445 && mouseY>325 && mouseY<365){//detecting start button

            if (page<1){//only increments if page is currently 0
                if(pages.soundon==true){StdAudio.play("click.wav");}

                page++;
                System.out.println("page: "+ page);
            }

        }

        if(mouseX>80 && mouseX<200 && mouseY>100 && mouseY<150){//player 1 rectangle
            if (page <3){
                if(pages.soundon==true){StdAudio.play("click.wav");}
                page++;
                player = "Player 1";

                //create a player object that has a highscore so that when you finish the game we can write the highscores to a specific file...

            }
        }

        if(mouseX>300 && mouseX<420 && mouseY>100 && mouseY<150){//player 2 rectangle
            if (page <3){
                if(pages.soundon==true){StdAudio.play("click.wav");}
                page++;
                player = "Player 2";
            }
        }

        if(mouseX>520 && mouseX<640 && mouseY>100 && mouseY<150){//player 3 rectangle
            if (page <3){
                if(pages.soundon==true){StdAudio.play("click.wav");}
                page++;
                player = "Player 3";
            }
        }

        if(mouseX>5 && mouseX<50 && mouseY>5 && mouseY<38){//detecting back button

            if(page==1 || page==2){//to make sure it doesn't go negative and not show anything (only when page=1)
                if(pages.soundon==true){StdAudio.play("click.wav");}
                page--;

            }

            System.out.println("backButton");
        }

        if (mouseX > 545 && mouseX< 573 && mouseY > 5 && mouseY < 30) {
            if(page==1 || page==2){
                if(pages.soundon==true){StdAudio.play("click.wav");}
                world.currentBlock.pause();
            }
        
            System.out.println("Pause");
        }
        if (mouseX > 625 && mouseX< 653 && mouseY > 5 && mouseY < 30) {
            if(page==1 || page==2){
                if(pages.soundon==true){StdAudio.play("click.wav");}
                world.currentBlock.resume();
            }
        
            System.out.println("Resume");
        }


        if(mouseX>280 && mouseX<360 && mouseY>500 && mouseY<580 && page==0){//detecting soundOn Button
            pages.soundon=true;
            StdAudio.play("click.wav");
        }

        if(mouseX>360 && mouseX<440 && mouseY>500 && mouseY<580 && page==0){//detecting soundOff Button
            pages.soundon=false;
        }

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {


    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked");
    }

    /******************MouseListener Methods Implemented********************************************************/


    /******************KeyListener Implementation***************************************************************/


    public void keyPressed(KeyEvent e) {//implementing methods from keylistener interface
        int keyCode = e.getKeyCode();
        //rotates the block if the up key is pressed
        if (counter % 2 == 1) {
            if (keyCode == KeyEvent.VK_UP) {
                world.currentBlock.rotate(world.currentState);
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                world.moveRight();
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                world.moveLeft();
            }
            if(keyCode == KeyEvent.VK_DOWN){
                FPS = 10;//this is to speed up the current block while it is falling
                if (counter % 2 == 1) {
                    world.currentState.Player1Score++;
                    System.out.println(world.currentState.Player1Score);
                }
                if (counter % 2 == 0) {
                    world.currentState.Player2Score++;
                    System.out.println(world.currentState.Player2Score);
                }
            }
        }

        if (counter % 2 == 0) {
            if (keyCode == KeyEvent.VK_W) {
                world.currentBlock.rotate(world.currentState);
            }
            if (keyCode == KeyEvent.VK_D) {
                world.moveRight();
            }
            if (keyCode == KeyEvent.VK_A) {
                world.moveLeft();
            }
            if(keyCode == KeyEvent.VK_S){
                FPS = 10;//this is to speed up the current block while it is falling
                if (counter % 2 == 1) {
                    world.currentState.Player1Score++;
                    System.out.println(world.currentState.Player1Score);
                }
                if (counter % 2 == 0) {
                    world.currentState.Player2Score++;
                    System.out.println(world.currentState.Player2Score);
                }
            }
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            //world.hold();//to add a block to the hold
        }

        if(keyCode == KeyEvent.VK_SHIFT){//method for saving the score and player --> need to include another && gameover part to if statement
            SavedScores.saveScore(player, 100);
            System.out.println("saved score");
        }


    }


    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            FPS = 2;
        }

    }


    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
    }
    /****************************KeyListener Methods Implemented**************************************************/

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Main() {
        world = new World(WIDTH * BLOCKSIZE, HEIGHT * BLOCKSIZE);//initialize the instance of the world class, dimensions are (720,660)
        addKeyListener(this);//adding key/mouselisteners so functions can be performed. 
        addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH * BLOCKSIZE, HEIGHT * BLOCKSIZE));
        //this.highScores = new Scoreboard();
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TETRIS!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stops running program when JFrame is closed
        gameover = false;
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }


    /************************These Methods Handle Graphics********************************************************/
    public void paintComponent(Graphics g) {//graphics method
        super.paintComponent(g);
        pages.setupBackground(g);
        Graphics2D g2d = (Graphics2D)g;//typecasting
        if (page==0) {
            drawStartScreen(g);
            //pages.soundOption(g);

            if (pages.soundon == true) {
                //draws image
                g2d.drawImage(i.soundOnImage,280,500, (int)(i.soundOnImage.getWidth(this)/4.5), (int)(i.soundOnImage.getHeight(this)/4.5), this);
            }
            else{
                g2d.drawImage(i.soundOffImage,280,500, (int)(i.soundOffImage.getWidth(this)/4.5), (int)(i.soundOffImage.getHeight(this)/4.5), this);

            }
        }
        if(page==1){
            //pages.chooseUserProfile(g);
            g2d.drawImage(i.backButton, 5,5,i.backButton.getWidth(this)/25,i.backButton.getHeight(this)/25,this);
            g2d.drawImage(i.level1Image, 80,100,i.level1Image.getWidth(this)/17,i.level1Image.getHeight(this)/17,this);
            g2d.drawImage(i.level2Image, 300,100,i.level2Image.getWidth(this)/17,i.level2Image.getHeight(this)/17,this);
            g2d.drawImage(i.level3Image, 520,100,i.level3Image.getWidth(this)/17,i.level3Image.getHeight(this)/17,this);
            g2d.drawImage(i.instructions, 90,200,(int)(i.instructions.getWidth(this)/3.5),(int)(i.instructions.getHeight(this)/3.5),this );
        }
        if (page==2){
            world.drawPlayer(g);
            gameGraphics(g);
            if(gameover==true){
                System.out.println("GAME OVER");
                int p1score = world.currentState.Player1Score;
                int p2score = world.currentState.Player2Score;
                world.currentBlock.isPaused=true;
                world.nextBlock.isPaused = true;
                int Winner = Math.max(p1score, p2score);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH*30, HEIGHT*30);
                g2d.drawImage(i.gameOverImage,40,40,i.gameOverImage.getWidth(this)/3,i.gameOverImage.getHeight(this)/3,this);
                g.setColor(Color.black);
                if(Winner == p1score){
                    String playert = "Player 1 Wins! ";
                    g.drawString(playert + "" +"score: " + Winner, 150, 300); 
                }
                if(Winner == p2score){
                    String playert = "Player 2 Wins! ";
                    g.drawString(playert + "" +"score: " + Winner, 150, 300); 
                }

                
            }
        }
        if(page==3){
            drawGameOver(g);
        }
    }
    public void drawGameOver(Graphics g){
       int p1score = world.currentState.Player1Score;
       int p2score = world.currentState.Player2Score;
       int winner = Math.max(p1score, p2score);
       g.drawString("winning score: " + winner, 100, 200); ;
      // this.highScores.append("Player 1 ", p1score);
       //this.highScores.append("Player 2 ", p2score);


    }


    public void drawStartScreen(Graphics g) {//method for drawing of the startscreen
        Graphics2D g2d = (Graphics2D)g;//typecasting
        //draws image
        g2d.drawImage(i.title, 70,60,(int)(i.title.getWidth(this)/3.5),(int)(i.title.getHeight(this)/3.5),this);
        g2d.drawImage(i.button, 240,230,280,280,this);
    }


    public void gameGraphics(Graphics g){//method for drawing of the game graphics
        world.drawBoard(g);
        Graphics2D g2d = (Graphics2D)g;//typecasting

        //draws back button, play button, and pause button
        g2d.drawImage(i.backButton,5,5,i.backButton.getWidth(this)/25,i.backButton.getHeight(this)/25,this);
        g2d.drawImage(i.playButton,625,5,i.playButton.getWidth(this)/20,i.playButton.getHeight(this)/20,this);
        g2d.drawImage(i.pauseButton,545,5,i.pauseButton.getWidth(this)/20,i.pauseButton.getHeight(this)/20,this);

        //draws current player and score
        world.drawPlayer(g);
    }
}//class Main.java