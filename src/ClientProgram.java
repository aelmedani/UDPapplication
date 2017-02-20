import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Scanner;

/**
 * This class will implement and maintain information using a specific data structure. 
 * @author Abdalla_Elmedani
 * @version 1.0
 */
public class ClientProgram {

	/**
	 * The main method. 
	 * @param args
	 */
	public static void main(String[] args) throws IOException{

		/**
		 * A flag to check if the user wants to continue the program.
		 */
		Boolean continue_The_Application = true;
		/**
		 * A flag to store the user input. 
		 */
		String Yes_Or_No = null;

		// Outer while loop to run the application.
		while (continue_The_Application) {

			byte [] sent_Data = new byte [1024]; //This string will contain the sent data from the client.
			byte [] received_Data = new byte [1024];  //This string will contain the received data from server.  

			String DNS_or_IP = "localhost"; //I will use local host on my local machine. This should be changed for different IPs. 
			String itemId = null; // This should contain the item Id number. It will be used to matched a list of numbers in the server. 
			String quote = null;
			
			// String to shut down the server. Note: In real life you should never do this!
			String shut_Down_Server = "yes";
			
			// Boolean to validate the user input.
			Boolean invalidInput = true;
			
			// Date object. Will be used to store time. 
			Date request_Time = new Date();
			Date respond_Time = new Date();
			long request_Time_Milliseconds = 0;
			long respond_Time_Milliseconds = 0;

			//This class represents an Internet Protocol (IP) address.
			//Source: http://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html
			InetAddress address = null;

			// The datagram packet class. 
			DatagramPacket datagram_Packet = null;
			DatagramPacket received_Datagram_Packet = null; // This will store the received datagram packet from server. 
			
			// The datagram socket class.
			DatagramSocket client_datagram_Socket = new DatagramSocket();

			//Display a message to ask the User to input the DNS or IP of the machine on which the Server Program runs.
			System.out.println("\nPlease enter the the DNS or the IP of the "
					+ "machine on which the server program runs: ");
			
			Scanner scanner = new Scanner (System.in);
			
			DNS_or_IP = scanner.nextLine();

			//Display a pre-coded table as a standard output.

			System.out.println("---------------------------------------------");
			System.out.println("| Item ID 	| Item Description          |");
			System.out.println("---------------------------------------------");
			System.out.println("| 00001 	| New Inspiron 15           |");
			System.out.println("---------------------------------------------");
			System.out.println("| 00002 	| New Inspiron 17           |");
			System.out.println("---------------------------------------------");
			System.out.println("| 00003 	| New Inspiron 15R          |");
			System.out.println("---------------------------------------------");
			System.out.println("| 00004 	| New Inspiron 15z UltraBook|");
			System.out.println("---------------------------------------------");
			System.out.println("| 00005 	| XPS 14 Ultrabook          |");
			System.out.println("---------------------------------------------");
			System.out.println("| 00006 	| New XPS 12 UltrabookXPS   |");
			System.out.println("---------------------------------------------");

			//Display a message on the standard output to ask the User to input an Item ID.
			//validate the user input. If the input is not a valid Item ID, ask the User to re-type it.

			while (invalidInput) {
				System.out.println("Please enter the item ID.");
				System.out.println("Please use numbers only. Make sure you include the"
						+ " '0000' before the itemId in your input.\n\n");
				
				itemId = scanner.nextLine().trim();
				
				if (itemId.equals("00001") || itemId.equals("00002") || itemId.equals("00003") || 
						itemId.equals("00004") || itemId.equals("00005") || itemId.equals("00006")) {
					
					invalidInput = false;
				}
			} // End of the validation while loop. 
			

			//send a request including this Item ID to the Server program to ask for a quote. 
			address = InetAddress.getByName(DNS_or_IP);
			sent_Data = itemId.getBytes();
			datagram_Packet = new DatagramPacket(sent_Data, sent_Data.length, address, 5060);
			client_datagram_Socket.send(datagram_Packet);
			
			System.out.println("\nYou have submitted your request at: " + (request_Time));
			
			// Store the request time in Millis.
			request_Time_Milliseconds = System.currentTimeMillis();
			
			received_Datagram_Packet = new DatagramPacket(received_Data, received_Data.length);
			client_datagram_Socket.receive(received_Datagram_Packet);
			quote = new String (received_Datagram_Packet.getData());
			
			// Store the respond time in Mills. 
			respond_Time_Milliseconds = System.currentTimeMillis();
			
			System.out.println("\nYour item information is: " + quote);
			System.out.println("RTT of query : " + (respond_Time_Milliseconds - request_Time_Milliseconds) + " Milliseconds.");
			System.out.println("\nThe response time was: " + respond_Time);

			// This loop will determine if you client wants to continue using this application.
			while (continue_The_Application) {
				
				System.out.println("\nDo you want to continue the application?");
				System.out.println("\nPlease type Y or N.");
				
				Yes_Or_No = scanner.nextLine().trim().toLowerCase();

				if (Yes_Or_No.equalsIgnoreCase("y") || Yes_Or_No.equalsIgnoreCase("n")) {

					if (Yes_Or_No.equalsIgnoreCase("n")) {

						continue_The_Application = false;
						
						System.out.println("\nThank you for shopping with us. Bye.");
						System.out.println("The server will shut down immediatly.");
						
						// Shut-down the server. 
						sent_Data=shut_Down_Server.getBytes();
						datagram_Packet = new DatagramPacket(sent_Data, sent_Data.length, address, 5060);
						client_datagram_Socket.send(datagram_Packet);
						
						// Close the client socket. 
						client_datagram_Socket.close();
						scanner.close(); // Close the scanner. 
					}
					else {
						
						System.out.println("\nThank you, please continue shopping.");
						// Break out of this loop and continue the application.
						
						break;  
					}

				} // End of the outer if statement. 

			} // End of the the small final while loop. 

		} // End of the first while loop 


	} // End of the main method. 

} // End of the class. 
