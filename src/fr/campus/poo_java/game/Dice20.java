package fr.campus.poo_java.game;

import java.util.Random;

public class Dice20 implements Dice {
	@Override
	public int roll () {
		Random random = new Random();
		return random.nextInt(20) + 1;
	}
}
