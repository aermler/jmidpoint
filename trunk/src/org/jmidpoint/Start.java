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

// Command line tool to test the Midpoint-Class

package org.jmidpoint;

import java.awt.GridLayout;
import javax.swing.*;

public class Start {

	public static void main(String[] args) {

		Midpoint mp1 = new Midpoint();
		Midpoint mp2 = new Midpoint(7, 1.0, 0.5, true, -252);

		double[] prob = { 0.1, 0.5, 0.2, 0.01, 0.15, 0.04 };
		mp1.getDiscreteIntField();
		mp1.getDiscreteDoubleField();
		mp1.getIntField(prob);
//		MidpointTools.array2dOut(mp1.getIntField(prob));
//		MidpointTools.array2dOut(mp2.getDiscreteDoubleField());
//		MidpointTools.count2dArray(mp1.getIntField(prob), 3);

	}

}
