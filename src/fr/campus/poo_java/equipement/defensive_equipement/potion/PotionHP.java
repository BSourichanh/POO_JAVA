package fr.campus.poo_java.equipement.defensive_equipement.potion;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;


public class PotionHP extends DefensiveEquipement {
	public PotionHP () {
		this.type = Enums.DefEquip.PotionPV;
		this.hp = 2;
		this.name = "P+";
	}
}
