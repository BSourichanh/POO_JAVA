package fr.campus.poojava.game.board;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private final List<Character> players = new ArrayList<>();
	private final List<Enemy> enemies = new ArrayList<>();
	private final List<OffensiveEquipment> offEquip = new ArrayList<>();
	private final List<DefensiveEquipment> defEquip = new ArrayList<>();
	private final int id;

	public Cell (int id) {
		this.id = id;
	}

	public List<Character> getPlayers () {
		return this.players;
	}

	public List<Enemy> getEnemies () {
		return this.enemies;
	}

	public List<OffensiveEquipment> getOffEquip () {
		return this.offEquip;
	}

	public List<DefensiveEquipment> getDefEquip () {
		return this.defEquip;
	}

	public void addPlayer (Character player) {
		this.players.add(player);
	}

	public void removePlayer (Character player) {
		this.players.remove(player);
	}

	public void addPotion (DefensiveEquipment potion) {
		this.defEquip.add(potion);
	}

	public void removePotion (DefensiveEquipment potion) {
		defEquip.remove(potion);
	}

	public void addEnemy (Enemy enemy) {
		this.enemies.add(enemy);
	}

	public void removeEnemy (Enemy enemy) {
		this.enemies.remove(enemy);
	}

	public boolean isEnemiesEmpty () {
		return enemies.isEmpty();
	}

	public boolean isDefEquipEmpty () {
		return defEquip.isEmpty();
	}

	public int getPos () {
		return this.id;
	}

	public boolean isOffEquipEmpty () {
		return this.offEquip.isEmpty();
	}

	public void addOffEquip (OffensiveEquipment offEquip) {
		this.offEquip.add(offEquip);
	}

	public void removeOffEquip (OffensiveEquipment offEquip) {
		this.offEquip.remove(offEquip);
	}
}
