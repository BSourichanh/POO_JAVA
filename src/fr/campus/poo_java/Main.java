package fr.campus.poo_java;

import fr.campus.poo_java.db.Database;

public  class  Main {
    void main() {
        /*Database db = new Database();
        db.clearHeroes();
        db.pingSQL();
        db.getHeroes();*/
        Game game = new Game();
        game.initGame();
        game.startGame();
    }
}