package main.java.characters.monsters;

import main.java.Main;
import main.java.characters.Hero;
import main.java.characters.Monster;

import java.awt.geom.Point2D.Double;

public class SuperDrunk extends Monster {

	private int dx;
	private int dy;
	public SuperDrunk(Double point) {
		super(point);
		this.setHp(10);
		this.dx = 10;
		this.dy = 5;
		setHasGravity(false);
		setCharacterScale(2);
	}

	@Override
	public String getName() {
		return "superDrunk";
	}
	
	public void move(Hero h){
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		if (this.getX() <= 0 || this.getX() + UNIT_WIDTH*getCharacterScale() >= Main.WIDTH) dx = dx * -1;
		if (this.getY() <= 0 || this.getY() + UNIT_HEIGHT*getCharacterScale() >= Main.HEIGHT) dy = dy * -1;
	}

}
