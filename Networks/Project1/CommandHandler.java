// David Hughes n00814425 10/25/15
//This is a server program that must be run on a Unix/Linux machine for it to run the different commands. 
//A client connects to this program and this will display how many people connected and what commands 
//they have run. It runs continuously and the only way to stop it is by hitting cntrl+z. This must be also 
//run with CommandHandler.java executes the option that the client request

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.EOFException;

public class CommandHandler implements Runnable {
	private Socket socket;
	private int clientNum;					//Take in client int to send string back
	
	private String commandMap[] = {			//Runs what request the client would like the server to do
		"date",
		"uptime",
		"free -m",
		"netstat",
		"who",
		"ps ax",
		"quit"
	};
	
	private String messageMap[] = {			//Display array based on what int is passed in
		"date",
		"uptime",
		"memory use",
		"netstat",
		"current users",
		"running processes",
		"quit"
	};
	
	public CommandHandler(Socket socket, int clientNum) {
		this.socket = socket;
		this.clientNum = clientNum;	
	}

	public void run() {							//Start runnable
		try {
			DataInputStream downStream = new DataInputStream(socket.getInputStream());
			DataOutputStream upStream = new DataOutputStream(socket.getOutputStream());
			
			while (true) {
				if (downStream.available() > 0){

		                        long start = System.currentTimeMillis();				//Start calc time for response
					int clientSelection = downStream.readInt();		//Read int from Client
					String commandOutput = IssueSystemCommand(clientSelection);
				
					upStream.writeUTF(commandOutput);		//Send string back to client
					long now = System.currentTimeMillis();	//Finished with job calc time
			        System.out.println("Server elapsed time for request is: " + (now - start) / 1000.0) ;
					upStream.flush();
				}
			}
			
		} catch (EOFException eo) {				// Catch Errors
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String IssueSystemCommand(int option) {		//Print out for what the client request. Takes int from client sends string
		
		String systemCommand = commandMap[option];
		String message = messageMap[option];
		String commandOutput = "";
		
		System.out.println("Responding to " + message + " request from client " + clientNum);
		
		if (option == 6) {
			Thread.currentThread().interrupt();
		}
		else{
			commandOutput = RunCommand(systemCommand);
		}
		return commandOutput;
		
	}
	
	private String RunCommand(String command) {			//Assembles message to be sent back to the client 
		StringBuilder responseOutput = new StringBuilder();
		
		try {
			Runtime runTime = Runtime.getRuntime();
			Process proc = runTime.exec(command);
			
			InputStream consoleResponse = proc.getInputStream();			
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(consoleResponse));
			
			String output;
			while ((output = consoleReader.readLine()) != null) {		//Sends all and assembles message
				responseOutput.append(output);
				responseOutput.append("\n");
			}
			
			return responseOutput.toString();		
		}
		catch(IOException e) {									//Error in assembly
			e.printStackTrace();
		}
		
		return null;
	}
}
