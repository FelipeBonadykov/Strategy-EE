import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Felipe Bonadykov This class uses Web Sockets to pair two players. It
 *         may resemble a simple chat app. A user that made a move sends a
 *         message to the opponent. The opponent receives the message, positions
 *         figures according to it and makes a move, sending a message to the
 *         player.
 */
public class PlayerConnector {

	private String ip = new ServerConnector().bindToOpponent();
	private int port = 8080;// 1024< port < 65535 // some lower than 1024 are used by other apps

	private Socket socket;
	private PrintWriter senderToServer;
	private BufferedReader receiverFromServer;
	private BattleField battleField;
	private boolean isPlayersTurn;
	public static boolean flag = true;
	private Thread receiveUpdateFromServer = new Thread("Update Field") {
		public void run() {
			receiveMapFromOpponent();
		}
	};
	private Thread updateLabel = new Thread() {
		public void run() {
			while (flag) {
				synchronized (battleField) {
					battleField.updateWhosTurnIsLabel();
				}
			}
		}
	};

	private void connectToOpponent() {
		try (ServerSocket sersock = new ServerSocket(port, 8, InetAddress.getByName(ip))){
			socket = sersock.accept();
			BattleField.firstOrSecond = true;
			isPlayersTurn = true;
		} catch (Exception e) {
			try {
				socket = new Socket(ip, port);
				BattleField.firstOrSecond = false;
				isPlayersTurn = false;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void setupChanelOfContact() throws IOException {
		senderToServer = new PrintWriter(socket.getOutputStream(), true);
		receiverFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public PlayerConnector(BattleField battleField) {
		try {
			this.battleField = battleField;
			connectToOpponent();
			Main.welcomeWindow.getFrame().dispose();
			setupChanelOfContact();
			receiveUpdateFromServer.start();
			updateLabel.start();
		} catch (IOException e) {
			System.err.println("Something went wrong");
			e.printStackTrace();
		}
	}

	public boolean isMyTurn() {
		return isPlayersTurn;
	}

	public void sendMapToOpponent(String map) {
		isPlayersTurn = false;
		senderToServer.println(map);
		senderToServer.flush();
	}

	private void receiveMapFromOpponent() {
		String receivedMessage;
		while (flag) {
			try {
				if ((receivedMessage = receiverFromServer.readLine()) != null) {
					isPlayersTurn = true;
					battleField.updateBattleField(receivedMessage);
				}
			} catch (Exception e) {
				flag = false;
			}
		}
	}

}
