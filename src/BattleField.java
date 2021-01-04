import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
// MUST ROTATE THE BOARD ACCORDINGLY
/**
 * @author Felipe Bonadykov BattleField class has the field, buttons, and the
 *         logic of the game. Connection side is managed in XConnector classes
 */
public class BattleField {
	private JFrame battleFieldFrame = new JFrame();
	private JButton gridOfSquaresAsButtons[][] = new JButton[18][18];
	
	private JLabel whoseTurnIsLabel;
	private int utilityCounter = 0;// to avoid same actions (like take and take, put and put)
	private Color defaultButtonColor = new Color(0.4f, 0.5f, 0.4f);
	private Color whereCanGoButtonColor = new Color(0.4f, 0.4f, 0.6f);
	private int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 70;
	private boolean shouldLimitMoves = false;
	
	public static boolean firstOrSecond;
	private PlayerConnector playerConnector = new PlayerConnector(this);

	private Icon figureForCopyPast;

	private Icon soldier1 = resizeImagesOfFigures("res/Screen/US/soldier.png");
	private Icon tank1 = resizeImagesOfFigures("res/Screen/US/tank.png");
	private Icon airplane1 = resizeImagesOfFigures("res/Screen/US/airplane.png");
	private Icon rocket1 = resizeImagesOfFigures("res/Screen/US/rocket.png");
	private Icon hq1 = resizeImagesOfFigures("res/Screen/US/hq.png");

	private Icon soldier2 = resizeImagesOfFigures("res/Screen/SU/soldier.png");
	private Icon tank2 = resizeImagesOfFigures("res/Screen/SU/tank.png");
	private Icon airplane2 = resizeImagesOfFigures("res/Screen/SU/airplane.png");
	private Icon rocket2 = resizeImagesOfFigures("res/Screen/SU/rocket.png");
	private Icon hq2 = resizeImagesOfFigures("res/Screen/SU/hq.png");
	
	private Icon resizeImagesOfFigures(String dir) {
		return new ImageIcon(new ImageIcon(dir).getImage().getScaledInstance((windowHeight - 100) / 18, (windowHeight - 140) / 18, Image.SCALE_DEFAULT));
	}
	
	public JFrame getFrame() {
		return battleFieldFrame;
	}
	
	private void drawFrame() {
		battleFieldFrame.setTitle("STRATEGY the world 🗺");
		battleFieldFrame.setIconImage(new ImageIcon(Main.ICON).getImage());
		battleFieldFrame.setLocation(150, 10);
		battleFieldFrame.setSize(windowHeight, windowHeight);
		battleFieldFrame.setResizable(false);
		battleFieldFrame.setContentPane(new JLabel(new ImageIcon(Main.MAP)));// map on background
		battleFieldFrame.setLayout(new FlowLayout());
		battleFieldFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whoseTurnIsLabel = new JLabel();
		whoseTurnIsLabel.setPreferredSize(new Dimension((int)(0.9 * windowHeight), (int) (0.02* windowHeight)));
		whoseTurnIsLabel.setText("Place your figures as you want on your half of field and press the red button --->");
		whoseTurnIsLabel.setBackground(Color.LIGHT_GRAY);
		whoseTurnIsLabel.setOpaque(true);
		whoseTurnIsLabel.setVisible(true);
		JButton allowLimitMovesButton = new JButton();
		allowLimitMovesButton.setForeground(Color.BLACK);
		allowLimitMovesButton.setBackground(Color.red);
		allowLimitMovesButton.setSize((int)(0.2 * windowHeight), (int) (0.02* windowHeight));
		allowLimitMovesButton.addActionListener(limitMoves -> shouldLimitMoves = true);
		battleFieldFrame.add(whoseTurnIsLabel);
		battleFieldFrame.add(allowLimitMovesButton);
	}

	private void adjustButtons() {
		for (byte i = 0; i < gridOfSquaresAsButtons.length; i++)
			for (byte j = 0; j < gridOfSquaresAsButtons.length; j++) {
				gridOfSquaresAsButtons[i][j] = new JButton();
				gridOfSquaresAsButtons[i][j].setPreferredSize(new Dimension((int) ((0.85 * windowHeight) / 18), (int) ((0.80 * windowHeight) / 18)));
				gridOfSquaresAsButtons[i][j].setVisible(true);
				paintButtons(i, j);
				battleFieldFrame.add(gridOfSquaresAsButtons[i][j]);
			}
	}
	
