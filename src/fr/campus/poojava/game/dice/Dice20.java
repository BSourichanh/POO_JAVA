package fr.campus.poojava.game.dice;

import java.util.Random;

/**
 * Implémentation d'un dé à 20 faces (valeurs de 1 à 20) utilisé pour les jets de critique.
 *
 * @author BSourichanh
 */
public class Dice20 implements Dice {
	private final Random random = new Random();

	/**
	 * Lance le dé à 20 faces.
	 *
	 * @return Un entier aléatoire compris entre 1 et 20 inclus.
	 */
	@Override
	public int roll () {
		return random.nextInt(1, 21);
	}
}
