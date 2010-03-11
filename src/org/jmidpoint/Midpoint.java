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

package org.jmidpoint;

import java.util.Arrays;
import java.util.Random;

/**
 * @author andreas
 * 
 */
class Midpoint {

	private double sigma, h, delta;

	private boolean addition;

	private int maxlevel, n, dim, seed, dd, d, x, y, stage;

	private double[][] dField;

	private int[][] iField, discIField;

	private double[] oneDArray, borders;

	private Random r;

	/**
	 * Creates a field with standard parameters. Creates a field with standard
	 * parameters with the Midpoint algorithm. Uses standard parameters:
	 * maxlevel = 8; standard deviation = 1.0; h = 0.8; random additions = on;
	 * seed = -3333
	 */
	Midpoint() {
		maxlevel = 8; // maximal number of recursions (n = 2 ^ maxlevel)
		sigma = 1.0; // initial standard deviation
		h = 0.8; // parameter that determines fractal dimension (dd = 3 - h)
		addition = true; // turns on/off random additions
		seed = -3333; // seed value for random number generator

		update();
	}

	/**
	 * Creates a field with the parameters
	 * 
	 * @param maxlevel
	 *            Determines the field size
	 * @param sigma
	 *            Standard deviation
	 * @param h
	 *            Determines the fractal dimension (dimension = 3 - h)
	 * @param addition
	 *            Switches random additions on/off
	 * @param seed
	 *            Seed value for random number generator
	 */
	Midpoint(int maxlevel, double sigma, double h, boolean addition, int seed) {
		this.maxlevel = maxlevel; // maximal number of recursions (n = 2 ^
		// maxlevel)
		this.sigma = sigma; // initial standard deviation
		this.h = h; // parameter that determines fractal dimension (dd = 3 - h)
		this.addition = addition; // turns on/off random additions
		this.seed = seed; // seed value for random number generator

		update();
	}

	/**
	 * Helper function for 3 variables. Helper function for 3 variables.
	 * Calculates the mean of the 3 variables and adds to it a random gauss
	 * number
	 * 
	 * @param delta
	 * @param x0
	 * @param x1
	 * @param x2
	 * @return
	 */
	private double f3(double delta, double x0, double x1, double x2) {
		Random rf3 = new Random();
		return ((x0 + x1 + x2) / 3 + delta * rf3.nextGaussian());
	}

	// TODO: Random() vs. Math.random() überprüfen

	/**
	 * Helper function for 4 variables. Helper function for 4 variables.
	 * Calculates the mean of the 4 variables and adds to it a random gauss
	 * number
	 * 
	 * @param delta
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param x3
	 * @return
	 */
	private double f4(double delta, double x0, double x1, double x2, double x3) {
		Random rf4 = new Random();
		return ((x0 + x1 + x2 + x3) / 4 + delta * rf4.nextGaussian());

	}

