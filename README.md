# dockerchat
A copy of the mtchat repo with updated docker files.

This repo contains programs to implement a multi-threaded TCP chat server and client

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them.
* MtServer.java listens for client connections and creates a ClientHandler for each new client.
* ClientHandler.java receives messages from a client and relays it to the other clients.


## Identifying Information

### Repo Owner
* Name: Ariel Gutierrez
* Student ID: 2318163
* Email: arigutierrez@chapman.edu
* Course: CPSC - 353
* Assignment: PA04 - Chat

### Partner
* Name: Diego Lopez
* Student ID: 2378206
* Email: lopezramos@chapman.edu
* Course: CPSC 353
* Assignment: PA04 - Chat

### Partner
* Name: Joan Karstrom
* Student ID: 2318286
* Email: Karstrom@chapman.edu
* Course: CPSC - 353
* Assignment: PA04 - Chat

## Contributions
The changes on this repository were made from simultaneously working on the
project together and then adding the changes through one user to avoid merge conflicts.

* Ariel
  * Changed port numbers to 9006
  * Implemented Client class and changed ArrayList of Sockets to Clients
  * Implemented unique username feature
  * Added score and isHost variables to Client object
  * Implemented feature of assigning host to the first person who writes "host"
  * Implemented feature of assigning another person as the host, if host leaves
  * Added documentation to source files
* Joan
  * Implemented identifying username at the beginning of each message
  * Implemented "Who?" which displays the list of current users
  * Added the Award function which allows the host to add a point to the appointed user 
* Diego
  * Implemented code for obtaining client username
  * Implemented quit functionality
  * Implemented feature of having a command SCORES than only host can use to display scores

## Source Files

* Client.java
* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java

## References

* Class Notes and Textbook

## Known Errors

* To close the server program, the user must use `Ctrl + C`.
* There is no verification for the spelling of usernames.

## Build Instructions

* cd into the repository
* run the command: `javac *.java`

## Execution Instructions

* In one terminal, run `java MtServer`
* In another terminal, run `java MtClient`
  * You can run multiple client programs so that they can interact with each other
* Game Instructions:
  * The first client to enter "host" will be the host of the game:
  * The host will send True/False questions to the other clients and whoever answers first is awarded points.
    * The host awards points manually with the command "AWARD"
  * The host can use the command "SCORES" to send a message with the scores of each client will be sent to all the clients.
