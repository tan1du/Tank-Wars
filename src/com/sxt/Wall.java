package com.sxt;

import java.awt.*;

public class Wall extends GameObject{

    //尺寸
    int length = 50;

    public Wall(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, gamePanel);

    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, length, length);
    }
}
