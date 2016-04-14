/**
 * 
 */
package com.snizzles.nfrostn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * @author Siri
 *
 */
public class NFrostN extends JFrame implements ActionListener {

	private int gridWidth;
	private int gridHeight;
	private int density;
	private Random seed;
	private Timer refresh;
	private boolean[][] theGrid;
	private DisplayPanel displayPanel;
	private final int maxDiffusionSteps;
	private int currentStep;
	private boolean newParticle;
	private int totalOfParticles;
	private int diffuseW;
	private int diffuseH;
	/**
	 * @throws HeadlessException
	 */
	public NFrostN(int width, int height, int density, int seed) throws HeadlessException {
		refresh = new Timer(0, this);
		//System.out.println(SwingUtilities.isEventDispatchThread());
		this.gridWidth = width;
		this.gridHeight = height;
		this.density = density;
		if (seed ==0){
			this.seed = new Random();
		}else{
			this.seed = new Random(seed);
		}
		theGrid = new boolean[gridWidth][gridHeight];
		displayPanel = new DisplayPanel(this);
		maxDiffusionSteps = width*height;
		newParticle = true;
		totalOfParticles = 0;
		diffuseW= -1;
		diffuseH = -1;


		//set up JFrame stuff
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("NFrostN");
		this.setSize(width*4, height*4);
		this.setMinimumSize(new Dimension(width*4,height*4));
		this.setMaximumSize(new Dimension(width*4,height*4));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.add(displayPanel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		refresh.start();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		densityReached();
		singleParticleDiffusion();
		this.repaint();
		//refresh.stop();

	}
	private void densityReached() {
		double particles = totalOfParticles;
		double sites = (gridWidth*gridHeight);
		double percentD = particles/sites;
		int percent = (int)(percentD*100);
		if(percent>=density){
			refresh.stop();
		}
		
	}
	private void singleParticleDiffusion() {
		if (newParticle){
			startDiffusion();
			newParticle = false;
			totalOfParticles++;
			currentStep++;
			return;
		}
		if(currentStep >= maxDiffusionSteps){
			newParticle = true;
			currentStep = 0;
			return;
		}
		if(move()){
			currentStep = 0;
			newParticle = true;
			return;
		}
		System.out.println("currentStep: "+currentStep);
		System.out.println("maxDiffusionSteps: "+maxDiffusionSteps);

	}
	private boolean move() {
		currentStep++;
		if(checkForEnd()){
			System.out.println("checkforend");
			return true;
		}
		Direction next = pickDirection();
		if(next == Direction.NORTH){
			return moveNorth();
		}
		if(next == Direction.EAST){
			return moveEast();
		}
		if(next == Direction.SOUTH){
			return moveSouth();
		}
		if(next == Direction.WEST){
			return moveWest();
		}
		return false;
	}
	private boolean moveWest() {
		//System.out.println("west");
		theGrid[diffuseW][diffuseH] = false;
		theGrid[((diffuseW-1)+gridWidth)%gridWidth][diffuseH] = true;
		diffuseW = ((diffuseW-1)+gridWidth)%gridWidth;
		return false;
	}
	private boolean moveSouth() {
		theGrid[diffuseW][diffuseH] = false;
		theGrid[diffuseW][(diffuseH+1)%gridHeight] = true;
		diffuseH = (diffuseH+1)%gridHeight;
		return false;
	}
	private boolean moveEast() {
		theGrid[diffuseW][diffuseH] = false;
		 theGrid[(diffuseW+1)%gridWidth][diffuseH] = true;
		 diffuseW = (diffuseW+1)%gridWidth;
		return false;
	}
	private boolean moveNorth() {
		theGrid[diffuseW][diffuseH] = false;
		theGrid[diffuseW][((diffuseH-1)+gridHeight)%gridHeight] = true;
		diffuseH = ((diffuseH-1)+gridHeight)%gridHeight;
		return false;
	}
	private boolean checkForEnd() {
		if(accessNorth()||accessEast()||accessSouth()||accessWest()){
			return true;
		}
		return false;
	}
	private boolean accessWest() {
		return theGrid[((diffuseW-1)+gridWidth)%gridWidth][diffuseH];
	}
	private boolean accessSouth() {
		return theGrid[diffuseW][(diffuseH+1)%gridHeight];
	}
	private boolean accessEast() {
		return theGrid[(diffuseW+1)%gridWidth][diffuseH];
	}
	private boolean accessNorth() {
		return theGrid[diffuseW][((diffuseH-1)+gridHeight)%gridHeight];
	}

	private Direction pickDirection() {
		int direction = seed.nextInt(4);
		for(Direction chosen: Direction.values()){
			if (chosen.ordinal() == direction){
				return chosen;
			}
		}
		return null;
		
	}
	private void startDiffusion() {

		int startW = seed.nextInt(gridWidth);
		int startH = seed.nextInt(gridHeight);
		while(theGrid[startW][startH]){
			startW = seed.nextInt(gridWidth);
			startH = seed.nextInt(gridHeight);
		}
		diffuseW = startW;
		diffuseH = startH;
		if (!theGrid[startW][startH]){
			theGrid[startW][startH] = true;
		}
	}
	public boolean[][] getTheGrid() {
		return theGrid;
	}



}
