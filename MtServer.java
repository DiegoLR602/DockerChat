import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MTServer.java
 *
 * <p>This program implements a simple multithreaded chat server.  Every client that
 * connects to the server can broadcast data to all other clients.
 * The server stores an ArrayList of sockets to perform the broadcast.
 *
 * <p>The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * <p>To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */
public class MtServer {
  // Maintain list of all clients for broadcast
  private ArrayList<Client> clientList;

  /**
   * MtServer: Default constructor that initializes the array list clientList.
   */
  public MtServer() {
    clientList = new ArrayList<Client>();
  }

  /**
   * getConnection: Function that creates a Socket object for the server and
   * waits for clients to join. When a client joins, a Client object is created,
   * added to an ArrayList of Clients, and a thread starts to handle the client.
   */
  private void getConnection() {
    // Wait for a connection from the client
    try {
      System.out.println("Waiting for client connections on port 9007.");
      ServerSocket serverSock = new ServerSocket(9007);
      // This is an infinite loop, the user will have to shut it down
      // using control-c
      while (true) {
        Socket connectionSock = serverSock.accept();
        // Create Client object using Socket
        Client connectionClient = new Client(connectionSock, "");
        // Add this client to the list
        clientList.add(connectionClient);
        // Send to ClientHandler the client and arrayList of all clients
        ClientHandler handler = new ClientHandler(
            connectionClient, this.clientList);
        Thread theThread = new Thread(handler);
        theThread.start();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * main method.
   *
   * @params not used.
   */
  public static void main(String[] args) {
    MtServer server = new MtServer();
    server.getConnection();
  }
} // MtServer
