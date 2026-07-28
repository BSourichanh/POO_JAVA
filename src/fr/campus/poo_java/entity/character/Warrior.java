package fr.campus.poo_java.entity.character;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.game.cell.Cell;

public class Warrior extends Character {
	public Warrior (Enums.EntityType type, String name, int id, Cell cell) {
		super(type, name, id, cell);
	}
}
