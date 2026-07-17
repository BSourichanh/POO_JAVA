public class Ennemies {
    private int id;
    private String name;
    private EnnemiesType type;
    private int hp;
    private int dmg;

    Ennemies(EnnemiesType type, String name, int id) {
        this.id = id;
        switch (type) {
            case Goblin:
                this.hp = 5;
                this.dmg = 3;
                this.name = type.toString();
                this.type = type;
                break;
            case Sorcier:
                this.hp = 8;
                this.dmg = 5;
                this.name = type.toString();
                this.type = type;
                break;
            case Dragon:
                this.hp = 15;
                this.dmg = 8;
                this.name = type.toString();
                this.type = type;
                break;
        }
    }
}
