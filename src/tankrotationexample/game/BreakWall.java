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

