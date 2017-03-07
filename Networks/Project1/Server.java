// David Hughes n00814425 10/25/15
//This is a server program that must be run on a Unix/Linux machine for it to run the different commands. 
//A client connects to this program and this will display how many people connected and what commands 
//they have run. It runs continuously and the only way to stop it is by hitting cntrl+z. This must be also 
//run with CommandHandler.java executes the option that the client request

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	ServerSocket listenSocket;
	int clientNum = 1;
	
	public static void main(String[] args) {
		Server server = new Server();
	}
	
	public Server() {
		try {
			System.out.println("Server starting");			//Starts server and just runs nonstop
			listenSocket = new ServerSocket(2425);			//Binds to n number set port
			ListenForClients();								//Waits for client
			
		}
		catch(IOException ex) {
			System.err.println(ex);							//Error
		}
	}
	
	public void ListenForClients() {
		while (true) {
			try {
				Socket socket = listenSocket.accept();		//Connected to new client
				System.out.println("Accepted client connection");	//Prints connection and request
				System.out.println("Running thread " + clientNum);
				
				CommandHandler handler = new CommandHandler(socket, clientNum);
				Thread thread = new Thread(handler);	//Handles request from client
				thread.start();
				
				clientNum++;							//For each new client added increament
				
			} catch (IOException e) {					//Error
				e.printStackTrace();
			}
		}
	}
}
