package main.java.characters;

import main.java.characters.projectiles.EnemyBubble;

import java.awt.geom.Point2D;

public class Hero extends Character {

	private boolean isDead = false;
	private int shotCooldown = 0;

	public Hero(Point2D.Double point) {
		super(point);
		setCharacterType(CharacterType.HERO);
		setHasGravity(true);
	}

	@Override
	public void shoot(boolean b) {
		if(b && shotCooldown <= 0) {
			shotCooldown = 200;
			super.shoot(b);
		} else if (!b) {
			super.shoot(b);
		}
	}

	@Override
	public void move() {
		if(shotCooldown > 0) shotCooldown -= 10;
		super.move();
	}

	@Override
	public String getName() {
		return "hero";		
	}

	public void setIsDead(boolean b) {
		isDead = b;
	}
	public boolean getIsDead() {
		return isDead;
	}

	@Override
	public void collideWith(Monster m) {
		setIsDead(true);
	}

	@Override
	public void collideWith(EnemyBubble m) {
		setIsDead(true);
	}

	@Override
	public void collideWith(BubbleBoi b) {
		b.markToRemove();
		b.hitByHero();
	}
	@Override
	public void collideWith(Fruit f) {
		f.markToRemove();
	}
}
