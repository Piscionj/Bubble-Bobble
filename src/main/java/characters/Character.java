package main.java.characters;

import main.java.Main;
import main.java.characters.monsters.SuperDrunk;
import main.java.characters.projectiles.Bubble;
import main.java.characters.projectiles.EnemyBubble;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Character {

	public enum CharacterType {
		HERO, MONSTER, BUBBLE, ENEMY_BUBBLE, ENTRAPPED_MONSTER, FRUIT
	}

	public static final int UNIT_WIDTH = 40;
	public static final int UNIT_HEIGHT = 40;
	public static final int MAX_FALLING_VELOCITY = 7;

	private boolean hasGravity = false;
	private boolean facingRight = true;
	private boolean hasContainingCharacter = false;
	private Character containedCharacter;
	private CharacterType characterType;
	private int pointsWorth = 0;
	private double characterScale = 1;

	private int dX = 0;
	private int dY = 0;

	private int x;
	private int y;
	private boolean onPlatform;
	private boolean isJumping;
	private Point2D.Double spawnPoint;
	private boolean isShoot = false;
	private BufferedImage img;
	private boolean shouldRemove = false;


	public Character(Point2D.Double point){
		this.spawnPoint = point;
		this.x = (int) this.spawnPoint.getX() - UNIT_WIDTH;
		this.y = (int) this.spawnPoint.getY() - UNIT_HEIGHT;
		this.onPlatform = true;
		this.isJumping = false;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/images/" + getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean hasGravity() {
		return this.hasGravity;
	}
	public void setHasGravity(boolean b) {
		this.hasGravity = b;
	}

	public void setDX(int dx) {
		this.dX = dx;
	}
	public void move() {
		this.setX(this.getX() + dX);
		if(dX != 0)
			facingRight = dX > 0;
		if(isJumping)
			jump();
		else if(!onPlatform)
			fall();
		offScreen();
	}
	public void move(Hero hero) {
		move();
	}
	public void collideWith(Character c) {
		switch (c.getCharacterType()) {
			case HERO:
				collideWith((Hero) c);
				break;
			case MONSTER:
				collideWith((Monster) c);
				break;
			case ENEMY_BUBBLE:
				collideWith((EnemyBubble) c);
				break;
			case BUBBLE:
				collideWith((Bubble) c);
				break;
			case ENTRAPPED_MONSTER:
				collideWith((BubbleBoi) c);
				break;
			case FRUIT:
				collideWith((Fruit) c);
				break;
		}
	}
	public void collideWith(Monster c) {}
	public void collideWith(Hero c) {}
	public void collideWith(BubbleBoi c) {}
	public void collideWith(Bubble c) {}
	public void collideWith(EnemyBubble c) {}
	public void collideWith(Fruit c) {}

	public void shoot(boolean b) {
		this.isShoot = b;
	}
	public boolean isShoot() {
		return this.isShoot;
	}
	public Point2D.Double getSpawnPoint(){
		return this.spawnPoint;
	}
	public abstract String getName();
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getDY() {
		return this.dY;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}


	public void draw(Graphics g) {
		if(getName() == "mainscreen" || getName() == "endscreen")
			g.drawImage(img, 0, 0, Main.WIDTH, Main.HEIGHT, null);
		else {
			if (facingRight)
				g.drawImage(img, this.getX(), this.getY(), (int) (UNIT_WIDTH * getCharacterScale()), (int) (UNIT_HEIGHT * getCharacterScale()), null);
			else
				g.drawImage(img, this.getX() + UNIT_WIDTH, this.getY(), (int) (-UNIT_WIDTH * getCharacterScale()), (int) (UNIT_HEIGHT * getCharacterScale()), null);
		}
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public boolean hasContainedCharacter() {
		return hasContainingCharacter;
	}
	public void setContainedCharacter(Character c) {
		hasContainingCharacter = true;
		containedCharacter = c;
	}
	public Character getContainedCharacter() {
		return containedCharacter;
	}

	public void setOnPlatform(boolean b) {
		this.onPlatform = b;
	}
	public boolean getOnPlatform() {
		return this.onPlatform;
	}

	public void setJumping(boolean b, int jumpingVeloctity) {
		this.isJumping = b;
		this.dY = jumpingVeloctity;
	}
	
	public boolean isJumping() {
		return this.isJumping;
	}
	public void fall() {
		this.setY(this.getY() + dY);
		if(dY < MAX_FALLING_VELOCITY)
			this.dY++;
	}

	public void jump() {
		this.setY(this.getY() - dY);
		this.dY--;
		if(dY < 0)
			isJumping = false;
	}

	public boolean overlaps(Character otherGuy){
		int i = 1;
		if (otherGuy instanceof SuperDrunk) i = 2;
		if((this.getX() >= otherGuy.getX() && this.getX() <= otherGuy.getX() + UNIT_WIDTH*i)
			|| (this.getX() + UNIT_WIDTH >= otherGuy.getX() && this.getX() + UNIT_WIDTH <= otherGuy.getX() + UNIT_WIDTH*i ))
		if((this.getY() >= otherGuy.getY()  && this.getY() <= otherGuy.getY() + UNIT_HEIGHT*i)
				|| (this.getY() + UNIT_HEIGHT >= otherGuy.getY() && this.getY() <= otherGuy.getY())) {
			return true;
		}
		return false;
	}
	
	public void offScreen() {
		if(this.getY() >= Main.HEIGHT)
			this.setY(0);
		if(this.getX() >= Main.WIDTH)
			this.setX(0);
		else if (this.getX() + UNIT_WIDTH <= 0)
			this.setX(Main.WIDTH - UNIT_WIDTH);
	}
	public void markToRemove() {
		this.shouldRemove = true;
	}
	public void unmarkToRemove() {
		shouldRemove = false;
	}
	public boolean shouldRemove() {
		return this.shouldRemove;
	}
	public void setCharacterType(CharacterType charType) {
		characterType = charType;
	}
	public CharacterType getCharacterType() {
		return characterType;
	}
	public void setHasContainingCharacter(boolean b) {
		hasContainingCharacter = b;
	}
	public int getPointsWorth() {
		return pointsWorth;
	}
	public void setPointsWorth(int points) {
		pointsWorth = points;
	}
	public void setFacingRight(boolean b) {
		facingRight = b;
	}
	public double getCharacterScale() {
		return characterScale;
	}
	public void setCharacterScale(double scale) {
		characterScale = scale;
	}
}
