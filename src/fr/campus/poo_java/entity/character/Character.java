package fr.campus.poo_java.entity.character;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Entity;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;
import fr.campus.poo_java.game.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {
	protected int moveAvailable = 0;
	protected List<OffensiveEquipment> offEquipments = new ArrayList<>();
	protected List<DefensiveEquipement> defensiveEquipments = new ArrayList<>();
	protected OffensiveEquipment currentOffEquip;
	protected Cell currentCell;
	
	public Character (Enums.EntityType type, String name, int id, Cell cell) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.currentCell = cell;
		
		switch (type) {
			case Guerrier:
				this.lifePoints = 10;
				this.strength = 5;
				break;
			case Mage:
				this.lifePoints = 7;
				this.strength = 7;
				break;
		}
	}
	
	public int getMoveAvailable () {
		return this.moveAvailable;
	}
	
	public List<DefensiveEquipement> getDefensiveEquipment () {
		return defensiveEquipments;
	}
	public List<OffensiveEquipment> getOffensiveEquipment () {
		return offEquipments;
	}
	public OffensiveEquipment getCurrentOffEquipment () {
		return this.currentOffEquip;
	}
	
	public void setMoveAvailable (int moveAvailable) {
		this.moveAvailable = moveAvailable;
	}
	public void addOffensiveEquipment (OffensiveEquipment offEquip) {
		this.offEquipments.add(offEquip);
	}
	public void addDefensiveEquipment (DefensiveEquipement defEquip) {
		this.defensiveEquipments.add(defEquip);
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
		offEquipments.remove(offEquip);
		if (currentOffEquip != null)
			offEquipments.add(currentOffEquip);
		currentOffEquip = offEquip;
		return 1;
	}
	
	public void decreaseMoveAvailable () {
		this.moveAvailable--;
	}
	public void removeDefensiveEquipment (DefensiveEquipement defEquip) {
		defensiveEquipments.remove(defEquip);
	}
	public void moveEntityToCell (Cell startCell, Cell nextCell) {
		nextCell.addPlayer(this);
		startCell.removePlayer(this);
		currentCell = nextCell;
		this.pos = nextCell.getPos();
	}
	public void useDefEquip (DefensiveEquipement potion) {
		this.lifePoints += potion.getHp();
		removeDefensiveEquipment(potion);
	}
	
	public boolean isDefEquipEmpty () {
		return this.defensiveEquipments.isEmpty();
	}
	public boolean isOffEquipEmpty () {
		return this.offEquipments.isEmpty();
	}
	
	public DefensiveEquipement getDefEquipById (int id) {
		if (id > 0 && id <= defensiveEquipments.size())
			return defensiveEquipments.get(id - 1);
		return null;
	}
	public OffensiveEquipment getOffEquipById (int id) {
		if (id >= 0 && id < offEquipments.size())
			return offEquipments.get(id);
		return null;
	}
	
	
}
