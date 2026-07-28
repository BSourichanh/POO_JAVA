package fr.campus.poojava.equipment.defensive;

public abstract class DefensiveEquipment {
	protected String name;
	protected int hp;

	public DefensiveEquipment (String name, int hp) {
		this.name = name;
		this.hp = hp;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public int getHp () {
		return hp;
	}

	public void setHp (int hp) {
		this.hp = hp;
	}
}
