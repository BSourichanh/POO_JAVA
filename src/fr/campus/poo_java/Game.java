package fr.campus.poo_java;

import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.entity.character.Warrior;
import fr.campus.poo_java.entity.character.Wizard;
import fr.campus.poo_java.entity.enemies.Dragon;
import fr.campus.poo_java.entity.enemies.Goblin;
import fr.campus.poo_java.entity.enemies.Sorcier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public int currentPlayer = 0;
    public int currentTurn = 0;
    public Enums.GameState gameState = Enums.GameState.Idle;
    Scanner sc = new Scanner(System.in);

    public int maxPlayer = 2;
    public int maxCell = 4;
    public static int maxEnemies = 1;
    public static int textOffset = 4;
    public List<Character> playerList = new ArrayList<>();
    public List<Enemy> enemyList = new ArrayList<>();
    public Cell[] cellTable =  new Cell[maxCell];
    Random random = new Random();

    public void showData() {
        for (Character player : playerList) {
            player.getInfo();
        }
        for (Enemy enemy: enemyList) {
            enemy.getInfo();
        }
    }

    public void showCellsData(){
        for (int i = 0; i < maxCell; i++)
        {
            System.out.print("[");
            List<Character> players = cellTable[i].players;
            List<Enemy> enemies = cellTable[i].ennemies;
            for (int p = 0; p < players.size(); p++)
            {
                if (p > 0)
                    System.out.print("|");
                System.out.print(players.get(p).getName().substring(0, textOffset));
            }
            if (!players.isEmpty() && !enemies.isEmpty())
                System.out.print("|");
            for (int e = 0; e < enemies.size(); e++)
            {
                if (e > 0)
                    System.out.print("|");
                System.out.print(enemies.get(e).getType().toString().substring(0, textOffset));
            }
            System.out.print("]");
        }
        System.out.println("");
    }

    public void initCells() {
        for (int i = 0; i < maxCell; i++)
            cellTable[i] = new Cell(i);
    }

    public void initPlayers(){
        Character tmp;
        Menu tmpMenu = new Menu();
        for (int i = 0; i < maxPlayer; i++)
        {
            System.out.println("Joueur : " + (i+1) + " de choisir");
            int idType = tmpMenu.chooseClass();
            String name = tmpMenu.requestName();
            switch (idType)
            {
                case 1:
                    tmp = new Warrior(Enums.EntityType.Guerrier, name, i, cellTable[0]);
                    playerList.add(tmp);
                    cellTable[0].players.add(tmp);
                    break;
                case  2:
                    tmp = new Wizard(Enums.EntityType.Mage, name, i, cellTable[0]);
                    playerList.add(tmp);
                    cellTable[0].players.add(tmp);
                    break;
            }
        }
    }

    Enums.EntityType randomEnemyType() {
        Enums.EntityType[] types = { Enums.EntityType.Goblin, Enums.EntityType.Sorcier, Enums.EntityType.Dragon };
        return types[random.nextInt(types.length)];
    }

    public void initEnemies() {

        for (int i = 0; i < maxEnemies; i++)
        {
            //int cellIndex = random.nextInt(cellTable.length);
            int cellIndex = 1;
            Enemy tmp = null;
            switch (randomEnemyType()) {
                case Enums.EntityType.Goblin:
                    tmp = new Goblin();
                    cellTable[cellIndex].addEnnemy(tmp);
                    this.enemyList.add(tmp);
                    break;
                case Enums.EntityType.Sorcier:
                    tmp = new Sorcier();
                    cellTable[cellIndex].addEnnemy(tmp);
                    this.enemyList.add(tmp);
                    break;
                case Enums.EntityType.Dragon:
                    tmp = new Dragon();
                    cellTable[cellIndex].addEnnemy(tmp);
                    this.enemyList.add(tmp);
                    break;
            }

        }
    }

    public int throwDice() {
        Random random = new Random();
        int diceValue = random.nextInt(6) + 1;
        //return diceValue;
        return 1;
    }

    public String requestInput() {
        String text = sc.nextLine();

        return text;
    }

    public void checkBattle(Character player, Enemy enemy)
    {
        enemy.setHp(enemy.getHp() - player.getDmg());
    }

    public void setMaxPlayer(int nb) {
    this.maxPlayer = nb;
    }

    public void updateGame() {
        Character player = playerList.get(currentPlayer);
        if (player.getPos() == 62) {
            gameState = Enums.GameState.Finish;
            System.out.println("Joueur " + player.getName() + " le "+ player.getType() + " à atteinds la dernière case");
        }
        else
            System.out.println("Tour de " + player.getName());
        switch (gameState) {
            case Idle:
                System.out.println("1) Lancer de dée | 2) Utiliser potion");
                String inputText = requestInput();
                if (inputText.equals("1")) {
                    int dice = throwDice();
                    player.moveAvailable = dice;
                    gameState = Enums.GameState.Moving;
                }
                /*else if (requestInput().equals("2")) {
                    player.usePotion();
                    gameState = fr.campus.poo_java.Enums.GameState.Idle;
                }*/
                else if (inputText.equals("42")) {
                    player.moveAvailable = 63;
                    gameState = Enums.GameState.Moving;
                }
                else {
                    System.out.println("Choix invalide");
                    updateGame();
                }
                break;
            case Moving:
                System.out.println("Entrer) Avance de " + playerList.get(currentPlayer).moveAvailable + " case");
                int pPos = player.getPos();
                if (requestInput().equals("")) {
                    if (player.moveAvailable > 0 && pPos + player.moveAvailable < maxCell - 1) {
                        player.moveEntityToCell(cellTable[pPos], cellTable[pPos + 1]);
                        player.moveAvailable--;
                        if (!cellTable[player.getPos()].isEnnemiesEmpty())
                            gameState = Enums.GameState.InBattle;
                        else if (player.moveAvailable == 0)
                            gameState = Enums.GameState.End;
                    }
                    else if (pPos + player.moveAvailable > maxCell - 1) {
                        player.moveEntityToCell(cellTable[pPos], cellTable[maxCell - 1]);
                    }
                }
                break;
            case InBattle:
                Enemy enemy = cellTable[player.getPos()].ennemies.get(0);
                System.out.println("Joueur " + player.getName() + " le " + player.getType() + " tombe sur " + enemy.getName());
                System.out.println("Joueur  : " + player.getName() + " le " + player.getType() + " PV : " + player.getHp() + " Dégats : " + player.getDmg());
                System.out.println("Ennemie : " + enemy.getName() + " PV : " + player.getHp() + " Dégats : " + player.getDmg());
                requestInput();
                checkBattle(player, enemy);
                if (enemy.getHp() < 0)
                break;
            case End:
                System.out.println("Enter) Fin de tour du joueur : " + player.getName() + " le " + player.getEntityType());
                requestInput();
                gameState = Enums.GameState.Idle;
                currentPlayer++;
                if (currentPlayer > playerList.size() - 1)
                    currentPlayer = 0;
                break;
            case Finish:
                System.out.println("Fin du jeu");
                break;
        }
    }
}
