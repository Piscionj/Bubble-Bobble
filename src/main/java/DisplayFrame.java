package main.java;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class DisplayFrame extends JFrame{
		
	public DisplayFrame() throws IOException{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new DisplayComponent(panel), BorderLayout.CENTER);
		add(panel);
	}

}
