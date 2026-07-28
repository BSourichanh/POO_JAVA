package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;

public class Goblin extends Enemy {
	private static int nextId = 0;
	
	public Goblin (int pos) {
		super(Enums.EntityType.Goblin, Enums.EntityType.Goblin.toString(), nextId++, pos, 5, 3);
	}
}
