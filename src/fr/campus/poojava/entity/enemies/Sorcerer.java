package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

public class Sorcerer extends Enemy {

	public Sorcerer (int pos) {
		super(EntityType.SORCERER, "Sorcier", 0, pos, 9, 2);
	}

}
