package fr.campus.poojava.entity;

/**
 * Classe abstraite de base représentant une entité vivante du jeu (Personnage ou Ennemi).
 * Contient les caractéristiques communes telles que le nom, le type, la position et les points de vie.
 *
 * @author BSourichanh
 */
public abstract class Entity {
	/**
	 * Identifiant unique de l'entité
	 */
	protected int id;
	
	/**
	 * Nom de l'entité
	 */
	protected String name;
	
	/**
	 * Type d'entité (WARRIOR, WIZARD, GOBLIN, etc.)
	 */
	protected EntityType type;
	
	/**
	 * Position courante sur le plateau (0-indexée)
	 */
	protected int pos;
	
	/**
	 * Points de vie courants de l'entité
	 */
	protected int lifePoints;
	
	/**
	 * Points de vie maximum de l'entité
	 */
	protected int maxLifePoints;
	
	/**
	 * Force/Dégâts de base de l'entité
	 */
	protected int strength;
	
	/**
	 * Constructeur par défaut.
	 */
	public Entity () {
	}
	
	/**
	 * Constructeur complet d'une entité.
	 *
	 * @param type       Le type d'entité.
	 * @param name       Le nom de l'entité.
	 * @param id         L'identifiant unique.
	 * @param pos        La position initiale.
	 * @param lifePoints Les points de vie initiaux (définissent également les PV max).
	 * @param strength   La force/dégâts de base.
	 */
	public Entity (EntityType type, String name, int id, int pos, int lifePoints, int strength) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.pos = pos;
		this.lifePoints = lifePoints;
		this.maxLifePoints = lifePoints;
		this.strength = strength;
	}
	
	/**
	 * @return L'identifiant unique.
	 */
	public int getId () {
		return id;
	}
	
	/**
	 * @param id Le nouvel identifiant.
	 */
	public void setId (int id) {
		this.id = id;
	}
	
	/**
	 * @return Le nom de l'entité.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * @param name Le nouveau nom.
	 */
	public void setName (String name) {
		this.name = name;
	}
	
	/**
	 * @return Le type d'entité.
	 */
	public EntityType getType () {
		return type;
	}
	
	/**
	 * @param type Le nouveau type d'entité.
	 */
	public void setType (EntityType type) {
		this.type = type;
	}
	
	/**
	 * @return La position courante sur le plateau.
	 */
	public int getPos () {
		return pos;
	}
	
	/**
	 * @param pos La nouvelle position sur le plateau.
	 */
	public void setPos (int pos) {
		this.pos = pos;
	}
	
	/**
	 * @return Les points de vie actuels.
	 */
	public int getHp () {
		return lifePoints;
	}
	
	/**
	 * @param lifePoints Les nouveaux points de vie.
	 */
	public void setHp (int lifePoints) {
		this.lifePoints = lifePoints;
	}
	
	/**
	 * @return Les points de vie maximum.
	 */
	public int getMaxHp () {
		return maxLifePoints > 0 ? maxLifePoints : lifePoints;
	}
	
	/**
	 * @return La force/dégâts de base.
	 */
	public int getDmg () {
		return strength;
	}
	
	/**
	 * @param strength La nouvelle force/dégâts de base.
	 */
	public void setDmg (int strength) {
		this.strength = strength;
	}
}
