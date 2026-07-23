package fr.campus.poo_java.equipement.defensive_equipement;

import fr.campus.poo_java.Enums;

public class DefensiveEquipement {
    protected Enums.DefEquip type;
    protected String name;
    protected int hp;

    public Enums.DefEquip getType() {
        return this.type;
    }

    public String getName(){
        return this.name;
    }


    public int getHp() {
        return this.hp;
    }
}
