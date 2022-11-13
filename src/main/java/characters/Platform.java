package main.java.characters;

import main.java.characters.Character;

import java.awt.geom.Point2D.Double;


public class Platform extends Character {

	public Platform(Double point) {
		super(point);
	}

	@Override
	public String getName() {
		return "platform";
	}

}
