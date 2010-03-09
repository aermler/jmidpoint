/*************************************************************************
 * Copyright 2009, 2010 (c) Andreas Ermler                               *
 *                                                                       *
 * This file is part of jmidpoint.                                       *
 *                                                                       *
 * jmidpoint is free software: you can redistribute it and/or modify     *
 * it under the terms of the GNU General Public License as published by  *
 * the Free Software Foundation, either version 3 of the License, or     *
 * (at your option) any later version.                                   *
 *                                                                       *
 * jmidpoint is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 * GNU General Public License for more details.                          *
 *                                                                       *
 * You should have received a copy of the GNU General Public License     *
 * along with jmidpoint.  If not, see <http://www.gnu.org/licenses/>.    *
 *************************************************************************/


import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class MyGUI extends JFrame {
	
	private JLabel maxlevelLabel;
	private JLabel sigmaLabel;
	private JLabel hLabel;
	private JLabel additionLabel;
	private JLabel seedLabel;
	private JLabel probabilitiesLabel;
	private JLabel paramLabel;
	
	private JTextField maxlevelTF;
	private JTextField sigmaTF;
	private JTextField hTF;
	private JCheckBox additionCB;
	private JTextField seedTF;
	private JTextField probabilitiesTF;
	private JButton calcButton;
	
	private BufferedImage image;
	
	private JPanel controlPanel;
	private JScrollPane picturePanel;
	
	private JTextArea pictureTA;
	
	private Midpoint mp;
	
	public MyGUI() {
		initGUI();
	}

	/**
	 * 
	 */
	private void initGUI(){
		
		maxlevelLabel = new JLabel("Maxlevel:");
		sigmaLabel = new JLabel("Sigma:");
		hLabel = new JLabel("H:");
		additionLabel = new JLabel("Addition:");
		seedLabel = new JLabel("Seed:");
		probabilitiesLabel = new JLabel("Probabilities:");
		paramLabel = new JLabel("Parameters:");
		
		maxlevelTF = new JTextField("8");
		sigmaTF = new JTextField("1.0");
		hTF = new JTextField("0.8");
		additionCB = new JCheckBox();
		seedTF = new JTextField("-123");
		probabilitiesTF = new JTextField("0.5;0.5");
		calcButton = new JButton("Show field");
		
		pictureTA = new JTextArea();
		
		controlPanel = new JPanel();
		picturePanel = new JScrollPane();
		
		
		
		mp = new Midpoint();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("jMidpoint 0.1");
		
		
		calcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				calcButtonPushed(e);
			}
		});
		
		controlPanel.setLayout(new GridLayout(4,4,5,0));
		setLayout(new GridLayout(2,1));
		
		controlPanel.add(maxlevelLabel);
		controlPanel.add(maxlevelTF);
		controlPanel.add(seedLabel);
		controlPanel.add(seedTF);
		controlPanel.add(sigmaLabel);
		controlPanel.add(sigmaTF);
		controlPanel.add(probabilitiesLabel);
		controlPanel.add(probabilitiesTF);
		controlPanel.add(hLabel);
		controlPanel.add(hTF);
		controlPanel.add(paramLabel);
		controlPanel.add(calcButton);
		controlPanel.add(additionLabel);
		controlPanel.add(additionCB);
		
//		picturePanel.add(pictureTA);
		picturePanel.setViewportView(pictureTA);
		add(controlPanel);
		add(picturePanel);
		
		
		pack();
	}

	private void calcButtonPushed(ActionEvent e) {
		
		picturePanel.remove(pictureTA);
		
		mp.setMaxlevel(Integer.parseInt(maxlevelTF.getText()));
		mp.setSigma(Double.parseDouble(sigmaTF.getText()));
		mp.setH(Double.parseDouble(hTF.getText()));
		mp.setAddition(additionCB.isEnabled());
		mp.setSeed(Integer.parseInt(seedTF.getText()));
		
		String stringProbs[] = probabilitiesTF.getText().split(";");
		
		int len = stringProbs.length;
		double doubleProbs[] = new double[len];
		double probsTogether = 0.0;
		for (int i = 0; i < len; ++i) {
			doubleProbs[i] = Double.parseDouble(stringProbs[i]);
			probsTogether += doubleProbs[i];
		}
		
		if (probsTogether != 1.0) {
			for (int i = 0; i < len; ++i) {
				doubleProbs[i] = doubleProbs[i] / probsTogether;
			}
			System.out.println("Normalized probabilities.");
		}
			
		mp.update();
		
		Tools.array2dOut(mp.getIntField(doubleProbs));
		
		int size = mp.getFieldSize();
		
		String outStr = "";
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				outStr += mp.getFieldCell(i,j) + " ";
			}
			outStr += "\n";
		}
		pictureTA = new JTextArea(size,size);
//		picturePanel.add(pictureTA);
		picturePanel.setViewportView(pictureTA);
		pictureTA.setText(outStr);
		
		
		
		image = new BufferedImage(size,size,BufferedImage.TYPE_USHORT_GRAY);
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				image.setRGB(i,j,10);
			}
		}
		
//		picturePanel.add(image);
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		new MyGUI().setVisible(true);

	}



}
 