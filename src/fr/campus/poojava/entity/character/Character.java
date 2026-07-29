package fr.campus.poojava.entity.character;

import fr.campus.poojava.entity.Entity;
import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;
import fr.campus.poojava.game.board.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un personnage joueur dans le jeu.
 * Gère les déplacements, l'inventaire d'armes/sorts, l'équipement actif et les potions.
 *
 * @author BSourichanh
 */
public abstract class Character extends Entity {
	/** Nombre de cases de déplacement restantes pour le tour courant */
	protected int moveAvailable = 0;

	/** Inventaire des équipements offensifs possédés (armes ou sorts) */
	protected List<OffensiveEquipment> offEquipments = new ArrayList<>();

	/** Inventaire des équipements défensifs possédés (potions de soin) */
	protected List<DefensiveEquipment> defensiveEquipments = new ArrayList<>();

	/** Équipement offensif actuellement tenu en main/équipé */
	protected OffensiveEquipment currentOffEquip;

	/**
	 * Constructeur de personnage.
	 *
	 * @param type            Le type de personnage (WARRIOR ou WIZARD).
	 * @param name            Le nom du héros.
	 * @param id              L'identifiant du joueur.
	 * @param initialHp       Points de vie de départ.
	 * @param initialStrength Force de base de départ.
	 */
	public Character (EntityType type, String name, int id, int initialHp, int initialStrength) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.lifePoints = initialHp;
		this.strength = initialStrength;
		this.pos = 0;
	}

	/**
	 * Vérifie de façon polymorphique si le personnage peut équiper un objet offensif donné.
	 *
	 * @param offEquip L'équipement offensif à tester.
	 * @return true si le personnage peut équiper l'objet, false sinon.
	 */
	public abstract boolean canEquip (OffensiveEquipment offEquip);

	/** @return Le nombre de cases de déplacement disponibles. */
	public int getMoveAvailable () {
		return this.moveAvailable;
	}

	/** @return La liste des équipements défensifs (potions). */
	public List<DefensiveEquipment> getDefensiveEquipment () {
		return defensiveEquipments;
	}

	/** @return La liste des équipements offensifs en réserve. */
	public List<OffensiveEquipment> getOffensiveEquipment () {
		return offEquipments;
	}

	/** @return L'équipement offensif actuellement équipé (null si mains nues). */
	public OffensiveEquipment getCurrentOffEquipment () {
		return this.currentOffEquip;
	}

	/** @param moveAvailable Le nombre de mouvements disponibles à définir. */
	public void setMoveAvailable (int moveAvailable) {
		this.moveAvailable = moveAvailable;
	}

	/**
	 * Ajoute un nouvel équipement offensif dans l'inventaire.
	 *
	 * @param offEquip L'équipement offensif à ajouter.
	 */
	public void addOffensiveEquipment (OffensiveEquipment offEquip) {
		this.offEquipments.add(offEquip);
	}

	/**
	 * Ajoute une potion dans l'inventaire défensif.
	 *
	 * @param defEquip La potion à ajouter.
	 */
	public void addDefensiveEquipment (DefensiveEquipment defEquip) {
		this.defensiveEquipments.add(defEquip);
	}

	/**
	 * Équipe un objet offensif depuis l'inventaire s'il est compatible avec la classe du personnage.
	 *
	 * @param offEquip L'équipement à équiper.
	 * @return 1 si l'équipement a réussi, -1 si incompatible ou nul.
	 */
	public int setCurrentOffEquip (OffensiveEquipment offEquip) {
		if (offEquip == null || !canEquip(offEquip)) {
			return -1;
		}
		offEquipments.remove(offEquip);
		if (currentOffEquip != null) {
			offEquipments.add(currentOffEquip);
		}
		currentOffEquip = offEquip;
		return 1;
	}

	/** Décrémente le nombre de cases de déplacement restantes. */
	public void decreaseMoveAvailable () {
		this.moveAvailable--;
	}

	/**
	 * Retire un équipement défensif de l'inventaire.
	 *
	 * @param defEquip La potion à retirer.
	 */
	public void removeDefensiveEquipment (DefensiveEquipment defEquip) {
		defensiveEquipments.remove(defEquip);
	}

	/**
	 * Déplace le personnage de la case de départ vers la case d'arrivée.
	 *
	 * @param startCell Case de départ (peut être null).
	 * @param nextCell  Case d'arrivée.
	 */
	public void moveEntityToCell (Cell startCell, Cell nextCell) {
		if (nextCell != null) {
			nextCell.addPlayer(this);
			if (startCell != null) {
				startCell.removePlayer(this);
			}
			this.pos = nextCell.getPos();
		}
	}

	/**
	 * Consomme une potion pour restaurer les points de vie et la retire de l'inventaire.
	 *
	 * @param potion La potion à utiliser.
	 */
	public void useDefEquip (DefensiveEquipment potion) {
		this.lifePoints += potion.getHp();
		removeDefensiveEquipment(potion);
	}

	/** @return true si l'inventaire de potions est vide, false sinon. */
	public boolean isDefEquipEmpty () {
		return this.defensiveEquipments.isEmpty();
	}

	/** @return true si l'inventaire d'armes/sorts est vide, false sinon. */
	public boolean isOffEquipEmpty () {
		return this.offEquipments.isEmpty();
	}

	/**
	 * Récupère une potion par son index (1-indexé).
	 *
	 * @param id L'identifiant 1-indexé.
	 * @return La potion correspondante ou null.
	 */
	public DefensiveEquipment getDefEquipById (int id) {
		if (id > 0 && id <= defensiveEquipments.size()) {
			return defensiveEquipments.get(id - 1);
		}
		return null;
	}

	/**
	 * Récupère un équipement offensif par son index (0-indexé).
	 *
	 * @param id L'index 0-indexé.
	 * @return L'équipement offensif ou null.
	 */
	public OffensiveEquipment getOffEquipById (int id) {
		if (id >= 0 && id < offEquipments.size()) {
			return offEquipments.get(id);
		}
		return null;
	}
}
