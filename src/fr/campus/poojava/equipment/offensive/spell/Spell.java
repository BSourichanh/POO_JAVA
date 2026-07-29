package fr.campus.poojava.equipment.offensive.spell;

import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

/**
 * Classe abstraite intermédiaire représentant un sort magique.
 *
 * @author BSourichanh
 */
public abstract class Spell extends OffensiveEquipment {

	/**
	 * Constructeur d'un sort magique.
	 *
	 * @param name   Le nom du sort.
	 * @param damage Le bonus de dégâts du sort.
	 */
	public Spell (String name, int damage) {
		super(name, damage, OffEquipType.SPELL);
	}
}
