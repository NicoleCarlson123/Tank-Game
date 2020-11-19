package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedUpPowerUp extends GameObjects {
    private Rectangle bounds;

    SpeedUpPowerUp(BufferedImage img, int x, int y){
        super(x,y,img);
        this.bounds = new Rectangle(x,y, this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }

}
