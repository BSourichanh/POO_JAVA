package fr.campus.poojava.game.dice;

import java.util.Random;

public class Dice6 implements Dice {
	private final Random random = new Random();

	@Override
	public int roll () {
		return random.nextInt(1, 7);
	}
}
