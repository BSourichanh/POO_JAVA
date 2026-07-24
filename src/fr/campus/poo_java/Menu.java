package fr.campus.poo_java;

import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static int textOffset = 3;
    protected Scanner sc = new Scanner(System.in);
    private int currentId;

    public void showSeperator() {
        System.out.println("=============================================================" +
                "=============================================================");
    }

    // Choix joueurs
    public int chooseClass() {
        System.out.println("1) Guerrier\n2) Mage");
        int tmp = Integer.parseInt(requestInput());
        tmp = checkInput(1, 2, tmp);
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

    public int requestNb() {
        System.out.println("Entrer votre choix :");
        String input = requestInput();
        if (!input.isEmpty()){
            int tmp = Integer.parseInt(input);
            return tmp;
        }
        return 0;
    }

    //Affichage pendant le tour
    public void showCurrentPlayerTurn(Character player) {
        System.out.println("Tour de " + player.getName() + " le " + player.getType() + ".");
    }

    public void showPlayerIdleAction() {
        System.out.println("1) Lancer de dée\n2) Utiliser potion\n3) Equipement");
    }

    public void requestInputDiceThrow(Character player) {
        System.out.println("Entrer) " + player.getName() + " à fait un lancer de dée de " + player.moveAvailable);
        requestInput();
    }

    public void showMoveAvailable(Character player) {
        System.out.println("Déplacement disponible : " + player.moveAvailable + ".");
    }

    public Enums.GameState requestInputToMove(Character player) {
        System.out.println("Entrer) Avance de une case\n" + "1) Inventaire");
            int input = requestNb();
            if (input != 1 || input == 0)
                return Enums.GameState.Moving;
            else {
                OffensiveEquipment tmpOffEquip = player.getOffEquipById(input - 1);
                if (tmpOffEquip == null) {
                    System.out.println("Inventaire vide.\nEntrer) avancer d'une case");
                    requestInput();
                    return Enums.GameState.Moving;
                }else
                    showOffEquips(player);
                if (player.getCurrentOffEquipement() == null) {
                    int nb = requestNb();
                    tmpOffEquip = player.getOffEquipById(nb - 1);
                    player.setCurrentOffEquip(tmpOffEquip);
                    player.removeFromInventoryOffEquipement(tmpOffEquip);
                } else {
                    player.moveOffEquipToInventory();
                    player.setCurrentOffEquip(tmpOffEquip);
                    player.removeFromInventoryOffEquipement(tmpOffEquip);
                }
            }
        return Enums.GameState.Moving;
    }

    public void showPickDefEquip(Character player, DefensiveEquipement defEquip) {
        System.out.println(player.getName()
                + " le " + player.getType()
                + " ramasse " + defEquip.getType()
        );
        requestInput();
    }

    public void showPickOffEquip(Character player, OffensiveEquipment offEquip) {
        System.out.println(player.getName()
                + " le " + player.getType()
                + " ramasse " + offEquip.getType()
        );
        requestInput();
    }

    //Affichage Battle
    public void showBattleInfo(Character player, Enemy enemy) {
        System.out.println(player.getName() + " le " + player.getType() + " tombe sur " + enemy.getName() + "\n");
        System.out.println("Joueur  : " + player.getName() + " le " + player.getType() + " PV : " + player.getHp() + " Dégats : " + player.getDmg());
        System.out.println("Ennemie : " + enemy.getName() + " PV : " + player.getHp() + " Dégats : " + player.getDmg() + "\n");
        System.out.println("Entrer) Pour attaquer.");
        requestInput();
    }

    public void showDmg(Character player, Enemy enemy) {
        if (player.getCurrentOffEquipement() == null)
            System.out.println(player.getName() + " attaque " + enemy.getName() + " à main nue");
        else
            System.out.println(player.getName() + " attaque " + enemy.getName() + " avec " + player.getCurrentOffEquipement().getName());
        System.out.println(player.getName() + " inflige " + player.getDmg() + " à " + enemy.getName() + " PV restant " + enemy.getHp());
    }

    public void showBattleResult(Character player, Enemy enemy) {
        if (enemy.getHp() < 0) {
            System.out.println(enemy.getName() + " est mort.\n");
        } else {
            System.out.println(enemy.getName() + " atttaque !");
            System.out.println(enemy.getName() + " inflige " + enemy.getDmg() + " à " + player.getName() + " PV restant " + player.getHp());
            System.out.println(enemy.getName() + " s'enfuit !\n");
        }
        System.out.println("Entrer) Fin de combat");
        requestInput();
    }

    //Affichage fin de tour / jeu
    public void showPlayerEndTurn(Character player) {
        System.out.println("Enter) Fin de tour de " + player.getName() + " le " + player.getEntityType());
    }

    public void showPlayerFinish(Character player) {
        System.out.println("Joueur " + player.getName() + " le " + player.getType() + " à atteinds la dernière case.");
    }

    public void showEndGame() {
        System.out.println("Fin du jeu");
    }

    //Requête input
    protected int checkInput(int start, int end, int input) {
        if (input >= start && input <= end)
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

    public void showWrongChoice() {
        System.out.println("Choix invalide");
    }

    //=================Affichage info
    public void printSeparatorData(int nbInCell) {
        if (currentId < nbInCell) {
            System.out.print("|");
            currentId++;
        }
    }

    public void printPlayers(List<Character> players, int nbInCell) {
        for (int p = 0; p < players.size(); p++) {
            Character tmp = players.get(p);
            if (tmp.getName().length() < textOffset)
                System.out.print(players.get(p).getName());
            else
                System.out.print(players.get(p).getName().substring(0, textOffset));
            printSeparatorData(nbInCell);
        }
    }

    public void printEnemies(List<Enemy> enemies, int nbInCell) {
        for (int e = 0; e < enemies.size(); e++) {
            System.out.print(enemies.get(e).getType().toString().substring(0, textOffset));
            printSeparatorData(nbInCell);
        }
    }

    public void printDefEquip(List<DefensiveEquipement> defEquip, int nbInCell) {
        for (int e = 0; e < defEquip.size(); e++) {
            System.out.print(defEquip.get(e).getName());
            printSeparatorData(nbInCell);
        }
    }

    public void printOffEquip(List<OffensiveEquipment> offEquip, int nbInCell) {
        for (int e = 0; e < offEquip.size(); e++) {
            System.out.print(offEquip.get(e).getName().substring(0, textOffset));
            printSeparatorData(nbInCell);
        }
    }

    public void showCellsData(Cell[] cellTable, int maxCell) {
        for (int i = 0; i < maxCell; i++) {
            System.out.print("[");
            List<Character> players = cellTable[i].players;
            List<Enemy> enemies = cellTable[i].enemies;
            List<DefensiveEquipement> defEquip = cellTable[i].defEquip;
            List<OffensiveEquipment> offEquip = cellTable[i].offEquip;

            int tValue = players.size() + enemies.size() + defEquip.size() + offEquip.size() - 1;
            currentId = 0;
            printPlayers(players, tValue);
            printEnemies(enemies, tValue);
            printDefEquip(defEquip, tValue);
            printOffEquip(offEquip, tValue);

            System.out.print("]");
        }
        System.out.println("\n");
    }

    public void showAllData(Cell[] cellsTable) {
        for (Cell cell : cellsTable) {
            for (Character character : cell.players) {
                System.out.print("Character | id : " + character.getId()
                        + " | pos : " + character.getPos()
                        + " | name : " + character.getName()
                        + " | type : " + character.getType()
                        + " | hp : " + character.getHp()
                        + " | dmg : " + character.getDmg()
                        + " |"
                );
                if (!character.isDefEquipEmpty()) {
                    System.out.print(" defEquip [ ");
                    for (DefensiveEquipement defEquip : character.getDefensiveEquipment()) {
                        System.out.print(defEquip.getName()
                                + " type : " + defEquip.getType()
                                + " hp : " + defEquip.getHp()
                                + ", ");
                    }
                    System.out.print("]\n");
                } else if (!character.isOffEquipEmpty()) {
                    System.out.print(" offEquip [");
                    for (OffensiveEquipment offEquip : character.getOffensiveEquipment()) {
                        System.out.print(offEquip.getName()
                                + " type : " + offEquip.getType()
                                + " dmg : " + offEquip.getDamage()
                                + ",  ");
                    }
                    System.out.print("]\n");
                } else
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
            for (DefensiveEquipement defEquip : cell.defEquip) {
                System.out.print("Def | Type : " + defEquip.getType()
                        + " | hp : " + defEquip.getHp()
                        + "\n"
                );
            }
            for (OffensiveEquipment offEquip : cell.offEquip) {
                System.out.print("Offensive equipement | name : " + offEquip.getName()
                        + " | type : " + offEquip.getType()
                        + " | dmg : " + offEquip.getDamage()
                        + "\n"
                );
            }
        }
    }

    public void showCurrentPlayer(Character player) {
        System.out.print("Tour de "
                + player.getName()
                + " | type : " + player.getType()
                + " | hp : " + player.getHp()
                + " | dmg : " + player.getDmg()
                + " |"
        );
        if (!player.isDefEquipEmpty()) {
            System.out.print(" defEquip [ ");
            for (DefensiveEquipement defEquip : player.getDefensiveEquipment()) {
                System.out.print(defEquip.getName()
                        + " type : " + defEquip.getType()
                        + " hp : " + defEquip.getHp()
                        + ", ");
            }
            //System.out.print("]\n");
        }
        if (player.getCurrentOffEquipement() != null)
            System.out.print(" Equipement : " + player.getCurrentOffEquipement().getName() + " |");
        if (!player.isOffEquipEmpty()) {
            System.out.print(" offEquip [");
            for (OffensiveEquipment offEquip : player.getOffensiveEquipment()) {
                System.out.print(offEquip.getName()
                        + " type : " + offEquip.getType()
                        + " dmg : " + offEquip.getDamage()
                        + ",  ");
            }
            //System.out.print("]\n");
        }

        System.out.print("\n");
    }

    public boolean showDefEquips(Character player) {
        if (player.getDefensiveEquipment().isEmpty()) {
            System.out.println("Pas de potion.");
            requestInput();
            return false;
        }
        System.out.println("Potion : ");
        for (int i = 0; i < player.getDefensiveEquipment().size(); i++) {
            System.out.println((i + 1) + ") " + player.getDefensiveEquipment().get(i).getType());
        }
        return true;
    }

    public boolean showOffEquips(Character player) {

        boolean val = true;
        if (player.getCurrentOffEquipement() != null) {
            System.out.println("Equipé : " + player.getCurrentOffEquipement().getName()
                    + " | type : " + player.getCurrentOffEquipement().getType()
                    + " | dmg : " + player.getCurrentOffEquipement().getDamage()
            );
        } else {
            System.out.println("Aucun objet équipé");
        }
        if (player.getOffensiveEquipment().isEmpty()) {
            System.out.println("Entrer) Inventaire vide.");
            requestInput();
            val = false;
        } else {
            for (int i = 0; i < player.getOffensiveEquipment().size(); i++) {
                System.out.println((i + 1) + ") " + player.getOffensiveEquipment().get(i).getName());
            }
            System.out.print("\n");
            val = true;
        }
        return val;
    }
}
