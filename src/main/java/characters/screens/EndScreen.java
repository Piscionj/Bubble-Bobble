package main.java.characters.screens;

import main.java.characters.Character;

import java.awt.geom.Point2D.Double;


public class EndScreen extends Character {

	public EndScreen(Double point) {
		super(point);
	}

	@Override
	public String getName() {
		return "endscreen";
	}
}
