import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class will display several information such as a user input. 
 * It will contact a server program in a way representing UDP. 
 * @author Abdalla-Elmedani
 * @version 1.0
 */
public class ServerProgram {

	/**
	 * The main method. 
	 * @param args
	 */
	public static void main(String[] args) throws IOException{

		// Create a datagram socket and include the port number for the socket.
		// Source: https://docs.oracle.com/javase/7/docs/api/java/net/DatagramSocket.html
		DatagramSocket serverSocket = new DatagramSocket(5060); // Update the socket number according to your machine. 

		//Create two datagram packet. This class represents a datagram packet. 
		// Source: https://docs.oracle.com/javase/7/docs/api/java/net/DatagramPacket.html
		DatagramPacket datagramPacket = null;
		DatagramPacket send_Datagram_Packet = null;

		//This class represents an Internet Protocol (IP) address.
		//Source: http://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html
		InetAddress address = null;

		//This string will contain the sent data from the client.
		byte [] sent_Data = new byte [1024]; 
		//This string will contain the received data from server. 
		byte [] received_Data = new byte [1024];   

		// Port number. 
		int port = 0;
		
		// the item Id selected by the client. 
		String itemId = null;
		String qoute = null;

		// Create a two dimensional array to maintain the units prices and details. 
		String [] [] items_details = new String [6] [4];

		//Fill up the array with the appropriate date. 
		items_details[0][0] = "00001";
		items_details[0][1] = "New Inspiron 15";
		items_details[0][2] = "$379.99";
		items_details[0][3] = "157";
		items_details[1][0] = "00002";
		items_details[1][1] = "New Inspiron 17";
		items_details[1][2] = "$449.99";
		items_details[1][3] = "128";
		items_details[2][0] = "00003";
		items_details[2][1] = "New Inspiron 15R";
		items_details[2][2] = "$549.99";
		items_details[2][3] = "202";
		items_details[3][0] = "00004";
		items_details[3][1] = "New Inspiron 15z Ultrabook";
		items_details[3][2] = "$749.99";
		items_details[3][3] = "315";
		items_details[4][0] = "00005";
		items_details[4][1] = "XPS 14 Ultrabook";
		items_details[4][2] = "$999.99";
		items_details[4][3] = "261";
		items_details[5][0] = "00006";
		items_details[5][1] = " New XPS 12 UltrabookXPS";
		items_details[5][2] = "$1199.99";
		items_details[5][3] = "178";


		// Set a flag to get more packets and will be used in to start an infinite loop. 
		Boolean stayInTheLoop = true;
		
		while(stayInTheLoop) {

			try {
				// Receive the datagram packets from the user. 
				datagramPacket = new DatagramPacket(received_Data, received_Data.length);
				serverSocket.receive(datagramPacket);
				itemId = new String (datagramPacket.getData(), 0, datagramPacket.getLength());


				if (itemId.equals("00001")) {
					qoute = "\nItemID : " + items_details[0][0] + "\nItem Description : "+ items_details[0][1] + 
							"\nItem Price : "+items_details[0][2] + "\nLot Number : " + items_details[0][3];
				}

				else if (itemId.equals("00002")) {
					qoute = "\nItemID : " + items_details[1][0] + "\nItem Description : "+ items_details[1][1] + 
							"\nItem Price : "+items_details[1][2] + "\nLot Number : " + items_details[1][3];
				}

				else if (itemId.equals("00003")) {
					qoute = "\nItemID : " + items_details[2][0] + "\nItem Description : "+ items_details[2][1] + 
							"\nItem Price : "+items_details[2][2] + "\nLot Number : " + items_details[2][3];
				}

				else if (itemId.equals("00004")) {
					qoute = "\nItemID : " + items_details[3][0] + "\nItem Description : "+ items_details[3][1] + 
							"\nItem Price : "+items_details[3][2] + "\nLot Number : " + items_details[3][3];
				}

				else if (itemId.equals("00005")) {
					qoute = "\nItemID : " + items_details[4][0] + "\nItem Description : "+ items_details[4][1] + 
							"\nItem Price : "+items_details[4][2] + "\nLot Number : " + items_details[4][3];
				}

				else if (itemId.equals("00006")) {
					qoute = "\nItemID : " + items_details[5][0] + "\nItem Description : "+ items_details[5][1] + 
							"\nItem Price : "+items_details[5][2] + "\nLot Number : " + items_details[5][3];
				}

				else if (itemId.equalsIgnoreCase("yes")) {
					// Shut down the server. 
					qoute = "The server will shut down immediatly. Thank you.";
					address = datagramPacket.getAddress();
					port = datagramPacket.getPort();
					
					sent_Data = qoute.getBytes();
					send_Datagram_Packet = new DatagramPacket(sent_Data, sent_Data.length , address , port);
					serverSocket.send(send_Datagram_Packet);
					serverSocket.close();
					System.exit(0);
				}

				address = datagramPacket.getAddress();
				port = datagramPacket.getPort();

				// Now prepare the data to be sent back to client.
				sent_Data = qoute.getBytes();
				send_Datagram_Packet = new DatagramPacket(sent_Data, sent_Data.length , address , port);
				serverSocket.send(send_Datagram_Packet);


			} catch (Exception e) {
				e.printStackTrace();
				
				// Exit the while loop.
				stayInTheLoop = false;
				serverSocket.close();
				
 			} // End of the catch block.
			
		} // End of the loop.

	} // End of the main method.
} // End of the class.
