public class Entity {
    private int id;
    private Enums.EntityType type;
    private String name;
    private int hp = 0;
    private int dmg = 0;
    private Cell currentCell;
    private int pos = 0;
    public Entity(Enums.EntityType type, String name, int id, Cell cell) {
        this.id = id;
        this.type = type;
        this.name = (name != null) ? name : type.toString();
        this.currentCell = cell;
        this.pos = cell.getPos();
        switch (type) {
            case Warrior:
                this.hp = 10;
                this.dmg = 5;
                break;
            case Wizard:
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
    }

    void getInfo()
    {
        System.out.println("ID : " + this.id +" | Pos : "+ this.pos +" | Name : " + this.name + " | Type : " + this.type + " | HP : " + this.hp + " | DMG : " + this.dmg);
    }

    Enums.EntityType getType() {
        return this.type;
    }
}
