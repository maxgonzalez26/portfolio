import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ReadImages {
    public static Image title;
    public static Image button;
    public static Image soundOnImage;
    public static Image soundOffImage;

    public static Image level1Image;
    public static Image level2Image;
    public static Image level3Image;
    public static Image instructions;

    public static Image backButton;
    public static Image playButton;
    public static Image pauseButton;


    public static Image blockImage;//unfilled blocks
    public static Image blockImage1;//blue block
    public static Image blockImage2;//red block

    public static Image gameOverImage;
    public ReadImages(){
        try {
            
            //to compile in terminal
            this.title = ImageIO.read(new File("FolderForImages/origTitle.png")); ///reading image file
            this.button = ImageIO.read(new File("FolderForImages/button.png"));
            this.soundOnImage = ImageIO.read(new File("FolderForImages/soundOn.png"));
            this.soundOffImage = ImageIO.read(new File("FolderForImages/soundOff.png"));

            this.backButton = ImageIO.read(new File("FolderForImages/backbutton.png")); //this is used in the third page too
            this.level1Image = ImageIO.read(new File("FolderForImages/level1.jpg"));
            this.level2Image = ImageIO.read(new File("FolderForImages/level2.jpg"));
            this.level3Image = ImageIO.read(new File("FolderForImages/level3.jpg"));
            this.instructions = ImageIO.read(new File("FolderForImages/toplay.png"));

            this.playButton = ImageIO.read(new File("FolderForImages/playbutton.jpg"));
            this.pauseButton = ImageIO.read(new File("FolderForImages/pausebutton.jpg"));
            this.blockImage = ImageIO.read(new File("FolderForImages/BlockPicture.png"));
            this.blockImage1 = ImageIO.read(new File("FolderForImages/RedBlock.png"));
            this.blockImage2 = ImageIO.read(new File("FolderForImages/BlueBlock.png"));
            this.gameOverImage = ImageIO.read(new File("FolderForImages/gameover.png"));

            /* 
            //images for first page
            this.title = ImageIO.read(new File("origTitle.png")); ///reading image file
            this.button = ImageIO.read(new File("button.png")); ///reading image file
            this.soundOnImage = ImageIO.read(new File("soundOn.png"));
            this.soundOffImage = ImageIO.read(new File("soundOff.png"));


            //images for second page
            this.backButton = ImageIO.read(new File("backbutton.png")); //this is used in the third page too
            this.level1Image = ImageIO.read(new File("level1.jpg"));
            this.level2Image = ImageIO.read(new File("level2.jpg"));
            this.level3Image = ImageIO.read(new File("level3.jpg"));
            this.instructions = ImageIO.read(new File("toplay.png"));

            //images for third page
            this.playButton = ImageIO.read(new File("playbutton.jpg"));
            this.pauseButton = ImageIO.read(new File("pausebutton.jpg"));
            this.blockImage = ImageIO.read(new File("BlockPicture.png"));
            this.blockImage1 = ImageIO.read(new File("RedBlock.png"));
            this.blockImage2 = ImageIO.read(new File("BlueBlock.png"));
            this.gameOverImage = ImageIO.read(new File("gameover.png"));
*/


        } catch (IOException e) {
            System.err.println(e);
        }
    }
}


