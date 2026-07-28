package fr.campus.poo_java.entity.character;

import fr.campus.poo_java.Cell;
import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Character;

public class Wizard extends Character {
	public Wizard (Enums.EntityType type, String name, int id, Cell cell) {
		super(type, name, id, cell);
		this.lifePoints = 7;
		this.strength = 7;
		
	}
}
