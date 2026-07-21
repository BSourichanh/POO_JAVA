package fr.campus.poo_java;

public class Enums {
    public enum EntityType {
        Guerrier, Mage, Goblin, Sorcier, Dragon
    }
    public enum ItemType{
        Weapon, Spell, Potion
    }
    public enum Weapon{
    Massue, Epée
    }
    public enum Spell{
        Eclair, Boule_de_feu
    }
    public  enum  Potion{
        PotionPV, GrandePotionPV
    }

    public enum GameState{
        Idle, Moving, InBattle, End, Finish
    }
}
