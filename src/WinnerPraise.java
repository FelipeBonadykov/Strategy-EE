import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
// IT MUST CONGRAT THE SAME SIDE. THE BOARD IS NOT ROTATED YET
/**
 * @author Felipe Bonadykov This class displays flag of the side that won to
 *         praise the winner. This window appears only when the game is over.
 */
public class WinnerPraise {
	private static JFrame winnerWindow = new JFrame();
		
	private static void drawFrame(boolean didThePlayerWin, boolean isFirst) {
		Main.battleField.getFrame().dispose();
		String playerWon = isFirst? "USA": "USSR";
		if(didThePlayerWin) 
			winnerWindow.setTitle("Congratulations!!!");
		else
			winnerWindow.setTitle("Your opponent won.");
		winnerWindow.setSize(600, 339);
		winnerWindow.setLocationRelativeTo(null);
		winnerWindow.setIconImage(new ImageIcon(Main.ICON).getImage());
		winnerWindow.setResizable(false);
		winnerWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		winnerWindow.setContentPane(new JLabel(new ImageIcon("res/Result/"+playerWon+"/flag.png")));
		winnerWindow.setVisible(true);
	}
	
	public static void checkForWinner(JButton first, JButton second, Icon hq1, Icon hq2, boolean isFirst) {
		boolean didThePlayerWin = false;
		if (first.getIcon() != hq1)
			if (isFirst) {
				didThePlayerWin = false;
			} else {
				didThePlayerWin = true;
			}
		else if (second.getIcon() != hq2)
			if (isFirst) {
				didThePlayerWin = true;
			} else {
				didThePlayerWin = false;
			}
		else
			return;
		drawFrame(didThePlayerWin, isFirst);
		PlayerConnector.flag = false;
	}

}
