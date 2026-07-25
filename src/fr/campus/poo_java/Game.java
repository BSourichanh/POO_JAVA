package fr.campus.poo_java;

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

    public  static int maxEnemies = 24;
    private static int maxPotion = 8;
    public  static int maxWeapon = 16;
    public Cell[] cellTable =  new Cell[maxCell];
    Random random = new Random();

    private Menu menu;

    public Game(){
        gameState = Enums.GameState.Idle;
        menu = new Menu();
    }

    //Init
    public void initCells() {
        for (int i = 0; i < maxCell; i++)
            cellTable[i] = new Cell(i);
    }
    public void initPlayers(){
        Character tmp;
        for (int i = 0; i < maxPlayer; i++)
        {

            int idType = menu.chooseClass(maxPlayer, i);
            String name = menu.requestName();
            switch (idType)
            {
                case 1:
                    tmp = new Warrior(Enums.EntityType.Guerrier, name, i, cellTable[0]);
                    cellTable[0].players.add(tmp);
                    break;
                case  2:
                    tmp = new Wizard(Enums.EntityType.Mage, name, i, cellTable[0]);
                    cellTable[0].players.add(tmp);
                    break;
            }
        }
    }
    public void initEnemies() {

        for (int i = 0; i < maxEnemies; i++)
        {
            int cellIndex = random.nextInt(1, cellTable.length);
            Enemy tmp = null;
            if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty())
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
            if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty())
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
            if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty())
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

    public Enums.GameState  manageAction(Character player) {
        menu.showHeader(player, cellTable, maxCell);
        menu.showPlayerIdleAction();
        int input = menu.requestNb();
        if (input == -1)
            return manageAction(player);
        if (input == 1) {
            player.moveAvailable = throwDice();;
            menu.requestInputDiceThrow(player);
            return Enums.GameState.Moving;
        }
        else if (input == 2) {
            if (menu.showDefEquips(player)) {
                input = menu.requestNb();
                DefensiveEquipement potion = player.getDefEquipById(input);
                if (potion != null)
                    player.useDefEquip(potion);
            }
        }
        else if (input == 3)
        {
            if (menu.showOffEquips(player)){
                input = menu.requestNb();
                if (input == 0)
                    return  Enums.GameState.Idle;
                OffensiveEquipment tmpOffEquip = player.getOffEquipById(input - 1);
                player.setCurrentOffEquip(tmpOffEquip);
                return Enums.GameState.Idle;
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
        menu.showHeader(player, cellTable, maxCell);
        menu.showCurrentPlayerTurn(player);
        if (player.moveAvailable == 0) {
            return Enums.GameState.End;
        }
        int pPos = player.getPos();
        menu.showMoveAvailable(player);
        Enums.GameState action = menu.requestInputAction(player);
        if  (action == Enums.GameState.Moving) {
            if (pPos + 1 < maxCell) {
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
            } else {
                player.moveEntityToCell(cellTable[pPos], cellTable[maxCell - 1]);
                return Enums.GameState.Finish;
            }
        }else if (action == Enums.GameState.Inventory){
            return Enums.GameState.Inventory;
        }
        return Enums.GameState.Moving;
    }

    public Enums.GameState manageInventory(Character player) {
        menu.showHeader(player, cellTable, maxCell);
        if (!menu.showOffEquips(player))
            return Enums.GameState.Moving;
        int nb = menu.requestNb();
        if (nb <= 0)
            return Enums.GameState.Moving;
        OffensiveEquipment tmpOffEquip = player.getOffEquipById(nb - 1);
        if (player.setCurrentOffEquip(tmpOffEquip) == -1) {
            menu.showInvalideItemType(player);
            return manageInventory(player);
        }else
            return Enums.GameState.Inventory;
    }

    public void manageBattle(Character player) {
        menu.showHeader(player, cellTable, maxCell);
        Enemy enemy = cellTable[player.getPos()].enemies.getFirst();
        menu.showBattleInfo(player, enemy);
        checkBattle(player, enemy);
        menu.showDmg(player, enemy);
        menu.showBattleResult(player, enemy);
        if (enemy.getHp() > 0)
            player.setHp(player.getHp() - enemy.getDmg());
        if (player.getHp() <= 0)
            killPlayer(player);
        cellTable[player.getPos()].removeEnemy(enemy);
    }

    // Le joueur mort est retiré de sa cellule : il n'apparait plus sur le plateau
    // et getPlayerById ne le trouve plus.
    public void killPlayer(Character player) {
        player.setHp(0);
        cellTable[player.getPos()].removePlayer(player);
        menu.showPlayerDeath(player);
    }

    public int countAlivePlayers() {
        int count = 0;
        for (Cell cell : cellTable)
            count += cell.players.size();
        return count;
    }

    // Passe au prochain joueur encore en vie.
    public void nextPlayer() {
        for (int i = 1; i <= maxPlayer; i++) {
            int id = (currentPlayer + i) % maxPlayer;
            if (getPlayerById(id) != null) {
                currentPlayer = id;
                return;
            }
        }
    }

    public void playTurn() {
        Character player = getPlayerById(currentPlayer);
        System.out.println("//" + gameState);
        if (player.moveAvailable == 0 && gameState == Enums.GameState.Moving) {
            gameState = Enums.GameState.End;
        }
            if (player.getPos() == maxCell - 1) {
                gameState = Enums.GameState.Finish;
                menu.showPlayerFinish(player);
            }

            switch (gameState) {
                case Idle:
                    gameState = manageAction(player);
                    break;
                case Moving:
                    gameState = manageMove(player);
                    break;
                case Inventory:
                    gameState = manageInventory(player);
                    break;
                case InBattle:
                    manageBattle(player);
                    if (player.getHp() <= 0) {
                        if (countAlivePlayers() == 0) {
                            menu.showGameOver();
                            gameState = Enums.GameState.Finish;
                        } else {
                            nextPlayer();
                            gameState = Enums.GameState.Idle;
                        }
                    } else
                        gameState = Enums.GameState.Moving;
                    break;
                case End:
                    menu.showHeader(player, cellTable, maxCell);
                    menu.showPlayerEndTurn(player);
                    menu.requestInput();
                    nextPlayer();
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
        setMaxPlayer(menu.requestNbPlayer(maxPlayer));
        initPlayers();
        initEnemies();
        initOffEquip();
        initDefEquip();
    }

    public void startGame() {
        this.initGame();
        while (gameState != Enums.GameState.Finish)
            playTurn();
    }
}
