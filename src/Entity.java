public class Entity {
    private int id;
    private Enums.EntityType type;
    private String name;
    private int hp = 0;
    private int dmg = 0;
    private Cell currentCell;
    private int pos = 0;
    public  int moveAvaible = 0;

    public Entity(Enums.EntityType type, String name, int id, Cell cell) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.currentCell = cell;
        this.pos = cell.getPos();
        switch (type) {
            case Guerrier:
                this.hp = 10;
                this.dmg = 5;
                break;
            case Mage:
                this.hp = 7;
                this.dmg = 7;
                break;
            case Goblin:
                this.hp = 5;
                this.dmg = 3;
                break;
            case Sorcier:
                this.hp = 8;
                this.dmg = 5;
                break;
            case Dragon:
                this.hp = 15;
                this.dmg = 8;
                break;
        }
        if (type != Enums.EntityType.Guerrier && type != Enums.EntityType.Mage) {
            this.name = type.toString();
        }
    }

    void moveEntityToCell(Cell startCell, Cell nextCell) {
        nextCell.addPlayer(this);
        startCell.removePlayer(this);
        currentCell = nextCell;
        this.pos = nextCell.getPos();
    }

    void getInfo() {
        System.out.println("ID : " + this.id +" | Pos : "+ this.pos +" | Name : " + this.name + " | Type : " + this.type + " | HP : " + this.hp + " | DMG : " + this.dmg);
    }

    int getPos() {
        return this.pos;
    }

    int getId() {
        return this.id;
    }

    Enums.EntityType getEntityType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    Enums.EntityType getType() {
        return this.type;
    }
}
