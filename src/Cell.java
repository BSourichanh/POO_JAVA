import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id = 0;
    public List<Entity> players =  new ArrayList<Entity>();
    public List<Entity> ennemies =  new ArrayList<Entity>();

    void addPlayer(Entity player) {
        this.players.add(player);
    }

    void removePlayer(Entity player){
        this.players.remove(player);
    }

    void addEnnemy(Entity ennemy) {
        this.ennemies.add(ennemy);
    }

    boolean isEmpty(){
        if (players.isEmpty() && ennemies.isEmpty())
            return true;
        return false;
    }

    boolean isEnnemiesEmpty()
    {
        if (!ennemies.isEmpty())
        {
            return true;
        }
        return false;
    }
}
