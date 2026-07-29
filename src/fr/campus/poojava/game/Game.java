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

/**
 * Moteur principal du jeu de plateau Donjons & Dragons.
 * Gère la boucle de jeu au tour par tour, les transitions de la machine à états (GameState),
 * l'initialisation du plateau et des joueurs, et les interactions utilisateur.
 *
 * @author BSourichanh
 */
public class Game {
	private int currentPlayer = 0;
	private GameState gameState;
	private int maxPlayer = 2;
	private final GameBoard board;
	protected Menu menu;
	protected MenuBattle menuBattle;
	protected BattleManager battleManager;
	private final Random random = new Random();

	/**
	 * Constructeur de la partie. Initialise le plateau de 63 cases et les composants d'interface.
	 */
	public Game () {
		this.gameState = GameState.IDLE;
		this.board = new GameBoard(63);
		this.menu = new Menu();
		this.menuBattle = new MenuBattle();
	}

	/** @return Le plateau de jeu. */
	public GameBoard getBoard () {
		return board;
	}

	/**
	 * Récupère un joueur par son identifiant unique.
	 *
	 * @param id L'identifiant du joueur.
	 * @return Le joueur correspondant ou null.
	 */
	public Character getPlayerById (int id) {
		return board.getPlayerById(id);
	}

	/**
	 * Définit le nombre de joueurs dans la partie.
	 *
	 * @param nb Le nombre de joueurs (1 ou 2).
	 */
	public void setMaxPlayer (int nb) {
		this.maxPlayer = nb;
	}

	/**
	 * Gère la fuite d'un joueur en le reculant d'un nombre aléatoire de cases (1 à 6).
	 *
	 * @param player Le joueur qui prend la fuite.
	 * @return L'état de fin de tour (GameState.END).
	 */
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

	/**
	 * Gère le choix d'action initial du tour d'un joueur (Lancer de dé, Potion, Équipement).
	 *
	 * @param player Le joueur actif.
	 * @return Le nouvel état de jeu.
	 */
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

	/**
	 * Gère le déplacement case par case du joueur et les événements déclenchés (Combats, Potions, Équipements).
	 *
	 * @param player Le joueur en mouvement.
	 * @return Le nouvel état de jeu.
	 */
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

	/**
	 * Gère la sélection et l'équipement d'une arme ou d'un sort dans l'inventaire.
	 *
	 * @param player Le joueur.
	 * @return Le nouvel état de jeu.
	 */
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

	/**
	 * Gère la sélection et l'utilisation d'une potion dans l'inventaire.
	 *
	 * @param player Le joueur.
	 * @return Le nouvel état de jeu.
	 */
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

	/**
	 * Délégué de gestion de combat vers le BattleManager.
	 *
	 * @param player Le joueur engagé en combat.
	 * @return Le nouvel état de jeu.
	 */
	public GameState initBattle (Character player) {
		return battleManager.manageBattle(player, board);
	}

	/**
	 * Retire un joueur mort de la partie et informe l'interface.
	 *
	 * @param player Le joueur décédé.
	 */
	public void removePlayer (Character player) {
		player.setHp(0);
		board.getCell(player.getPos()).removePlayer(player);
		menuBattle.showPlayerDeath(player);
	}

	/** Passe le tour au joueur vivant suivant. */
	public void nextPlayer () {
		for (int i = 1; i <= maxPlayer; i++) {
			int id = (currentPlayer + i) % maxPlayer;
			if (board.getPlayerById(id) != null) {
				currentPlayer = id;
				return;
			}
		}
	}

	/** Exécute une étape/un tour complet de la partie selon la machine à états. */
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
			case IN_BATTLE -> gameState = initBattle(player);
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

	/** Initialise une nouvelle partie (création des joueurs, ennemis, équipements). */
	public void initGame () {
		setMaxPlayer(menu.requestNbPlayer(maxPlayer));
		board.initPlayers(maxPlayer, menu);
		board.initEnemies();
		board.initOffEquip();
		board.initDefEquip();
		battleManager = new BattleManager(menu, this);
	}

	/** Affiche la bannière d'introduction et lance la boucle de jeu principale. */
	public void startGame () {
		ConsoleTheme.printBanner();
		this.initGame();
		while (gameState != GameState.FINISH) {
			playTurn();
		}
	}
}