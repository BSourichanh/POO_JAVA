package fr.campus.poo_java.ui;

import fr.campus.poo_java.Enums;
import fr.campus.poo_java.entity.character.Character;
import fr.campus.poo_java.entity.enemies.Enemy;

public class MenuBattle extends Menu {
	
	public void showEncounter (Character player, Enemy enemy) {
		System.out.println(player.getName() + " le " + player.getType() + " tombe sur " + enemy.getName() + "\n");
		requestInput("Entrer) Pour lancer le combat.");
	}
	
	public int requestInputBattleAction () {
		System.out.println("1) Attaquer.\n2) Fuir.");
		return requestNb();
	}
	
	public int showBattleInfo (Character player, Enemy enemy, Enums.BattleState state) {
		if (player.getCurrentOffEquipment() == null)
			System.out.println(player.getName() + " le " + player.getType() + " | PV : " + player.getHp() + " | Dégats : " + player.getDmg());
		else
			System.out.println(player.getName() + " le " + player.getType() + " | PV : " + player.getHp() + " | Dégats : " + player.getDmg() + " + " + player.getCurrentOffEquipment().getDamage());
		System.out.println(enemy.getName() + " | PV : " + enemy.getHp() + " Dégats : " + enemy.getDmg() + "\n");
		if (state == Enums.BattleState.PLAYER_TURN) {
			return requestInputBattleAction();
		} else
			System.out.println("Entrer) L'ennemi attaque.");
		requestInput("Entrer) Continuer.");
		return -1;
	}
	
	public void printPlayerDmgTo (Character player, Enemy enemy, Enums.Crit crit) {
		String weapon = "main nue.";
		int dmg = player.getDmg();
		if (player.getCurrentOffEquipment() != null) {
			weapon = player.getCurrentOffEquipment().getName();
			dmg += player.getCurrentOffEquipment().getDamage();
		}
		System.out.println(player.getName() + " attaque " + enemy.getName() + " avec " + weapon);
		if (crit == Enums.Crit.Critique) {
			System.out.println("Critique !");
			System.out.print(player.getName() + " inflige " + (dmg + 2) + " à " + enemy.getName() + " PV restant ");
		}else if (crit == Enums.Crit.Echec_Critique) {
			System.out.println("Echec critique...");
			System.out.print(player.getName() + " inflige " + 0 + " à " + enemy.getName() + " PV restant ");
		}else {
			System.out.print(player.getName() + " inflige " + dmg + " à " + enemy.getName() + " PV restant ");
		}
		
		if (enemy.getHp() > 0)
			System.out.println(enemy.getHp() + ".");
		else
			System.out.println("0.");
		System.out.println();
	}
	
	public void printEnemyDmgTo(Character player, Enemy enemy, Enums.Crit crit){
		System.out.println(enemy.getName() + " atttaque " + player.getName() + ".");
		int dmg = enemy.getDmg();
		if (crit == Enums.Crit.Critique) {
			System.out.println("Critique !");
			System.out.print(enemy.getName() + " inflige " + (dmg + 2) + " à " + player.getName() + " PV restant ");
		}else if (crit == Enums.Crit.Echec_Critique) {
			System.out.println("Echec critique...");
			System.out.print(enemy.getName() + " inflige " + 0 + " à " + player.getName() + " PV restant ");
		}else {
			System.out.print(enemy.getName() + " inflige " + dmg + " à " + player.getName() + " PV restant ");
		}
		
		if (player.getHp() > 0)
			System.out.println(player.getHp() + ".");
		else
			System.out.println("0.");
	}
	
	public void showDmg (Character player, Enemy enemy, Enums.BattleState state, Enums.Crit crit) {
		if (state == Enums.BattleState.PLAYER_TURN)
			printPlayerDmgTo(player, enemy, crit);
		else
			printEnemyDmgTo(player, enemy, crit);
		requestInput("Entrer) Continuer.");
	}
	
	public void showBattleResult (Enemy enemy) {
		if (enemy.getHp() <= 0) {
			System.out.println(enemy.getName() + " est mort.\n");
			requestInput("Entrer) Fin de combat");
		}
	}
	
	public void showPlayerDeath (Character player) {
		System.out.println(player.getName() + " le " + player.getType() + " est mort !");
		requestInput("Entrer) Pour continuer.");
	}
	
	public void showBattleTurn (Character player, Enemy enemy, Enums.BattleState state) {
		if (state == Enums.BattleState.PLAYER_TURN)
			System.out.println("Tour de " + player.getName() + " le " + player.getType());
		else
			System.out.println("Tour de " + enemy.getName());
	}
	
	public void showFlee (int rand) {
		System.out.println("Vous avez fuit.");
		requestInput("Entrer) vous reculer de " + rand + " cases.");
	}
}
