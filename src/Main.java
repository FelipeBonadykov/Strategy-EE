/**
 * @author Felipe Bonadykov Main class contains all constants and launches the
 *         game
 */
public class Main {
	
    public static final String ICON = "res/sign.png";
	public static final String MAP = "res/Screen/map.jpg";
	public static final String MOVIE = "res/Start/BattleMovie.gif";
	public static final String MUSIC = "res/Start/StartMusic.wav";
	public static final String WAR = "res/Screen/sound.wav";
	public static final String PROGRESS = "res/Progress/progress.fb";
	public static final String URL = "https://strategyonline.rj.r.appspot.com/strategy-ee";
	//  "http://localhost:8080/strategy-ee"; // uncomment to test on localhost

	public static WelcomeWindow welcomeWindow;
	public static BattleField battleField;
	public static void main(String[] args) {
		welcomeWindow = new WelcomeWindow();
	}
}