import java.net.Socket;

/**
 * Client.java
 *
 * <p>This class contains contains a socket object, username, score, and
 * a variable to see if the Client is a host.
 *
 */
public class Client {
  public Socket connectionSock = null;
  public String username = "";
  public int score = 0;
  public boolean isHost = false;

  /**
   * Client: This constructor assigns this.connectionSock to Socket object and
   * this.username to username.
   *
   * @param sock Socket Object associated to the Client
   * @param username String that contains the username of the Client
   */
  Client(Socket sock,  String username) {
    this.connectionSock = sock;
    this.username = username;
  }
}
