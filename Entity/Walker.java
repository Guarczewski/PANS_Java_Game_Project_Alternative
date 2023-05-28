package Entity;

import CustomUtilityTools.Config;
import Index.Main;
import Blocks.Node;
import Blocks.PathNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Walker {
    public final PathNode[][] myNodes;
    public Entity entity;
    private final List <PathNode> myNodesToVisit, correctPath;
    private PathNode myStart, myGoal;
    public int currentCordX, currentCordY, nextCordX, nextCordY;
    private boolean updatedList = false, running = false;

    public Walker(PathNode[][] nodes, PathNode Start, PathNode End, Entity entity) {
        myNodesToVisit = new ArrayList<>();
        myNodes = new PathNode[Config.SIZE_WIDTH][Config.SIZE_HEIGHT];
        correctPath = new ArrayList<>();
        this.entity = entity;
        for (int i = 0; i < Config.SIZE_WIDTH; i++) {
            for (int j = 0; j < Config.SIZE_HEIGHT; j++) {
                myNodes[i][j] = new PathNode(nodes[i][j].cordX, nodes[i][j].cordY);

            }
        }

        this.myStart = new PathNode(Start.cordX, Start.cordY);
        this.myGoal = new PathNode(End.cordX, End.cordY);
        Reset();
    }

    public void Render(Graphics2D graphics2D){
        graphics2D.setColor(Color.PINK);
        graphics2D.fillOval(currentCordX - Main.offsetX, currentCordY - Main.offsetY, Config.NODE_SIZE, Config.NODE_SIZE);
    }

    public void setStartAndGoal(PathNode myStart, PathNode myGoal){
        this.myStart = myStart;
        this.myGoal = myGoal;
    }

    public void Reset(){
        for (int i = 0; i < Config.SIZE_WIDTH; i++) {
            for (int j = 0; j < Config.SIZE_HEIGHT; j++) {
                myNodes[i][j].ResetNode();
            }
        }
        myNodesToVisit.clear();
        myNodesToVisit.add(myNodes[myStart.cordX][myStart.cordY]);
        myNodesToVisit.get(0).EvaluateCosts(myStart,myGoal);
        Start();
    }

    public void Start() {
        new Thread(() -> {
            try {
                while (myNodesToVisit.get(0).hCost != 0) {
                    PathNode temp = myNodesToVisit.get(0);
                    myNodesToVisit.remove(myNodesToVisit.get(0));
                    CheckAround(temp);

                }
                correctPath.clear();
                myNodesToVisit.get(0).GetParent(correctPath);
                updatedList = true;
                Walk();
            }
            catch (Exception ignored) {

            }
        }).start();
    }

    public void Walk(){
        if (!running) {
            running = true;
            new Thread(() -> {
                try {
                    for (int i = correctPath.size() - 1; i >= 0; i--) {

                        currentCordX = correctPath.get(i).cordX * Config.NODE_SIZE;
                        currentCordY = correctPath.get(i).cordY * Config.NODE_SIZE;

                        if (i - 1 >= 0) {
                            nextCordX = correctPath.get(i - 1).cordX * Config.NODE_SIZE;
                            nextCordY = correctPath.get(i - 1).cordY * Config.NODE_SIZE;
                        }

                        // Smooth Walking
                        while (currentCordX != nextCordX || currentCordY != nextCordY) {

                            if (currentCordX < nextCordX) {
                                currentCordX++;
                                entity.velX = 1;
                            }
                            else if (currentCordX > nextCordX) {
                                currentCordX--;
                                entity.velX = -1;
                            }
                            else {
                                entity.velX = 0;
                            }

                            if (currentCordY < nextCordY) {
                                currentCordY++;
                                entity.velY = 1;
                            }
                            else if (currentCordY > nextCordY) {
                                currentCordY--;
                                entity.velY = -1;
                            }
                            else {
                                entity.velY = 0;
                            }


                            //entity.UpdateHitBoxCord(currentCordX,currentCordY);


                            try {
                                Thread.sleep(5);
                            } catch (Exception e) {
                              //  System.out.println("123");
                            }
                        }

                        //System.out.println("123");

                        if (updatedList) {
                            i = correctPath.size() - 1;
                            updatedList = false;
                        }
                        try {
                            Thread.sleep(20);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    running = false;
                    entity.velX = entity.velY = 0;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    running = false;
                    entity.velX = entity.velY = 0;
                }
            }).start();
        }
    }
    public void CheckAround(PathNode Me){
        int myX = Me.cordX;
        int myY = Me.cordY;

        Me.EvaluateCosts(myStart,myGoal);

        if (myX -1 >= 0) {
            if (myNodes[myX - 1][myY].open && Main.pathNodes[myX -1][myY].walkable) {
                myNodes[myX - 1][myY].EvaluateCosts(myStart, myGoal);
                myNodes[myX - 1][myY].SetParent(Me);
                myNodesToVisit.add(myNodes[myX - 1][myY]);
            }
        }

        if (myY - 1 >= 0){
            if (myNodes[myX][myY - 1].open && Main.pathNodes[myX][myY-1].walkable) {
                myNodes[myX][myY - 1].EvaluateCosts(myStart, myGoal);
                myNodes[myX][myY - 1].SetParent(Me);
                myNodesToVisit.add(myNodes[myX][myY - 1]);
            }
        }

        if (myX + 1 < Config.SIZE_WIDTH) {
            if (myNodes[myX + 1][myY].open && Main.pathNodes[myX + 1][myY].walkable) {
                myNodes[myX + 1][myY].EvaluateCosts(myStart, myGoal);
                myNodes[myX + 1][myY].SetParent(Me);
                myNodesToVisit.add(myNodes[myX + 1][myY]);
            }
        }

        if (myY + 1 < Config.SIZE_HEIGHT){
            if (myNodes[myX][myY + 1].open && Main.pathNodes[myX][myY + 1].walkable) {
                myNodes[myX][myY + 1].EvaluateCosts(myStart, myGoal);
                myNodes[myX][myY + 1].SetParent(Me);
                myNodesToVisit.add(myNodes[myX][myY + 1]);
            }
        }

        Collections.sort(myNodesToVisit);

    }


}
