package fr.campus.poo_java;

import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.util.List;
import java.util.Scanner;

public class Menu {
    protected Scanner sc = new Scanner(System.in);
    public static int textOffset = 3;

    public void showSeperator() {
        System.out.println("=============================================================" +
                "=============================================================");
    }
    public int requestPotion(Character player) {
        System.out.println("Entrer votre choix :");
        return Integer.parseInt(requestInput());
    }
    public boolean showPlayerPotion(Character player){
        if (player.getDefensiveEquipement().isEmpty()) {
            System.out.println("Pas de potion.");
            requestInput();
            return false;
        }
        System.out.println("Potion : ");
        for (int i = 0; i < player.getDefensiveEquipement().size(); i++) {
            System.out.println((i+1) + ") " + player.getDefensiveEquipement().get(i).getType());
        }
        return true;
    }
    public void showPickDefEquip(Character player, DefensiveEquipement defEquip) {
        System.out.println(player.getName()
                + " le " + player.getType()
                + " ramasse " + defEquip.getType()
        );
        requestInput();
    }
    public void showPlayerEndTurn(Character player){
        System.out.println("Enter) Fin de tour de " + player.getName() + " le " + player.getEntityType());
    }
    public void showEndGame() {
        System.out.println("Fin du jeu");
    }
    public void showWrongChoice() {
        System.out.println("Choix invalide");
    }
    public void showDiceThrow(Character player){
        System.out.println(player.getName() + " à fait un lancer de dée de " + player.moveAvailable);
    }
    public void showPlayerIdleAction() {
        System.out.println("1) Lancer de dée\n2) Utiliser potion");
    }
    public void showPlayerFinish(Character player){
        System.out.println("Joueur " + player.getName() + " le "+ player.getType() + " à atteinds la dernière case.");
    }
    public void showCurrentPlayerTurn(Character player){
        System.out.println("Tour de " + player.getName() + " le " + player.getType() + ".");
    }
    public void requestInputToMove(Character player){
        System.out.println("Entrer) Avance de " + player.moveAvailable + " case");
        requestInput();
    }
    public void showMoveAvailable(Character player){
        System.out.println("Déplacement disponible : " + player.moveAvailable + ".\n");
    }
    public void showCellsData(Cell[] cellTable, int maxCell){
        for (int i = 0; i < maxCell; i++)
        {
            System.out.print("[");
            List<Character> players = cellTable[i].players;
            List<Enemy> enemies = cellTable[i].enemies;
            List<DefensiveEquipement> defEquip = cellTable[i].defEquip;
            List<OffensiveEquipment> offEquip = cellTable[i].offEquip;
            for (int p = 0; p < players.size(); p++)
            {
                if (p > 0)
                    System.out.print("|");
                Character tmp = players.get(p);
                if (tmp.getName().length() < textOffset)
                    System.out.print(players.get(p).getName());
                else
                    System.out.print(players.get(p).getName().substring(0, textOffset));
            }
            if (!players.isEmpty() && (!enemies.isEmpty() || !defEquip.isEmpty() || !offEquip.isEmpty()))
                System.out.print("|");
            for (int e = 0; e < enemies.size(); e++)
            {
                if (e > 0)
                    System.out.print("|");
                System.out.print(enemies.get(e).getType().toString().substring(0, textOffset));
            }
            for (int e = 0; e < defEquip.size(); e++)
            {
                if (e > 0)
                    System.out.print("|");
                System.out.print(defEquip.get(e).getName());
            }
            System.out.print("]");
        }
        System.out.println("\n");
    }
    public void showBattleResult(Character player, Enemy enemy){
        if (enemy.getHp() < 0) {
            System.out.println(enemy.getName() + " est mort.\n");
        }
        else
        {
            System.out.println(enemy.getName() + " atttaque !");
            System.out.println(enemy.getName() + " inflige " + enemy.getDmg() + " à " + player.getName() + " PV restant " + player.getHp());
            System.out.println(enemy.getName() + " s'enfuit !\n");
        }
        System.out.println("Entrer) Fin de combat");
        requestInput();
    }
    public void showDmg(Character player, Enemy enemy){
        System.out.println(player.getName() + " attaque " + enemy.getName());
        System.out.println(player.getName() + " inflige " + player.getDmg() + " à " + enemy.getName() + " PV restant " + enemy.getHp());
    }
    public void showBattleInfo(Character player, Enemy enemy) {
        System.out.println(player.getName() + " le " + player.getType() + " tombe sur " + enemy.getName()+ "\n");
        System.out.println("Joueur  : " + player.getName() + " le " + player.getType() + " PV : " + player.getHp() + " Dégats : " + player.getDmg());
        System.out.println("Ennemie : " + enemy.getName() + " PV : " + player.getHp() + " Dégats : " + player.getDmg() + "\n");
        System.out.println("Entrer) Pour attaquer.");
        requestInput();
    }
    protected int checkInput(int start, int end, int input) {
        if (input >= start && input <=end)
            return input;
        else
            return -1;
    }
    public String requestInput() {
        System.out.print(">");
        String input = sc.nextLine();
        System.out.print("\n");
        return input;
    }
    public int chooseClass() {
        System.out.println("1) Guerrier\n2) Mage");
        int tmp = Integer.parseInt(requestInput());
        tmp = checkInput(1,2, tmp);
        if (tmp != -1)
            return tmp;
        chooseClass();
        return -1;
    }
    public String requestName() {
        System.out.println("Entrer votre nom.");
        return requestInput();
    }
    public int requestNbPlayer() {
        System.out.println("1-2) Combien de joueurs ?");
        int tmp = Integer.parseInt(requestInput());
        if (checkInput(1, 2, tmp) != -1)
            return tmp;
        else {
            System.out.println("Erreur, 1-2");
            requestNbPlayer();
        }
        return -1;
    }
    public void showData(Cell[] cellsTable) {
        for (Cell cell : cellsTable)
        {
            for (Character character : cell.players) {
            System.out.print("Character | id : " + character.getId()
                    + " | pos : " + character.getPos()
                    + " | name : " + character.getName()
                    + " | type : " + character.getType()
                    + " | hp : " + character.getHp()
                    + " | dmg : " + character.getDmg()
            );
                if (!character.isDefEquipEmpty()) {
                    System.out.print(" def : ");
                    for (DefensiveEquipement defEquip : character.getDefensiveEquipement())
                    {
                        System.out.print(defEquip.getName() + ", ");
                    }
                    System.out.print("\n");
                }
                else
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
            for (DefensiveEquipement defEquip : cell.defEquip){
                System.out.print("Def | Type : " + defEquip.getType()
                        + " | hp : " + defEquip.getHp()
                        + "\n"
                );
            }
        }
    }

}
