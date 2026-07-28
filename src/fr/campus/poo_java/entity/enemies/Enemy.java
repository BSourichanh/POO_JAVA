package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Entity;

public class Enemy extends Entity {
	
	public Enemy (Enums.EntityType type, String name, int id, int pos, int lifePoints, int strength) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.pos = pos;
		this.lifePoints = lifePoints;
		this.strength = strength;
	}
	
}
