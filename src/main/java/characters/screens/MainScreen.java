package main.java.characters.screens;

import main.java.characters.Character;

import java.awt.geom.Point2D.Double;


public class MainScreen extends Character {

	public MainScreen(Double point) {
		super(point);
	}

	@Override
	public String getName() {
		return "mainscreen";
	}
}
