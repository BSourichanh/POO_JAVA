package fr.campus.poo_java.entity.character;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.game.cell.Cell;

public class Wizard extends Character {
	public Wizard (Enums.EntityType type, String name, int id, Cell cell) {
		super(type, name, id, cell);
	}
}
