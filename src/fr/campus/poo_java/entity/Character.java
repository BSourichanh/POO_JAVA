package fr.campus.poo_java.entity;

import fr.campus.poo_java.Cell;
import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.defensive_equipement.Potion;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

public class Character {
    protected int id;
    protected String name;
    protected Enums.EntityType type;
    protected int strength;
    protected int lifePoints;
    protected List<OffensiveEquipment> offEquipement = new ArrayList<>();
    protected List<DefensiveEquipement> defensiveEquipement = new ArrayList<>();
    protected Cell currentCell;
    protected int pos = 0;
    public int moveAvailable = 0;

    public Character(Enums.EntityType type, String name, int id, Cell cell) {
    this.type = type;
    this.name = name;
    this.currentCell = cell;
    }


    public void moveEntityToCell(Cell startCell, Cell nextCell) {
        nextCell.addPlayer(this);
        startCell.removePlayer(this);
        currentCell = nextCell;
        this.pos = nextCell.getPos();
    }

    public void getInfo() {
        System.out.println("ID : " + this.id +" | Pos : "+ this.pos +" | Name : " + this.name + " | Type : " + this.type + " | HP : " + this.lifePoints + " | DMG : " + this.strength);
    }

    public int getPos() {
        return this.pos;
    }

    public int getId() {
        return this.id;
    }

    public Enums.EntityType getEntityType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Enums.EntityType getType() {
        return this.type;
    }

    public  int getHp(){return  this.lifePoints;}

    public  void setHp (int hp){
        this.lifePoints = hp;
    }

    public int getDmg (){
        return this.strength;
    }

    public void usePotion(DefensiveEquipement potion) {
        this.lifePoints += potion.getHp();
        removeDefensiveEquipement(potion);
    }

    public void addDefensiveEquipement(DefensiveEquipement defEquip){
        this.defensiveEquipement.add(defEquip);
    }

    public void removeDefensiveEquipement(DefensiveEquipement defEquip) {
        defensiveEquipement.remove(defEquip);
    }

    public boolean isDefEquipEmpty(){
        return this.defensiveEquipement.isEmpty();
    }

    public List<DefensiveEquipement> getDefensiveEquipement(){
        return defensiveEquipement;
    }

    public List<OffensiveEquipment> getOffensiveEquipement(){return offEquipement;}

    @Override
    public String toString() {
        return (this.name + " : PV : " + this.lifePoints + " DMG : " + this.strength);
    }

    public void addPotion(DefensiveEquipement defEquip){
        this.defensiveEquipement.add(defEquip);
    }

    public DefensiveEquipement getPotion(int id)
    {
        if (id > 0)
            return defensiveEquipement.get(id - 1);
        return null;
    }
}
