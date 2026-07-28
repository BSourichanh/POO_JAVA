package fr.campus.poojava.entity;

public abstract class Entity {
	protected int id;
	protected String name;
	protected EntityType type;
	protected int pos;
	protected int lifePoints;
	protected int maxLifePoints;
	protected int strength;

	public Entity () {}

	public Entity (EntityType type, String name, int id, int pos, int lifePoints, int strength) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.pos = pos;
		this.lifePoints = lifePoints;
		this.maxLifePoints = lifePoints;
		this.strength = strength;
	}

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public EntityType getType () {
		return type;
	}

	public void setType (EntityType type) {
		this.type = type;
	}

	public int getPos () {
		return pos;
	}

	public void setPos (int pos) {
		this.pos = pos;
	}

	public int getHp () {
		return lifePoints;
	}

	public int getMaxHp () {
		return maxLifePoints > 0 ? maxLifePoints : lifePoints;
	}

	public void setHp (int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public int getDmg () {
		return strength;
	}

	public void setDmg (int strength) {
		this.strength = strength;
	}
}
