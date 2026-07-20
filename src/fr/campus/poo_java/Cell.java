package fr.campus.poo_java;

import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id = 0;
    public List<Character> players =  new ArrayList<Character>();
    public List<Enemy> ennemies =  new ArrayList<>();

    Cell(int id) {
        this.id = id;
    }

    public void addPlayer(Character player) {
        this.players.add(player);
    }

    public void removePlayer(Character player){
        this.players.remove(player);
    }

    void addEnnemy(Enemy ennemy) {
        this.ennemies.add(ennemy);
    }

    boolean isEmpty(){
        if (players.isEmpty() && ennemies.isEmpty())
            return true;
        return false;
    }

    boolean isEnnemiesEmpty() {
        return ennemies.isEmpty();
    }

    public int getPos() {
        return this.id;
    }
}
