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

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MidpointGUI extends JFrame {

	private JLabel maxlevelLabel;

	private JLabel sigmaLabel;

	private JLabel hLabel;

	private JLabel additionLabel;

	private JLabel seedLabel;

	private JLabel probabilitiesLabel;

	private JLabel paramLabel;

	private JLabel formatLabel;

	private JTextField maxlevelTF;

	private JTextField sigmaTF;

	private JTextField hTF;

	private JCheckBox additionCB;

	private JTextField seedTF;

	private JTextField probabilitiesTF;

	private JButton calcButton;

	private JRadioButton textRB;

	private JRadioButton imageRB;

	private BufferedImage image;

	// private JPanel controlPanel;

	private JScrollPane picturePanel;

	private JTextArea pictureTA;

	private Midpoint mp;

	private Random r;

	private Boolean norm;

	private Boolean finished;

	public MidpointGUI() {

		initGUI();

	}

	private static void addComponent(Container cont, GridBagLayout gbl,
			Component c, int x, int y, int width, int height, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.ipadx = 0;
		gbc.ipady = 5;
		// gbc.insets = new Insets(0,5,0,0);
		gbl.setConstraints(c, gbc);
		cont.add(c);

	}

	private void initGUI() {

		maxlevelLabel = new JLabel("Maxlevel:");
		sigmaLabel = new JLabel("Sigma:");
		hLabel = new JLabel("H:");
		additionLabel = new JLabel("Addition:");
		seedLabel = new JLabel("Seed:");
		probabilitiesLabel = new JLabel("Probabilities:");
		paramLabel = new JLabel();
		formatLabel = new JLabel("Output format:");

		maxlevelTF = new JTextField("8");
		sigmaTF = new JTextField("1.0");
		hTF = new JTextField("0.8");
		additionCB = new JCheckBox();
		seedTF = new JTextField("-123");
		probabilitiesTF = new JTextField("0.5;0.3;0.2");
		calcButton = new JButton("Show field");
		textRB = new JRadioButton("Text");
		imageRB = new JRadioButton("Image");

		pictureTA = new JTextArea();

		picturePanel = new JScrollPane();

		mp = new Midpoint();
		norm = false;
		finished = true;

		imageRB.setSelected(true);

		ButtonGroup bg = new ButtonGroup();
		bg.add(imageRB);
		bg.add(textRB);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("jMidpoint 0.2");

		// final Boolean finished = false;

		calcButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// final boolean finished = false;
				if (finished == true) {
					finished = false;
					new Thread() {
						public void run() {
							try {
								calcButtonPushed(e);
							} catch (Exception e) {

								finished = true;
							}
							finished = true;
						}
					}.start();
				} else {
					System.out.println("Rechnet noch...");
					JOptionPane
							.showMessageDialog(
									null,
									"The programm is still calculating. Please wait"
											+ " until it finishes before starting it again.",
									"Still calculating",
									JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		addComponent(this, gbl, maxlevelLabel, 0, 0, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, maxlevelTF, 1, 0, 1, 1, 1.0, 0.0);
		addComponent(this, gbl, sigmaLabel, 0, 1, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, sigmaTF, 1, 1, 1, 1, 1.0, 0.0);
		addComponent(this, gbl, hLabel, 0, 2, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, hTF, 1, 2, 1, 1, 1.0, 0.0);
		addComponent(this, gbl, additionLabel, 0, 3, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, additionCB, 1, 3, 1, 1, 1.0, 0.0);

		addComponent(this, gbl, seedLabel, 2, 0, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, seedTF, 3, 0, 1, 1, 1.0, 0.0);
		addComponent(this, gbl, probabilitiesLabel, 2, 1, 1, 1, 0.2, 0.0);
		addComponent(this, gbl, probabilitiesTF, 3, 1, 1, 1, 1.0, 0.0);

		addComponent(this, gbl, calcButton, 2, 2, 2, 2, 1.0, 0.0);

		addComponent(this, gbl, formatLabel, 0, 4, 2, 2, 1.0, 0.0);
		addComponent(this, gbl, imageRB, 2, 4, 2, 1, 1.0, 0.0);
		addComponent(this, gbl, textRB, 2, 5, 2, 1, 1.0, 0.0);

		addComponent(this, gbl, paramLabel, 0, 6, 4, 1, 1.0, 0.0);
		addComponent(this, gbl, picturePanel, 0, 7, 4, 1, 1.0, 1.0);

		setSize(400, 500);
	}

	private void calcButtonPushed(ActionEvent e) {

		norm = false;
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
			norm = true;
		}

		mp.update();

		mp.getIntField(doubleProbs);

		int size = mp.getFieldSize();

		if (imageRB.isSelected()) {

			image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

			int[] rgb_array = new int[len];

			r = new Random();

			for (int i = 0; i < len; ++i) {
				rgb_array[i] = new Color(r.nextInt(256), r.nextInt(256), r
						.nextInt(256)).getRGB();
			}

			for (int i = 0; i < size; ++i) {
				for (int j = 0; j < size; ++j) {
					image.setRGB(i, j, rgb_array[mp.getFieldCell(i, j)]);
				}
			}

			ImagePanel ip = new ImagePanel(image);

			picturePanel.setViewportView(ip);

		} else {
			pictureTA = new JTextArea(size, size);
			for (int i = 0; i < size; ++i) {
				for (int j = 0; j < size; ++j) {
					pictureTA.append(Integer.toString(mp.getFieldCell(i, j)));
				}
				pictureTA.append("\n");
			}

			picturePanel.setViewportView(pictureTA);

		}

		if (!norm) {
			paramLabel.setText("Size of field: " + size + "x" + size);
		} else {
			paramLabel.setText("<html>Size of field: " + size + "x" + size
					+ "<p/>Warning: Probabilities normalized </html>");
		}

		repaint();
	}

	class ImagePanel extends JPanel {

		BufferedImage image;

		ImagePanel(BufferedImage image) {
			this.image = image;
			this.setPreferredSize(new Dimension(image.getHeight(), image
					.getWidth()));
		}

		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}

	}

	public static void main(String[] args) {

		new MidpointGUI().setVisible(true);

	}

}
