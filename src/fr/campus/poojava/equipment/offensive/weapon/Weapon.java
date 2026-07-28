package fr.campus.poojava.equipment.offensive.weapon;

import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

public abstract class Weapon extends OffensiveEquipment {

	public Weapon (String name, int damage) {
		super(name, damage, OffEquipType.WEAPON);
	}

}
