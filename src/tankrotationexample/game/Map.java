package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Map extends GameConstants {}
   /* public static ArrayList<GameObjects> objects = new ArrayList<>();
    private BufferedImage background, wall, wall2, healthPowerUp,increasedDamage, speedUp;
    public static final int WORLD_WIDTH = 2010;
    public static final int WORLD_HEIGHT = 2010;

    Map(){

    }
    public void initializeMap(){
        try{
            background = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));
            wall = read(new File("resources/Wall1.gif"));
            wall2 = read(new File("resources/Wall2.gif"));
            healthPowerUp = read(new File("Bouncing.gif"));
            increasedDamage = read(new File("Weapon.gif"));
            speedUp = read(new File("Explosion_small.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        objects.add(new HealthPowerUp(healthPowerUp, 774, 448));
        objects.add(new PlusOneLifePowerUp(increasedDamage, 65, 900));
        objects.add(new SpeedUpPowerUp(speedUp, 1446, 87));
    }

        void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < WORLD_WIDTH/background.getWidth()+1; i++){
            for(int j =0; j<WORLD_HEIGHT/background.getHeight()+1;j++){
                g2d.drawImage(background,i*background.getWidth(),j*background.getHeight(),null);
            }
        }
        for(int i =0; i<objects.size();i++){
            objects.get(i).drawImage(g2d);
        }
    }

}*/
