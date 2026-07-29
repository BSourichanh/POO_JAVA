package fr.campus.poojava.game.board;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une case individuelle sur le plateau de jeu.
 * Contient les joueurs présents, les ennemis, et les équipements/potions déposés.
 *
 * @author BSourichanh
 */
public class Cell {
	private final List<Character> players = new ArrayList<>();
	private final List<Enemy> enemies = new ArrayList<>();
	private final List<OffensiveEquipment> offEquip = new ArrayList<>();
	private final List<DefensiveEquipment> defEquip = new ArrayList<>();
	private final int id;
	
	/**
	 * Constructeur d'une case de plateau.
	 *
	 * @param id L'identifiant/position de la case (0 à 62).
	 */
	public Cell (int id) {
		this.id = id;
	}
	
	/**
	 * @return La liste des joueurs actuellement sur la case.
	 */
	public List<Character> getPlayers () {
		return this.players;
	}
	
	/**
	 * @return La liste des ennemis sur la case.
	 */
	public List<Enemy> getEnemies () {
		return this.enemies;
	}
	
	/**
	 * @return La liste des équipements offensifs sur la case.
	 */
	public List<OffensiveEquipment> getOffEquip () {
		return this.offEquip;
	}
	
	/**
	 * @return La liste des équipements défensifs (potions) sur la case.
	 */
	public List<DefensiveEquipment> getDefEquip () {
		return this.defEquip;
	}
	
	/**
	 * @param player Le joueur à ajouter sur la case.
	 */
	public void addPlayer (Character player) {
		this.players.add(player);
	}
	
	/**
	 * @param player Le joueur à retirer de la case.
	 */
	public void removePlayer (Character player) {
		this.players.remove(player);
	}
	
	/**
	 * @param potion La potion à déposer sur la case.
	 */
	public void addPotion (DefensiveEquipment potion) {
		this.defEquip.add(potion);
	}
	
	/**
	 * @param potion La potion à retirer de la case.
	 */
	public void removePotion (DefensiveEquipment potion) {
		defEquip.remove(potion);
	}
	
	/**
	 * @param enemy L'ennemi à placer sur la case.
	 */
	public void addEnemy (Enemy enemy) {
		this.enemies.add(enemy);
	}
	
	/**
	 * @param enemy L'ennemi à retirer de la case.
	 */
	public void removeEnemy (Enemy enemy) {
		this.enemies.remove(enemy);
	}
	
	/**
	 * @return true si la case ne contient aucun ennemi, false sinon.
	 */
	public boolean isEnemiesEmpty () {
		return enemies.isEmpty();
	}
	
	/**
	 * @return true si la case ne contient aucune potion, false sinon.
	 */
	public boolean isDefEquipEmpty () {
		return defEquip.isEmpty();
	}
	
	/**
	 * @return La position/identifiant de la case (0-indexé).
	 */
	public int getPos () {
		return this.id;
	}
	
	/**
	 * @return true si la case ne contient aucun équipement offensif, false sinon.
	 */
	public boolean isOffEquipEmpty () {
		return this.offEquip.isEmpty();
	}
	
	/**
	 * @param offEquip L'équipement offensif à déposer sur la case.
	 */
	public void addOffEquip (OffensiveEquipment offEquip) {
		this.offEquip.add(offEquip);
	}
	
	/**
	 * @param offEquip L'équipement offensif à retirer de la case.
	 */
	public void removeOffEquip (OffensiveEquipment offEquip) {
		this.offEquip.remove(offEquip);
	}
}
