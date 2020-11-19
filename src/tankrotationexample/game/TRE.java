/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    //public static BufferedImage bulletImage;
    private Launcher lf;
    public static long tick = 0;
    ArrayList<GameObjects> walls;
    private Map background;


    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                //if(this.t1.getLives() || this.t2.getLives()){
                //    this.lf.setFrame("end");
                //    return;
               // }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(700);
        this.t1.setY(100);

        this.t2.setX(300);
        this.t2.setY(300);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakwall = null;
        BufferedImage unbreakwall = null;
        walls = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank2.gif")));
            unbreakwall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall1.gif")));
            breakwall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall2.gif")));


            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
             int numCols = Integer.parseInt(mapInfo[0]);
             int numRows = Integer.parseInt(mapInfo[1]);

             for(int curRow = 0; curRow < numRows; curRow++){
                 row = mapReader.readLine();
                 mapInfo = row.split("\t");
                 for(int curCol = 0; curCol < numCols; curCol++){
                    switch(mapInfo[curCol]){
                        case "2":
                            this.walls.add(new BreakWall(breakwall, curCol *30, curRow*30 ));
                            Map.objects.add(new BreakWall( breakwall, curCol *30, curRow*30));
                            break;
                        case "3":
                        case "9":
                            this.walls.add(new UnBreakWall(unbreakwall, curCol *30, curRow*30));
                            Map.objects.add(new UnBreakWall(unbreakwall, curCol *30, curRow*30));

                    }
                 }
             }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(700, 100, 0, 0, 0, t1img);
        t2 = new Tank(300, 300, 0, 0,(short) 0,t2img);
        background = new Map();
        background.initializeMap();

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);

        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        buffer.setColor(Color.BLACK);

        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);


        this.background.drawImage(buffer);
        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        int boundsX = checkBoundsX(t1);
        int boundsY = checkBoundsY(t1);
        int boundsX2 = checkBoundsX(t2);
        int boundsY2 = checkBoundsY(t2);

        BufferedImage leftHalf =  world.getSubimage(boundsX2, boundsY2, GameConstants.GAME_SCREEN_WIDTH/2,  GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf =  world.getSubimage(boundsX, boundsY, GameConstants.GAME_SCREEN_WIDTH/2,  GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage( leftHalf,0,0,null);
        g2.drawImage( rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 4,0,null);

        AffineTransform minimap = AffineTransform.getTranslateInstance(GameConstants.GAME_SCREEN_WIDTH/2.5, 0);
        minimap.scale(0.17,0.17);
        g2.drawImage(world, minimap, null);

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t1.getLives(), GameConstants.GAME_SCREEN_WIDTH * 65/80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 2 Health: " + this.t1.getHealth(), GameConstants.GAME_SCREEN_WIDTH * 50 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 50 / 80 + i, GameConstants.GAME_SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t2.getLives(), GameConstants.GAME_SCREEN_WIDTH * 27/80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 1 Health: " + this.t2.getHealth(), GameConstants.GAME_SCREEN_WIDTH * 12 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 13 / 80 + i, GameConstants.GAME_SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
        this.t1.isWon();
        this.t2.isWon();




    }

    public int checkBoundsX(Tank tank1){

        int x = tank1.getX() - GameConstants.GAME_SCREEN_WIDTH/4;

        if(x < 0){
            x = 0;

        }else if(x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2){
            x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2;
        }

        return x;
    }

    public int checkBoundsY(Tank tank2) {

        int x = tank2.getY();

        if (x < 0) {
            x = 0;

        } else if (x > GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            x = GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }

        return x;
    }
}
