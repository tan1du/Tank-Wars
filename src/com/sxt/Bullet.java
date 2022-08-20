package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject{
    //尺寸
    int width = 10;
    int height = 10;
    //速度
    int speed = 7;
    //方向
    Direction direction;

    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    public void leftward() {
        x -= speed;
    }
    public void rightward() {
        x += speed;
    }
    public void upward() {
        y -= speed;
    }
    public void downward() {
        y += speed;
    }

    public void go() {
        switch (direction) {
            case LEFT:
                leftward();
                break;
            case RIGHT:
                rightward();
                break;
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
        }
        this.hitWall();
        this.moveToBorder();
        this.hitBase();
    }

    public void hitBot() {
        ArrayList<Bot> bots = this.gamePanel.botList;
        for (Bot bot: bots) {
            if(this.gerRec().intersects(bot.gerRec())) {
                this.gamePanel.blastList.add(new Blast("", bot.x-34, bot.y-14, this.gamePanel));
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    public void hitBase() {
        ArrayList<Base> baseList = this.gamePanel.baseList;
        for (Base base: baseList) {
            if(this.gerRec().intersects(base.gerRec())) {
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    //子弹与墙壁碰撞检查
    public void hitWall() {
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //遍历列表
        for (Wall wall: walls) {
            if (this.gerRec().intersects(wall.gerRec())) {
                //删去围墙和子弹
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                //停止循环
                break;
            }

        }
    }

    public void moveToBorder() {
        if (x < 0 || x + width > this.gamePanel.getWidth()) {
            this.gamePanel.removeList.add(this);
        }
        if (y < 0 || y + height > this.gamePanel.getHeight()) {
            this.gamePanel.removeList.add(this);
        }
    }




    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitBot();

    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}
