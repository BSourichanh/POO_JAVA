package fr.campus.poo_java.equipement.offensive_equipement.spell;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.offensive_equipement.Spell;

public class ThunderBolt extends Spell {
	public ThunderBolt () {
		this.dmg = 2;
		this.name = "eclair";
		this.type = Enums.OffEquipType.Spell;
	}
}
