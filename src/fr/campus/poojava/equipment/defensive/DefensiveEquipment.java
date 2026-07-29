package fr.campus.poojava.equipment.defensive;

/**
 * Classe abstraite de base représentant un équipement défensif (ex: Potion de soin).
 *
 * @author BSourichanh
 */
public abstract class DefensiveEquipment {
	/**
	 * Nom de l'équipement défensif
	 */
	protected String name;
	
	/**
	 * Points de santé restaurés par l'équipement
	 */
	protected int hp;
	
	/**
	 * Constructeur d'un équipement défensif.
	 *
	 * @param name Le nom de la potion.
	 * @param hp   Les points de soin apportés.
	 */
	public DefensiveEquipment (String name, int hp) {
		this.name = name;
		this.hp = hp;
	}
	
	/**
	 * @return Le nom de l'équipement.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * @param name Le nouveau nom de l'équipement.
	 */
	public void setName (String name) {
		this.name = name;
	}
	
	/**
	 * @return Les points de vie restaurés.
	 */
	public int getHp () {
		return hp;
	}
	
	/**
	 * @param hp Les nouveaux points de vie restaurés.
	 */
	public void setHp (int hp) {
		this.hp = hp;
	}
}
