package main;

import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile, size real on screen
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //FPS
    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this,keyHandler);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*@Override
    public void run() {
        //METODO SLEEP, funcional pero poco eficiente.
        double drawInterval = (double) 1000000000 / FPS; // 0.01666 Seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        //Ejecución del juego
        while(gameThread!=null){
            // 1 UPDATE: update information such as character positions.
            update();
            // 2 DRAW: draw the screen with the updated information.
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                remainingTime = (remainingTime<0)?0:remainingTime;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
    @Override
    public void run(){
        spawn();
        // METODO Delta/Acumulador
        double drawInterval = (double) 1000000000 / FPS; // 0.01666 Seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        //check
        long timer = 0;
        int drawCount = 0;


        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/drawInterval;
            timer += (currentTime-lastTime);
            lastTime = currentTime;
            if(delta>=1) {
                // 1 UPDATE: update information such as character positions.
                update();
                // 2 DRAW: draw the screen with the updated information.
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update(){
        player.update();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
        graphics2D.dispose();
    }

    public void spawn(){
        // Set player's default position
        player.setDefaultValues((screenWidth/2)-(tileSize/2),(screenHeight/2)-(tileSize/2),4);
    }
}
