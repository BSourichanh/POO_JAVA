package fr.campus.poojava.equipment.offensive;

public abstract class OffensiveEquipment {
	protected String name;
	protected int damage;
	protected OffEquipType type;

	public OffensiveEquipment (String name, int damage, OffEquipType type) {
		this.name = name;
		this.damage = damage;
		this.type = type;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public int getDamage () {
		return damage;
	}

	public void setDamage (int damage) {
		this.damage = damage;
	}

	public OffEquipType getType () {
		return type;
	}

	public void setType (OffEquipType type) {
		this.type = type;
	}
}
