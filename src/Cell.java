import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id = 0;
    private List<Player> players =  new ArrayList<Player>();
    private List<Ennemy> ennemies =  new ArrayList<Ennemy>();

    void addPlayer(Player player) {
        this.players.add(player);
    }

    void removePlayer(Player player){
        this.players.remove(player);
    }
}
