package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Enemy;

public class Goblin extends Enemy {
    public Goblin(){
        this.type = Enums.EntityType.Goblin;
        this.name = this.type.toString();
        this.lifePoints = 5;
        this.strength = 3;
    }
}
