package Index;

import Blocks.Node;
import Blocks.PathNode;
import CustomUtilityTools.Config;
import CustomUtilityTools.UtilityTools;
import Entity.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

public class Main extends JFrame implements KeyListener, MouseWheelListener {
    private static final Set<Character> keyEventList = new HashSet<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<NPC> npcs = new ArrayList<>();
    public static CPanel cPanel;
    public static Main main;
    public static Player player;
    public static int offsetX = 0, offsetY = 0;
    public static long lastBombDrop = System.currentTimeMillis();
    public static Node[][] nodes;
    public static PathNode[][] pathNodes;
   // public static Walker walker;
    Main(){
        super("BomberGuy!");
        setBounds(0, 0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cPanel = new CPanel();
        addKeyListener(this);
        addMouseWheelListener(this);
        setContentPane(cPanel);
        setVisible(true);
    }
    public static void main(String[] args) {

        try {
            Node.Node_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Nodes/Breakable.png")));
            Node.Node_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Nodes/NonBreakable.png")));
            Node.Node_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Nodes/Grass.png")));

            Node.Bomb_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Bomb/Bomb_00.png")));
            Node.Bomb_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Bomb/Bomb_01.png")));
            Node.Bomb_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Bomb/Bomb_02.png")));

            Node.Item_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Decoration/Decoration_01.png")));
            Node.Decoration_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Decoration/Decoration_00.png")));

            Node.Node_02 = UtilityTools.RemakeImage(Node.Node_02, Config.NODE_SIZE, Config.NODE_SIZE);
            Node.Node_01 = UtilityTools.RemakeImage(Node.Node_01, Config.NODE_SIZE, Config.NODE_SIZE);
            Node.Node_00 = UtilityTools.RemakeImage(Node.Node_00, Config.NODE_SIZE, Config.NODE_SIZE);

            Node.Bomb_00 = UtilityTools.RemakeImage(Node.Bomb_00, Config.NODE_SIZE, Config.NODE_SIZE);
            Node.Bomb_01 = UtilityTools.RemakeImage(Node.Bomb_01, Config.NODE_SIZE, Config.NODE_SIZE);
            Node.Bomb_02 = UtilityTools.RemakeImage(Node.Bomb_02, Config.NODE_SIZE, Config.NODE_SIZE);

            Node.Item_00 = UtilityTools.RemakeImage(Node.Item_00, Config.NODE_SIZE, Config.NODE_SIZE);
            Node.Decoration_00 = UtilityTools.RemakeImage(Node.Decoration_00, Config.NODE_SIZE, Config.NODE_SIZE);

            Enemy.centerImage_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Enemy/Enemy_Center_00.png")));
            Enemy.centerImage_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Enemy/Enemy_Center_01.png")));
            Enemy.centerImage_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Enemy/Enemy_Center_02.png")));

            Player.centerImage_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Center_00.png")));
            Player.centerImage_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Center_01.png")));
            Player.centerImage_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Center_02.png")));

            Player.rightImage_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Right_00.png")));
            Player.rightImage_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Right_01.png")));
            Player.rightImage_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Right_02.png")));

            Player.leftImage_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Left_00.png")));
            Player.leftImage_01 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Left_01.png")));
            Player.leftImage_02 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/Player/Player_Left_02.png")));

            NPC.NPC_00 = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Images/NPC/NPC_00.png")));


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        main = new Main();
        StartUpdating();
    }

    private static void StartUpdating(){
        new Thread(() -> {
            // FPS Stuff
            long lastTime = System.nanoTime();
            double nsRender = 1000000000.0 / 60;
            double nsUpdate = 1000000000.0 / 240;
            double nsAnimation = 1000000000.0 / 10;
            double deltaRender = 0;
            double deltaUpdate = 0;
            double deltaAnimation = 0;
            int updates = 0;
            int frames = 0;
            long timer = System.currentTimeMillis();

            while(true) {
                long now = System.nanoTime();

                deltaRender += (now - lastTime) / nsRender;
                deltaUpdate += (now - lastTime) / nsUpdate;
                deltaAnimation += (now - lastTime) / nsAnimation;
                lastTime = now;

                if(deltaRender >= 1) {
                    deltaRender--;
                    cPanel.repaint();
                    frames++;
                }

                if (deltaUpdate >= 1) {
                    deltaUpdate--;
                    updates++;
                    Update();
                }

                if (deltaAnimation >= 1) {
                    deltaAnimation--;
                    UpdateAnimation();
                }

                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    main.setTitle("Ticks: " + updates + " FPS: " + frames + " || Player Array Cord X: " + player.arrayCordX + " Player Array Y: " + player.arrayCordY + " || Render Time:  " + cPanel.RenderTime);
                    updates = 0;
                    frames = 0;
                }
            }

        }).start();
    }
    public static void UpdateAnimation(){
        List<Enemy> enemyToRemoveList = new ArrayList<>();

        for (Enemy enemy : enemies) {

            if (enemy.currentHealth <= 0) {
                enemyToRemoveList.add(enemy);
                continue;
            }

            enemy.Move();
            enemy.animationStage++;
            if (enemy.animationStage >= 4)
                enemy.animationStage = 0;
        }

        enemies.removeAll(enemyToRemoveList);
        enemyToRemoveList.clear();

        for (NPC npc : npcs) {
            npc.Move();
            npc.animationStage++;
            if (npc.animationStage >= 4)
                npc.animationStage = 0;
        }


        player.animationStage++;
        if (player.animationStage >= 4)
            player.animationStage = 0;
    }

    public static void SaveMap(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Plik.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(nodes);
            objectOutputStream.close();
        }
        catch (Exception ignored){

        }
    }

    public static void LoadMap(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Plik.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Node[][] localNodes = (Node[][]) objectInputStream.readObject();
            objectInputStream.close();

            for (int i = 0; i < Config.SIZE_WIDTH; i++)
                for (int j = 0; j < Config.SIZE_HEIGHT; j++)
                    nodes[i][j] = localNodes[i][j];

        }
        catch (Exception ignored){

        }
    }

    public static void Update(){

        try {

            if (keyEventList.size() > 0) {
                for (Character character : keyEventList) {
                    switch (character) {
                        case 'w' -> player.velY = -Config.PLAYER_MAX_SPEED_Y;
                        case 's' -> player.velY = Config.PLAYER_MAX_SPEED_Y;
                        case 'a' -> player.velX = -Config.PLAYER_MAX_SPEED_X;
                        case 'd' -> player.velX = Config.PLAYER_MAX_SPEED_X;
                        case 'h' -> SaveMap();
                        case 'x' -> {
                            if (System.currentTimeMillis() - lastBombDrop > 1000) {
                                lastBombDrop = System.currentTimeMillis();
                                bombs.add(new Bomb(0, 0,Config.NODE_SIZE - 32, Config.NODE_SIZE - 32));
                                bombs.get(bombs.size() - 1).DropBomb();
                            }
                        }
                    }
                }
            }
            player.Move();
        }
        catch (Exception ignored) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        keyEventList.add(e.getKeyChar());
    }


    @Override
    public synchronized void keyReleased(KeyEvent e) {
        keyEventList.remove(e.getKeyChar());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      //  System.out.println("Eeeeoooo");
        System.out.println(e.getWheelRotation());
        Config.NODE_SIZE -= e.getWheelRotation();
        for (int i = 0; i < Config.SIZE_WIDTH; i++) {
            for (int j = 0; j < Config.SIZE_HEIGHT; j++) {
                nodes[i][j].UpdateNodeSize();

            }
        }
    }
}
