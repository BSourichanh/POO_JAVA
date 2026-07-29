package fr.campus.poojava;

import fr.campus.poojava.game.Game;

/**
 * Classe principale et point d'entrée de l'application Donjons & Dragons.
 *
 * @author BSourichanh
 * @version 1.0
 */
public class Main {
	/**
	 * Point d'entrée de l'application. Initialise et démarre une partie de jeu.
	 *
	 * @param args Arguments de la ligne de commande (non utilisés).
	 */
	public static void main (String[] args) {
		Game game = new Game();
		game.startGame();
	}
}
