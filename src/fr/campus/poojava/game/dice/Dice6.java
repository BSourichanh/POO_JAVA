package fr.campus.poojava.game.dice;

import java.util.Random;

/**
 * Implémentation d'un dé standard à 6 faces (valeurs de 1 à 6).
 *
 * @author BSourichanh
 */
public class Dice6 implements Dice {
	private final Random random = new Random();

	/**
	 * Lance le dé à 6 faces.
	 *
	 * @return Un entier aléatoire compris entre 1 et 6 inclus.
	 */
	@Override
	public int roll () {
		return random.nextInt(1, 7);
	}
}
