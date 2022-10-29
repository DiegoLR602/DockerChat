import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of clients to handle broadcast.
 *
 */
public class ClientHandler implements Runnable {
  private Client connectionClient = null;
  private ArrayList<Client> clientList;

  /**
   * ClientHandler: Overloaded constructor that sets this.connectionClient to
   * client and this.clientList to clientList.
   *
   * @param connectionClient Client object
   * @param clientList ArrayList of Client Objects
   */
  ClientHandler(Client client, ArrayList<Client> clientList) {
    this.connectionClient = client;
    this.clientList = clientList;  // Keep reference to master list
  }

  /**
   * Run: Function that receives input from a client. It carries out any
   * commands sent by the client or sends any messages from the client to the
   * other clients. The current commands are: "QUIT", "Who?".
   */
  public void run() {
    try {
      // Obtain socket information from Client object
      Socket connectionSock = connectionClient.connectionSock;
      System.out.println("Connection made with socket " + connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        if ((connectionClient.username).equals("")) {
          // Prompt client/user for username if no username exists
          DataOutputStream thisClientOutput = new DataOutputStream(
              connectionSock.getOutputStream());
          thisClientOutput.writeBytes("PLEASE ENTER A USERNAME:\n");
          String user = clientInput.readLine();
          // The server will not permit the client to choose a username that is
          // already being used by another client
          // If a client tries to use a name that is already being used, the
          // server will prompt the client to try another name.
          boolean uniqueUser = false;
          while (!uniqueUser) {
            uniqueUser = true; // assume it is unique
            for (Client c : clientList) {
              if (c.username.equals(user)) {
                // username is not unique
                thisClientOutput.writeBytes("ANOTHER CLIENT HAS THIS "
                    + "USERNAME. PLEASE ENTER A NEW USERNAME:\n");
                user = clientInput.readLine();
                uniqueUser = false;
                break;
              }
            }
          }
          // if we get here, the username is unique
          connectionClient.username = user;
          if (connectionClient.username.equals("host") && hostExists() == false) {
            // Host will be the first person to add “host” as a username,
            // should be the first person who connects to the server
            connectionClient.isHost = true;
            thisClientOutput.writeBytes("YOU ARE NOW THE HOST.\n");
          }
          thisClientOutput.writeBytes("USERNAME HAS BEEN SET. YOU CAN NOW "
              + "START SENDING MESSAGES.\n");
        }

        // Get data sent from a client
        String clientText = clientInput.readLine();
        if (clientText != null) {
          System.out.println("Received from " + connectionClient.username + ": " + clientText);
          if (clientText.toUpperCase().equals("QUIT")) {
            // Remove from arraylist
            clientList.remove(connectionClient);
            sendMessageToAll(connectionClient.username + " has left the server.\n");
            System.out.println("Closing connection for socket " + connectionSock);

            if (connectionClient.isHost && clientList.size() != 0) {
              // If host leaves, assign another client as the host
              Client newHost = clientList.get((int) Math.random() * clientList.size());
              newHost.isHost = true;
              sendMessageToAll(newHost.username + " is now the host.\n");
            }
            connectionSock.close();
            break;
          } else if (clientText.equals("Who?")) {
            // Prints a list of clients online
            DataOutputStream thisClientOutput = new DataOutputStream(
                connectionSock.getOutputStream());
            thisClientOutput.writeBytes("CURRENT LIST OF USERS:\n");
            for (Client c : clientList) {
              thisClientOutput.writeBytes(c.username + "\n");
            }
          } else if (connectionClient.isHost && clientText.equals("SCORES")) {
            DataOutputStream thisClientOutput = new DataOutputStream(
                connectionSock.getOutputStream());
              for(Client c : clientList) {
                thisClientOutput.writeBytes(c.username + "'s score: " + c.score + "\n");
                sendMessageToAll(c.username + "'s score: " + c.score + "\n");
              }
          } else if (clientText.equals("AWARD")) {
            //Host awards points, so add a command that lets the host add points
            if(connectionClient.isHost == true) {
              DataOutputStream thisClientOutput = new DataOutputStream(
                  connectionSock.getOutputStream());
              thisClientOutput.writeBytes("Enter username to award points: \n");
              String hostText = clientInput.readLine();
              boolean userExists = false;
              for (Client c : clientList) {
                if (c.username.equals(hostText)) {
                  // username exists
                  c.score += 1;
                  userExists = true;
                  String message = "POINT AWARDED TO " + hostText + ".\n";
                  thisClientOutput.writeBytes(message);
                  sendMessageToAll(message);
                }
              }
              if (!userExists) {
                thisClientOutput.writeBytes("USERNAME DOES NOT EXIST. RETRY 'AWARD' COMMAND.\n");
              }
            }
          } else {
            // Turn around and output this data
            // to all other clients except the one
            // that sent us this information
            if (connectionClient.isHost) {
              sendMessageToAll(connectionClient.username + " (host): " + clientText + "\n");
            } else {
              sendMessageToAll(connectionClient.username + ": " + clientText + "\n");
            }
          }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          clientList.remove(connectionClient);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      clientList.remove(connectionClient);
    }
  }

  /**
   * sendMessageToAll: Sends a specified text message to ALL clients
   * EXCEPT the current client.
   *
   * @param message a String that contains a message
   */
  public void sendMessageToAll(String message) {
    try {
      for (Client c : clientList) {
        if (c != connectionClient) {
          DataOutputStream clientOutput = new DataOutputStream(
              c.connectionSock.getOutputStream());
          clientOutput.writeBytes(message);
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      clientList.remove(connectionClient);
    }
  }

  /**
   * hostExists: Function that returns true if there already exists a host and
   * false if there is no current host.
   *
   * @return boolean (true or false)
   */
  public boolean hostExists() {
    for (Client c : clientList) {
      if (c.isHost) {
        return true;
      }
    }
    return false;
  }
} // ClientHandler for MtServer.java
