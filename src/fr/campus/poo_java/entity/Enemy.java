package fr.campus.poo_java.entity;

import fr.campus.poo_java.Cell;
import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

public class Enemy {
    protected int id;
    protected String name;
    protected Enums.EntityType type;
    protected int strength;
    protected int lifePoints;
    protected Cell currentCell;
    protected int pos = 0;

    public void getInfo() {
        System.out.println("ID : " + this.id +" | Pos : "+ this.pos +" | Name : " + this.name + " | Type : " + this.type + " | HP : " + this.lifePoints + " | DMG : " + this.strength);
    }

    public Enums.EntityType getType() {
        return this.type;
    }

    public String getName() {return this.name;}

    public  int getHp(){return  this.lifePoints;}

    public  void setHp (int hp){this.lifePoints = hp;}

    public int getDmg (){return this.strength;}

    @Override
    public String toString() {
        return (this.name + " : PV : " + this.lifePoints + " DMG : " + this.strength);
    }
}
