//David Hughes n00814425 11/2/15
//This is the server and client implementation and has to be run with all of the following Server.java, Program.java, and IServer.java or 
//Client.java and IServer.java. The following program takes in a request from the client to do a terminal command in Linux terminal and 
//return the info displayed back to the client. The server needs to be quit out by executing cntrl+ c and can only be executed properly on 
//a Linux machine. Client is closed by selected the exit menu. 

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	
	/*
	 *These methods has no input and returns a string
	 *It will takes in the request from the client and then
	 * pulls it from the server then send the selected command back
	 */
	String getCurrentTime() throws RemoteException;
	String getUpTime() throws RemoteException;
	String getAvailableMemory() throws RemoteException;
	String getCurrentUserCount() throws RemoteException;

}
