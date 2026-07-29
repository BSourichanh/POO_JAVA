package fr.campus.poojava.game;

/**
 * Énumération des états généraux de la machine à états du jeu.
 *
 * @author BSourichanh
 */
public enum GameState {
	/** En attente de l'action de lancer de dé */
	IDLE,
	/** Phase de déplacement sur le plateau */
	MOVING,
	/** Gestion de l'inventaire des armes/sorts */
	INVENTORY,
	/** Utilisation d'une potion de soin */
	POTION,
	/** Combat engagé */
	IN_BATTLE,
	/** Fin d'un affrontement */
	BATTLE_END,
	/** Fuite réussie d'un combat */
	FLEE,
	/** Fin du tour du joueur courant */
	END,
	/** Partie terminée (victoire ou Game Over) */
	FINISH
}
