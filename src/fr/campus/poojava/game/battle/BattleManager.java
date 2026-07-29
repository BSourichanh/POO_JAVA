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
	private final MenuBattle menuBattle;
	private final Dice dice;
	
	/**
	 * Constructeur du gestionnaire de combat.
	 *
	 * @param menu Le menu pour l'interaction utilisateur.
	 * @param game La référence de la partie en cours.
	 */
	public BattleManager (Menu menu, Game game) {
		this(menu, game, new MenuBattle(), new Dice20());
	}
	
	/**
	 * Constructeur avec injection complète de dépendances (DIP / Tests).
	 *
	 * @param menu       Le menu principal.
	 * @param game       La partie en cours.
	 * @param menuBattle L'interface de rendu des combats.
	 * @param dice       L'implémentation du dé pour le jet critique.
	 */
	public BattleManager (Menu menu, Game game, MenuBattle menuBattle, Dice dice) {
		this.menu = menu;
		this.game = game;
		this.menuBattle = menuBattle;
		this.dice = dice;
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
	 * Gère l'intégralité d'un affrontement : annonce d'engagement, déroulement du combat tour par tour,
	 * résolution des dégâts et détermination de la suite du jeu.
	 *
	 * @param player Le joueur engagé dans le combat.
	 * @param board  Le plateau de jeu.
	 * @return Le nouvel état de jeu après résolution du combat.
	 */
	public GameState manageBattle (Character player, fr.campus.poojava.game.board.GameBoard board) {
		Cell currentCell = board.getCell(player.getPos());
		if (currentCell == null || currentCell.getEnemies().isEmpty()) {
			return GameState.MOVING;
		}
		
		Enemy enemy = currentCell.getEnemies().getFirst();
		menuBattle.showEncounter(player, enemy);
		
		BattleState state = BattleState.PLAYER_TURN;
		while (true) {
			menu.showHeader(player, board.getCellTable(), board.getMaxCell());
			if (currentCell.getEnemies().isEmpty()) {
				return player.getMoveAvailable() == 0 ? GameState.END : GameState.MOVING;
			}
			
			menuBattle.showBattleTurn(player, enemy, state);
			if (menuBattle.showBattleInfo(player, enemy, state) == 2) {
				return GameState.FLEE;
			}
			
			Crit crit = this.checkCrit(this.dice.roll());
			this.execBattle(player, enemy, state, crit);
			menuBattle.showDmg(player, enemy, state, crit);
			menuBattle.showBattleResult(enemy);
			
			if (enemy.getHp() <= 0) {
				currentCell.removeEnemy(enemy);
				return player.getMoveAvailable() == 0 ? GameState.END : GameState.MOVING;
			} else if (player.getHp() <= 0) {
				game.removePlayer(player);
				if (board.countAlivePlayers() == 0) {
					menu.showGameOver();
					if (menu.requestPlayAgain()) {
						game.initGame();
						game.playTurn();
					}
					return GameState.FINISH;
				}
				game.nextPlayer();
				return GameState.IDLE;
			}
			
			state = (state == BattleState.PLAYER_TURN) ? BattleState.ENEMY_TURN : BattleState.PLAYER_TURN;
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
