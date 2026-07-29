package fr.campus.poojava.equipment.offensive.weapon;

import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

/**
 * Classe abstraite intermédiaire représentant une arme physique.
 *
 * @author BSourichanh
 */
public abstract class Weapon extends OffensiveEquipment {
	
	/**
	 * Constructeur d'une arme physique.
	 *
	 * @param name   Le nom de l'arme.
	 * @param damage Le bonus de dégâts.
	 */
	public Weapon (String name, int damage) {
		super(name, damage, OffEquipType.WEAPON);
	}
}
