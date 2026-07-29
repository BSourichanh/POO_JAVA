package fr.campus.poojava.game.battle;

/**
 * Énumération des résultats de jet de dé pour les coups critiques.
 *
 * @author BSourichanh
 */
public enum Crit {
	/**
	 * Coup critique récompensé par +2 dégâts supplémentaires
	 */
	CRITIQUE,
	/**
	 * Échec critique annulant tous les dégâts de l'attaque
	 */
	ECHEC_CRITIQUE,
	/**
	 * Attaque normale
	 */
	NORMAL
}
