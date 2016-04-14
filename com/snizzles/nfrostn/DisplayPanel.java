package com.snizzles.nfrostn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private NFrostN engine;
	
	public DisplayPanel(NFrostN engine) {
		this.engine = engine;
		this.setLayout(null);
		this.setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		boolean[][] theGrid = engine.getTheGrid();
		for (int w = 0; w < theGrid.length; w++){
			for (int h = 0; h< theGrid[0].length; h++){
				if(theGrid[w][h]){
					g2d.fillRect(w*4, h*4, 4, 4);
				}
			}
		}
		
	}

}
