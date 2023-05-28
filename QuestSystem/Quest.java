package QuestSystem;


import Entity.Player;

import java.util.ArrayList;
import java.util.List;

abstract class Objective {
    String description, title;
    boolean completed;
    int goldReward, expReward;

    Objective (String title, String description, int goldReward, int expReward) {
        this.title = title;
        this.description = description;
        this.goldReward = goldReward;
        this.expReward = expReward;
        completed = false;
    }

    abstract void checkForCompletion(Player player);

}

class DeliveryObjective extends Objective {
    DeliveryObjective (String title, String description, int goldReward, int expReward) {
        super(title, description, goldReward, expReward);
    }

    @Override
    void checkForCompletion(Player player) {

    }
}

class CollectObjective extends Objective {
    CollectObjective (String title, String description, int goldReward, int expReward) {
        super(title, description, goldReward, expReward);
    }

    @Override
    void checkForCompletion(Player player) {

    }
}

class DefeatObjective extends Objective {
    DefeatObjective (String title, String description, int goldReward, int expReward) {
        super(title, description, goldReward, expReward);
    }

    @Override
    void checkForCompletion(Player player) {

    }
}

public class Quest {

    List<Objective> objectiveList;

    Quest(){
        objectiveList = new ArrayList<>();
        objectiveList.add(new CollectObjective("Carrot Collector", "", 1,1));
        objectiveList.add(new DefeatObjective("Carrot Defeater", "",1,1));
        objectiveList.add(new DeliveryObjective("Carrot Deliverer", "",1,1));
    }

}
