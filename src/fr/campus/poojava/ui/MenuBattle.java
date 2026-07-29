package fr.campus.poojava.ui;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.game.battle.BattleState;
import fr.campus.poojava.game.battle.Crit;

/**
 * Composant de présentation dédié à l'affichage des événements et des cartes de combat.
 *
 * @author BSourichanh
 */
public class MenuBattle {
	private final Menu menu = new Menu();

	private String getEnemyIcon (Enemy enemy) {
		String name = enemy.getName().toLowerCase();
		if (name.contains("dragon")) return ConsoleTheme.SYM_DRAGON;
		if (name.contains("gob")) return ConsoleTheme.SYM_GOBLIN;
		if (name.contains("sorc")) return ConsoleTheme.SYM_SORCERER;
		return ConsoleTheme.SYM_ENEMY;
	}

	/**
	 * Affiche l'encadré d'engagement de combat.
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi rencontré.
	 */
	public void showEncounter (Character player, Enemy enemy) {
		String enemyIcon = getEnemyIcon(enemy);
		ConsoleTheme.printBox("⚔️ COMBAT ENGAGÉ ! ⚔️",
				ConsoleTheme.BRIGHT_YELLOW + player.getName() + " le " + player.getType() + ConsoleTheme.RESET
						+ " fait face à " + ConsoleTheme.BRIGHT_RED + enemyIcon + " " + enemy.getName() + ConsoleTheme.RESET + " !",
				ConsoleTheme.DIM + "Préparez-vous au combat..." + ConsoleTheme.RESET
		);
		menu.requestInput("Appuyez sur [Entrée] pour engager la bataille.");
	}

	/**
	 * Demande la décision tactique du joueur (Attaquer ou Fuir).
	 *
	 * @return 1 pour attaquer, 2 pour fuir.
	 */
	public int requestInputBattleAction () {
		System.out.println(ConsoleTheme.BRIGHT_CYAN + "⚔️ Décision tactique :" + ConsoleTheme.RESET);
		System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[1]" + ConsoleTheme.RESET + " ⚔️ Attaquer");
		System.out.println("  " + ConsoleTheme.BRIGHT_YELLOW + "[2]" + ConsoleTheme.RESET + " 🏃 Fuir le combat");
		return menu.requestNb();
	}

	/**
	 * Affiche la carte d'arène de combat comparant les PV et dégâts des deux combattants.
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param state  L'état du tour (PLAYER_TURN ou ENEMY_TURN).
	 * @return La décision du joueur (si PLAYER_TURN) ou -1 (si ENEMY_TURN).
	 */
	public int showBattleInfo (Character player, Enemy enemy, BattleState state) {
		String enemyIcon = getEnemyIcon(enemy);

		int playerDmg = player.getDmg();
		String weaponName = "Mains nues";
		if (player.getCurrentOffEquipment() != null) {
			playerDmg += player.getCurrentOffEquipment().getDamage();
			weaponName = player.getCurrentOffEquipment().getName() + " (+" + player.getCurrentOffEquipment().getDamage() + ")";
		}

		String playerHpBar = ConsoleTheme.getHealthBar(player.getHp(), player.getMaxHp(), 8);
		String enemyHpBar = ConsoleTheme.getHealthBar(enemy.getHp(), enemy.getMaxHp(), 8);

		ConsoleTheme.printBox("⚔️ ARENE DE COMBAT ⚔️",
				ConsoleTheme.BRIGHT_GREEN + ConsoleTheme.BOLD + player.getName() + " (" + player.getType() + ")" + ConsoleTheme.RESET,
				ConsoleTheme.SYM_HEART + " Santé   : " + playerHpBar,
				ConsoleTheme.SYM_WEAPON + " Attaque : " + ConsoleTheme.BOLD + playerDmg + ConsoleTheme.RESET + " dégâts (" + weaponName + ")",
				"",
				ConsoleTheme.BRIGHT_RED + ConsoleTheme.BOLD + enemyIcon + " " + enemy.getName() + ConsoleTheme.RESET,
				ConsoleTheme.SYM_HEART + " Santé   : " + enemyHpBar,
				ConsoleTheme.SYM_WEAPON + " Attaque : " + ConsoleTheme.BOLD + enemy.getDmg() + ConsoleTheme.RESET + " dégâts"
		);

		if (state == BattleState.PLAYER_TURN) {
			return requestInputBattleAction();
		} else {
			System.out.println(ConsoleTheme.BRIGHT_RED + "⚠️ L'ennemi " + enemy.getName() + " se prépare à riposter..." + ConsoleTheme.RESET);
		}
		menu.requestInput("Appuyez sur [Entrée] pour continuer.");
		return -1;
	}

