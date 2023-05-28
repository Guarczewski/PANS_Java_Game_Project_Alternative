package Index;

import Blocks.Node;
import CustomUtilityTools.Config;
import Entity.*;
import Package.*;
import Blocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CPanel extends JPanel {
    Boxing box;
    public static int StartX, StartY, EndX, EndY;
    public long RenderStart, RenderEnd, RenderTime;
    LightArea x = new LightArea(200);

    public CPanel(){
        setBackground(Color.black);
        Random random = new Random();
        Main.player = new Player(Config.WINDOW_WIDTH / 2,Config.WINDOW_HEIGHT / 2,Config.NODE_SIZE, Config.NODE_SIZE);

        Main.npcs.add(new NPC((Config.WINDOW_WIDTH - 100 )/ 2,Config.WINDOW_HEIGHT / 2,Config.NODE_SIZE, Config.NODE_SIZE));

        box = new Boxing();

        Main.nodes = new Node[Config.SIZE_WIDTH][Config.SIZE_HEIGHT];
        Main.pathNodes = new PathNode[Config.SIZE_WIDTH][Config.SIZE_HEIGHT];
        for (int i = 0; i < Config.SIZE_WIDTH; i++) {
            for (int j = 0; j < Config.SIZE_HEIGHT; j++) {
                Main.nodes[i][j] = new Node(i, j);
                Main.pathNodes[i][j] = new PathNode(i,j);
                if (i % 2 == 0 && j % 2 == 0)
                    Main.nodes[i][j].type = 1;
                if (i == 0 || i == Config.SIZE_WIDTH - 1 || j == 0 || j == Config.SIZE_HEIGHT - 1) {
                    Main.nodes[i][j].type = 1;
                    Main.pathNodes[i][j].walkable = false;
                }
                else {
                    int k = random.nextInt(1000);
                    if (k < 650) {
                        Main.nodes[i][j].type = 2;
                        Main.pathNodes[i][j].walkable = false;
                    }
                    else if (k > 950) {
                        Main.enemies.add(new Enemy((i * Config.NODE_SIZE), (j * Config.NODE_SIZE),Config.NODE_SIZE, Config.NODE_SIZE,1,0));

                    }
                }
            }
        }
        Main.enemies.add(new Enemy((1 * Config.NODE_SIZE), (1 * Config.NODE_SIZE),Config.NODE_SIZE, Config.NODE_SIZE,1,0));
        System.out.println(Main.enemies.size());
     //   Main.walker = new Walker(Main.pathNodes, Main.pathNodes[3][3], Main.pathNodes[15][15], Main.enemies.get(0));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;


        RenderStart = System.nanoTime();

        StartX = Main.player.arrayCordX - Config.DRAW_DISTANCE_X;
        EndX = Main.player.arrayCordX + Config.DRAW_DISTANCE_X;

        StartY = Main.player.arrayCordY - Config.DRAW_DISTANCE_Y;
        EndY = Main.player.arrayCordY + Config.DRAW_DISTANCE_Y;

        if (StartX < 0)
            StartX = 0;

        if (StartY < 0)
            StartY = 0;

        if (EndX > Config.SIZE_WIDTH)
            EndX = Config.SIZE_WIDTH;

        if (EndY > Config.SIZE_HEIGHT)
            EndY = Config.SIZE_HEIGHT;

        for (int i = StartX; i < EndX; i++)
            for (int j = StartY; j < EndY; j++)
                Main.nodes[i][j].Render(g2D);

        for (Bomb bomb : Main.bombs)
           if (bomb.bombSet)
               bomb.Render(g2D);

    //    Main.walker.Render(g2D);

        for (Enemy enemy : Main.enemies) {
            enemy.Render(g2D);
          //  System.out.println(enemy.hitBox.getBounds());
        }

      //  for (NPC npc : Main.npcs)
      //      npc.Render(g2D);



        Main.player.Render(g2D);

        x.Render(g2D);



        box.paintComponent(g2D);
        g2D.dispose();
        RenderEnd = System.nanoTime();
        RenderTime = RenderEnd - RenderStart;
    }
}