	private void paintButtons(int i, int j) {
		gridOfSquaresAsButtons[i][j].setBackground(defaultButtonColor);		
		if (i % 2 == 0) {// M every second from 1
			if (j % 2 == 0) {// L every second from 1
				gridOfSquaresAsButtons[i][j].setOpaque(true);
			} else {// L every second from 2
				gridOfSquaresAsButtons[i][j].setOpaque(false);
			}
		} else {// M every second from 2
			if (j % 2 == 1) {// L every second from 2
				gridOfSquaresAsButtons[i][j].setOpaque(true);
			} else {// every second from 1
				gridOfSquaresAsButtons[i][j].setOpaque(false);
			}
		}		
	}
	
	private void setDefaultPositionOfFigures() {
		// figures are represented as Images
		// USSR
		// Soldiers
		for (byte k = 1; k < 16; k++) {
			gridOfSquaresAsButtons[4][k].setIcon(soldier2);
		}
		gridOfSquaresAsButtons[4][4].setIcon(null);
		gridOfSquaresAsButtons[4][8].setIcon(null);
		gridOfSquaresAsButtons[4][12].setIcon(null);
		for (byte k = 3; k < 14; k++) {
			gridOfSquaresAsButtons[1][k].setIcon(soldier2);
		}
		gridOfSquaresAsButtons[1][6].setIcon(null);
		gridOfSquaresAsButtons[1][10].setIcon(null);
		// Technique
		for (byte k = 2; k < 16; k += 4) {
			gridOfSquaresAsButtons[3][k].setIcon(tank2);
		}
		for (byte k = 4; k < 13; k += 4) {
			gridOfSquaresAsButtons[0][k].setIcon(tank2);
		}
		gridOfSquaresAsButtons[2][6].setIcon(airplane2);
		gridOfSquaresAsButtons[2][10].setIcon(airplane2);
		gridOfSquaresAsButtons[0][6].setIcon(rocket2);
		gridOfSquaresAsButtons[0][10].setIcon(rocket2);
		// Head Quarter
		gridOfSquaresAsButtons[0][9].setIcon(hq2);
		// USA
		// Soldiers
		for (byte k = 1; k < 16; k++) {
			gridOfSquaresAsButtons[13][k].setIcon(soldier1);
		}
		gridOfSquaresAsButtons[13][4].setIcon(null);
		gridOfSquaresAsButtons[13][8].setIcon(null);
		gridOfSquaresAsButtons[13][12].setIcon(null);
		for (byte k = 3; k < 14; k++) {
			gridOfSquaresAsButtons[16][k].setIcon(soldier1);
		}
		gridOfSquaresAsButtons[16][6].setIcon(null);
		gridOfSquaresAsButtons[16][10].setIcon(null);
		// Technique
		for (byte k = 2; k < 16; k += 4) {
			gridOfSquaresAsButtons[14][k].setIcon(tank1);
		}
		for (byte k = 4; k < 13; k += 4) {
			gridOfSquaresAsButtons[17][k].setIcon(tank1);
		}
		gridOfSquaresAsButtons[15][6].setIcon(airplane1);
		gridOfSquaresAsButtons[15][10].setIcon(airplane1);
		gridOfSquaresAsButtons[17][6].setIcon(rocket1);
		gridOfSquaresAsButtons[17][10].setIcon(rocket1);
		// Head Quarter
		gridOfSquaresAsButtons[17][9].setIcon(hq1);
	}

