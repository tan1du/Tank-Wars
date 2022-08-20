package com.sxt;

import java.awt.*;

public class Blast extends GameObject{
    //爆炸图集
    static Image[] imgs = new Image[8];
    int explodeCount = 0;

    static {
        for (int i = 0; i < 8; i++) {
            imgs[i] = Toolkit.getDefaultToolkit().getImage("" +
                    "images/blast/blast" + (i+1) + ".png");

        }
    }
    public Blast(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelft(Graphics g) {
        if (explodeCount < 8) {
            g.drawImage(imgs[explodeCount], x, y, null);
            explodeCount++;
        }

    }

    @Override
    public Rectangle gerRec() {
        return null;
    }
}
