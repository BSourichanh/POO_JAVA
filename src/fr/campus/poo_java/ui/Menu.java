package fr.campus.poo_java.ui;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;
import fr.campus.poo_java.game.Cell;

import java.util.List;
import java.util.Scanner;

public class Menu {
	// Un seul Scanner partagé : un Scanner met System.in en mémoire tampon,
	// donc plusieurs instances se volent les entrées.
	protected static final Scanner sc = new Scanner(System.in);
	public static int textOffset = 3;
	private int currentId;
	
	public void showSeperator () {
		System.out.println("=============================================================" +
				"=============================================================");
	}
	
	// Choix joueurs
	public int chooseClass (int maxPlayer, int id) {
		System.out.println("Joueur " + (id + 1) + " de choisir.\n");
		System.out.println("Classes :\n1) Guerrier\n2) Mage");
		int tmp = requestNb();
		tmp = checkInput(maxPlayer, tmp);
		if (tmp != -1)
			return tmp;
		return chooseClass(maxPlayer, id);
	}
	
	public String requestName () {
		String inputText = requestInput("Entrer votre nom.");
		if (inputText.isEmpty())
			return requestName();
		return inputText;
	}
	
	public int requestNbPlayer (int maxPlayer) {
		String inputText = requestInput("1-2) Combien de joueurs ?");
		if (inputText.isEmpty())
			return requestNbPlayer(maxPlayer);
		else {
			int tmp;
			try {
				tmp = Integer.parseInt(inputText.trim());
			} catch (NumberFormatException e) {
				System.out.println("Erreur, 1-2");
				return requestNbPlayer(maxPlayer);
			}
			if (checkInput(maxPlayer, tmp) != -1)
				return tmp;
			else {
				System.out.println("Erreur, 1-2");
				return requestNbPlayer(maxPlayer);
			}
		}
	}
	