	/**
	 * Affiche l'attaque du joueur et le résultat du coup (critique, échec ou normal).
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param crit   Le type de coup.
	 */
	public void printPlayerDmgTo (Character player, Enemy enemy, Crit crit) {
		String weapon = "ses mains nues";
		int dmg = player.getDmg();
		if (player.getCurrentOffEquipment() != null) {
			weapon = player.getCurrentOffEquipment().getName();
			dmg += player.getCurrentOffEquipment().getDamage();
		}

		System.out.println(ConsoleTheme.BRIGHT_GREEN + "⚔️ " + player.getName() + " attaque " + enemy.getName() + " avec " + weapon + " !" + ConsoleTheme.RESET);

		if (crit == Crit.CRITIQUE) {
			System.out.println(ConsoleTheme.BRIGHT_YELLOW + ConsoleTheme.BOLD + "💥 COUP CRITIQUE ! (+2 dégâts)" + ConsoleTheme.RESET);
			dmg += 2;
			System.out.println(ConsoleTheme.BRIGHT_GREEN + "⚡ Dégâts infligés : " + ConsoleTheme.BOLD + dmg + ConsoleTheme.RESET + " à " + enemy.getName());
		} else if (crit == Crit.ECHEC_CRITIQUE) {
			System.out.println(ConsoleTheme.BRIGHT_RED + ConsoleTheme.BOLD + "❌ ÉCHEC CRITIQUE ! L'attaque a raté..." + ConsoleTheme.RESET);
			dmg = 0;
			System.out.println(ConsoleTheme.DIM + "⚡ Dégâts infligés : 0" + ConsoleTheme.RESET);
		} else {
			System.out.println(ConsoleTheme.BRIGHT_GREEN + "⚡ Dégâts infligés : " + ConsoleTheme.BOLD + dmg + ConsoleTheme.RESET + " à " + enemy.getName());
		}

		int enemyHp = Math.max(0, enemy.getHp());
		System.out.println("❤️ PV restant de " + enemy.getName() + " : " + ConsoleTheme.getHealthBar(enemyHp, enemy.getMaxHp(), 8));
		System.out.println();
	}

	/**
	 * Affiche la riposte de l'ennemi et les dégâts subis par le joueur.
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param crit   Le type de coup.
	 */
	public void printEnemyDmgTo (Character player, Enemy enemy, Crit crit) {
		String enemyIcon = getEnemyIcon(enemy);
		System.out.println(ConsoleTheme.BRIGHT_RED + enemyIcon + " " + enemy.getName() + " frappe " + player.getName() + " !" + ConsoleTheme.RESET);
		int dmg = enemy.getDmg();

		if (crit == Crit.CRITIQUE) {
			System.out.println(ConsoleTheme.BRIGHT_RED + ConsoleTheme.BOLD + "💥 COUP CRITIQUE ENNEMI ! (+2 dégâts)" + ConsoleTheme.RESET);
			dmg += 2;
			System.out.println(ConsoleTheme.BRIGHT_RED + "💔 Dégâts subis : " + ConsoleTheme.BOLD + dmg + ConsoleTheme.RESET + " par " + player.getName());
		} else if (crit == Crit.ECHEC_CRITIQUE) {
			System.out.println(ConsoleTheme.BRIGHT_GREEN + ConsoleTheme.BOLD + "🛡️ L'ennemi a manqué son attaque !" + ConsoleTheme.RESET);
			dmg = 0;
			System.out.println(ConsoleTheme.DIM + "💔 Dégâts subis : 0" + ConsoleTheme.RESET);
		} else {
			System.out.println(ConsoleTheme.BRIGHT_RED + "💔 Dégâts subis : " + ConsoleTheme.BOLD + dmg + ConsoleTheme.RESET + " par " + player.getName());
		}

		int playerHp = Math.max(0, player.getHp());
		System.out.println("❤️ PV restant de " + player.getName() + " : " + ConsoleTheme.getHealthBar(playerHp, player.getMaxHp(), 8));
	}