	// returns the 2d-array, double-values were casted to int-values
	/**
	 * Returns the discrete values of the array casted to int-values. Returns
	 * the discrete double values of the array casted to int-values.
	 * 
	 * @return Discrete 2D-Array where the double-values were just casted to
	 *         int-values
	 */
	public int[][] getDiscreteIntField() {
		discIField = new int[dim][dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				discIField[i][j] = (int) dField[i][j];
			}
		}
		System.out.println(discIField.length);
		return discIField;
	}

	// returns the 2d-array
	/**
	 * Returns the discrete 2D-Array as it is returned by the midpoint
	 * algorithm.
	 * 
	 * @return Discrete 2D-Array as returned by the algorithm
	 */
	public double[][] getDiscreteDoubleField() {
		System.out.println(dField.length);
		return dField;
	}

	// returns the 2d-array divided into (# of probabilities) classes
	/**
	 * Returns a 2d-array of integers divided into classes. Returns a 2D-array
	 * of integers divided into classes.
	 * 
	 * @param probabilities
	 *            Array of the probabilites of the classes, the field is divided
	 *            into a number of classes determined by the length of this
	 *            array. Have to be 1.0 added together
	 * @return 2D-Array divided into classes, number of classes determined by
	 *         the length of the input array.
	 * 
	 */
	public int[][] getIntField(double[] probabilities) {

		iField = new int[dim][dim];

		// Copy 2d-Field into 1d-Array
		oneDArray = new double[dim * dim];
		int index = 0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				oneDArray[index++] = dField[i][j];
			}
		}

		// Sort 1d-Array
		Arrays.sort(oneDArray);

		// Get borders of the classes
		borders = new double[probabilities.length - 1];
		index = 0;
		for (int i = 0; i < probabilities.length - 1; i++) {
			index = index
					+ (int) Math.round(probabilities[i] * oneDArray.length);
			borders[i] = oneDArray[index - 1];
		}

		// assign the values of the field to the different classes
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (dField[i][j] <= borders[0]) {
					iField[i][j] = 0;
				} else {
					for (int k = 1; k < borders.length; k++) {
						if (dField[i][j] > borders[k - 1]
								&& dField[i][j] <= borders[k]) {
							iField[i][j] = k;
						}
					}
					if (dField[i][j] > borders[borders.length - 1]) {
						iField[i][j] = borders.length;
					}
				}

			}
		}
		// System.out.println(iField.length);
		return iField;
	}

	// Implementation of the algorithm MidPointFM2D
	// (from: The Science of Fractal Images,
	// Barnsley, Devaney, Mandelbrot, Peitgen, Saupe, Voss
	// 1988, Springer Verlag
	// p.100)
	/**
	 * @return
	 */
	public double[][] update() {

		r = new Random(seed);

		// size of Field (N+1,N+1)
		n = (int) Math.pow(2, maxlevel);
		dField = new double[n + 1][n + 1];
		dim = n + 1;

		// set the initial random corners
		delta = sigma;
		dField[0][0] = delta * r.nextGaussian();
		dField[0][n] = delta * r.nextGaussian();
		dField[n][0] = delta * r.nextGaussian();
		dField[n][n] = delta * r.nextGaussian();

		dd = n;
		d = n / 2;

		for (stage = 1; stage < maxlevel + 1; stage++) {
			// going from grid type I to type II
			delta = delta * Math.pow(0.5, (0.5 * h));

			// interpolate and offset points
			for (x = d; x <= n - d; x = x + dd) {
				for (y = d; y <= n - d; y = y + dd) {
					dField[x][y] = f4(delta, dField[x + d][y + d],
							dField[x + d][y - d], dField[x - d][y + d],
							dField[x - d][y - d]);
				}

			}

			// displace other points also if needed
			if (addition) {
				for (x = 0; x <= n; x = x + dd) {
					for (y = 0; y <= n; y = y + dd) {
						dField[x][y] = dField[x][y] + delta * r.nextGaussian();
					}
				}
			}

			// going from grid type II to type I
			delta = delta * Math.pow(0.5, (0.5 * h));
			// interpolate and offset boundary grid points
			for (x = d; x <= n - d; x = x + dd) {
				dField[x][0] = f3(delta, dField[x + d][0], dField[x - d][0],
						dField[x][d]);
				dField[x][n] = f3(delta, dField[x + d][n], dField[x - d][n],
						dField[x][n - d]);
				dField[0][x] = f3(delta, dField[0][x + d], dField[0][x - d],
						dField[d][x]);
				dField[n][x] = f3(delta, dField[n][x + d], dField[n][x - d],
						dField[n - d][x]);
			}

			// interpolate and offset interior grid points
			for (x = d; x <= n - d; x = x + dd) {
				for (y = dd; y <= n - d; y = y + dd) {
					dField[x][y] = f4(delta, dField[x][y + d],
							dField[x][y - d], dField[x + d][y],
							dField[x - d][y]);
				}
			}

			for (x = dd; x <= n - d; x = x + dd) {
				for (y = d; y <= n - d; y = y + dd) {
					dField[x][y] = f4(delta, dField[x][y + d],
							dField[x][y - d], dField[x + d][y],
							dField[x - d][y]);
				}
			}

			// displace other points also if needed
			if (addition) {
				for (x = 0; x <= n; x += dd) {
					for (y = 0; y <= n; y += dd) {
						dField[x][y] = dField[x][y] + delta * r.nextGaussian();
					}
				}
				for (x = d; x <= n - d; x += dd) {
					for (y = d; y <= n - d; y += dd) {
						dField[x][y] = dField[x][y] + delta * r.nextGaussian();
					}
				}
			}

			dd = dd / 2;
			d = d / 2;

		}

		return dField;
	}

	// Getters and setters
	/**
	 * @return
	 */
	public boolean isAddition() {
		return addition;
	}

	/**
	 * @param addition
	 */
	public void setAddition(boolean addition) {
		this.addition = addition;
		update();
	}

	/**
	 * @return
	 */
	public double getH() {
		return h;
	}

	/**
	 * @param h
	 */
	public void setH(double h) {
		this.h = h;
		update();
	}

	/**
	 * @return
	 */
	public int getMaxlevel() {
		return maxlevel;
	}

	/**
	 * @param maxlevel
	 */
	public void setMaxlevel(int maxlevel) {
		this.maxlevel = maxlevel;
		update();
	}

	/**
	 * @return
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 */
	public void setSeed(int seed) {
		this.seed = seed;
		update();
	}

	/**
	 * @return
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * @param sigma
	 */
	public void setSigma(double sigma) {
		this.sigma = sigma;
		update();
	}

	/**
	 * @return
	 */
	public int getDim() {
		return dim;
	}

	/**
	 * @param dim
	 */
	public void setDim(int dim) {
		this.dim = dim;
		update();
	}

	public int getFieldSize() {
		return iField.length;
	}

	public int getFieldCell(int x, int y) {
		return iField[x][y];
	}

}
