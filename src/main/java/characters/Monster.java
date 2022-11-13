package main.java.characters;

import main.java.characters.projectiles.Bubble;

import java.awt.geom.Point2D.Double;

public abstract class Monster extends Character {

	private int hp = 1;
	public Monster(Double point) {
		super(point);
		setCharacterType(CharacterType.MONSTER);
		setHasGravity(true);
		setHasContainingCharacter(true);
		setPointsWorth(500);
	}

	@Override
	public Character getContainedCharacter() {
		return new BubbleBoi(new Double(getX(), getY()), this);
	}

	public int getHp(){
		return this.hp;
	}
	public void hit(){
		this.hp--;
		if(getHp() <= 0)
			markToRemove();
	}
	public void setHp(int x){
		this.hp = x;
	}
	public abstract String getName();

	@Override
	public void collideWith(Hero h) {
		h.setIsDead(true);
	}

	@Override
	public void collideWith(Bubble b) {
		this.hit();
		b.markToRemove();
	}
}
