package fr.campus.poojava.equipment.defensive.potion;

import fr.campus.poojava.equipment.defensive.DefensiveEquipment;

/**
 * Classe abstraite intermédiaire représentant une potion de soin.
 *
 * @author BSourichanh
 */
public abstract class Potion extends DefensiveEquipment {

	/**
	 * Constructeur d'une potion.
	 *
	 * @param name Le nom de la potion.
	 * @param hp   Les points de vie restaurés.
	 */
	public Potion (String name, int hp) {
		super(name, hp);
	}
}
