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

import java.util.Arrays;


class Tools {
	
//	static <T> void array2dout(T[][] array) {
//		for (int i = 0; i < array.length; i++) {
//			for (int j = 0; j < array.length; j++) {
//				System.out.print(array[i][j] +" ");
//			}
//			System.out.print("\n");
//		}
//	}
		
//	// writes a 2d-int-array to stdout
	static int array2dOut(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print(array[i][j] +" ");
			}
			System.out.print("\n");
		}
		return array.length;
	}
	
	// writes a 2d-double-array to stdout
	static void array2dOut(double[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print(array[i][j] +" ");
			}
			System.out.print("\n");
		}
	}
	
	// counts for each class how many fields are taken and writes the number to stdout
	static void count2dArray(int[][] array, int classes) {
		int[] counts = new int[classes];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int k = 0; k < classes; k++) {
					if (array[i][j] == k) {
						counts[k]++;
					}
						
				}
			}
		}
		System.out.println(Arrays.toString(counts));
	}
}
