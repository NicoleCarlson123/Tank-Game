package tankrotationexample.game;


import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author anthony-pc
 */
public class Tank extends GameObjects {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private Rectangle hitBox;
    private int health;
    private int lives;
    private int R = 2;
    private int damage = 5;


    private final float ROTATIONSPEED = 3.0f;
    private ArrayList<Bullet> ammo;


    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.lives = 3;
        this.health = 100;
        this.angle = angle;
        this.ammo = new ArrayList<>();
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        // Map.objects.add(this);
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TRE.tick % 20 == 0) {
            Bullet b = new Bullet(x, y, angle, TRE.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_HEIGHT - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public int getHealth() {
        return health;
    }

    public void takeDamage() {
        health -= damage;
        if (health <= 0) {
            health = 100;
            lives -= 1;
        }
    }

    public int getLives() {
        return lives;
    }

    public void isWon() {
        if (lives == 0) {
            System.exit(1);
        }
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
        this.ammo.forEach(bullet -> bullet.drawImage(g));

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
