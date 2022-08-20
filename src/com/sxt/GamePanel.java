package com.sxt;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    //定义双缓存图片
    Image offScreemImage = null;
    //窗口长宽
    int width = 800;
    int height = 610;
    Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.jpeg");
    int y = 150;
    //游戏模式 0 未开始  1 单人模式  2 双人模式 5 游戏胜利
    int state = 0;
    int a = 1;
    //重绘次数
    int count = 0;
    //以生产敌人数量
    int enemyCount = 0;
    //游戏元素列表
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    ArrayList<Bot> botList = new ArrayList<Bot>();
    ArrayList<Bullet> removeList = new ArrayList<Bullet>();
    ArrayList<Tank> playerList = new ArrayList<Tank>();
    ArrayList<Wall> wallList = new ArrayList<Wall>();
    ArrayList<Base> baseList = new ArrayList<Base>();
    ArrayList<Blast> blastList = new ArrayList<Blast>();
    //PlayerOne
    PlayerOne playerOne = new PlayerOne("images/player1/p1tankU.jpeg",
             125, 510, this, "images/player1/p1tankU.jpeg",
            "images/player1/p1tankL.jpeg", "images/player1/p1tankR.jpeg",
            "images/player1/p1tankD.jpeg");
    //PlayerTwo
    PlayerTwo playerTwo = new PlayerTwo("images/player2/p2tankU.jpeg",
            625, 510, this, "images/player2/p2tankU.jpeg",
            "images/player2/p2tankL.jpeg", "images/player2/p2tankR.jpeg",
            "images/player2/p2tankD.jpeg");
    //Base
    Base base = new Base("images/base.jpeg", 365,540, this);

    //启动的方法
    public void launch(){
        //标题
        setTitle("坦克大战");
        //窗口的初始大小
        setSize(width, height);
        setLocationRelativeTo(null);
        //使屏幕剧中
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
        //添加窗口监视器
        this.addKeyListener(new GamePanel.KeyMonitor());

        for (int i = 0; i < 13; i++) {
            wallList.add(new Wall("images/walls.jpeg", i*62, 170,this));
        }
        wallList.add(new Wall("images/walls.jpeg", 303, 540,this));
        wallList.add(new Wall("images/walls.jpeg", 303, 480,this));
        wallList.add(new Wall("images/walls.jpeg", 365, 480,this));
        wallList.add(new Wall("images/walls.jpeg", 427, 480,this));
        wallList.add(new Wall("images/walls.jpeg", 427, 540,this));
        //添加基地
        baseList.add(base);


        //重绘
        while (true) {
            //游戏胜利判定
            if(botList.size() == 0 && enemyCount == 10) {
                state = 5;
            }
            //游戏失败判定
            if ((playerList.size() == 0 && (state == 1 || state == 2)) || baseList.size() == 0) {
                state = 4;
            }
            //添加电脑坦克
            if(count % 100 == 1 && enemyCount < 10 && (state ==1 || state == 2)) {
                //添加随机数，保证坦克随机生成
                Random random = new Random();
                int rnum = random.nextInt(800);
                botList.add(new Bot("images/enemy/enemy1U.png",
                        rnum, 110, this, "images/enemy/enemy1U.png",
                        "images/enemy/enemy1L.png", "images/enemy/enemy1R.png",
                        "images/enemy/enemy1D.png"));
                enemyCount++;
            }

            repaint();
            try {
                Thread.sleep(25);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void paint(Graphics g) {
//        System.out.println(bulletList.size());
        //创建和窗口大小相同的Image图片
        if(offScreemImage == null) {
            offScreemImage = this.createImage(width,height);
        }
        //获取该图片的画笔
        Graphics gImage = offScreemImage.getGraphics();
        //设置画笔颜色
        gImage.setColor(Color.gray);
        //绘制实心矩阵
        gImage.fillRect(0 ,0, width,height);
        //改变笔画颜色
        gImage.setColor(Color.blue);
        gImage.setFont(new Font("仿宋", Font.BOLD, 50));
        //state=0，游戏未开始；

        if(state == 0) {
            gImage.drawString("选择游戏模式", 220, 100);
            gImage.drawString("单人模式", 220, 200);
            gImage.drawString("双人模式", 220, 300);
            //绘制指针
            gImage.drawImage(select, 160, y, null);
        }
        else if(state == 1 || state == 2) {
            gImage.setFont(new Font("仿宋", Font.BOLD, 30));
            gImage.setColor(Color.red);
            gImage.drawString("剩余敌人：" + botList.size(), 10, 60);
            //添加游戏元素
            for (Tank player: playerList) {
                player.paintSelft(gImage);
            }
            for (Bullet bullet:bulletList) {
                bullet.paintSelft(gImage);
            }
            bulletList.removeAll(removeList);
            for(Bot bot: botList){
                bot.paintSelft(gImage);
            }

            for(Wall wall: wallList) {
                wall.paintSelft(gImage);
            }
            for(Base base: baseList) {
                base.paintSelft(gImage);
            }
            for (Blast blast: blastList) {
                blast.paintSelft(gImage);
            }
            //重绘一次
            count++;
        }
        else if (state == 3) {
            gImage.drawString("游戏暂停", 220, 200);
        }
        else if (state == 4) {
            gImage.drawString("游戏失败", 220, 200);
        }
        else if (state == 5) {
            gImage.drawString("游戏胜利", 220, 200);
        }
        g.drawImage(offScreemImage, 0, 0, null);



    }
    //键盘监视器
    class KeyMonitor extends KeyAdapter{
        //按下键盘
        @Override
        public void keyPressed(KeyEvent e) {
            //返回键值
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_1:
                    a = 1;
                    y = 150;
                    break;
                case KeyEvent.VK_2:
                    a = 2;
                    y = 250;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playerOne);
                    //Playertwo
                    if (state == 2) {
                        playerList.add(playerTwo);
                        playerTwo.alive = true;
                    }
                    playerOne.alive = true;

                    break;
                case KeyEvent.VK_P:
                    if (state != 3) {
                        a = state;
                        state = 3;
                    }
                    else {
                        state = a;
                        if (a == 0) {
                            a = 1;
                        }
                    }
                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
            }
        }
        //松开键盘
        @Override
        public void keyReleased(KeyEvent e) {
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }

    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }
}