	public int requestNb () {
		String input = requestInput("Entrer votre choix :");
		if (input.isEmpty())
			return -1;
		try {
			return Integer.parseInt(input.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	//Affichage pendant le tour
	public void showCurrentPlayerTurn (Character player) {
		System.out.println("Tour de " + player.getName() + " le " + player.getType() + ".");
	}
	
	public void showPlayerIdleAction () {
		System.out.println("1) Lancer de dée\n2) Utiliser potion\n3) Equipement");
	}
	
	public void requestInputDiceThrow (Character player) {
		requestInput("Entrer) " + player.getName() + " à fait un lancer de dée de "+ player.moveAvailable);
	}
	
	public void showMoveAvailable (Character player) {
		System.out.println("Déplacement disponible : " + player.moveAvailable + "\n");
	}
	
	public Enums.GameState requestInputAction (Character player) {
		System.out.println("""
				Entrer) Avance de une case
				1) Inventaire
				2) Potion""");
		int input = requestNb();
		
		if (input == 1) {
			if (player.isOffEquipEmpty()) {
				System.out.println("Inventaire vide.");
				return requestInputAction(player);
			} else {
				return Enums.GameState.Inventory;
			}
		} else if (input == 2) {
			if (player.isDefEquipEmpty()) {
				System.out.println("Inventaire vide.");
				return requestInputAction(player);
			} else {
				return Enums.GameState.Potion;
			}
		} else
			return Enums.GameState.Moving;
	}
	
	public void showPickDefEquip (Character player, DefensiveEquipement defEquip) {
		requestInput("Enter) " + player.getName()
				+ " le " + player.getType()
				+ " ramasse " + defEquip.getName()
		);
	}
	
	public void showPickOffEquip (Character player, OffensiveEquipment offEquip) {
		requestInput(player.getName()
				+ " le " + player.getType()
				+ " ramasse " + offEquip.getType()
		);
	}
	
	public void showInvalideItemType () {
		requestInput("Entrer) L'item ne peut pas être équipé pour votre classe");
	}
	
	//Affichage Battle
	
	
	//Affichage fin de tour / jeu
	public void showGameOver () {
		requestInput("Entrer) Tous les joueurs sont morts, fin de la partie.");
	}
	
	public void showPlayerEndTurn (Character player) {
		System.out.println("Enter) Fin de tour de " + player.getName() + " le " + player.getEntityType());
	}
	
	public void showPlayerFinish (Character player) {
		System.out.println("Joueur " + player.getName() + " le " + player.getType() + " à atteinds la dernière case.");
	}
	
	public void showEndGame () {
		System.out.println("Fin du jeu");
	}
	
	//Requête input
	protected int checkInput (int end, int input) {
		if (input >= 1 && input <= end)
			return input;
		else
			return -1;
	}
	
	public String requestInput (String message) {
		System.out.println(message);
		System.out.print("> ");
		String input = sc.nextLine();
		System.out.print("\n");
		return input;
	}
	
	public void showWrongChoice () {
		System.out.println("Choix invalide");
	}
	
	//=================Affichage info
	public void printSeparatorData (int nbInCell) {
		if (currentId < nbInCell) {
			System.out.print("|");
			currentId++;
		}
	}
	
	public void printPlayers (List<Character> players, int nbInCell) {
		for (Character tmp : players) {
			if (tmp.getName().length() < textOffset)
				System.out.print(tmp.getName());
			else
				System.out.print(tmp.getName().substring(0, textOffset));
			printSeparatorData(nbInCell);
		}
	}
	
	public void printEnemies (List<Enemy> enemies, int nbInCell) {
		for (Enemy enemy : enemies) {
			System.out.print(enemy.getType().toString().substring(0, textOffset));
			printSeparatorData(nbInCell);
		}
	}
	
	public void printDefEquip (List<DefensiveEquipement> defEquip, int nbInCell) {
		for (DefensiveEquipement defensiveEquipement : defEquip) {
			System.out.print(defensiveEquipement.getName());
			printSeparatorData(nbInCell);
		}
	}
	
	public void printOffEquip (List<OffensiveEquipment> offEquip, int nbInCell) {
		for (OffensiveEquipment offensiveEquipment : offEquip) {
			System.out.print(offensiveEquipment.getName().substring(0, textOffset));
			printSeparatorData(nbInCell);
		}
	}
	
	public void showCellsData (Cell[] cellTable, int maxCell) {
		for (int i = 0; i < maxCell; i++) {
			System.out.print("[");
			List<Character> players = cellTable[i].players;
			List<Enemy> enemies = cellTable[i].enemies;
			List<DefensiveEquipement> defEquip = cellTable[i].defEquip;
			List<OffensiveEquipment> offEquip = cellTable[i].offEquip;
			
			int tValue = players.size() + enemies.size() + defEquip.size() + offEquip.size() - 1;
			currentId = 0;
			printPlayers(players, tValue);
			printEnemies(enemies, tValue);
			printDefEquip(defEquip, tValue);
			printOffEquip(offEquip, tValue);
			
			System.out.print("]");
		}
		System.out.print("\n");
	}
	
	public void showAllData (Cell[] cellsTable) {
		for (Cell cell : cellsTable) {
			for (Character character : cell.players) {
				System.out.print("Character | id : " + character.getId()
						+ " | pos : " + character.getPos()
						+ " | name : " + character.getName()
						+ " | type : " + character.getType()
						+ " | hp : " + character.getHp()
						+ " | dmg : " + character.getDmg()
						+ " |"
				);
				if (!character.isDefEquipEmpty()) {
					System.out.print(" defEquip [ ");
					for (DefensiveEquipement defEquip : character.getDefensiveEquipment()) {
						System.out.print(defEquip.getName()
								+ " type : " + defEquip.getType()
								+ " hp : " + defEquip.getHp()
								+ ", ");
					}
					System.out.print("]\n");
				} else if (!character.isOffEquipEmpty()) {
					System.out.print(" offEquip [");
					for (OffensiveEquipment offEquip : character.getOffensiveEquipment()) {
						System.out.print(offEquip.getName()
								+ " type : " + offEquip.getType()
								+ " dmg : " + offEquip.getDamage()
								+ ",  ");
					}
					System.out.print("]\n");
				} else
					System.out.print("\n");
			}
			for (Enemy enemy : cell.enemies) {
				System.out.print("Ennemi | id : " + enemy.getId()
						+ " | pos : " + enemy.getPos()
						+ " | type : " + enemy.getType()
						+ " | hp : " + enemy.getHp()
						+ " | dmg : " + enemy.getDmg()
						+ "\n"
				);
			}
			for (DefensiveEquipement defEquip : cell.defEquip) {
				System.out.print("Def | Type : " + defEquip.getType()
						+ " | hp : " + defEquip.getHp()
						+ "\n"
				);
			}
			for (OffensiveEquipment offEquip : cell.offEquip) {
				System.out.print("Offensive equipement | name : " + offEquip.getName()
						+ " | type : " + offEquip.getType()
						+ " | dmg : " + offEquip.getDamage()
						+ "\n"
				);
			}
		}
	}
	
	public void showCurrentPlayer (Character player) {
		System.out.print("Tour de "
				+ player.getName()
				+ " | type : " + player.getType()
				+ " | hp : " + player.getHp()
				+ " | dmg : " + player.getDmg()
				+ " |"
		);
		if (!player.isDefEquipEmpty()) {
			System.out.print(" defEquip [ ");
			for (DefensiveEquipement defEquip : player.getDefensiveEquipment()) {
				System.out.print(defEquip.getName()
						+ " type : " + defEquip.getType()
						+ " hp : " + defEquip.getHp()
						+ ", ");
			}
		}
		if (player.getCurrentOffEquipement() != null)
			System.out.print(" Equipement : " + player.getCurrentOffEquipement().getName() + " |");
		if (!player.isOffEquipEmpty()) {
			System.out.print(" offEquip [");
			for (OffensiveEquipment offEquip : player.getOffensiveEquipment()) {
				System.out.print(offEquip.getName()
						+ " type : " + offEquip.getType()
						+ " dmg : " + offEquip.getDamage()
						+ ",  ");
			}
		}
		
		System.out.print("\n");
	}
	
	public boolean showDefEquips (Character player) {
		if (player.getDefensiveEquipment().isEmpty()) {
			requestInput("Entrer) Pas de potion.");
			return false;
		}
		System.out.println("Potion : ");
		for (int i = 0; i < player.getDefensiveEquipment().size(); i++) {
			System.out.println((i + 1) + ") " + player.getDefensiveEquipment().get(i).getType());
		}
		return true;
	}
	
	public boolean showOffEquips (Character player) {
		
		boolean val;
		if (player.getCurrentOffEquipement() != null) {
			System.out.println("Equipé : " + player.getCurrentOffEquipement().getName()
					+ " | type : " + player.getCurrentOffEquipement().getType()
					+ " | dmg : " + player.getCurrentOffEquipement().getDamage()
			);
		} else {
			System.out.println("Aucun objet équipé");
		}
		if (player.getOffensiveEquipment().isEmpty()) {
			requestInput("Entrer) Inventaire vide.");
			val = false;
		} else {
			for (int i = 0; i < player.getOffensiveEquipment().size(); i++) {
				System.out.println((i + 1) + ") " + player.getOffensiveEquipment().get(i).getName());
			}
			System.out.print("\n");
			val = true;
		}
		return val;
	}
	
	public void showHeader (Character player, Cell[] cellTable, int maxCell) {
		this.showSeperator();
		this.showCurrentPlayer(player);
		this.showCellsData(cellTable, maxCell);
		this.showSeperator();
	}
}
