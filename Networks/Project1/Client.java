// David Hughes n00814425 10/25/15
//This program is implemented as a client and connects to a Unix/Linux machine run server and can display 
//different information that the runner of the program asks it too such as time and date or people who 
//are on the server. This must be run with an argument of the ip trying to connect

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {								//Loads display into an array based system
	public String options[] = { 
		"Host current Date and Time",
		"Host uptime",
		"Host memory use",
		"Host Netstat",
		"Host current users",
		"Host running processes",
		"Quit"
	};
	
	private DataOutputStream upStream;			//Int being sent up to server
	private DataInputStream downStream;			//Data received from the server
	private Scanner input;						// Take input
	
	public static void main(String[] args) {
		
	
		if (args.length < 1){
			System.out.println("No ip address provided!");
			System.exit(0);
		}
		else{
			String url = args[0];
			Client c = new Client(url);
		}
	}
	
	public Client(String url) {		
		int port = 2425;						// Port based on n number
		Socket socket;
		input = new Scanner(System.in);
		
		try {
			socket = new Socket(url, port);		//Init domain and the port
			upStream = new DataOutputStream(socket.getOutputStream());
			downStream = new DataInputStream(socket.getInputStream());
			
			while (true) {
				DisplayMenu();					// Display menu and take in input
				int selectedOption = ReadInput();
				System.out.println(downStream.readUTF());
				upStream.flush(); 				//Clear

				if (options[selectedOption] == "Quit") {	//Quit when correct option done				
					input.close();
					System.exit(0);
				}
			}
			
		} catch (UnknownHostException e) {			//Error in input
			System.out.println("Need to execute with ip or lost/no connection to server"); 
		} catch (IOException e) {
			System.out.println("Need to execute with ip or lost/no connection to server"); 
		}
	}
	
	void DisplayMenu() {							//Menu options 1 - 7 
		System.out.println("Enter your choice");
		for (int index = 0; index < options.length; index ++) {
			String uiNumber = Integer.toString(index + 1);
			System.out.println(uiNumber + " - " + options[index]);
		}
	}
	
	int ReadInput() {								// Takes in the input and decides if it is valid or not
		int selectedOption;
		
		while(true) {
			if (input.hasNextInt()) {						
				selectedOption = input.nextInt() - 1;
				
				if (selectedOption < 0 || selectedOption >= options.length ){
					System.out.println("\nNot a recognized option. Try again. Use a number between 1 - 7.\n");
					DisplayMenu();
				}
				else{
					break;
				}
			}
			else if(input.hasNext()){
				input.next();
				System.out.println("\nNot a recognized option. Try again. Use a number between 1 - 7.\n");
				DisplayMenu();
			}
		} 
		
		System.out.println("\nUser has selected: " + options[selectedOption] + "\n");	//Display what use picked	
		SendCommand(selectedOption);
		
		return selectedOption;
	}
	
	void SendCommand(int option) {										//Send command to server
		try {
			upStream.writeInt(option);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
