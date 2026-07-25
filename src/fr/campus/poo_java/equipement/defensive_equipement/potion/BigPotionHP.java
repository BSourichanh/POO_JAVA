package fr.campus.poo_java.equipement.defensive_equipement.potion;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;

public class BigPotionHP extends DefensiveEquipement {
    public BigPotionHP() {
        this.type = Enums.DefEquip.GrandePotionPV;
        this.hp = 5;
        this.name = "P++";
    }
}
