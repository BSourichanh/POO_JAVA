public class Player {
    private int id;
    private String name;
    private PlayerType type;
    private int hp = 0;
    private int dmg = 0;

    Player(PlayerType type, String name,int id){
        this.id = id;
        this.type = type;
        this.name = name;

        if (type == PlayerType.Warrior) {
            this.hp = 10;
            this.dmg = 5;
        }
        else {
            this.hp = 7;
            this.dmg = 7;
        }
    }
}
