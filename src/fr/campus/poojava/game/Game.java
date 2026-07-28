package fr.campus.poojava.game;

import fr.campus.poojava.entity.character.Character;
import fr.campus.poojava.equipment.defensive.DefensiveEquipment;
import fr.campus.poojava.equipment.offensive.OffensiveEquipment;
import fr.campus.poojava.game.battle.BattleManager;
import fr.campus.poojava.game.board.Cell;
import fr.campus.poojava.game.board.GameBoard;
import fr.campus.poojava.game.dice.Dice6;
import fr.campus.poojava.ui.ConsoleTheme;
import fr.campus.poojava.ui.Menu;
import fr.campus.poojava.ui.MenuBattle;

import java.util.Random;

public class Game {
	private int currentPlayer = 0;
	private GameState gameState;
	private int maxPlayer = 2;
	private final GameBoard board;
	protected Menu menu;
	protected MenuBattle menuBattle;
	protected BattleManager battleManager;
	private final Random random = new Random();

	public Game () {
		this.gameState = GameState.IDLE;
		this.board = new GameBoard(63);
		this.menu = new Menu();
		this.menuBattle = new MenuBattle();
	}

	public GameBoard getBoard () {
		return board;
	}

	public Character getPlayerById (int id) {
		return board.getPlayerById(id);
	}

	public void setMaxPlayer (int nb) {
		this.maxPlayer = nb;
	}

	public GameState flee (Character player) {
		int rand = random.nextInt(1, 7);
		if (player.getPos() - rand < 0) {
			player.moveEntityToCell(board.getCell(player.getPos()), board.getCell(0));
		} else {
			player.moveEntityToCell(board.getCell(player.getPos()), board.getCell(player.getPos() - rand));
		}
		menuBattle.showFlee(rand);
		return GameState.END;
	}

	public GameState manageAction (Character player) {
		while (true) {
			menu.showHeader(player, board.getCellTable(), board.getMaxCell());
			menu.showPlayerIdleAction();
			int input = menu.requestNb();
			if (input == 1) {
				Dice6 dice = new Dice6();
				player.setMoveAvailable(dice.roll());
				menu.requestInputDiceThrow(player);
				return GameState.MOVING;
			} else if (input == 2) {
				this.managePotion(player);
				return GameState.IDLE;
			} else if (input == 3) {
				if (menu.showOffEquips(player)) {
					input = menu.requestNb();
					if (input != 0) {
						OffensiveEquipment tmpOffEquip = player.getOffEquipById(input - 1);
						player.setCurrentOffEquip(tmpOffEquip);
					}
				}
				return GameState.IDLE;
			} else if (input == 42) {
				player.setMoveAvailable(63);
				return GameState.MOVING;
			} else {
				menu.showWrongChoice();
			}
		}
	}

	public GameState manageMove (Character player) {
		menu.showHeader(player, board.getCellTable(), board.getMaxCell());
		menu.showCurrentPlayerTurn(player);

		int pPos = player.getPos();
		menu.showMoveAvailable(player);
		GameState action = menu.requestInputAction(player);
		if (action == GameState.MOVING) {
			if (pPos + 1 < board.getMaxCell()) {
				Cell tmpCell = board.getCell(pPos);
				player.moveEntityToCell(tmpCell, board.getCell(pPos + 1));
				tmpCell = board.getCell(pPos + 1);
				player.decreaseMoveAvailable();
				if (!tmpCell.isEnemiesEmpty()) {
					return GameState.IN_BATTLE;
				}
				if (!tmpCell.isDefEquipEmpty()) {
					DefensiveEquipment tmpDef = tmpCell.getDefEquip().get(0);
					menu.showPickDefEquip(player, tmpDef);
					player.addDefensiveEquipment(tmpDef);
					tmpCell.removePotion(tmpDef);
				}
				if (!tmpCell.isOffEquipEmpty()) {
					OffensiveEquipment tmpOff = tmpCell.getOffEquip().get(0);
					menu.showPickOffEquip(player, tmpOff);
					player.addOffensiveEquipment(tmpOff);
					tmpCell.removeOffEquip(tmpOff);
				}
				return GameState.MOVING;
			} else {
				player.moveEntityToCell(board.getCell(pPos), board.getCell(board.getMaxCell() - 1));
				return GameState.FINISH;
			}
		} else if (action == GameState.INVENTORY) {
			return GameState.INVENTORY;
		} else if (action == GameState.POTION) {
			return GameState.POTION;
		}
		if (player.getMoveAvailable() == 0 && gameState == GameState.MOVING) {
			return GameState.END;
		}
		return GameState.MOVING;
	}

