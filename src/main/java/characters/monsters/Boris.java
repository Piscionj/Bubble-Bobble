package main.java.characters.monsters;

import main.java.characters.Hero;
import main.java.characters.Monster;

import java.awt.geom.Point2D.Double;

// Wizard guy
public class Boris extends Monster {

	private int shotCooldown;
	public Boris(Double point) {
		super(point);
		shotCooldown = (int)(Math.random()*1000);
	}
	public String getName() {
		return "boris";
	}

	@Override
	public void move(Hero hero) {
		shotCooldown -= 10;
		if(shotCooldown <= 0)
			shoot(true);
		setFacingRight(hero.getX() > getX());
		if(!getOnPlatform())
			fall();
		offScreen();
	}

	@Override
	public void shoot(boolean b) {
		shotCooldown = 1000;
		super.shoot(b);
	}
	
}
