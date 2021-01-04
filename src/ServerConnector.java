import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Felipe Bonadykov This class is used to pair one player with another
 *         by passing key and IP address to server and retrieving IP of
 *         opponent.
 */
public class ServerConnector {
	private int number;
	
	private void getMyNumber() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(Main.URL_INIT).openConnection().getInputStream()));
		number = Integer.valueOf(br.readLine());
	}
	
	private String getIPFromServer() throws Exception{
		String ip = "none";
		try {
			ip = (String) ((JSONObject) new JSONParser().parse
					(new BufferedReader(new InputStreamReader(new URL(
							Main.URL_CONNECT+"?number="+number)
							.openConnection().getInputStream())).readLine()))
					.get("ipaddress");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showOptionDialog(null, "No internet access",
				"SYSTEM WARNING", JOptionPane.DEFAULT_OPTION, 
				JOptionPane.WARNING_MESSAGE, null, null, null);
			}	
		return ip;		
	}
	
	public String bindToOpponent() {
		String ip = "none";
		try {
			getMyNumber();
			while (ip.equals("none")) {
				ip = getIPFromServer();
				System.out.println(number+" "+ip);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}
	
}
