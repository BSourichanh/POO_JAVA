package fr.campus.poo_java.game;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.Menu;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;

public class BattleManager {
	private final Menu menu;
	private final Game game;
	private Enums.BattleState state = Enums.BattleState.PLAYER_TURN;
	
	public BattleManager (Menu menu, Game game) {
		this.menu = menu;
		this.game = game;
	}
	
	public Enums.GameState manageBattle (Character player) {
		menu.showHeader(player, game.cellTable, game.maxCell);
		Enemy enemy = game.cellTable[player.getPos()].enemies.getFirst();
		menu.showBattleTurn(player, enemy, state);
		if (menu.showBattleInfo(player, enemy, state) == 2 )
			return Enums.GameState.Flee;
		this.checkBattle(player, enemy, state);
		menu.showDmg(player, enemy, state);
		menu.showBattleResult(player, enemy);
		if (enemy.getHp() <= 0) {
			game.cellTable[player.getPos()].removeEnemy(enemy);
			return Enums.GameState.BattleEnd;
		} else if (player.getHp() <= 0) {
			game.removePlayer(player);
			return Enums.GameState.BattleEnd;
		}
		if (state == Enums.BattleState.PLAYER_TURN)
			state = Enums.BattleState.ENEMY_TURN;
		else
			state = Enums.BattleState.PLAYER_TURN;
		return manageBattle(player);
	}
	
	public void checkBattle (Character player, Enemy enemy, Enums.BattleState state) {
		if (state == Enums.BattleState.PLAYER_TURN) {
			if (player.getCurrentOffEquipement() == null)
				enemy.setHp(enemy.getHp() - player.getDmg());
			else
				enemy.setHp(enemy.getHp() - (player.getDmg() + player.getCurrentOffEquipement().getDamage()));
		} else
			player.setHp(player.getHp() - enemy.getDmg());
	}
}