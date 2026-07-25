package fr.campus.poo_java.equipement.offensive_equipement.spell;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.offensive_equipement.Spell;

public class FireBall extends Spell {
    public FireBall(){
        this.dmg = 7;
        this.name = "boule de feu";
        this.type = Enums.OffEquipType.Spell;
    }
}
