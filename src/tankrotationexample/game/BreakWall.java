package tankrotationexample.game;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class BreakWall extends GameObjects {

    private Rectangle bound;

    BreakWall (BufferedImage img, int x, int y) {
        super(x,y,img);
        this.bound = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }
}
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//
//public class BreakWall extends GameObjects {
//    int x, y;
//    Rectangle breakableWallBounds;
//    BufferedImage wallImage;
//
//    public BreakWall(int x, int y, BufferedImage wallImage) {
//        super(x, y, wallImage);
//        this.breakableWallBounds = new Rectangle(x,y,this.wallImage.getWidth(), this.wallImage.getHeight());
//        gameObjects.add(this);
//    }
//    @Override
//    public void drawImage(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.drawImage(this.wallImage, x, y, null);
//    }
//
//}
