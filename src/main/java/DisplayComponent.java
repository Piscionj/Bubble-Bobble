package main.java;

import main.java.characters.*;
import main.java.characters.Character;
import main.java.characters.monsters.Boris;
import main.java.characters.monsters.Buster;
import main.java.characters.monsters.Mdj;
import main.java.characters.monsters.SuperDrunk;
import main.java.characters.projectiles.Bubble;
import main.java.characters.projectiles.EnemyBubble;
import main.java.characters.screens.EndScreen;
import main.java.characters.screens.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class DisplayComponent extends JComponent implements ActionListener {

	private ArrayList<Character> charactersOnScreen = new ArrayList<>();
	private ArrayList<Character> charsToRemove = new ArrayList<>();
	private ArrayList<Platform> platforms = new ArrayList<>();
	private Hero hero;
	private MainScreen intro;
	private EndScreen endscreen;
	private int levelCount = 0;
	private int levelTimer = 1000;
	private int score = 0;

	private boolean leftPushed = false;
	private boolean rightPushed = false;
	
	public DisplayComponent(JPanel panel) {
		changeLevel();
		intro = new MainScreen(new Point2D.Double(0,0));
		endscreen =  new EndScreen(new Point2D.Double(0,0));
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == 65 ){
					leftPushed = true;
					hero.setDX(-3);
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == 68 ){
					rightPushed = true;
					hero.setDX(3);
				}
				else if (e.getKeyCode() == 85) {
//					if(levelCount == 0) {
						levelCount++;
						changeLevel();
				//	}
				}
				else if ((e.getKeyCode() == 87 || e.getKeyCode() == KeyEvent.VK_UP ) && hero.getOnPlatform()) {
					hero.setJumping(true, 20);
				}
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					hero.shoot(true);
				}
				else if (e.getKeyCode() == 72){
					System.out.println("Pressing the 'H' button will print the help keys");
					System.out.println("Pressing the 'U' button will move you up a level");
					System.out.println("Pressing the 'D' button will move you down a level");
					System.out.println("Pressing the Up Arrow will make the hero jump");
					System.out.println("Pressing the Right Arrow will move the hero to the right");
					System.out.println("Pressing the Left Arrow will move the hero to the left");
					System.out.println("Pressing the SpaceBar will make the hero shoot a bubble");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == 68 ) {
					if(!leftPushed)
						hero.setDX(0);
					rightPushed = false;
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == 65 ) {
					if(!rightPushed)
						hero.setDX(0);
					leftPushed = false;
				}
			}
		});
		
		Timer timer = new Timer(10, this);
		timer.start();
	}
	


	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		for(Platform p : platforms)
			p.draw(g);

		if(levelCount == 0)
			intro.draw(g);

		if(levelCount == 6)
			endscreen.draw(g);

		for (Character c : charactersOnScreen)
			c.draw(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Score: " + score, 10, 15);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(hero.getIsDead()) {
			hero.setIsDead(false);
			changeLevel();
		}

		handleCollisions();

		ArrayList<Character> charactersToAdd = new ArrayList<>();
		for (Character c : charactersOnScreen) {
			if(c.shouldRemove()) {
				charsToRemove.add(c);
				if(c.hasContainedCharacter())
					charactersToAdd.add(c.getContainedCharacter());
				continue;
			}
			if(c.hasGravity())
				platformCheck(c);
			if(c.isShoot() && c.getCharacterType() == Character.CharacterType.MONSTER) {
				if(c.isFacingRight())
					charactersToAdd.add(new EnemyBubble(new Point2D.Double(c.getX()+Character.UNIT_WIDTH*2, c.getY() + Character.UNIT_HEIGHT), c.isFacingRight()));
				else charactersToAdd.add(new EnemyBubble(new Point2D.Double(c.getX(), c.getY() + Character.UNIT_HEIGHT), c.isFacingRight()));
				c.shoot(false);
			}

			c.move(hero);
		}
		charactersOnScreen.addAll(charactersToAdd);

		// Shooting
		if (hero.isShoot()) {
			if(hero.isFacingRight())
				charactersOnScreen.add(new Bubble(new Point2D.Double(hero.getX()+Character.UNIT_WIDTH*2, hero.getY() + Character.UNIT_HEIGHT), hero.isFacingRight()));
			else charactersOnScreen.add(new Bubble(new Point2D.Double(hero.getX(), hero.getY() + Character.UNIT_HEIGHT), hero.isFacingRight()));
			hero.shoot(false);
		}
		for(Character c : charsToRemove) {
			this.charactersOnScreen.remove(c);
			score += c.getPointsWorth();
		}
		charsToRemove.clear();
		
		if(levelCount != 0 && levelCount != 5 && noMonstersOnScreen()) {
			levelTimer = levelTimer - 3;
			if(levelTimer <= 0){
				levelCount++;
				changeLevel();
			}
		}
		repaint();
	}

	public void platformCheck(Character c) {
		if(c.isJumping()) {
			c.setOnPlatform(false);
			return;
		}
		for (Platform platform : platforms) {
			if(Math.abs(platform.getY() - (c.getY() + Character.UNIT_HEIGHT)) < (c.getDY() + 1) && Math.abs(platform.getX() - c.getX()) < Character.UNIT_WIDTH) {
				c.setY(platform.getY() - Character.UNIT_HEIGHT); // Snap to platform
				c.setOnPlatform(true);
				return;
			}
		}
		c.setOnPlatform(false);
	}
	
	private void handleCollisions() {
		for(int i = 0; i < charactersOnScreen.size() - 1; i++) {
			for(int j = i + 1; j < charactersOnScreen.size(); j++) {
				Character character = charactersOnScreen.get(i);
				Character otherCharacter = charactersOnScreen.get(j);
				if (character.overlaps(otherCharacter))
					character.collideWith(otherCharacter);

			}
		}
	}

	public void changeLevel(){
		removeExtra();
		levelTimer = 1000;
		String filename = "/levels/level"+levelCount+".txt";
		try {
			computePositions(filename);
			repaint();
			System.out.println("Level " + levelCount + " loaded");
		} catch (IOException e2) {
			System.out.println("Level " + levelCount +" does not exist");
		}
	}

	private void computePositions(String fileName) throws IOException {
		int yPos = 0;
		int xPos;
		Scanner s = new Scanner(getClass().getResourceAsStream(fileName));
		while(s.hasNext()) {
			xPos = 0;
			String str = s.next();
			for (int i = 0; i < str.length(); i++) {
				xPos++;
				char characterChar = str.charAt(i);
				if(characterChar == '-') continue;
				Point2D.Double point = new Point2D.Double(xPos * Character.UNIT_WIDTH, (yPos * Character.UNIT_HEIGHT) + Character.UNIT_HEIGHT);
				switch (characterChar) {
					case 's':
						charactersOnScreen.add(new Boris(point));
						break;
					case 'b':
						charactersOnScreen.add(new Buster(point));
						break;
					case 'D':
						charactersOnScreen.add(new SuperDrunk(point));
						break;
					case 'p':
						platforms.add(new Platform(point));
						break;
					case 'H':
						hero = new Hero(point);
						charactersOnScreen.add(hero);
						break;
					case 'M':
						charactersOnScreen.add(new Mdj(point));
						break;
				}

			}
			yPos++;
		}
		s.close();
	}
	public void removeExtra() {
		charactersOnScreen.clear();
		platforms.clear();
	}
	public boolean noMonstersOnScreen() {
		for(Character c : charactersOnScreen)
			if (c.getCharacterType() == Character.CharacterType.MONSTER || c.getCharacterType() == Character.CharacterType.ENTRAPPED_MONSTER)
				return false;
		return true;
	}
}
