package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

public class Wizard extends Character {
	public Wizard (String name, int id) {
		super(EntityType.WIZARD, name, id, 7, 7);
	}

	@Override
	public boolean canEquip (OffensiveEquipment offEquip) {
		return offEquip != null && offEquip.getType() == OffEquipType.SPELL;
	}
}