	private void setActionsToButtons() {
		for (byte i = 0; i < gridOfSquaresAsButtons.length; i++) {
			for (byte j = 0; j < gridOfSquaresAsButtons[i].length; j++) {
				gridOfSquaresAsButtons[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JButton btn = (JButton) e.getSource();
											
						boolean notNullOrHQ = btn.getIcon() != null & btn.getIcon() != hq1 & btn.getIcon() != hq2;
						boolean isFirst = btn.getIcon() == soldier1 | btn.getIcon() == tank1 | btn.getIcon() == airplane1
								| btn.getIcon() == rocket1 | btn.getIcon() == hq1;
						boolean isSecond = btn.getIcon() == soldier2 | btn.getIcon() == tank2 | btn.getIcon() == airplane2
								| btn.getIcon() == rocket2 | btn.getIcon() == hq2;
						boolean isMyFigure = firstOrSecond ? isFirst : isSecond;

						if (playerConnector.isMyTurn() & isMyFigure & utilityCounter%2 == 0 & notNullOrHQ) {
							figureForCopyPast = btn.getIcon();// copying figure
							btn.setIcon(null);// removing figure from the button
							utilityCounter++;	
							if (shouldLimitMoves) {
								class LimitMovesClass {
									public LimitMovesClass() {
										for (int i = 0; i < 18; i++)
											for (int j = 0; j < 18; j++)
												if (btn == gridOfSquaresAsButtons[i][j]) {
													if (figureForCopyPast == rocket1 | figureForCopyPast == rocket2) {// rocket. moves +
														for (int k = -4; k < 5; k++) {
															if (j + k < 0) {
															} else if (j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i][j + k].setOpaque(true);
															}
														}
														for (int k = -4; k < 5; k++) {
															if (i + k < 0) {
															} else if (i + k > 17) {
															} else {
																gridOfSquaresAsButtons[i + k][j].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i + k][j].setOpaque(true);
															}
														}
														gridOfSquaresAsButtons[i][j].setBackground(Color.BLACK);
														gridOfSquaresAsButtons[i][j].setOpaque(true);
													}
													if (figureForCopyPast == airplane1 | figureForCopyPast == airplane2) {// airplane. moves * *
														for (int k = -3; k < 4; k++) {
															if (j + k < 0) {
															} else if (j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i][j + k].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i + k < 0) {
															} else if (i + k > 17) {
															} else {
																gridOfSquaresAsButtons[i + k][j].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i + k][j].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i + k < 0 | j + k < 0 | i + k > 17
																	| j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i + k][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i + k][j + k].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i - k < 0 | j + k < 0 | i - k > 17
																	| j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i - k][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i - k][j + k].setOpaque(true);
															}
														}
														gridOfSquaresAsButtons[i][j].setBackground(Color.BLACK);
														gridOfSquaresAsButtons[i][j].setOpaque(true);
													}
													if (figureForCopyPast == tank1 | figureForCopyPast == tank2) {// tank. moves L
														if (j - 2 > 17 | j - 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i][j - 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i][j - 2].setOpaque(true);
														}
														if (j + 2 > 17 | j + 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i][j + 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i][j + 2].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i + 2][j].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i + 2][j].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i - 2][j].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i - 2][j].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0 | j - 1 > 17 | j - 1 < 0) {
														} else {
															gridOfSquaresAsButtons[i + 2][j - 1].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i + 2][j - 1].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0 | j + 1 > 17 | j + 1 < 0) {
														} else {
															gridOfSquaresAsButtons[i + 2][j + 1].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i + 2][j + 1].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0 | j - 1 > 17 | j - 1 < 0) {
														} else {
															gridOfSquaresAsButtons[i - 2][j - 1].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i - 2][j - 1].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0 | j + 1 > 17 | j + 1 < 0) {
														} else {
															gridOfSquaresAsButtons[i - 2][j + 1].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i - 2][j + 1].setOpaque(true);
														}
														if (i + 1 > 17 | i + 1 < 0 | j + 2 > 17 | j + 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i + 1][j + 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i + 1][j + 2].setOpaque(true);
														}
														if (i + 1 > 17 | i + 1 < 0 | j - 2 > 17 | j - 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i + 1][j - 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i + 1][j - 2].setOpaque(true);
														}
														if (i - 1 > 17 | i - 1 < 0 | j + 2 > 17 | j + 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i - 1][j + 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i - 1][j + 2].setOpaque(true);
														}
														if (i - 1 > 17 | i - 1 < 0 | j - 2 > 17 | j - 2 < 0) {
														} else {
															gridOfSquaresAsButtons[i - 1][j - 2].setBackground(whereCanGoButtonColor);
															gridOfSquaresAsButtons[i - 1][j - 2].setOpaque(true);
														}
														gridOfSquaresAsButtons[i][j].setBackground(Color.BLACK);
														gridOfSquaresAsButtons[i][j].setOpaque(true);
													} 
													if (figureForCopyPast == soldier1 | figureForCopyPast == soldier2) {// soldier. moves *
														for (int k = -1; k < 2; k++) {
															if (j + k < 0)
																k++;
															else if (j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i][j + k].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i + k < 0 | i + k > 17) {
															} else {
																gridOfSquaresAsButtons[i + k][j].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i + k][j].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i + k < 0 | j + k < 0 | i + k > 17
																	| j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i + k][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i + k][j + k].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i - k < 0 | j + k < 0 | i - k > 17
																	| j + k > 17) {
															} else {
																gridOfSquaresAsButtons[i - k][j + k].setBackground(whereCanGoButtonColor);
																gridOfSquaresAsButtons[i - k][j + k].setOpaque(true);
															}
														}
														gridOfSquaresAsButtons[i][j].setBackground(Color.BLACK);
														gridOfSquaresAsButtons[i][j].setOpaque(true);
													}
												}
									}
								}
								new LimitMovesClass();
							}
						}
						if (playerConnector.isMyTurn() & utilityCounter%2 == 1 & !isMyFigure) {
							utilityCounter++;							
							if (shouldLimitMoves)
								for (int i = 0; i < 18; i++)
									for (int j = 0; j < 18; j++)
										paintButtons(i, j);
							btn.setIcon(figureForCopyPast);
							playerConnector.sendMapToOpponent(generateMapOfBattle());
						}
					}
				});
			}
		}
	}

	public void updateWhosTurnIsLabel() {
		if(shouldLimitMoves) {
			if (playerConnector.isMyTurn()) {
				whoseTurnIsLabel.setText("    Your turn ");
				whoseTurnIsLabel.setBackground(Color.cyan);
			} else {
				whoseTurnIsLabel.setText("    Your opponent's turn ");
				whoseTurnIsLabel.setBackground(Color.pink);
			}
		}
		try {
			WinnerPraise.checkForWinner(gridOfSquaresAsButtons[17][9], gridOfSquaresAsButtons[0][9], hq1, hq2, firstOrSecond);
		} catch (NullPointerException e) {}
		/*
		if (firstOrSecond) {
			WinnerPraise.checkForWinner(gridOfSquaresAsButtons[17][9], gridOfSquaresAsButtons[0][9]);// if somebody won	
		} else {
			WinnerPraise.checkForWinner(gridOfSquaresAsButtons[0][8], gridOfSquaresAsButtons[17][8]);// if somebody won	
		}
		return;
		*/
	}
	
	public void updateBattleField(String txt) {
		try {
			int index = 0;
			for (int i = 0; i < 18; i++) {
				for (int j = 0; j < 18; j++) {
					char inSwitch = txt.charAt(index++);
					switch (inSwitch) {
					case 's':
						gridOfSquaresAsButtons[i][j].setIcon(soldier1);
						break;
					case 't':
						gridOfSquaresAsButtons[i][j].setIcon(tank1);
						break;
					case 'a':
						gridOfSquaresAsButtons[i][j].setIcon(airplane1);
						break;
					case 'r':
						gridOfSquaresAsButtons[i][j].setIcon(rocket1);
						break;
					case 'h':
						gridOfSquaresAsButtons[i][j].setIcon(hq1);
						break;
					case 'S':
						gridOfSquaresAsButtons[i][j].setIcon(soldier2);
						break;
					case 'T':
						gridOfSquaresAsButtons[i][j].setIcon(tank2);
						break;
					case 'A':
						gridOfSquaresAsButtons[i][j].setIcon(airplane2);
						break;
					case 'R':
						gridOfSquaresAsButtons[i][j].setIcon(rocket2);
						break;
					case 'H':
						gridOfSquaresAsButtons[i][j].setIcon(hq2);
						break;
					default:
						gridOfSquaresAsButtons[i][j].setIcon(null);
						break;
					}
				}
			}
		} catch (Exception e) {}
	}

	private String generateMapOfBattle() {
		String txt = "";
		for (byte i = 0; i < 18; i++)
			for (byte j = 0; j < 18; j++) {
				if (gridOfSquaresAsButtons[i][j].getIcon() == null) 
					txt += 'o';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == soldier1) 
					txt += 's';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == tank1)
					txt += 't';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == airplane1) 
					txt += 'a';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == rocket1) 
					txt += 'r';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == hq1) 
					txt += 'h';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == soldier2)				
					txt += 'S';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == tank2)				
					txt += 'T';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == airplane2)				
					txt += 'A';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == rocket2) 				
					txt += 'R';
				else if (gridOfSquaresAsButtons[i][j].getIcon() == hq2) 				
					txt += 'H';				
			}
		return txt;
	}
	
	public BattleField() {
		drawFrame();
		adjustButtons();
		setDefaultPositionOfFigures();
		setActionsToButtons();
		battleFieldFrame.setVisible(true);
	}
}
