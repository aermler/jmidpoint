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

import javax.swing.*;

//import java.util.Arrays;


public class Start {

	public static void main(String[] args) {
		
		Midpoint mp1 = new Midpoint();
//		Midpoint mp2 = new Midpoint(7, 1.0, 0.5, true, -252);
		
		double[] prob = {0.1, 0.5, 0.2, 0.01, 0.15, 0.04};
		mp1.getDiscreteIntField();
		mp1.getDiscreteDoubleField();
		mp1.getIntField(prob);
		Tools.array2dOut(mp1.getIntField(prob));
		
		JFrame f = new JFrame("MidPoint-GUI");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		f.setLayout(new GridLayout(3, 4));
//		
//		f.add(new JLabel("Maxlevel:"));
//		f.add(new JTextField("8"));
//		f.add(new JLabel("Standard deviation:"));
//		f.add(new JTextField("1.0"));
//		f.add(new JLabel("H:"));
//		f.add(new JTextField("0.6"));
//		f.add(new JLabel("Addition:"));
//		f.add(new JCheckBox("",true));
//		f.add(new JLabel("Random number seed:"));
//		f.add(new JTextField("-234"));
//		f.add(new JProgressBar());
//		f.add(new JButton("Update"));
//		
//		
//		f.pack();
//		f.setVisible(true);


		
	}

}
