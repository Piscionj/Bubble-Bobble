package main.java.characters.projectiles;

import main.java.Main;
import main.java.characters.Character;
import main.java.characters.Monster;

import java.awt.geom.Point2D.Double;

public class Bubble extends Character {

	public Bubble(Double point, boolean isMovingRight) {
		super(point);
		setHasGravity(false);
		setCharacterType(CharacterType.BUBBLE);
		setCharacterScale(0.5);
		if(isMovingRight)
			setDX(6);
		else
			setDX(-6);
	}
	@Override
	public String getName() {
		return "bubble";
	}

	@Override
	public void offScreen() {
		if(this.getX() >= Main.WIDTH || this.getX() + UNIT_WIDTH <= 0)
			markToRemove();
	}

	@Override
	public void collideWith(Monster m) {
		m.hit();
		markToRemove();
	}
}
