package fr.campus.poo_java.game.dice;

import java.util.Random;

public class Dice6 implements Dice {
	@Override
	public int roll () {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}
}