	/**
	 * Affiche le résultat des dégâts échangés lors d'un tour de combat.
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param state  L'état du tour.
	 * @param crit   Le type de coup.
	 */
	public void showDmg (Character player, Enemy enemy, BattleState state, Crit crit) {
		if (state == BattleState.PLAYER_TURN) {
			printPlayerDmgTo(player, enemy, crit);
		} else {
			printEnemyDmgTo(player, enemy, crit);
		}
		menu.requestInput("Appuyez sur [Entrée] pour continuer.");
	}

	/**
	 * Affiche la victoire si un ennemi est terrassé.
	 *
	 * @param enemy L'ennemi vaincu.
	 */
	public void showBattleResult (Enemy enemy) {
		if (enemy.getHp() <= 0) {
			String enemyIcon = getEnemyIcon(enemy);
			ConsoleTheme.printBox("🏆 VICTOIRE EN COMBAT 🏆",
					ConsoleTheme.BRIGHT_GREEN + enemyIcon + " " + enemy.getName() + " a été terrassé !" + ConsoleTheme.RESET,
					ConsoleTheme.DIM + "Le chemin est désormais libre." + ConsoleTheme.RESET
			);
			menu.requestInput("Appuyez sur [Entrée] - Fin du combat.");
		}
	}

	/**
	 * Affiche l'encadré de la mort d'un joueur au combat.
	 *
	 * @param player Le joueur mort.
	 */
	public void showPlayerDeath (Character player) {
		ConsoleTheme.printBox("💀 ACCIDENT MORTEL 💀",
				ConsoleTheme.BRIGHT_RED + player.getName() + " le " + player.getType() + " a rendu son dernier souffle..." + ConsoleTheme.RESET
		);
		menu.requestInput("Appuyez sur [Entrée] pour continuer.");
	}

	/**
	 * Annonce le début d'un tour de combat (joueur ou ennemi).
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param state  L'état du tour.
	 */
	public void showBattleTurn (Character player, Enemy enemy, BattleState state) {
		if (state == BattleState.PLAYER_TURN) {
			System.out.println(ConsoleTheme.BRIGHT_GREEN + "👉 C'est votre tour, " + player.getName() + " !" + ConsoleTheme.RESET);
		} else {
			System.out.println(ConsoleTheme.BRIGHT_RED + "👉 Tour de l'ennemi " + enemy.getName() + " !" + ConsoleTheme.RESET);
		}
	}

	/**
	 * Affiche l'encadré d'une fuite réussie.
	 *
	 * @param rand Le nombre de cases de recul.
	 */
	public void showFlee (int rand) {
		ConsoleTheme.printBox("🏃 FUITE RÉUSSIE 🏃",
				ConsoleTheme.BRIGHT_YELLOW + "Vous prenez la fuite !" + ConsoleTheme.RESET,
				"Vous reculez de " + ConsoleTheme.BOLD + rand + ConsoleTheme.RESET + " case(s)."
		);
		menu.requestInput("Appuyez sur [Entrée] pour continuer.");
	}
}
