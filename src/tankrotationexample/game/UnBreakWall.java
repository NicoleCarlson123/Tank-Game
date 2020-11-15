package tankrotationexample.game;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class UnBreakWall extends GameObjects {

    private Rectangle bound;

    UnBreakWall (BufferedImage img, int x, int y) {
        super(x,y,img);
        this.bound = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }
}
//package tankrotationexample.game;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class UnBreakWall extends GameObjects{
//   int x, y;
//    BufferedImage wallImage;
//    Rectangle unBreakableWallBounds;
//
//    public UnBreakWall(int x, int y, BufferedImage wallImage) {
//        super(x,y,wallImage);
//        this.unBreakableWallBounds = new Rectangle(x,y,this.wallImage.getWidth(), this.wallImage.getHeight());
//        gameObjects.add(this);
//
//    }
//
//    public void drawImage(Graphics g) {
//
//        Graphics2D g2 = (Graphics2D) g;
//        g2.drawImage(this.wallImage, x, y, null);
//    }
//}
