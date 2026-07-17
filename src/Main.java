int maxPlayer = 1;
int maxCell = 63;

void showPlayerData(Player [] playerTable)
{
    for(int i = 0; i < playerTable.length; i++)
    {
        playerTable[i].getInfo();
    }
}

void main()
{
    Player[] playerTable = new Player[maxPlayer];
    Cell[] cellTable =  new Cell[maxCell];
    playerTable[0] = new Player(PlayerType.Warrior, "toto", 0);


    for (int i = 0; i < maxCell; i++)
        cellTable[i] = new Cell();
    for (int i = 0; i < maxPlayer; i++)
        cellTable[0].addPlayer(playerTable[0]);
    showPlayerData(playerTable);
}