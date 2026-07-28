package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

public class Warrior extends Character {
	public Warrior (String name, int id) {
		super(EntityType.WARRIOR, name, id, 10, 5);
	}

	@Override
	public boolean canEquip (OffensiveEquipment offEquip) {
		return offEquip != null && offEquip.getType() == OffEquipType.WEAPON;
	}
}
