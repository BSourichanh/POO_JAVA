package fr.campus.poojava.ui;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;
import fr.campus.poojava.game.board.Cell;

import java.util.ArrayList;
import java.util.List;

public class BoardRenderer {
	private static final int TEXT_OFFSET = 3;
	private int currentId;

	public void showSeparator () {
		System.out.println(ConsoleTheme.BRIGHT_BLUE + "════════════════════════════════════════════════════════════════════════════════" + ConsoleTheme.RESET);
	}

	public void printSeparatorData (int nbInCell) {
		if (currentId < nbInCell) {
			System.out.print("|");
			currentId++;
		}
	}

	public void printPlayers (List<Character> players, int nbInCell) {
		for (Character tmp : players) {
			if (tmp.getName().length() < TEXT_OFFSET) {
				System.out.print(tmp.getName());
			} else {
				System.out.print(tmp.getName().substring(0, TEXT_OFFSET));
			}
			printSeparatorData(nbInCell);
		}
	}

	public void printEnemies (List<Enemy> enemies, int nbInCell) {
		for (Enemy enemy : enemies) {
			System.out.print(enemy.getType().toString().substring(0, TEXT_OFFSET));
			printSeparatorData(nbInCell);
		}
	}

	public void printDefEquip (List<DefensiveEquipment> defEquip, int nbInCell) {
		for (DefensiveEquipment defensiveEquipment : defEquip) {
			System.out.print(defensiveEquipment.getName().substring(0, Math.min(TEXT_OFFSET, defensiveEquipment.getName().length())));
			printSeparatorData(nbInCell);
		}
	}

	public void printOffEquip (List<OffensiveEquipment> offEquip, int nbInCell) {
		for (OffensiveEquipment offensiveEquipment : offEquip) {
			System.out.print(offensiveEquipment.getName().substring(0, Math.min(TEXT_OFFSET, offensiveEquipment.getName().length())));
			printSeparatorData(nbInCell);
		}
	}

	private String getCellIconAnd2LetterContent (Cell cell) {
		List<Character> players = cell.getPlayers();
		List<Enemy> enemies = cell.getEnemies();
		List<DefensiveEquipment> defEquip = cell.getDefEquip();
		List<OffensiveEquipment> offEquip = cell.getOffEquip();

		if (!players.isEmpty()) {
			Character p = players.get(0);
			String icon = p.getType().toString().toUpperCase().contains("WARRIOR") || p.getType().toString().toLowerCase().contains("guerrier") ? "⚔️" : "🧙";
			String pCode = "J" + (p.getId() + 1);
			return "[" + ConsoleTheme.BRIGHT_GREEN + icon + pCode + ConsoleTheme.RESET + "]  ";
		} else if (!enemies.isEmpty()) {
			Enemy e = enemies.get(0);
			String enemyIcon = ConsoleTheme.SYM_ENEMY;
			String enemyName = e.getName().toLowerCase();
			String eCode = "EN";

			if (enemyName.contains("dragon")) {
				enemyIcon = ConsoleTheme.SYM_DRAGON;
				eCode = "DR";
			} else if (enemyName.contains("gob")) {
				enemyIcon = ConsoleTheme.SYM_GOBLIN;
				eCode = "GB";
			} else if (enemyName.contains("sorc")) {
				enemyIcon = ConsoleTheme.SYM_SORCERER;
				eCode = "SO";
			} else if (e.getName().length() >= 2) {
				eCode = e.getName().substring(0, 2).toUpperCase();
			}

			return "[" + ConsoleTheme.BRIGHT_RED + enemyIcon + eCode + ConsoleTheme.RESET + "]  ";
		} else if (!defEquip.isEmpty()) {
			DefensiveEquipment d = defEquip.get(0);
			String dName = d.getName().toLowerCase();
			String dCode = dName.contains("grande") ? "GP" : "PT";
			return "[" + ConsoleTheme.BRIGHT_CYAN + ConsoleTheme.SYM_POTION + dCode + ConsoleTheme.RESET + "]  ";
		} else if (!offEquip.isEmpty()) {
			OffensiveEquipment o = offEquip.get(0);
			String oName = o.getName().toLowerCase();
			String icon = ConsoleTheme.SYM_WEAPON;
			String oCode = "AR";

			if (oName.contains("épée") || oName.contains("epee") || oName.contains("sword")) {
				icon = "🗡️";
				oCode = "EP";
			} else if (oName.contains("massue") || oName.contains("mace")) {
				icon = "🔨";
				oCode = "MA";
			} else if (oName.contains("éclair") || oName.contains("eclair") || oName.contains("thunder")) {
				icon = "✨";
				oCode = "EC";
			} else if (oName.contains("boule") || oName.contains("fire")) {
				icon = "✨";
				oCode = "FE";
			} else if (o.getName().length() >= 2) {
				oCode = o.getName().substring(0, 2).toUpperCase();
			}

			return "[" + ConsoleTheme.BRIGHT_MAGENTA + icon + oCode + ConsoleTheme.RESET + "]  ";
		} else {
			return "[" + ConsoleTheme.DIM + "    " + ConsoleTheme.RESET + "]  ";
		}
	}

