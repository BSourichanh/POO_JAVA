package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;

public class Dragon extends Enemy {
	private static int nextId = 0;
	
	public Dragon (int pos) {
		super(Enums.EntityType.Dragon, Enums.EntityType.Dragon.toString(), nextId++, pos, 15, 8);
	}
}
