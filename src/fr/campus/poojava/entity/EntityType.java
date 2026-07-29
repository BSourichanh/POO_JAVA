package fr.campus.poojava.entity;

/**
 * Énumération représentant les différents types d'entités présentes dans le jeu (Héros et Ennemis)
 * avec leur nom d'affichage en français.
 *
 * @author BSourichanh
 */
public enum EntityType {
	/**
	 * Héros Guerrier
	 */
	WARRIOR("Guerrier"),
	/**
	 * Héros Mage
	 */
	WIZARD("Mage"),
	/**
	 * Ennemi Goblin
	 */
	GOBLIN("Goblin"),
	/**
	 * Ennemi Sorcier
	 */
	SORCERER("Sorcier"),
	/**
	 * Ennemi Dragon
	 */
	DRAGON("Dragon");
	
	private final String displayName;
	
	EntityType (String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return Le nom d'affichage convivial en français.
	 */
	public String getDisplayName () {
		return displayName;
	}
	
	/**
	 * @return Le nom d'affichage convivial en français.
	 */
	@Override
	public String toString () {
		return displayName;
	}
}
