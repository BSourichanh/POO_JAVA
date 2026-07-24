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
import fr.campus.poo_java.equipement.offensive_equipement.spell.FireBall;
import fr.campus.poo_java.equipement.offensive_equipement.spell.ThunderBolt;
import fr.campus.poo_java.equipement.offensive_equipement.weapon.Mace;
import fr.campus.poo_java.equipement.offensive_equipement.weapon.Sword;

import java.util.Random;
import java.util.Scanner;

public class Game {
    public int currentPlayer = 0;
    public Enums.GameState gameState = Enums.GameState.Idle;
    Scanner sc = new Scanner(System.in);

    public int maxPlayer = 2;
    public int maxCell = 63;

    public  static int maxEnemies = 20;
    private static int maxPotion = 10;
    public  static int maxWeapon = 50;
    public Cell[] cellTable =  new Cell[maxCell];
    Random random = new Random();

    private Menu menu;
    private Database db;

    public Game(){
        gameState = Enums.GameState.Idle;
        menu = new Menu();
        db = new Database();
    }

    //Init
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
    public void initEnemies() {

        for (int i = 0; i < maxEnemies; i++)
        {
            int cellIndex = random.nextInt(1, cellTable.length);
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
    public void initOffEquip(){
        for (int i = 0; i < maxWeapon; i++)
        {
            int cellIndex = random.nextInt(1,cellTable.length);
            OffensiveEquipment tmp = null;
            switch (randomOffEquipType()) {
                case Enums.OffEquip.Epée:
                    tmp = new Sword();
                    cellTable[cellIndex].addOffEquip(tmp);
                    break;
                case Enums.OffEquip.Massue:
                    tmp = new Mace();
                    cellTable[cellIndex].addOffEquip(tmp);
                    break;
                case Enums.OffEquip.Eclair:
                    tmp = new ThunderBolt();
                    cellTable[cellIndex].addOffEquip(tmp);
                    break;
                case Enums.OffEquip.Boule_de_feu:
                    tmp = new FireBall();
                    cellTable[cellIndex].addOffEquip(tmp);
                    break;
            }
        }
    }
    public  void initDefEquip() {
        for (int i = 0; i < maxPotion; i++)
        {
            int cellIndex = random.nextInt(1, cellTable.length);
            switch (randomDefEquipType()) {
                case Enums.DefEquip.GrandePotionPV:
                    cellTable[cellIndex].addPotion(new BigPotionHP());
                    break;
                case Enums.DefEquip.PotionPV:
                    cellTable[cellIndex].addPotion(new PotionHP());
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

    //Random
    Enums.EntityType randomEnemyType() {
        Enums.EntityType[] types = { Enums.EntityType.Goblin, Enums.EntityType.Sorcier, Enums.EntityType.Dragon };
        return types[random.nextInt(types.length)];
    }
    Enums.DefEquip randomDefEquipType() {
        Enums.DefEquip[] types = {Enums.DefEquip.PotionPV, Enums.DefEquip.GrandePotionPV};
        return types[random.nextInt(types.length)];
    }
    Enums.OffEquip randomOffEquipType(){
        Enums.OffEquip[] types = Enums.OffEquip.values();
        return types[random.nextInt(types.length)];
    }

    public int throwDice() {
        Random random = new Random();
        int diceValue = random.nextInt(6) + 1;
        return diceValue;
    }

    public void checkBattle(Character player, Enemy enemy) {
        if (player.getCurrentOffEquipement() == null)
        enemy.setHp(enemy.getHp() - player.getDmg());
        else
            enemy.setHp(enemy.getHp() - (player.getDmg() + player.getCurrentOffEquipement().getDamage()));
    }

    public void setMaxPlayer(int nb) {
    this.maxPlayer = nb;
    }

    public Enums.GameState  manageAction(String inputText, Character player) {
        int input = Integer.parseInt(inputText);
        if (input == 1) {
            player.moveAvailable = throwDice();;
            menu.requestInputDiceThrow(player);
            return Enums.GameState.Moving;
        }
        else if (input == 2) {
            if (menu.showDefEquips(player))
                input = menu.requestNb();
                player.useDefEquip(player.getDefEquipById(input));
            }
        else if (input == 3)
        {
            if (menu.showOffEquips(player)){
                input = menu.requestNb();
                if (input == 0)
                    return  Enums.GameState.Idle;
                OffensiveEquipment tmpOffEquip = player.getOffEquipById(input - 1);
                if (player.getCurrentOffEquipement() == null) {
                    player.setCurrentOffEquip(tmpOffEquip);
                    player.removeFromInventoryOffEquipement(tmpOffEquip);
                }
                else {
                    player.moveOffEquipToInventory();
                    player.setCurrentOffEquip(tmpOffEquip);
                    player.removeFromInventoryOffEquipement(tmpOffEquip);
                }
            }
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
        menu.showMoveAvailable(player);
        menu.requestInputToMove(player);
        if (player.moveAvailable > 0 && pPos + player.moveAvailable < maxCell) {
            Cell tmpCell = cellTable[pPos];
            player.moveEntityToCell(tmpCell, cellTable[pPos + 1]);
            tmpCell = cellTable[pPos + 1];
            player.moveAvailable--;
            menu.showCellsData(cellTable, maxCell);
            if (!tmpCell.isEnemiesEmpty())
                return Enums.GameState.InBattle;
            if (!tmpCell.isDefEquipEmpty()) {
                DefensiveEquipement tmpDef = tmpCell.defEquip.getFirst();
                menu.showPickDefEquip(player, tmpDef);
                player.addDefensiveEquipment(tmpDef);
                tmpCell.removePotion(tmpDef);
            }
            if (!tmpCell.isOffEquipEmpty()) {
                OffensiveEquipment tmpOff = tmpCell.offEquip.getFirst();
                menu.showPickOffEquip(player, tmpOff);
                player.addOffensiveEquipement(tmpOff);
                tmpCell.removeOffEquip(tmpOff);
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
        menu.showBattleResult(player, enemy);
        if (enemy.getHp() > 0)
            player.setHp(player.getHp() - enemy.getDmg());
        cellTable[player.getPos()].removeEnemy(enemy);
    }

    public void playTurn() {
        System.out.println( "//"+ gameState);
        menu.showSeperator();
        Character player = getPlayerById(currentPlayer);
        menu.showCurrentPlayer(player);
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
                gameState = manageMove(player);
                break;
            case InBattle:
                manageBattle(player);
                gameState = Enums.GameState.Moving;
                break;
            case End:
                menu.showPlayerEndTurn(player);
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
        initOffEquip();
        initDefEquip();
    }

    public void startGame() {
        while (gameState != Enums.GameState.Finish)
            playTurn();
    }
}
