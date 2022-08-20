package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBullet extends Bullet {
    public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }

    public void hitPlayer() {
        ArrayList<Tank> players = this.gamePanel.playerList;
        for (Tank player: players) {
            if(this.gerRec().intersects(player.gerRec())) {
                this.gamePanel.blastList.add(new Blast("", player.x-34, player.y-14, this.gamePanel));
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeList.add(this);
                player.alive = false;
                break;
            }
        }
    }

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitPlayer();

    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}
