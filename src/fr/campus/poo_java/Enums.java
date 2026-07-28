package fr.campus.poo_java;

public class Enums {
	public enum EntityType {
		Guerrier, Mage, Goblin, Sorcier, Dragon
	}
	
	public enum OffEquip {
		Massue, Epée, Eclair, Boule_de_feu
	}
	
	public enum OffEquipType {
		Weapon, Spell
	}
	
	public enum DefEquip {
		PotionPV, GrandePotionPV
	}
	
	public enum GameState {
		Idle, Moving, Inventory, Potion, InBattle, BattleEnd, Flee, End, Finish
	}
	
	public enum BattleState {
		PLAYER_TURN, ENEMY_TURN
	}
	
	public enum Crit {
		Critique, Echec_Critique, Normal
	}
}
