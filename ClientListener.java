/*
Name: Ariel Gutierrez
Student ID: 2318163
Email: arigutierrez@chapman.edu

Name: Diego Lopez
Student ID: 2378206
Email: lopezramos@chapman.edu

Name: Joan Karstrom
Student ID: 2318286
Email: Karstrom@chapman.edu

Course: CPSC - 353
Assignment: PA04 - Chat
*/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientListener.java
 *
 * <p>This class runs on the client end and just
 * displays any text received from the server.
 *
 */
public class ClientListener implements Runnable {
  private Socket connectionSock = null;

  /**
   * ClientListener: Constructor that sets connectionSock to sock.
   * @param sock Socket Object associated to the Client
   */
  ClientListener(Socket sock) {
    this.connectionSock = sock;
  }

  /**
   * Run: Function that gets message from server and displays it to the user.
   */
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverText != null) {
          System.out.println(serverText);
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
} // ClientListener for MtClient
