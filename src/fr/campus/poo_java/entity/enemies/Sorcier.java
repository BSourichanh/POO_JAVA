package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;

public class Sorcier extends Enemy {
	private static int nextId = 0;
	
	public Sorcier (int pos) {
		super(Enums.EntityType.Sorcier, Enums.EntityType.Sorcier.toString(), nextId++, pos, 8, 5);
	}
}
