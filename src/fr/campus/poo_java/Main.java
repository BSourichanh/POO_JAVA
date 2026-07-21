package fr.campus.poo_java;

public  class  Main {
    void main() {
        Game game = new Game();
        Menu menu = new Menu();
        game.initCells();
        game.setMaxPlayer(menu.requestNbPlayer());
        game.initPlayers();
        game.initEnemies();
        while (game.gameState != Enums.GameState.Finish)
        {
            game.showData();
            game.showCellsData();
            game.playTurn();
            System.out.println(game.gameState);
        }
    }
}