package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.offensive.OffEquipType;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

/**
 * Représente la classe de héros Mage (Wizard).
 * Caractéristiques : 7 PV initiaux, 7 d'attaque de base, utilise uniquement des sorts (SPELL).
 *
 * @author BSourichanh
 */
public class Wizard extends Character {
	
	/**
	 * Constructeur d'un Mage.
	 *
	 * @param name Le nom du mage.
	 * @param id   L'identifiant unique du joueur.
	 */
	public Wizard (String name, int id) {
		super(EntityType.WIZARD, name, id, 7, 7);
	}
	
	/**
	 * Détermine si le mage peut équiper un équipement offensif (uniquement les sorts).
	 *
	 * @param offEquip L'équipement offensif à tester.
	 * @return true si l'équipement est de type SPELL, false sinon.
	 */
	@Override
	public boolean canEquip (OffensiveEquipment offEquip) {
		return offEquip != null && offEquip.getType() == OffEquipType.SPELL;
	}
}
