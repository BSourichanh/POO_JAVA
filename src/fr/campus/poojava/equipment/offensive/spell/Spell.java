package fr.campus.poojava.equipment.offensive.spell;

import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

public abstract class Spell extends OffensiveEquipment {

	public Spell (String name, int damage) {
		super(name, damage, OffEquipType.SPELL);
	}

}
