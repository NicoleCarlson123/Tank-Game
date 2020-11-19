package tankrotationexample.game;

import org.w3c.dom.css.Rect;
import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Bullet extends GameObjects{
    private final int R= 4;
    protected int bx;
    protected int by;
    protected int angle;
    boolean alive;
    private static BufferedImage bullet;
    private Rectangle bound = new Rectangle(this.x, this.y, this.bullet.getWidth(), this.bullet.getHeight());

    Tank tank;

    static {
        try{
            bullet = read(new File("resources/Shell.gif"));
        }catch (IOException ex){

        }
    }

    Bullet(Tank tank, int x, int y, int angle){
        super(x,y, bullet);
        this.angle = angle;
        alive = true;

        this.tank = tank;

    }

    public boolean isAlive() {
        return alive;
    }

    public void update() {
        bx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        by = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += bx;
        y += by;
        checkCollision(this);
        updateBounds();

    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bullet.getWidth() / 2.0, this.bullet.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bullet, rotation, null);

    }

    public void checkCollision(Bullet bullet){
        GameObjects obj;
        Rectangle tbound = bullet.getBounds();
        for (int i =0; i< Map.objects.size();i++){
            obj = Map.objects.get(i);
            if (tbound.intersects(obj.getBounds()) && obj != tank){
                if(obj instanceof BreakWall) {
                    Map.objects.remove(obj);
                }
                if(obj instanceof Tank){
                    ((Tank) obj).takeDamage();
                }
                alive = false;
            }
        }
    }

    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, bullet.getWidth(), bullet.getHeight());
    }
}





