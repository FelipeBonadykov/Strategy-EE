import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * @author Felipe Bonadykov This class is a welcome window with start button. It
 *         is managed in a different thread
 *
 */
public class WelcomeWindow {
	private JFrame startWindow = new JFrame();
	private JButton startButton = new JButton();
	private JLabel waitingLabel = new JLabel();
	private boolean canCall = true;
	
	public JFrame getFrame() {
		return startWindow;
	}
	
	private void designFrame() {
		startWindow.setTitle("STRATEGY");
		startWindow.setSize(600, 339);
		startWindow.setLocationRelativeTo(null);
		startWindow.setResizable(false);
		startWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		startWindow.setIconImage(new ImageIcon(Main.ICON).getImage());
		startWindow.setContentPane(new JLabel(new ImageIcon(Main.MOVIE)));
		startWindow.setLayout(new FlowLayout());
	}
	
	private void setUI() {
		/*
		 * Add welcoming text
		 * and a link to webpage with rules explained
		 */
		waitingLabel.setSize(580, 100);
		waitingLabel.setText("                                      Waiting for opponent...                                       ");
		waitingLabel.setForeground(Color.YELLOW);
		waitingLabel.setFont(new Font("Arial", Font.BOLD, 17));
		waitingLabel.setVisible(false);
		
		startButton.setText("START");
		startButton.setBackground(Color.LIGHT_GRAY);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canCall) {
					waitingLabel.setVisible(true);
					new Thread("BattleField") {
						public void run() {
							Main.battleField = new BattleField();
						}
					}.start();
					canCall = false;
				}				
			}
		});
		
		startWindow.add(startButton);
		startWindow.add(waitingLabel);
	}

	public WelcomeWindow() {
		designFrame();
		setUI();
		startWindow.setVisible(true);
	}
}
