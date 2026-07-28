package fr.campus.poojava.ui;

public class ConsoleTheme {
	// ANSI Color Escape Codes
	public static final String RESET = "\u001B[0m";
	public static final String BOLD = "\u001B[1m";
	public static final String DIM = "\u001B[2m";
	public static final String ITALIC = "\u001B[3m";
	public static final String UNDERLINE = "\u001B[4m";

	// Foreground Colors
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	// Bright Foreground Colors
	public static final String BRIGHT_RED = "\u001B[91m";
	public static final String BRIGHT_GREEN = "\u001B[92m";
	public static final String BRIGHT_YELLOW = "\u001B[93m";
	public static final String BRIGHT_BLUE = "\u001B[94m";
	public static final String BRIGHT_MAGENTA = "\u001B[95m";
	public static final String BRIGHT_CYAN = "\u001B[96m";
	public static final String BRIGHT_WHITE = "\u001B[97m";

	// Background Colors
	public static final String BG_BLACK = "\u001B[40m";
	public static final String BG_RED = "\u001B[41m";
	public static final String BG_GREEN = "\u001B[42m";
	public static final String BG_YELLOW = "\u001B[43m";
	public static final String BG_BLUE = "\u001B[44m";
	public static final String BG_MAGENTA = "\u001B[45m";
	public static final String BG_CYAN = "\u001B[46m";

	// Symbols
	public static final String SYM_WARRIOR = "⚔️";
	public static final String SYM_WIZARD = "🧙";
	public static final String SYM_DRAGON = "🐉";
	public static final String SYM_GOBLIN = "👺";
	public static final String SYM_SORCERER = "🔮";
	public static final String SYM_ENEMY = "👾";
	public static final String SYM_POTION = "🧪";
	public static final String SYM_WEAPON = "🗡️";
	public static final String SYM_SPELL = "✨";
	public static final String SYM_DICE = "🎲";
	public static final String SYM_HEART = "❤️";
	public static final String SYM_SHIELD = "🛡️";
	public static final String SYM_SKULL = "💀";
	public static final String SYM_TROPHY = "🏆";
	public static final String SYM_CRIT = "💥";
	public static final String SYM_CROSS = "❌";

	/**
	 * Calcule la largeur d'affichage effective en colonnes terminal
	 * en prenant en compte les émojis BMP (ex: ❤️, ⚔️, ✨, 🎲), les surrogate pairs
	 * et en ignorant les séquences ANSI et sélecteurs de variation Unicode.
	 */
	public static int getDisplayWidth (String str) {
		if (str == null) return 0;
		// 1. Supprimer les séquences de couleur ANSI
		String clean = str.replaceAll("\u001B\\[[;\\d]*m", "");
		// 2. Supprimer les sélecteurs de variation Unicode (0-width)
		clean = clean.replaceAll("[\uFE00-\uFE0F]", "");

		int width = 0;
		for (int i = 0; i < clean.length(); i++) {
			char c = clean.charAt(i);
			if (Character.isHighSurrogate(c)) {
				width += 2;
				i++; // ignorer le low surrogate
			} else if ((c >= 0x2300 && c <= 0x27BF) || (c >= 0x2B00 && c <= 0x2BFF)) {
				// Emojis et symboles BMP (ex: ❤️ \u2764, ⚔ \u2694, ✨ \u2728, 🎲 \u2685, ⚡ \u26A1)
				width += 2;
			} else {
				width += 1;
			}
		}
		return width;
	}

	/**
	 * Generates a visual colored health bar.
	 */
	public static String getHealthBar (int currentHp, int maxHp, int barLength) {
		if (maxHp <= 0) maxHp = 1;
		int hp = Math.max(0, Math.min(currentHp, maxHp));
		int filled = (int) Math.round((double) hp / maxHp * barLength);
		int empty = barLength - filled;

		String color;
		double percentage = (double) hp / maxHp;
		if (percentage > 0.6) {
			color = BRIGHT_GREEN;
		} else if (percentage > 0.3) {
			color = BRIGHT_YELLOW;
		} else {
			color = BRIGHT_RED;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(color).append("[");
		for (int i = 0; i < filled; i++) {
			sb.append("█");
		}
		sb.append(DIM);
		for (int i = 0; i < empty; i++) {
			sb.append("░");
		}
		sb.append(RESET).append(color).append("] ");
		sb.append(BOLD).append(hp).append("/").append(maxHp).append(" HP").append(RESET);
		return sb.toString();
	}

	/**
	 * Banner ASCII Art pour le jeu.
	 */
	public static void printBanner () {
		String banner = BRIGHT_YELLOW + BOLD + """
				 ╔══════════════════════════════════════════════════════════════════════════╗
				 ║                                                                          ║
				 ║    ██████╗  ██████╗  ██████╗     ██╗ █████╗ ██╗   ██╗ █████╗             ║
				 ║    ██╔══██╗██╔═══██╗██╔═══██╗    ██║██╔══██╗██║   ██║██╔══██╗            ║
				 ║    ██████╔╝██║   ██║██║   ██║    ██║███████║██║   ██║███████║            ║
				 ║    ██╔═══╝ ██║   ██║██║   ██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║            ║
				 ║    ██║     ╚██████╔╝╚██████╔╝╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║            ║
				 ║    ╚═╝      ╚═════╝  ╚═════╝  ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝            ║
				 ║                                                                          ║
				 ║              ⚔️  DONJONS & DRAGONS - EDITION CONSOLE  🧙                   ║
				 ╚══════════════════════════════════════════════════════════════════════════╝
				""" + RESET;
		System.out.println(banner);
	}

	/**
	 * Print framed message box with perfectly matched column width across top, content, and bottom lines.
	 */
	public static void printBox (String title, String... lines) {
		int maxLen = getDisplayWidth(title);
		for (String line : lines) {
			int len = getDisplayWidth(line);
			if (len > maxLen) {
				maxLen = len;
			}
		}
		int width = Math.max(68, maxLen + 4);

		StringBuilder sb = new StringBuilder();
		sb.append(BRIGHT_CYAN).append("┌─ ").append(BOLD).append(title).append(RESET).append(BRIGHT_CYAN);
		int remainingHeader = width - getDisplayWidth(title) - 2;
		for (int i = 0; i < Math.max(0, remainingHeader); i++) {
			sb.append("─");
		}
		sb.append("┐\n").append(RESET);

		for (String line : lines) {
			int padding = width - getDisplayWidth(line) - 1;
			sb.append(BRIGHT_CYAN).append("│ ").append(RESET).append(line);
			for (int i = 0; i < Math.max(0, padding); i++) {
				sb.append(" ");
			}
			sb.append(BRIGHT_CYAN).append("│\n").append(RESET);
		}

		sb.append(BRIGHT_CYAN).append("└");
		for (int i = 0; i < width; i++) {
			sb.append("─");
		}
		sb.append("┘\n").append(RESET);

		System.out.print(sb.toString());
	}
}
