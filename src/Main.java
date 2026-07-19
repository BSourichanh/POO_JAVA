import java.util.List;
import java.util.Random;

int maxPlayer = 2;
int maxCell = 63;
int maxEnnemies = 5;

Entity[] playerTable = new Entity[maxPlayer];
Entity[] enemyTable = new Entity[maxEnnemies];
Cell[] cellTable =  new Cell[maxCell];

Random random = new Random();

Enums.EntityType randomEnemyType() {
    Enums.EntityType[] types = { Enums.EntityType.Goblin, Enums.EntityType.Sorcier, Enums.EntityType.Dragon };
    return types[random.nextInt(types.length)];
}

void initCells() {
    for (int i = 0; i < maxCell; i++)
        cellTable[i] = new Cell(i);
}

void initPlayers(){
    for (int i = 0; i < maxPlayer; i++)
        cellTable[0].addPlayer(playerTable[i]);
}

void initEnemies() {
    for (int i = 0; i < maxEnnemies; i++)
    {
        int cellIndex = random.nextInt(cellTable.length);
        Entity tmp = new Entity(randomEnemyType(), null, i, cellTable[cellIndex]);
        cellTable[cellIndex].addEnnemy(tmp);
        enemyTable[i] = tmp;
    }
}

void showData() {
    for (Entity entity : playerTable) {
        entity.getInfo();
    }
    for (Entity entity : enemyTable) {
        entity.getInfo();
    }
}

void showCellsData(){
    for (int i = 0; i < maxCell; i++)
    {
        System.out.print("[");
        List<Entity> players = cellTable[i].players;
        List<Entity> ennemies = cellTable[i].ennemies;
        for (int p = 0; p < players.size(); p++)
        {
            if (p > 0)
                System.out.print("|");
            System.out.print(players.get(p).getType().toString().substring(0, 3));
        }
        if (!players.isEmpty() && !ennemies.isEmpty())
            System.out.print("|");
        for (int e = 0; e < ennemies.size(); e++)
        {
            if (e > 0)
                System.out.print("|");
            System.out.print(ennemies.get(e).getType().toString().substring(0, 3));
        }
        System.out.print("]");
    }
    System.out.println("");
}

void main() {
    GameManager gameManager = new GameManager();
    initCells();
    playerTable[0] = new Entity(Enums.EntityType.Warrior, "toto", 0, cellTable[0]);
    playerTable[1] = new Entity(Enums.EntityType.Wizard, "tata", 1, cellTable[0]);
    initPlayers();
    initEnemies();
    while (gameManager.gameState != Enums.GameState.End)
    {
        showData();
        showCellsData();
        gameManager.updateGame(playerTable, cellTable);
    }
}