	public void showCellsData (Cell[] cellTable, int maxCell) {
		System.out.println(ConsoleTheme.BOLD + ConsoleTheme.BRIGHT_WHITE + "🗺️  PLATEAU DE JEU (Case 1 à " + maxCell + ") :" + ConsoleTheme.RESET);

		final int CELL_PER_ROW = 10;
		for (int row = 0; row < maxCell; row += CELL_PER_ROW) {
			int end = Math.min(row + CELL_PER_ROW, maxCell);

			StringBuilder lineNum = new StringBuilder();
			StringBuilder lineContent = new StringBuilder();

			for (int i = row; i < end; i++) {
				lineNum.append(String.format(ConsoleTheme.DIM + " [%02d]   " + ConsoleTheme.RESET, i + 1));
				lineContent.append(getCellIconAnd2LetterContent(cellTable[i]));
			}
			System.out.println(lineNum.toString());
			System.out.println(lineContent.toString());
			System.out.println();
		}
	}

	public void showAllData (Cell[] cellsTable) {
		System.out.println(ConsoleTheme.BRIGHT_YELLOW + "=== AUDIT COMPLET DU PLATEAU ===" + ConsoleTheme.RESET);
		for (Cell cell : cellsTable) {
			for (Character character : cell.getPlayers()) {
				System.out.print(ConsoleTheme.BRIGHT_GREEN + "Joueur | id : " + character.getId()
						+ " | pos : " + (character.getPos() + 1)
						+ " | nom : " + character.getName()
						+ " | classe : " + character.getType()
						+ " | hp : " + character.getHp()
						+ " | dmg : " + character.getDmg()
						+ " |" + ConsoleTheme.RESET
				);
				if (!character.isDefEquipEmpty()) {
					System.out.print(" Potion [ ");
					for (DefensiveEquipment defEquip : character.getDefensiveEquipment()) {
						System.out.print(defEquip.getName() + " hp : " + defEquip.getHp() + ", ");
					}
					System.out.print("]\n");
				} else if (!character.isOffEquipEmpty()) {
					System.out.print(" Équipement [");
					for (OffensiveEquipment offEquip : character.getOffensiveEquipment()) {
						System.out.print(offEquip.getName() + " type : " + offEquip.getType() + " dmg : " + offEquip.getDamage() + ",  ");
					}
					System.out.print("]\n");
				} else {
					System.out.print("\n");
				}
			}
			for (Enemy enemy : cell.getEnemies()) {
				System.out.print(ConsoleTheme.BRIGHT_RED + "Ennemi | id : " + enemy.getId() + " | pos : " + (enemy.getPos() + 1) + " | nom : " + enemy.getName() + " | hp : " + enemy.getHp() + " | dmg : " + enemy.getDmg() + ConsoleTheme.RESET + "\n");
			}
			for (DefensiveEquipment defEquip : cell.getDefEquip()) {
				System.out.print(ConsoleTheme.BRIGHT_CYAN + "Def | Nom : " + defEquip.getName() + " | hp : " + defEquip.getHp() + ConsoleTheme.RESET + "\n");
			}
			for (OffensiveEquipment offEquip : cell.getOffEquip()) {
				System.out.print(ConsoleTheme.BRIGHT_MAGENTA + "Off | Nom : " + offEquip.getName() + " | type : " + offEquip.getType() + " | dmg : " + offEquip.getDamage() + ConsoleTheme.RESET + "\n");
			}
		}
	}

	public void showCurrentPlayer (Character player) {
		String classIcon = player.getType().toString().toUpperCase().contains("WARRIOR") || player.getType().toString().toLowerCase().contains("guerrier") ? ConsoleTheme.SYM_WARRIOR : ConsoleTheme.SYM_WIZARD;
		String title = classIcon + " TOUR DE " + player.getName().toUpperCase() + " (" + player.getType() + ")";

		List<String> infoLines = new ArrayList<>();

		// Line 1: HP & Dmg
		String hpBar = ConsoleTheme.getHealthBar(player.getHp(), player.getMaxHp(), 10);
		infoLines.add(ConsoleTheme.SYM_HEART + " Santé     : " + hpBar + "  |  " + ConsoleTheme.SYM_WEAPON + " Dégâts de base : " + ConsoleTheme.BOLD + player.getDmg() + ConsoleTheme.RESET);

		// Line 2: Equipped item
		if (player.getCurrentOffEquipment() != null) {
			infoLines.add(ConsoleTheme.SYM_WEAPON + " Équipé    : " + ConsoleTheme.BRIGHT_YELLOW + player.getCurrentOffEquipment().getName()
					+ ConsoleTheme.RESET + " (+" + player.getCurrentOffEquipment().getDamage() + " dégâts)");
		} else {
			infoLines.add(ConsoleTheme.SYM_WEAPON + " Équipé    : " + ConsoleTheme.DIM + "Mains nues" + ConsoleTheme.RESET);
		}

		// Line 3: Inventory
		StringBuilder inv = new StringBuilder(ConsoleTheme.SYM_POTION + " Potions   : ");
		if (player.getDefensiveEquipment().isEmpty()) {
			inv.append(ConsoleTheme.DIM).append("Aucune").append(ConsoleTheme.RESET);
		} else {
			for (DefensiveEquipment pot : player.getDefensiveEquipment()) {
				inv.append(ConsoleTheme.BRIGHT_CYAN).append("[").append(pot.getName()).append("] ").append(ConsoleTheme.RESET);
			}
		}
		infoLines.add(inv.toString());

		// Line 4: Position
		infoLines.add("📍 Position  : Case " + ConsoleTheme.BRIGHT_YELLOW + (player.getPos() + 1) + ConsoleTheme.RESET);

		ConsoleTheme.printBox(title, infoLines.toArray(new String[0]));
	}

	public void showHeader (Character player, Cell[] cellTable, int maxCell) {
		this.showSeparator();
		this.showCurrentPlayer(player);
		this.showCellsData(cellTable, maxCell);
		this.showSeparator();
	}
}
