package entity;

import java.awt.image.BufferedImage;

public class Entity {
    // Set default position
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public Direction nowDirection;
    public enum Direction {UP,DOWN,LEFT,RIGHT};
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
