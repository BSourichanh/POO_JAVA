package fr.campus.poojava.game.dice;

/**
 * Interface représentant un dé utilisable pour les tirages aléatoires (déplacement, jets de combat).
 *
 * @author BSourichanh
 */
public interface Dice {
	/**
	 * Effectue un lancer de dé et retourne le résultat obtenu.
	 *
	 * @return La valeur du lancer de dé.
	 */
	int roll ();
}
