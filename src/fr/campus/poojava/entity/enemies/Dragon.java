package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

/**
 * Représente un ennemi redoutable de type Dragon.
 * Caractéristiques : 15 PV, 4 dégâts.
 *
 * @author BSourichanh
 */
public class Dragon extends Enemy {

	/**
	 * Constructeur d'un Dragon placé à une position donnée.
	 *
	 * @param pos La position sur le plateau.
	 */
	public Dragon (int pos) {
		super(EntityType.DRAGON, "Dragon", 0, pos, 15, 4);
	}
}
