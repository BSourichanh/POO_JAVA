package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.EntityType;

/**
 * Représente un ennemi de type Sorcier (Sorcerer).
 * Caractéristiques : 9 PV, 2 dégâts.
 *
 * @author BSourichanh
 */
public class Sorcerer extends Enemy {

	/**
	 * Constructeur d'un Sorcier placé à une position donnée.
	 *
	 * @param pos La position sur le plateau.
	 */
	public Sorcerer (int pos) {
		super(EntityType.SORCERER, "Sorcier", 0, pos, 9, 2);
	}
}
