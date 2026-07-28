package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

public class Goblin extends Enemy {

	public Goblin (int pos) {
		super(EntityType.GOBLIN, "Goblin", 0, pos, 6, 1);
	}

}
