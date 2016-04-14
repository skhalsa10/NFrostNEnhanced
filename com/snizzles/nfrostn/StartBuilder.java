package com.snizzles.nfrostn;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class StartBuilder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel densityLabel;
	private JLabel seedLabel;
	private JLabel[] labels;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField densityField;
	private JTextField seedField;
	private JTextField[] fields;
	private JButton startNFrostN;
	private JPanel displayPanel;

	public StartBuilder() throws HeadlessException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Parameter Collector ");
		this.setLayout(new BorderLayout());
		//Set up the labels
		widthLabel = new JLabel("Set Width(int): ");
		heightLabel = new JLabel("Set Height(int): ");
		densityLabel = new JLabel("Set Density(int): ");
		seedLabel = new JLabel("Set Seed(int): ");
		//add thelabels to the labels array
		labels = new JLabel[4];
		labels[0] = widthLabel;
		labels[1] = heightLabel;
		labels[2] = densityLabel;
		labels[3] = seedLabel;
		
		//set up the fields and input defaultvalues
		widthField = new JTextField("250");
		heightField = new JTextField("250");
		densityField = new JTextField("30");
		seedField = new JTextField("0");
		//add them to the fields[]
		fields = new JTextField[4];
		fields[0] = widthField;
		fields[1] = heightField;
		fields[2] = densityField;
		fields[3] = seedField;
		
		//sset up the button
		startNFrostN = new JButton("Start NFrostN");
		startNFrostN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NFrostN(Integer.parseInt(widthField.getText()),Integer.parseInt(heightField.getText()),
						Integer.parseInt(densityField.getText()), Integer.parseInt(seedField.getText()));
				
			}
			
		});
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());
		
		buildDisplay(labels,fields,startNFrostN, displayPanel);
		this.add(displayPanel, BorderLayout.CENTER);
		                        
		this.pack();
		this.setVisible(true);
	}
	
	private void buildDisplay(JLabel[] labels, JTextField[] fields, JButton startNFrostN, JPanel display) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int labelLength = labels.length;
		
		for (int i = 0; i < labelLength; i++){
			//sets the component next to the last one
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill =  GridBagConstraints.NONE;
			c.weightx= 0.0;
			display.add(labels[i], c);
			
			//addjtextfield
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			display.add(fields[i], c);
		}
		c.gridwidth = GridBagConstraints.WEST;
		c.fill =  GridBagConstraints.NONE;
		c.weightx= 0.0;
		display.add(startNFrostN, c);
		
	}

	public static void main(String[] args ){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new StartBuilder();				
			}
			
		});
		
	}
}
