package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

public class Dragon extends Enemy {

	public Dragon (int pos) {
		super(EntityType.DRAGON, "Dragon", 0, pos, 15, 4);
	}

}
