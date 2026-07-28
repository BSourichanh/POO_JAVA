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

public class BattleManager {
	private final Menu menu;
	private final Game game;
	private final MenuBattle menuBattle = new MenuBattle();
	private BattleState state = BattleState.PLAYER_TURN;

	public BattleManager (Menu menu, Game game) {
		this.menu = menu;
		this.game = game;
	}

	public Crit checkCrit (int dice) {
		if (dice == 1) {
			return Crit.ECHEC_CRITIQUE;
		} else if (dice == 20) {
			return Crit.CRITIQUE;
		}
		return Crit.NORMAL;
	}

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