	public GameState manageInventory (Character player) {
		while (true) {
			menu.showHeader(player, board.getCellTable(), board.getMaxCell());
			if (!menu.showOffEquips(player)) {
				return GameState.MOVING;
			}
			int nb = menu.requestNb();
			if (nb <= 0) {
				return GameState.MOVING;
			}
			OffensiveEquipment tmpOffEquip = player.getOffEquipById(nb - 1);
			if (player.setCurrentOffEquip(tmpOffEquip) == -1) {
				menu.showWrongItemType();
			} else {
				return GameState.INVENTORY;
			}
		}
	}

	public GameState managePotion (Character player) {
		if (menu.showDefEquips(player)) {
			int input = menu.requestNb();
			DefensiveEquipment potion = player.getDefEquipById(input);
			if (potion != null) {
				player.useDefEquip(potion);
			}
		}
		return GameState.MOVING;
	}

	public void removePlayer (Character player) {
		player.setHp(0);
		board.getCell(player.getPos()).removePlayer(player);
		menuBattle.showPlayerDeath(player);
	}

	public void nextPlayer () {
		for (int i = 1; i <= maxPlayer; i++) {
			int id = (currentPlayer + i) % maxPlayer;
			if (board.getPlayerById(id) != null) {
				currentPlayer = id;
				return;
			}
		}
	}

	public void playTurn () {
		Character player = board.getPlayerById(currentPlayer);
		if (player == null) {
			return;
		}
		if (player.getMoveAvailable() == 0 && gameState == GameState.MOVING) {
			gameState = GameState.END;
		}
		if (player.getPos() == board.getMaxCell() - 1) {
			gameState = GameState.FINISH;
			menu.showPlayerFinish(player);
		}

		switch (gameState) {
			case IDLE -> gameState = manageAction(player);
			case MOVING -> gameState = manageMove(player);
			case INVENTORY -> gameState = manageInventory(player);
			case POTION -> gameState = managePotion(player);
			case IN_BATTLE -> {
				Cell cell = board.getCell(player.getPos());
				if (!cell.getEnemies().isEmpty()) {
					menuBattle.showEncounter(player, cell.getEnemies().get(0));
				}
				gameState = battleManager.manageBattle(player, board.getCellTable(), board.getMaxCell());
				if (gameState == GameState.BATTLE_END) {
					if (player.getHp() <= 0) {
						if (board.countAlivePlayers() == 0) {
							menu.showGameOver();
							if (menu.requestPlayAgain()) {
								this.initGame();
								this.playTurn();
							} else {
								gameState = GameState.FINISH;
							}
						} else {
							nextPlayer();
							gameState = GameState.IDLE;
						}
					} else {
						gameState = GameState.MOVING;
					}
				} else if (gameState != GameState.FLEE) {
					if (player.getMoveAvailable() == 0) {
						gameState = GameState.END;
					} else {
						gameState = GameState.MOVING;
					}
				}
			}
			case FLEE -> {
				menu.showHeader(player, board.getCellTable(), board.getMaxCell());
				gameState = flee(player);
			}
			case END -> {
				menu.showHeader(player, board.getCellTable(), board.getMaxCell());
				menu.showPlayerEndTurn(player);
				nextPlayer();
				gameState = GameState.IDLE;
			}
			case FINISH -> menu.showEndGame();
		}
	}

	public void initGame () {
		setMaxPlayer(menu.requestNbPlayer(maxPlayer));
		board.initPlayers(maxPlayer, menu);
		board.initEnemies();
		board.initOffEquip();
		board.initDefEquip();
		battleManager = new BattleManager(menu, this);
	}

	public void startGame () {
		ConsoleTheme.printBanner();
		this.initGame();
		while (gameState != GameState.FINISH) {
			playTurn();
		}
	}
}