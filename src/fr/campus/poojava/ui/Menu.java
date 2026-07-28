package fr.campus.poojava.ui;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;
import fr.campus.poojava.game.GameState;
import fr.campus.poojava.game.board.Cell;

import java.util.Scanner;

public class Menu {
	protected static final Scanner sc = new Scanner(System.in);
	protected final BoardRenderer renderer = new BoardRenderer();

	public void showSeparator () {
		renderer.showSeparator();
	}

	public int chooseClass (int maxPlayer, int id) {
		while (true) {
			System.out.println(ConsoleTheme.BRIGHT_CYAN + "🧙 C'est au Joueur " + (id + 1) + " de choisir sa classe :" + ConsoleTheme.RESET);
			System.out.println(ConsoleTheme.BRIGHT_YELLOW + "  [1]" + ConsoleTheme.RESET + " ⚔️  Guerrier  " + ConsoleTheme.DIM + "(Combat rapproché, épée / massue)" + ConsoleTheme.RESET);
			System.out.println(ConsoleTheme.BRIGHT_YELLOW + "  [2]" + ConsoleTheme.RESET + " 🧙 Mage      " + ConsoleTheme.DIM + "(Sortilèges, boule de feu / éclair)" + ConsoleTheme.RESET);

			int tmp = requestNb();
			if (tmp == 1 || tmp == 2) {
				return tmp;
			}
			showWrongChoice();
		}
	}

	public String requestName () {
		while (true) {
			String inputText = requestInput("👤 Entrez votre nom de héros :");
			if (!inputText.trim().isEmpty()) {
				return inputText.trim();
			}
		}
	}

	public int requestNbPlayer (int maxPlayer) {
		while (true) {
			String inputText = requestInput("👥 Combien de joueurs souhaitent jouer ? (1-" + maxPlayer + ")");
			if (!inputText.isEmpty()) {
				try {
					int tmp = Integer.parseInt(inputText.trim());
					if (checkInput(tmp, maxPlayer) != -1) {
						return tmp;
					}
				} catch (NumberFormatException ignored) {
				}
				System.out.println(ConsoleTheme.BRIGHT_RED + "❌ Erreur, veuillez entrer un nombre de joueurs entre 1 et " + maxPlayer + ConsoleTheme.RESET);
			}
		}
	}

