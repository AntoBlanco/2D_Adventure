package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        nowDirection = Direction.DOWN;
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public void update() {
        //Si no se usa el else, se capturan varias teclas a la vez y causa que el personaje se mueva en diagonal.
        if(keyHandler.upPressed|| keyHandler.downPressed|| keyHandler.leftPressed|| keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                nowDirection = Direction.UP;
                y -= speed;
            }
            else if (keyHandler.downPressed) {
                nowDirection = Direction.DOWN;
                y += speed;
            }
            else if (keyHandler.leftPressed){
                nowDirection = Direction.LEFT;
                x -= speed;
            }
            else if(keyHandler.rightPressed) {
                nowDirection = Direction.RIGHT;
                x += speed;
            }
            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum==1){
                    spriteNum=2;
                }else if(spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }
    }

    public void draw(Graphics2D graphics2D){
        //graphics2D.setColor(Color.white);
        //graphics2D.fillRect(x,y, gamePanel.tileSize,gamePanel.tileSize);

        BufferedImage image = null;
        switch (nowDirection){
            case Direction.UP:
                image = (spriteNum==1)?up1:up2;
                break;
            case Direction.DOWN:
                image = (spriteNum==1)?down1:down2;
                break;
            case Direction.LEFT:
                image = (spriteNum==1)?left1:left2;
                break;
            case Direction.RIGHT:
                image = (spriteNum==1)?right1:right2;
                break;
        }
        graphics2D.drawImage(image,x,y,gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
