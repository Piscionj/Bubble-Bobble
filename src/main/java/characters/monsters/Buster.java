package main.java.characters.monsters;

import main.java.characters.Monster;

import java.awt.geom.Point2D.Double;


public class Buster extends Monster {

	private int turnAroundTimer = 1000;
	private int direction = 1; // Positive to move right, negative left

	public Buster(Double point) {
		super(point);
	}

	@Override
	public String getName() {
		return "buster";
	}
	
	@Override
	//main.java.characters.projectiles.Bubble Buster is basic enemy and will never be able to shoots
	public boolean isShoot() {
		return false;
	}

	@Override
	public void move() {
		if(turnAroundTimer > 0) {
			setDX(3 * direction);
			super.move();
			turnAroundTimer -= 10;
		} else {
			turnAroundTimer = 1000;
			direction *= -1;
		}
	}


}
