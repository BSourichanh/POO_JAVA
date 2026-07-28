package fr.campus.poojava.game.board;

import fr.campus.poojava.entity.EntityType;
import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.entity.character.Warrior;
import fr.campus.poojava.entity.character.Wizard;
import fr.campus.poojava.entity.enemies.Dragon;
import fr.campus.poojava.entity.enemies.Enemy;
import fr.campus.poojava.entity.enemies.Goblin;
import fr.campus.poojava.entity.enemies.Sorcerer;
import fr.campus.poojava.equipment.defensive.DefEquip;
import fr.campus.poojava.equipment.defensive.potion.BigPotionHP;
import fr.campus.poojava.equipment.defensive.potion.PotionHP;
import fr.campus.poojava.equipment.offensive.OffEquip;
import fr.campus.poojava.equipment.offensive.spell.FireBall;
import fr.campus.poojava.equipment.offensive.spell.ThunderBolt;
import fr.campus.poojava.equipment.offensive.weapon.Mace;
import fr.campus.poojava.equipment.offensive.weapon.Sword;
import fr.campus.poojava.ui.Menu;

import java.util.Random;

public class GameBoard {
	private static final int MAX_ENEMIES = 24;
	private static final int MAX_POTIONS = 8;
	private static final int MAX_WEAPONS = 16;

	private final int maxCell;
	private final Cell[] cellTable;
	private final Random random = new Random();

	public GameBoard (int maxCell) {
		this.maxCell = maxCell;
		this.cellTable = new Cell[maxCell];
		initCells();
	}

	public Cell[] getCellTable () {
		return cellTable;
	}

	public int getMaxCell () {
		return maxCell;
	}

	public Cell getCell (int index) {
		if (index >= 0 && index < maxCell) {
			return cellTable[index];
		}
		return null;
	}

	private void initCells () {
		for (int i = 0; i < maxCell; i++) {
			cellTable[i] = new Cell(i);
		}
	}

	public void initPlayers (int maxPlayer, Menu menu) {
		for (int i = 0; i < maxPlayer; i++) {
			int idType = menu.chooseClass(maxPlayer, i);
			String name = menu.requestName();
			Character player;
			if (idType == 1) {
				player = new Warrior(name, i);
			} else {
				player = new Wizard(name, i);
			}
			player.moveEntityToCell(null, cellTable[0]);
		}
	}

	public void initEnemies () {
		for (int i = 0; i < MAX_ENEMIES; i++) {
			int cellIndex = random.nextInt(1, cellTable.length);
			if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty()) {
				switch (randomEnemyType()) {
					case GOBLIN -> cellTable[cellIndex].addEnemy(new Goblin(cellIndex));
					case SORCERER -> cellTable[cellIndex].addEnemy(new Sorcerer(cellIndex));
					case DRAGON -> cellTable[cellIndex].addEnemy(new Dragon(cellIndex));
				}
			}
		}
	}

	public void initOffEquip () {
		for (int i = 0; i < MAX_WEAPONS; i++) {
			int cellIndex = random.nextInt(1, cellTable.length);
			if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty()) {
				switch (randomOffEquipType()) {
					case SWORD -> cellTable[cellIndex].addOffEquip(new Sword());
					case MACE -> cellTable[cellIndex].addOffEquip(new Mace());
					case LIGHTNING -> cellTable[cellIndex].addOffEquip(new ThunderBolt());
					case FIREBALL -> cellTable[cellIndex].addOffEquip(new FireBall());
				}
			}
		}
	}

	public void initDefEquip () {
		for (int i = 0; i < MAX_POTIONS; i++) {
			int cellIndex = random.nextInt(1, cellTable.length);
			if (cellTable[cellIndex].isEnemiesEmpty() && cellTable[cellIndex].isDefEquipEmpty() && cellTable[cellIndex].isOffEquipEmpty()) {
				switch (randomDefEquipType()) {
					case GRANDE_POTION_PV -> cellTable[cellIndex].addPotion(new BigPotionHP());
					case POTION_PV -> cellTable[cellIndex].addPotion(new PotionHP());
				}
			}
		}
	}

	public Character getPlayerById (int id) {
		for (Cell cell : cellTable) {
			for (Character player : cell.getPlayers()) {
				if (player.getId() == id) {
					return player;
				}
			}
		}
		return null;
	}

	public int countAlivePlayers () {
		int count = 0;
		for (Cell cell : cellTable) {
			count += cell.getPlayers().size();
		}
		return count;
	}

	private EntityType randomEnemyType () {
		EntityType[] types = {EntityType.GOBLIN, EntityType.SORCERER, EntityType.DRAGON};
		return types[random.nextInt(types.length)];
	}

	private DefEquip randomDefEquipType () {
		DefEquip[] types = {DefEquip.POTION_PV, DefEquip.GRANDE_POTION_PV};
		return types[random.nextInt(types.length)];
	}

	private OffEquip randomOffEquipType () {
		OffEquip[] types = OffEquip.values();
		return types[random.nextInt(types.length)];
	}
}
