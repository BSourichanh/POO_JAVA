package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.Entity;
import fr.campus.poojava.entity.EntityType;

public class Enemy extends Entity {

	public Enemy (EntityType type, String name, int id, int pos, int lifePoints, int strength) {
		super(type, name, id, pos, lifePoints, strength);
	}

}
