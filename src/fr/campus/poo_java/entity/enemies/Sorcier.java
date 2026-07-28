package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Enemy;

public class Sorcier extends Enemy {
	public Sorcier () {
		this.type = Enums.EntityType.Sorcier;
		this.name = this.type.toString();
		this.lifePoints = 8;
		this.strength = 5;
	}
}
