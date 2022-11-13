package main.java;

import main.java.characters.Character;

import javax.swing.*;
import java.io.IOException;

public class Main {

	public static final int TILE_WIDTH = 20;
	public static final int TILE_HEIGHT = 20;
	public static final int WIDTH = Character.UNIT_WIDTH * TILE_WIDTH;
	public static final int HEIGHT = Character.UNIT_HEIGHT * TILE_HEIGHT;

	public static void main(String[] args) {	

		JFrame frame;
		try {
		frame = new DisplayFrame();
			frame.setSize(WIDTH, HEIGHT + Character.UNIT_HEIGHT);
			frame.setTitle("Bubble Bobble");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
	 
	}
	
	

}
