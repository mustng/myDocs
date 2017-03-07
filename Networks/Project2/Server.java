//David Hughes n00814425 11/2/15
//This is the server application and has to be run with all of the following Server.java, Program.java, and IServer.java. The
//following program takes in a request from the client to do a terminal command in Linux terminal and return the info displayed 
//back to the client. The server needs to be quit out by executing cntrl+ c and can only be executed properly on a Linux machine.


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
	
	private static final long serialVersionUID = 1229452540526140823L; // The serialization runtime associates with class a version number
	
	protected Server() throws RemoteException {
		super();
	}
	/*
	 *These methods have no input and returns a string
	 *It will take in the request from the client and then
	 * pulls it from the server then send the selected command back
	 */
	public String getCurrentTime() throws RemoteException {	// allows the client to pull the date from server and return it
		System.out.println("Responding to date request");
		String time = RunCommand("date +\"%T\"");                  
		return time;
	}

	public String getUpTime() throws RemoteException {	// allows the client to pull how long the server has been up and return it	
		System.out.println("Responding to time request");
		String result = RunCommand("uptime");    
		return result;
	}

	public String getAvailableMemory() throws RemoteException { // allows the client to get the available memory and return it.
		System.out.println("Responding to memory request");
		String result = RunCommand("free -m");
		return result;
	}

	public String getCurrentUserCount() throws RemoteException {// allows the client to to see who is on the server and return it
		System.out.println("Responding to number of users");
		String result = RunCommand("who");
		return result;
	}

	/*This method gets the "command" string from the client and 
	 * then creates a returns the property that the client requested as a string back
	 */
	private String RunCommand(String command) {				
		StringBuilder responseOutput = new StringBuilder();	
		
		try {
			Runtime runTime = Runtime.getRuntime();		//try to run input
			Process proc = runTime.exec(command);
			
			InputStream consoleResponse = proc.getInputStream();			
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(consoleResponse));
			
			String output;	

			while ((output = consoleReader.readLine()) != null) {	
				responseOutput.append(output);	// keep reading output until there is no more. The output it received from server
				responseOutput.append("\n");
			}
			
			return responseOutput.toString();		
		}
		catch(IOException e) {
			System.out.println("Input/Output error!");
		}
		
        
		return null;
	}
}
