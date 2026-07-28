package fr.campus.poo_java.equipement.offensive_equipement;

import fr.campus.poo_java.Enums;

public class OffensiveEquipment {
	protected String name;
	protected int dmg;
	protected Enums.OffEquipType type;
	
	public String getName () {
		return this.name;
	}
	
	public int getDamage () {
		return this.dmg;
	}
	
	public Enums.OffEquipType getType () {
		return this.type;
	}
}
