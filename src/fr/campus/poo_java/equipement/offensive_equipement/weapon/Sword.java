package fr.campus.poo_java.equipement.offensive_equipement.weapon;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.offensive_equipement.Weapon;

public class Sword extends Weapon {
    public Sword() {
        this.dmg = 5;
        this.name = "epée";
        this.type = Enums.OffEquipType.Weapon;
    }
}
