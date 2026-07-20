import java.util.Random;
import java.util.Scanner;

public class GameManager {
    public int currentPlayer = 0;
    public int currentTurn = 0;
    public Enums.GameState gameState = Enums.GameState.Idle;
    Scanner sc = new Scanner(System.in);

    public int throwDice() {
        Random random = new Random();
        int diceValue = random.nextInt(6) + 1;
        return diceValue;
    }

    public String requestInput() {
        String text = sc.nextLine();

        return text;
    }

    public void updateGame(Entity[] playerTable, Cell[] cellTable) {
        Entity player = playerTable[currentPlayer];
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
                    player.moveAvaible = dice;
                    gameState = Enums.GameState.Moving;
                }
                /*else if (requestInput().equals("2")) {
                    player.usePotion();
                    gameState = Enums.GameState.Idle;
                }*/
                else if (inputText.equals("42")) {
                    player.moveAvaible = 63;
                    gameState = Enums.GameState.Moving;
                }
                else {
                    System.out.println("Choix invalide");
                    updateGame(playerTable, cellTable);
                }
                break;
            case Moving:
                System.out.println("Entrer) Avance de " + playerTable[currentPlayer].moveAvaible + " case");
                int pPos = player.getPos();
                if (requestInput().equals("")) {
                    if (player.moveAvaible > 0 && pPos + player.moveAvaible < 62) {
                        player.moveEntityToCell(cellTable[pPos], cellTable[pPos + 1]);
                        player.moveAvaible--;
                        if (!cellTable[player.getPos()].isEnnemiesEmpty())
                            gameState = Enums.GameState.InBattle;
                        else if (player.moveAvaible == 0)
                            gameState = Enums.GameState.End;
                    }
                    else if (pPos + player.moveAvaible > 62) {
                        player.moveEntityToCell(cellTable[pPos], cellTable[62]);
                    }
                }
                break;
            case InBattle:
                Entity ennemy = cellTable[player.getPos()].ennemies.get(0);
                System.out.println("Joueur " + player.getName() + " le " + player.getType() + " tombe sur " + ennemy.getName());
                requestInput();
                break;
            case End:
                System.out.println("Enter) Fin de tour du joueur : " + player.getName() + " le " + player.getEntityType());
                requestInput();
                gameState = Enums.GameState.Idle;
                currentPlayer++;
                if (currentPlayer > playerTable.length - 1)
                    currentPlayer = 0;
                break;
            case Finish:
                System.out.println("Fin du jeu");
                break;
        }
    }
}
