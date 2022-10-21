# dockerchat

This repo contains programs to implement a multi-threaded TCP chat server and client

* MtClient handles keyboard input from the user.
* ClientListener receives responses from the server and displays them.
* MtServer listens for client connections and creates a ClientHandler for each new client.
* ClientHandler receives messages from a client and relays it to the other clients.


## Identifying Information

### Repo Owner
* Name: Diego Lopez Ramos

### Partner
* Name: Ariel

### Partner
* Name: Joan

## Contributions

* Diego
  * Implemented code for obtaining client username
  * Implemented quit functionality
  * Implemented feature of having a command SCORES than only host can use to display scores
  * Implemented feature of assigning another person as the host, if host leaves
  * Added documentation to source files

* Ariel
  * Changed port numbers to 9006
  * Implemented Client class and changed ArrayList of Sockets to Clients
  * Implemented unique username feature
  * Added score and isHost variables to Client object
  * Implemented feature of assigning host to the first person who writes "host"

* Joan
  * Implemented identifying username at the beginning of each message
  * Implemented "Who?" which displays the list of current users
  * Added the Award function which allows the host to add a point to the appointed user 

## Source Files

* Client.java
* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java

## Build Instructions

* run the command: `javac *.java`

## Docker Instructions

* To package the client and server in Docker containers:

* Copy the MtClient.class and ClientListener.class files to the client subdirectory
* Copy the MtServer.class and ClientHandler.class files to the server subdirectory

* In the client subdirectory, to create the client Docker image use:
	docker image build -t client .

* In the server subdirectory, to create the server Docker image use:
	docker image build -t server .

## Game Instructions:

* The first client to enter "host" will be the host of the game:
* The host will send True/False questions to the other clients and whoever answers first is awarded points.
  * The host awards points manually with the command "AWARD"
* The host can use the command "SCORES" to send a message with the scores of each client will be sent to all the clients.
 
## Execution Instructions

* In one terminal, run `java MtServer`
* In another terminal, run `java MtClient`
  * You can run multiple client programs so that they can interact with each other

## Known Errors

* To close the server program, the user must use `Ctrl + C`.
* There is no verification for the spelling of usernames.
