package Entity;

import Blocks.Node;
import CustomUtilityTools.Config;
import Index.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bomb extends Entity {
    public boolean bombSet;
    public static Random random = new Random();
    public Bomb (int cordX, int cordY, int width, int height) {
        super(cordX, cordY,width, height);
    }
    @Override
    public void Render(Graphics2D graphics2D){

    }
    @Override
    public void Move() {

    }

    public void DropBomb(){
        if (!bombSet) {

            bombSet = true;
            arrayCordX = Main.player.arrayCordX;
            arrayCordY = Main.player.arrayCordY;

            new Thread(() -> {

                try {
                    Main.nodes[arrayCordX][arrayCordY].bombFusion = 3;
                    Thread.sleep(500);
                    Main.nodes[arrayCordX][arrayCordY].bombFusion = 2;
                    Thread.sleep(500);
                    Main.nodes[arrayCordX][arrayCordY].bombFusion = 1;
                    Thread.sleep(500);
                    Main.nodes[arrayCordX][arrayCordY].bombFusion = 0;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                animationStage = 0;

                Main.nodes[arrayCordX][arrayCordY].type = Config.BLANK_NODE_ID;

                for (Enemy enemy : Main.enemies) {
                    if (Math.sqrt(Math.pow(enemy.arrayCordX - arrayCordX,2) + Math.pow(enemy.arrayCordY - arrayCordY,2)) < ( 12)){
                        enemy.currentHealth = -100;
                    }
                }

                List<Node> nodes = new ArrayList<>();

                for (int i = 1; i < 20; i++) {

                    if (arrayCordX - i > 0) {
                        if (Main.nodes[arrayCordX - i][arrayCordY].type != 1) {
                            if (random.nextInt(100) < 2)
                                Main.nodes[arrayCordX - i][arrayCordY].hasItem = true;

                            Main.nodes[arrayCordX - i][arrayCordY].type = Config.BLANK_NODE_ID;
                            nodes.add(Main.nodes[arrayCordX - i][arrayCordY]);
                        }
                    }
                    if (arrayCordX + i < Config.SIZE_WIDTH) {
                        if (Main.nodes[arrayCordX + i][arrayCordY].type != 1) {
                            if (random.nextInt(100) < 2)
                                Main.nodes[arrayCordX + i][arrayCordY].hasItem = true;
                            Main.nodes[arrayCordX + i][arrayCordY].type = Config.BLANK_NODE_ID;
                            nodes.add(Main.nodes[arrayCordX + i][arrayCordY]);
                        }
                    }
                    if (arrayCordY - i > 0) {
                        if (Main.nodes[arrayCordX][arrayCordY - i].type != 1) {
                            if (random.nextInt(100) < 2)
                                Main.nodes[arrayCordX][arrayCordY - i].hasItem = true;
                            Main.nodes[arrayCordX][arrayCordY - i].type = Config.BLANK_NODE_ID;
                            nodes.add(Main.nodes[arrayCordX][arrayCordY - i]);
                        }
                    }
                    if (arrayCordY + i < Config.SIZE_HEIGHT) {
                        if (Main.nodes[arrayCordX][arrayCordY + i].type != 1) {
                            if (random.nextInt(100) < 2)
                                Main.nodes[arrayCordX][arrayCordY + i].hasItem = true;
                            Main.nodes[arrayCordX][arrayCordY + i].type = Config.BLANK_NODE_ID;
                            nodes.add(Main.nodes[arrayCordX][arrayCordY + i]);
                        }
                    }
                }

                if (arrayCordY + 1 < Config.SIZE_HEIGHT && arrayCordX + 1 < Config.SIZE_WIDTH ) {
                    if (Main.nodes[arrayCordX + 1][arrayCordY + 1].type != 1) {
                        if (random.nextInt(100) < 2)
                            Main.nodes[arrayCordX + 1][arrayCordY + 1].hasItem = true;
                        Main.nodes[arrayCordX + 1][arrayCordY + 1].type = Config.BLANK_NODE_ID;
                        nodes.add(Main.nodes[arrayCordX + 1][arrayCordY + 1]);
                    }
                }

                if (arrayCordY - 1 > 0 && arrayCordX - 1 > 0 ) {
                    if (Main.nodes[arrayCordX - 1][arrayCordY - 1].type != 1) {
                        if (random.nextInt(100) < 2)
                            Main.nodes[arrayCordX - 1][arrayCordY - 1].hasItem = true;
                        Main.nodes[arrayCordX - 1][arrayCordY - 1].type = Config.BLANK_NODE_ID;
                        nodes.add(Main.nodes[arrayCordX - 1][arrayCordY - 1]);
                    }
                }

                if (arrayCordY + 1 < Config.SIZE_HEIGHT && arrayCordX - 1 > 0 ) {
                    if (Main.nodes[arrayCordX - 1][arrayCordY + 1].type != 1) {
                        if (random.nextInt(100) < 2)
                            Main.nodes[arrayCordX - 1][arrayCordY + 1].hasItem = true;
                        Main.nodes[arrayCordX - 1][arrayCordY + 1].type = Config.BLANK_NODE_ID;
                        nodes.add(Main.nodes[arrayCordX - 1][arrayCordY + 1]);
                    }
                }

                if (arrayCordY - 1 > 0 && arrayCordX + 1 < Config.SIZE_WIDTH ) {
                    if (Main.nodes[arrayCordX + 1][arrayCordY - 1].type != 1) {
                        if (random.nextInt(100) < 2)
                            Main.nodes[arrayCordX + 1][arrayCordY - 1].hasItem = true;
                        Main.nodes[arrayCordX + 1][arrayCordY - 1].type = Config.BLANK_NODE_ID;
                        nodes.add(Main.nodes[arrayCordX + 1][arrayCordY - 1]);
                    }
                }


                bombSet = false;

                try {
                    for (Node node : nodes)
                        node.explosionPower = 4;
                    Thread.sleep(100);
                    for (Node node : nodes)
                        node.explosionPower = 3;
                    Thread.sleep(100);
                    for (Node node : nodes)
                        node.explosionPower = 2;
                    Thread.sleep(100);
                    for (Node node : nodes)
                        node.explosionPower = 1;
                    Thread.sleep(100);
                    for (Node node : nodes)
                        node.explosionPower = 0;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                Main.bombs.remove(this);

            }).start();
        }
    }

}
