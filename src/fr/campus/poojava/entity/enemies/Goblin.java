package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

/**
 * Représente un ennemi de type Goblin.
 * Caractéristiques : 6 PV, 1 dégât.
 *
 * @author BSourichanh
 */
public class Goblin extends Enemy {
	
	/**
	 * Constructeur d'un Goblin placé à une position donnée.
	 *
	 * @param pos La position sur le plateau.
	 */
	public Goblin (int pos) {
		super(EntityType.GOBLIN, "Goblin", 0, pos, 6, 1);
	}
}
