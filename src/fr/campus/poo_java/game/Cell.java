package fr.campus.poo_java.game;

import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	public List<Character> players = new ArrayList<>();
	public List<Enemy> enemies = new ArrayList<>();
	public List<OffensiveEquipment> offEquip = new ArrayList<>();
	public List<DefensiveEquipement> defEquip = new ArrayList<>();
	private int id;
	
	public Cell (int id) {
		this.id = id;
	}
	
	public void addPlayer (Character player) {
		this.players.add(player);
	}
	
	public void removePlayer (Character player) {
		this.players.remove(player);
	}
	
	public void addPotion (DefensiveEquipement potion) {
		this.defEquip.add(potion);
	}
	
	public void removePotion (DefensiveEquipement potion) {
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
