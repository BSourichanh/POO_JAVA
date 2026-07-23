package fr.campus.poo_java;

import fr.campus.poo_java.db.Database;
import fr.campus.poo_java.entity.Character;
import fr.campus.poo_java.entity.Enemy;
import fr.campus.poo_java.entity.character.Warrior;
import fr.campus.poo_java.entity.character.Wizard;
import fr.campus.poo_java.entity.enemies.Dragon;
import fr.campus.poo_java.entity.enemies.Goblin;
import fr.campus.poo_java.entity.enemies.Sorcier;
import fr.campus.poo_java.equipement.defensive_equipement.DefensiveEquipement;
import fr.campus.poo_java.equipement.defensive_equipement.potion.BigPotionHP;
import fr.campus.poo_java.equipement.defensive_equipement.potion.PotionHP;
import fr.campus.poo_java.equipement.offensive_equipement.OffensiveEquipment;

import java.security.PublicKey;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public int currentPlayer = 0;
    public Enums.GameState gameState = Enums.GameState.Idle;
    Scanner sc = new Scanner(System.in);

    public int maxPlayer = 2;
    public int maxCell = 4;
    public static int maxEnemies = 1;
    private static int maxPotion = 1;
    public Cell[] cellTable =  new Cell[maxCell];
    Random random = new Random();

    private Menu menu;
    private Database db;

    public Game(){
        gameState = Enums.GameState.Idle;
        menu = new Menu();
        db = new Database();
    }

    public void showData() {
        for (int i = 0 ; i < cellTable.length - 1; i++)
        {
            Cell currentCell = cellTable[i];
            if (!currentCell.players.isEmpty())
                for (int y = 0; y < currentCell.players.size(); y++)
                {
                    cellTable[i].players.get(y).getInfo();
                }
        }
        for (int i = 0 ; i < cellTable.length - 1; i++)
        {
            Cell currentCell = cellTable[i];
            if (!currentCell.enemies.isEmpty())
                for (int y = 0; y < currentCell.enemies.size(); y++)
                {
                    cellTable[i].enemies.get(y).getInfo();
                }
        }
        System.out.println("\n");
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
                    cellTable[0].players.add(tmp);
                    db.createHeros(tmp);
                    break;
                case  2:
                    tmp = new Wizard(Enums.EntityType.Mage, name, i, cellTable[0]);
                    cellTable[0].players.add(tmp);
                    db.createHeros(tmp);
                    break;
            }
        }
    }

    public Character getPlayerById(int id){
        for (Cell cell : cellTable) {
            for (Character player : cell.players)
                if (player.getId() == id)
                    return player;
        }
        return null;
    }

    Enums.EntityType randomEnemyType() {
        Enums.EntityType[] types = { Enums.EntityType.Goblin, Enums.EntityType.Sorcier, Enums.EntityType.Dragon };
        return types[random.nextInt(types.length)];
    }

    Enums.DefEquip randomPotionType() {
        Enums.DefEquip[] types = {Enums.DefEquip.PotionPV, Enums.DefEquip.GrandePotionPV};
        return types[random.nextInt(types.length)];
    }

    public  void initPotion() {
        for (int i = 0; i < maxPotion; i++)
        {
            //int cellIndex = random.nextInt(cellTable.length);
            int cellIndex = 2;
            switch (randomPotionType()) {
                case Enums.DefEquip.GrandePotionPV:
                    cellTable[cellIndex].addPotion(new BigPotionHP());
                    break;
                case Enums.DefEquip.PotionPV:
                    cellTable[cellIndex].addPotion(new PotionHP());
                    break;
            }
        }
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
                    cellTable[cellIndex].addEnemy(tmp);
                    break;
                case Enums.EntityType.Sorcier:
                    tmp = new Sorcier();
                    cellTable[cellIndex].addEnemy(tmp);
                    break;
                case Enums.EntityType.Dragon:
                    tmp = new Dragon();
                    cellTable[cellIndex].addEnemy(tmp);
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

    public void checkBattle(Character player, Enemy enemy) {
        enemy.setHp(enemy.getHp() - player.getDmg());
    }

    public void setMaxPlayer(int nb) {
    this.maxPlayer = nb;
    }

    public Enums.GameState  manageAction(String inputText, Character player) {
        int input = Integer.parseInt(inputText);
        if (input == 1) {
            player.moveAvailable = throwDice();;
            menu.showDiceThrow(player);
            return Enums.GameState.Moving;
        }
        else if (input == 2) {
            if (menu.showPlayerPotion(player))
                input = menu.requestPotion(player);
                player.usePotion(player.getPotion(input));
            }
        else if (input == 42) {
            player.moveAvailable = 63;
            return Enums.GameState.Moving;
        }
        else {
            menu.showWrongChoice();
            playTurn();
        }
        return Enums.GameState.Idle;
    }

    public Enums.GameState manageMove(Character player){
        if (player.moveAvailable == 0) {
            return Enums.GameState.End;
        }
        int pPos = player.getPos();
        menu.requestInputToMove(player);
        if (player.moveAvailable > 0 && pPos + player.moveAvailable < maxCell) {
            Cell tmpCell = cellTable[pPos];
            player.moveEntityToCell(tmpCell, cellTable[pPos + 1]);
            tmpCell = cellTable[pPos + 1];
            player.moveAvailable--;
            if (!tmpCell.isEnemiesEmpty())
                return Enums.GameState.InBattle;
            if (!tmpCell.isDefEquipEmpty()) {
                DefensiveEquipement tmpDef = tmpCell.defEquip.getFirst();
                menu.showPickDefEquip(player, tmpDef);
                player.addDefensiveEquipement(tmpDef);
                tmpCell.removePotion(tmpDef);
            }
            return Enums.GameState.Moving;
        }
        else if (pPos + player.moveAvailable > maxCell) {
            player.moveEntityToCell(cellTable[pPos], cellTable[maxCell]);
            return Enums.GameState.Finish;
        }
        return null;
    }

    public void manageBattle(Character player) {
        Enemy enemy = cellTable[player.getPos()].enemies.getFirst();
        menu.showBattleInfo(player, enemy);
        checkBattle(player, enemy);
        menu.showDmg(player, enemy);
        enemy.setHp(enemy.getHp() - player.getDmg());
        menu.showBattleResult(player, enemy);
        cellTable[player.getPos()].removeEnemy(enemy);
    }

    public void playTurn() {
        menu.showSeperator();
        Character player = getPlayerById(currentPlayer);
        menu.showData(cellTable);
        if (player.getPos() == maxCell -1) {
            gameState = Enums.GameState.Finish;
            menu.showPlayerFinish(player);
        }
        menu.showSeperator();
        menu.showCellsData(cellTable, maxCell);
        switch (gameState) {
            case Idle:
                menu.showCurrentPlayerTurn(player);
                menu.showPlayerIdleAction();
                gameState = manageAction(menu.requestInput(), player);
                break;
            case Moving:
                menu.showMoveAvailable(player);
                gameState = manageMove(player);
                break;
            case InBattle:
                manageBattle(player);
                gameState = Enums.GameState.Moving;
                break;
            case End:
                menu.showPlayerEndTurn(player);
                menu.showSeperator();
                menu.requestInput();
                if (currentPlayer < maxPlayer - 1)
                    currentPlayer++;
                else
                    currentPlayer = 0;
                gameState = Enums.GameState.Idle;
                break;
            case Finish:
                menu.showEndGame();
                break;
        }
    }

    public void initGame() {
        menu = new Menu();
        initCells();
        setMaxPlayer(menu.requestNbPlayer());
        initPlayers();
        initEnemies();
        initPotion();
    }

    public void startGame() {
        while (gameState != Enums.GameState.Finish)
            playTurn();
    }
}
