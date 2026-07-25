package fr.campus.poo_java.entity.character;

import fr.campus.poo_java.Cell;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.Enums;

public class Warrior extends Character {
    public Warrior(Enums.EntityType type, String name, int id, Cell cell)
    {
        super(type, name, id, cell);
        this.lifePoints = 10;
        this.strength = 5;
    }
}
