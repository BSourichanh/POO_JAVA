package fr.campus.poojava.equipment.offensive;

/**
 * Classe abstraite de base représentant un équipement offensif (Arme ou Sort).
 *
 * @author BSourichanh
 */
public abstract class OffensiveEquipment {
	/** Nom de l'équipement offensif */
	protected String name;

	/** Dégâts supplémentaires accordés par l'équipement */
	protected int damage;

	/** Type d'équipement offensif (WEAPON ou SPELL) */
	protected OffEquipType type;

	/**
	 * Constructeur d'un équipement offensif.
	 *
	 * @param name   Le nom de l'équipement.
	 * @param damage Le bonus de dégâts.
	 * @param type   Le type d'équipement offensif.
	 */
	public OffensiveEquipment (String name, int damage, OffEquipType type) {
		this.name = name;
		this.damage = damage;
		this.type = type;
	}

	/** @return Le nom de l'équipement. */
	public String getName () {
		return name;
	}

	/** @param name Le nouveau nom. */
	public void setName (String name) {
		this.name = name;
	}

	/** @return Les dégâts supplémentaires accordés. */
	public int getDamage () {
		return damage;
	}

	/** @param damage Les nouveaux dégâts. */
	public void setDamage (int damage) {
		this.damage = damage;
	}

	/** @return Le type d'équipement offensif. */
	public OffEquipType getType () {
		return type;
	}

	/** @param type Le nouveau type d'équipement. */
	public void setType (OffEquipType type) {
		this.type = type;
	}
}