	public int requestNb () {
		String input = requestInput("Entrez votre choix :");
		if (input.isEmpty()) {
			return -1;
		}
		try {
			return Integer.parseInt(input.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public void showCurrentPlayerTurn (Character player) {
		String icon = player.getType().toString().contains("WARRIOR") || player.getType().toString().contains("Guerrier") ? ConsoleTheme.SYM_WARRIOR : ConsoleTheme.SYM_WIZARD;
		System.out.println(ConsoleTheme.BOLD + ConsoleTheme.BRIGHT_GREEN + icon + " C'est le tour de " + player.getName() + " le " + player.getType() + " !" + ConsoleTheme.RESET);
	}

	public void showPlayerIdleAction () {
		System.out.println(ConsoleTheme.BOLD + ConsoleTheme.BRIGHT_CYAN + "📜 Actions disponibles :" + ConsoleTheme.RESET);
		System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[1]" + ConsoleTheme.RESET + " 🎲 Lancer de dé");
		System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[2]" + ConsoleTheme.RESET + " 🧪 Utiliser une potion");
		System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[3]" + ConsoleTheme.RESET + " 🗡️ Équiper un objet");
	}

	public void requestInputDiceThrow (Character player) {
		requestInput(ConsoleTheme.BRIGHT_YELLOW + "🎲 Appuyez sur [Entrée] - " + player.getName() + " a obtenu un lancer de dé de " + ConsoleTheme.BOLD + player.getMoveAvailable() + ConsoleTheme.RESET);
	}

	public void showMoveAvailable (Character player) {
		System.out.println("🚶 Déplacement disponible : " + ConsoleTheme.BRIGHT_YELLOW + ConsoleTheme.BOLD + player.getMoveAvailable() + " case(s)" + ConsoleTheme.RESET + "\n");
	}

	public GameState requestInputAction (Character player) {
		while (true) {
			System.out.println(ConsoleTheme.BRIGHT_CYAN + "🎮 Choisissez une action :" + ConsoleTheme.RESET);
			System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[Entrée]" + ConsoleTheme.RESET + " 🚶 Avancer d'une case");
			System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[1]" + ConsoleTheme.RESET + " 🎒 Inventaire des armes/sorts");
			System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[2]" + ConsoleTheme.RESET + " 🧪 Inventaire des potions");

			int input = requestNb();

			if (input == 1) {
				if (player.isOffEquipEmpty()) {
					System.out.println(ConsoleTheme.BRIGHT_RED + "❌ Inventaire d'équipements vide." + ConsoleTheme.RESET);
				} else {
					return GameState.INVENTORY;
				}
			} else if (input == 2) {
				if (player.isDefEquipEmpty()) {
					System.out.println(ConsoleTheme.BRIGHT_RED + "❌ Aucune potion disponible." + ConsoleTheme.RESET);
				} else {
					return GameState.POTION;
				}
			} else {
				return GameState.MOVING;
			}
		}
	}

	public void showPickDefEquip (Character player, DefensiveEquipment defEquip) {
		requestInput(ConsoleTheme.BRIGHT_CYAN + "✨ Appuyez sur [Entrée] - " + player.getName() + " le " + player.getType() + " ramasse " + ConsoleTheme.BOLD + defEquip.getName() + ConsoleTheme.RESET + " !");
	}

	public void showPickOffEquip (Character player, OffensiveEquipment offEquip) {
		requestInput(ConsoleTheme.BRIGHT_MAGENTA + "✨ Appuyez sur [Entrée] - " + player.getName() + " le " + player.getType() + " ramasse " + ConsoleTheme.BOLD + offEquip.getName() + ConsoleTheme.RESET + " !");
	}

	public void showWrongItemType () {
		requestInput(ConsoleTheme.BRIGHT_RED + "⚠️ Cet objet ne peut pas être équipé pour votre classe !" + ConsoleTheme.RESET);
	}

	public void showGameOver () {
		ConsoleTheme.printBox("💀 FIN DE LA PARTIE 💀",
				ConsoleTheme.BRIGHT_RED + "Tous les héros sont tombés au combat..." + ConsoleTheme.RESET,
				""
		);
	}

	public boolean requestPlayAgain() {
		String input = requestInput("💀 Voulez-vous recommencer une partie ? (oui/non)");
		return input.equalsIgnoreCase("oui") || input.equalsIgnoreCase("o");
	}

	public void showPlayerEndTurn (Character player) {
		System.out.println(ConsoleTheme.DIM + "🔚 Appuyez sur [Entrée] - Fin de tour de " + player.getName() + " le " + player.getType() + ConsoleTheme.RESET);
	}

	public void showPlayerFinish (Character player) {
		ConsoleTheme.printBox("🏆 VICTOIRE ÉPIQUE 🏆",
				ConsoleTheme.BRIGHT_YELLOW + ConsoleTheme.BOLD + player.getName() + " le " + player.getType() + " a atteint la dernière case !" + ConsoleTheme.RESET,
				"Félicitations pour votre bravoure !"
		);
	}

	public void showEndGame () {
		System.out.println(ConsoleTheme.BRIGHT_GREEN + ConsoleTheme.BOLD + "\n🎉 Partie terminée ! Merci d'avoir joué !" + ConsoleTheme.RESET);
	}

	protected int checkInput (int input, int end) {
		if (input >= 1 && input <= end) {
			return input;
		} else {
			return -1;
		}
	}

	public String requestInput (String message) {
		System.out.println(message);
		System.out.print(ConsoleTheme.BRIGHT_YELLOW + "❯ " + ConsoleTheme.RESET);
		String input = sc.nextLine();
		System.out.print("\n");
		return input;
	}

	public void showWrongChoice () {
		System.out.println(ConsoleTheme.BRIGHT_RED + "❌ Choix invalide. Veuillez réessayez." + ConsoleTheme.RESET);
	}

	public void showCellsData (Cell[] cellTable, int maxCell) {
		renderer.showCellsData(cellTable, maxCell);
	}

	public void showAllData (Cell[] cellsTable) {
		renderer.showAllData(cellsTable);
	}

	public void showCurrentPlayer (Character player) {
		renderer.showCurrentPlayer(player);
	}

	public boolean showDefEquips (Character player) {
		if (player.getDefensiveEquipment().isEmpty()) {
			requestInput(ConsoleTheme.DIM + "Appuyez sur [Entrée] - Pas de potion." + ConsoleTheme.RESET);
			return false;
		}
		System.out.println(ConsoleTheme.BRIGHT_CYAN + "🧪 Potions en réserve :" + ConsoleTheme.RESET);
		for (int i = 0; i < player.getDefensiveEquipment().size(); i++) {
			System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[" + (i + 1) + "]" + ConsoleTheme.RESET + " " + player.getDefensiveEquipment().get(i).getName()
					+ " (" + ConsoleTheme.GREEN + "+" + player.getDefensiveEquipment().get(i).getHp() + " HP" + ConsoleTheme.RESET + ")");
		}
		return true;
	}

	public boolean showOffEquips (Character player) {
		if (player.getCurrentOffEquipment() != null) {
			System.out.println("🗡️ Équipé actuellement : " + ConsoleTheme.BRIGHT_YELLOW + player.getCurrentOffEquipment().getName()
					+ ConsoleTheme.RESET + " | Type : " + player.getCurrentOffEquipment().getType()
					+ " | Dégâts : +" + ConsoleTheme.BRIGHT_RED + player.getCurrentOffEquipment().getDamage() + ConsoleTheme.RESET
			);
		} else {
			System.out.println("🗡️ Équipé actuellement : " + ConsoleTheme.DIM + "Mains nues" + ConsoleTheme.RESET);
		}
		if (player.getOffensiveEquipment().isEmpty()) {
			requestInput(ConsoleTheme.DIM + "Appuyez sur [Entrée] - Inventaire vide." + ConsoleTheme.RESET);
			return false;
		} else {
			System.out.println(ConsoleTheme.BRIGHT_CYAN + "\n🎒 Équipements dans l'inventaire :" + ConsoleTheme.RESET);
			for (int i = 0; i < player.getOffensiveEquipment().size(); i++) {
				OffensiveEquipment item = player.getOffensiveEquipment().get(i);
				System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[" + (i + 1) + "]" + ConsoleTheme.RESET + " " + item.getName()
						+ " (" + item.getType() + ") - Dégâts: +" + ConsoleTheme.BRIGHT_RED + item.getDamage() + ConsoleTheme.RESET);
			}
			System.out.print("\n");
			return true;
		}
	}

	public void showHeader (Character player, Cell[] cellTable, int maxCell) {
		renderer.showHeader(player, cellTable, maxCell);
	}
}