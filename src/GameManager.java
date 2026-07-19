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
        System.out.println("Dice value: " + diceValue);
        return diceValue;
    }
    public String requestInput() {
        String text = sc.nextLine();
        return text;
    }

    public void updateGame(Entity[] playerTable, Cell[] cellTable) {
        Entity tmp = playerTable[currentPlayer];
        switch (gameState) {
            case Idle:
                System.out.println("1) Lancer de dée | 2) Utiliser potion");
                if (requestInput().equals("1")) {
                    int dice = throwDice();
                    tmp.moveAvaible = dice;
                    gameState = Enums.GameState.Moving;
                }
                break;
            case Moving:
                System.out.println("Déplacement disponible: " + playerTable[currentPlayer].moveAvaible);
                if (requestInput().equals("")) {
                    if (tmp.moveAvaible > 0) {
                        cellTable[tmp.getPos()].removePlayer(tmp);
                        cellTable[tmp.getPos() + 1].addPlayer(tmp);
                        tmp.moveEntityToCell(cellTable[tmp.getPos() + 1]);
                        tmp.moveAvaible--;
                    } else {
                        gameState = Enums.GameState.End;
                    }
                }
                break;
            case InBattle:
                break;
            case End:
                break;
        }
    }
}
