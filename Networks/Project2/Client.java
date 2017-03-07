//David Hughes n00814425 11/2/15
//The following program is the client of a server program. This display options 1 through 5
//for the user to select. Upon selection the client will take the value and send it to the server
//as a request then leaving the server to retrieve the information off itself and send it back. This
//must be ran as Client.java and IServer.java compiled together. There must also be the ip argument 
//supplied upon execution of the client otherwise an error will be displayed. This may work on a windows pc 
//however server will need to be executed on a Linux machine.


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static String options[] = { 
		"Host current Date and Time",		//Displays the output to the console for the user to select option
		"Host uptime",
		"Host memory use",
		"Host current users",
		"Quit"
	};
	
	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) throws RemoteException, NotBoundException  {
		String url = " ";
		
		if(args.length < 1){
			System.out.println("No ip address provided!");	// checks for a valid input from the user
			System.exit(0);
		}
		else{
			url = args[0];
		}
		try{
			Registry registry =	LocateRegistry.getRegistry(url,2425);  // designates the port of the server to connect to
			IServer remote = (IServer) registry.lookup("dh");			// special char that is like a password to use the server
			
			try {
				while (true) {
					DisplayMenu();
					int selectedOption = ReadInput();
	
					if (options[selectedOption] == "Quit") {
						System.exit(0);
					} 
					else {
						long start = System.currentTimeMillis();				//Start calc time for response
						long now;
						switch(selectedOption) {
							case 0:
								String t = remote.getCurrentTime();
								System.out.println(t);				// input 1 gets the time from the server
								now = System.currentTimeMillis();	//finished with job calc time
						        System.out.println("Server elapsed time for request is: " + (now - start) / 10.0);
								break;
				
							case 1:
								String upTime = remote.getUpTime();
								System.out.println(upTime);			// runs the command upTime in the terminal of the server
								now = System.currentTimeMillis();	//finished with job calc time
						        System.out.println("Server elapsed time for request is: " + (now - start) / 10.0);
								break;
							case 2:
								String availableMemory = remote.getAvailableMemory();
								System.out.println(availableMemory);// runs command to get how long the server has been up
								now = System.currentTimeMillis();	//finished with job calc time
						        System.out.println("Server elapsed time for request is: " + (now - start) / 10.0);
								break;
							case 3:
								String activeUsers = remote.getCurrentUserCount();
								System.out.println(activeUsers);	// simply gets all the users who are on the server
								now = System.currentTimeMillis();	//finished with job calc time
						        System.out.println("Server elapsed time for request is: " + (now - start) / 10.0);
								break;
						}
					}
				}
				
			} catch (Exception e) {			// first catch that throws exception if the server stops responding.
				System.out.println("Lost connection with server!");
			}
		}catch (Exception e) {				// second catch that throws an exception if client cant connect to the server
			System.out.println("No sever found at ip supplied!");
		}
	}
	/*This method takes in the display value from 0 to 4 and adds one for
	 * it to work properly 
	 */
	static void DisplayMenu() {
		System.out.println("Enter your choice");
		for (int index = 0; index < options.length; index ++) {
			String uiNumber = Integer.toString(index + 1);
			System.out.println(uiNumber + " - " + options[index]);
		}
	}
	
/* This method takes in the input and validates it to makes sure it is a selected option 
 * and handles it from there. If not valid displays an error message. The input is actually
 * 0 through 4 but when taken in is subtracted. Upon selection of the proper menu returns what is 
 * selected
 */
	static int ReadInput() throws RemoteException {
		int selectedOption;
		
		while(true) {
			if (input.hasNextInt()) {						//take in input
				selectedOption = input.nextInt() - 1;
				
				if (selectedOption < 0 || selectedOption >= options.length) { //validate options to make sure it is proper value or error
					System.out.println("Not a recognized option. Try again. Use a number between 1 - 5.");	
					DisplayMenu();				
				} else {				
					break;
				}
			}
			else if (input.hasNext()) {
				input.nextLine(); //validate options to make sure it is proper value or error
				System.out.println("Not a recognized option. Try again. Use a number between 1 - 5.");	
				DisplayMenu();		
			}
		} 
		
		System.out.println("User has selected: " + options[selectedOption]);		

		
		return selectedOption;
	}
}
