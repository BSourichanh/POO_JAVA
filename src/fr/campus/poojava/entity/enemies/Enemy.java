package fr.campus.poojava.entity.enemies;

import fr.campus.poojava.entity.Entity;
import fr.campus.poojava.entity.EntityType;

/**
 * Classe de base représentant un ennemi rencontré sur le plateau de jeu.
 *
 * @author BSourichanh
 */
public class Enemy extends Entity {

	/**
	 * Constructeur d'un ennemi.
	 *
	 * @param type       Le type d'ennemi.
	 * @param name       Le nom de l'ennemi.
	 * @param id         L'identifiant unique.
	 * @param pos        La position sur le plateau.
	 * @param lifePoints Les points de vie initiaux.
	 * @param strength   La force d'attaque de l'ennemi.
	 */
	public Enemy (EntityType type, String name, int id, int pos, int lifePoints, int strength) {
		super(type, name, id, pos, lifePoints, strength);
	}
}
