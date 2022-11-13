package main.java.characters;

import main.java.Main;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class BubbleBoi extends Character {

	private int bubbleTimer = 1000;
	private boolean hitByHero = false;

	public BubbleBoi(Point2D.Double point, Monster containedMonster) {
		super(point);
		setCharacterType(CharacterType.ENTRAPPED_MONSTER);
		setHasContainingCharacter(true);
		containedMonster.unmarkToRemove();
		setContainedCharacter(containedMonster);
	}

	@Override
	public String getName() {
		return "bubbleBoi";
	}
	
	@Override
	public void move(){
		this.setY(this.getY() - 1);
		bubbleTimer -= 10;
		if(bubbleTimer <= 0)
			markToRemove();
	}

	@Override
	public void collideWith(Hero hero) {
		markToRemove();
		hitByHero();
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	@Override
	public Character getContainedCharacter() {
		if(hitByHero) {
			Random random = new Random();
			Point2D.Double point = new Point2D.Double(random.nextInt(Main.WIDTH),random.nextInt(Main.HEIGHT));
			return new Fruit(point);
		}
		else {
			super.getContainedCharacter().setX(getX());
			super.getContainedCharacter().setY(getY());
			return super.getContainedCharacter();
		}
	}

	public void hitByHero() {
		hitByHero = true;
	}
}
