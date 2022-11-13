package main.java.characters;

import java.awt.geom.Point2D.Double;


public class Fruit extends Character {

	public Fruit(Double point) {
		super(point);
		setPointsWorth(100);
		setCharacterType(CharacterType.FRUIT);
	}

	@Override
	public String getName() {
		return "orange";
	}
}
