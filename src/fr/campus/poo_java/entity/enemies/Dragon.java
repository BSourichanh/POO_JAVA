package fr.campus.poo_java.entity.enemies;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Enemy;

public class Dragon extends Enemy {
	public Dragon () {
		this.type = Enums.EntityType.Dragon;
		this.name = this.type.toString();
		this.lifePoints = 15;
		this.strength = 8;
	}
}
