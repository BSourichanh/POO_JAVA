package fr.campus.poojava.game.battle;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.game.Game;
import fr.campus.poojava.game.GameState;
import fr.campus.poojava.game.board.Cell;
import fr.campus.poojava.game.dice.Dice;
import fr.campus.poojava.game.dice.Dice20;
import fr.campus.poojava.ui.Menu;
import fr.campus.poojava.ui.MenuBattle;

/**
 * Gestionnaire des affrontements en combat au tour par tour entre un joueur et un ennemi.
 * Gère le déroulement des attaques, le lancer de dé pour les coups critiques et la fuite.
 *
 * @author BSourichanh
 */
public class BattleManager {
	private final Menu menu;
	private final Game game;
	private final MenuBattle menuBattle = new MenuBattle();
	private BattleState state = BattleState.PLAYER_TURN;

	/**
	 * Constructeur du gestionnaire de combat.
	 *
	 * @param menu Le menu pour l'interaction utilisateur.
	 * @param game La référence de la partie en cours.
	 */
	public BattleManager (Menu menu, Game game) {
		this.menu = menu;
		this.game = game;
	}

	/**
	 * Détermine si le jet de d20 donne un coup critique, un échec critique ou un coup normal.
	 *
	 * @param dice La valeur du d20 (1 à 20).
	 * @return Le type de coup (CRITIQUE, ECHEC_CRITIQUE, NORMAL).
	 */
	public Crit checkCrit (int dice) {
		if (dice == 1) {
			return Crit.ECHEC_CRITIQUE;
		} else if (dice == 20) {
			return Crit.CRITIQUE;
		}
		return Crit.NORMAL;
	}

	/**
	 * Gère la boucle de combat tour par tour jusqu'à la victoire, la fuite ou la mort d'un participant.
	 *
	 * @param player    Le joueur engagé dans le combat.
	 * @param cellTable Le tableau des cases du plateau.
	 * @param maxCell   Le nombre total de cases.
	 * @return Le nouvel état de jeu après le combat.
	 */
	public GameState manageBattle (Character player, Cell[] cellTable, int maxCell) {
		while (true) {
			menu.showHeader(player, cellTable, maxCell);
			if (cellTable[player.getPos()].getEnemies().isEmpty()) {
				return GameState.BATTLE_END;
			}
			Enemy enemy = cellTable[player.getPos()].getEnemies().get(0);
			menuBattle.showBattleTurn(player, enemy, state);
			if (menuBattle.showBattleInfo(player, enemy, state) == 2) {
				return GameState.FLEE;
			}
			Dice dice = new Dice20();
			Crit crit = this.checkCrit(dice.roll());
			this.execBattle(player, enemy, state, crit);
			menuBattle.showDmg(player, enemy, state, crit);
			menuBattle.showBattleResult(enemy);
			if (enemy.getHp() <= 0) {
				cellTable[player.getPos()].removeEnemy(enemy);
				return GameState.BATTLE_END;
			} else if (player.getHp() <= 0) {
				game.removePlayer(player);
				return GameState.BATTLE_END;
			}

			if (state == BattleState.PLAYER_TURN) {
				state = BattleState.ENEMY_TURN;
			} else {
				state = BattleState.PLAYER_TURN;
			}
		}
	}

	/**
	 * Applique les dégâts calculés (avec prise en compte du coup critique) sur la cible.
	 *
	 * @param player Le joueur.
	 * @param enemy  L'ennemi.
	 * @param state  L'état du tour (PLAYER_TURN ou ENEMY_TURN).
	 * @param crit   Le type de coup (CRITIQUE, ECHEC_CRITIQUE, NORMAL).
	 */
	public void execBattle (Character player, Enemy enemy, BattleState state, Crit crit) {
		if (state == BattleState.PLAYER_TURN) {
			int dmg = player.getDmg();
			if (player.getCurrentOffEquipment() != null) {
				dmg += player.getCurrentOffEquipment().getDamage();
			}
			if (crit == Crit.ECHEC_CRITIQUE) {
				dmg = 0;
			} else if (crit == Crit.CRITIQUE) {
				dmg += 2;
			}
			enemy.setHp(enemy.getHp() - dmg);
		} else {
			int dmg = enemy.getDmg();
			if (crit == Crit.ECHEC_CRITIQUE) {
				dmg = 0;
			} else if (crit == Crit.CRITIQUE) {
				dmg += 2;
			}
			player.setHp(player.getHp() - dmg);
		}
	}
}
