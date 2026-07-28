package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.Entity;
import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;
import fr.campus.poojava.game.board.Cell;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity {
	protected int moveAvailable = 0;
	protected List<OffensiveEquipment> offEquipments = new ArrayList<>();
	protected List<DefensiveEquipment> defensiveEquipments = new ArrayList<>();
	protected OffensiveEquipment currentOffEquip;

	public Character (EntityType type, String name, int id, int initialHp, int initialStrength) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.lifePoints = initialHp;
		this.strength = initialStrength;
		this.pos = 0;
	}

	public abstract boolean canEquip (OffensiveEquipment offEquip);

	public int getMoveAvailable () {
		return this.moveAvailable;
	}

	public List<DefensiveEquipment> getDefensiveEquipment () {
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

	public void addDefensiveEquipment (DefensiveEquipment defEquip) {
		this.defensiveEquipments.add(defEquip);
	}

	public int setCurrentOffEquip (OffensiveEquipment offEquip) {
		if (offEquip == null || !canEquip(offEquip)) {
			return -1;
		}
		offEquipments.remove(offEquip);
		if (currentOffEquip != null) {
			offEquipments.add(currentOffEquip);
		}
		currentOffEquip = offEquip;
		return 1;
	}

	public void decreaseMoveAvailable () {
		this.moveAvailable--;
	}

	public void removeDefensiveEquipment (DefensiveEquipment defEquip) {
		defensiveEquipments.remove(defEquip);
	}

	public void moveEntityToCell (Cell startCell, Cell nextCell) {
		if (nextCell != null) {
			nextCell.addPlayer(this);
			if (startCell != null) {
				startCell.removePlayer(this);
			}
			this.pos = nextCell.getPos();
		}
	}

	public void useDefEquip (DefensiveEquipment potion) {
		this.lifePoints += potion.getHp();
		removeDefensiveEquipment(potion);
	}

	public boolean isDefEquipEmpty () {
		return this.defensiveEquipments.isEmpty();
	}

	public boolean isOffEquipEmpty () {
		return this.offEquipments.isEmpty();
	}

	public DefensiveEquipment getDefEquipById (int id) {
		if (id > 0 && id <= defensiveEquipments.size()) {
			return defensiveEquipments.get(id - 1);
		}
		return null;
	}

	public OffensiveEquipment getOffEquipById (int id) {
		if (id >= 0 && id < offEquipments.size()) {
			return offEquipments.get(id);
		}
		return null;
	}
}
