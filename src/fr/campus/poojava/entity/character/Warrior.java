package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

/**
 * Représente la classe de héros Guerrier (Warrior).
 * Caractéristiques : 10 PV initiaux, 5 d'attaque de base, utilise uniquement des armes (WEAPON).
 *
 * @author BSourichanh
 */
public class Warrior extends Character {

	/**
	 * Constructeur d'un Guerrier.
	 *
	 * @param name Le nom du guerrier.
	 * @param id   L'identifiant unique du joueur.
	 */
	public Warrior (String name, int id) {
		super(EntityType.WARRIOR, name, id, 10, 5);
	}

	/**
	 * Détermine si le guerrier peut équiper un équipement offensif (uniquement les armes).
	 *
	 * @param offEquip L'équipement offensif à tester.
	 * @return true si l'équipement est de type WEAPON, false sinon.
	 */
	@Override
	public boolean canEquip (OffensiveEquipment offEquip) {
		return offEquip != null && offEquip.getType() == OffEquipType.WEAPON;
	}
}
