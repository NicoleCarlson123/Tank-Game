package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Map {
    public static final int WORLD_WIDTH = 2010;
    public static final int WORLD_HEIGHT = 2010;
    private BufferedImage background, healthPowerUp, increasedDamage, speedUp;    public static ArrayList<GameObjects> objects = new ArrayList<>();

    Map() {

    }

    public void initializeMap() {
        try {
            background = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));
            healthPowerUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Pickup.gif")));
            speedUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Explosion_small.gif")));
            increasedDamage = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Weapon.gif")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        objects.add(new HealthPowerUp(healthPowerUp, 774, 448));
        objects.add(new PlusOneLifePowerUp(increasedDamage, 65, 900));
        objects.add(new SpeedUpPowerUp(speedUp, 1446, 87));

    }

    public void drawImage(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < WORLD_WIDTH / background.getWidth() + 1; i++) {
            for (int j = 0; j < WORLD_HEIGHT / background.getHeight() + 1; j++) {
                g2d.drawImage(background, i * background.getWidth(), j * background.getHeight(), null);
            }

        }
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).drawImage(g2d);
        }
    }
}
