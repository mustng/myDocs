//David Hughes n00814425 11/2/15
//This is the server application and has to be run with all of the following Server.java, Program.java, and IServer.java. The
//following program takes in a request from the client to do a terminal command in Linux terminal and return the info displayed 
//back to the client. The server needs to be quit out by executing cntrl+ c and can only be executed properly on a Linux machine.

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*Start of the main server and binds to the 
 * new file with a unique id also designates the port number
 */
public class Program {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		Server impl = new Server();
    	Registry registry = LocateRegistry.createRegistry(2425);
    	registry.bind("dh", impl);
   		System.out.println("Server starting");
		
	}
}
