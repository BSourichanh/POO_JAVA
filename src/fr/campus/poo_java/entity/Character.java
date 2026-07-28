package fr.campus.poo_java.entity;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;
import fr.campus.poo_java.game.Cell;

import java.util.ArrayList;
import java.util.List;

public class Character {
	public int moveAvailable = 0;
	protected int id;
	protected String name;
	protected Enums.EntityType type;
	protected int strength;
	protected int lifePoints;
	protected List<OffensiveEquipment> offEquipements = new ArrayList<>();
	protected List<DefensiveEquipement> defensiveEquipements = new ArrayList<>();
	protected OffensiveEquipment currentOffEquip;
	protected Cell currentCell;
	protected int pos = 0;
	
	public Character (Enums.EntityType type, String name, int id, Cell cell) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.currentCell = cell;
	}
	
	public void moveEntityToCell (Cell startCell, Cell nextCell) {
		nextCell.addPlayer(this);
		startCell.removePlayer(this);
		currentCell = nextCell;
		this.pos = nextCell.getPos();
	}
	
	public void getInfo () {
		System.out.println("ID : " + this.id + " | Pos : " + this.pos + " | Name : " + this.name + " | Type : " + this.type + " | HP : " + this.lifePoints + " | DMG : " + this.strength);
	}
	
	public int getPos () {
		return this.pos;
	}
	
	public int getId () {
		return this.id;
	}
	
	public Enums.EntityType getEntityType () {
		return this.type;
	}
	
	public String getName () {
		return this.name;
	}
	
	public Enums.EntityType getType () {
		return this.type;
	}
	
	public int getHp () {
		return this.lifePoints;
	}
	
	public void setHp (int hp) {
		this.lifePoints = hp;
	}
	
	public int getDmg () {
		return this.strength;
	}
	
	public void useDefEquip (DefensiveEquipement potion) {
		this.lifePoints += potion.getHp();
		removeDefensiveEquipment(potion);
	}
	
	public void addDefensiveEquipment (DefensiveEquipement defEquip) {
		this.defensiveEquipements.add(defEquip);
	}
	
	public void removeDefensiveEquipment (DefensiveEquipement defEquip) {
		defensiveEquipements.remove(defEquip);
	}
	
	public boolean isDefEquipEmpty () {
		return this.defensiveEquipements.isEmpty();
	}
	
	public boolean isOffEquipEmpty () {
		return this.offEquipements.isEmpty();
	}
	
	public List<DefensiveEquipement> getDefensiveEquipment () {
		return defensiveEquipements;
	}
	
	public List<OffensiveEquipment> getOffensiveEquipment () {
		return offEquipements;
	}
	
	@Override
	public String toString () {
		return (this.name + " : PV : " + this.lifePoints + " DMG : " + this.strength);
	}
	
	public DefensiveEquipement getDefEquipById (int id) {
		if (id > 0 && id <= defensiveEquipements.size())
			return defensiveEquipements.get(id - 1);
		return null;
	}
	
	public void addOffensiveEquipement (OffensiveEquipment offEquip) {
		this.offEquipements.add(offEquip);
	}
	
	public OffensiveEquipment getCurrentOffEquipement () {
		return this.currentOffEquip;
	}
	
	public int setCurrentOffEquip (OffensiveEquipment offEquip) {
		if (offEquip == null)
			return -1;
		switch (offEquip.getType()) {
			case Weapon:
				if (this.type != Enums.EntityType.Guerrier)
					return -1;
				break;
			case Spell:
				if (this.type != Enums.EntityType.Mage)
					return -1;
				break;
		}
		offEquipements.remove(offEquip);
		if (currentOffEquip != null)
			offEquipements.add(currentOffEquip);
		currentOffEquip = offEquip;
		return 1;
	}
	
	public OffensiveEquipment getOffEquipById (int id) {
		if (id >= 0 && id < offEquipements.size())
			return offEquipements.get(id);
		return null;
	}
}
