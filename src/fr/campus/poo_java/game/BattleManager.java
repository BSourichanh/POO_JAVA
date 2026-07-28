package fr.campus.poo_java.game;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.ui.Menu;
import fr.campus.poo_java.ui.MenuBattle;

public class BattleManager {
	private final Menu menu;
	private final Game game;
	private Enums.BattleState state = Enums.BattleState.PLAYER_TURN;
	
	public BattleManager (Menu menu, Game game) {
		this.menu = menu;
		this.game = game;
	}
	
	public Enums.Crit checkCrit (int dice) {
		if (dice == 1)
			return Enums.Crit.Echec_Critique;
		else if (dice == 20)
			return Enums.Crit.Critique;
		return Enums.Crit.Normal;
	}
	
	public Enums.GameState manageBattle (Character player) {
		MenuBattle menuBattle = new MenuBattle();
		
		menu.showHeader(player, game.cellTable, game.maxCell);
		Enemy enemy = game.cellTable[player.getPos()].enemies.getFirst();
		menuBattle.showBattleTurn(player, enemy, state);
		if (menuBattle.showBattleInfo(player, enemy, state) == 2)
			return Enums.GameState.Flee;
		Dice dice = new Dice20();
		Enums.Crit crit = this.checkCrit(dice.roll());
		this.execBattle(player, enemy, state, crit);
		menuBattle.showDmg(player, enemy, state, crit);
		menuBattle.showBattleResult(enemy);
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
	
	public void execBattle (Character player, Enemy enemy, Enums.BattleState state, Enums.Crit crit) {
		if (state == Enums.BattleState.PLAYER_TURN) {
			int dmg = player.getDmg();
			if (player.getCurrentOffEquipement() != null)
				dmg += player.getCurrentOffEquipement().getDamage();
			if (crit == Enums.Crit.Echec_Critique)
				dmg = 0;
			else if (crit == Enums.Crit.Critique)
				dmg += 2;
			enemy.setHp(enemy.getHp() - dmg);
		} else {
			int dmg = enemy.getDmg();
			if (crit == Enums.Crit.Echec_Critique)
				dmg = 0;
			else if (crit == Enums.Crit.Critique)
				dmg += 2;
			player.setHp(player.getHp() - dmg);
		}
	}
}