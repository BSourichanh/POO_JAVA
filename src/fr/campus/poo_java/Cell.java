package fr.campus.poo_java;

import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id = 0;
    public List<Character> players =  new ArrayList<>();
    public List<Enemy> enemies =  new ArrayList<>();
    public List<OffensiveEquipment> offEquip = new ArrayList<>();
    public List<DefensiveEquipement> defEquip = new ArrayList<>();

    Cell(int id) {
        this.id = id;
    }

    public void addPlayer(Character player) {
        this.players.add(player);
    }

    public void removePlayer(Character player){
        this.players.remove(player);
    }

    void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy){this.enemies.remove(enemy);}

    boolean isEmpty(){
        if (players.isEmpty() && enemies.isEmpty())
            return true;
        return false;
    }

    boolean isEnemiesEmpty() {
        return enemies.isEmpty();
    }

    public int getPos() {
        return this.id;
    }
}
