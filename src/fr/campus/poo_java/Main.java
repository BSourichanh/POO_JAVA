package fr.campus.poo_java;

import fr.campus.poo_java.entity.character.Warrior;
import fr.campus.poo_java.entity.character.Wizard;

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
            game.updateGame();
            System.out.println(game.gameState);
        }
    }
}