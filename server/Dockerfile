FROM openjdk:11

COPY Client.java /deployments/
COPY MtServer.java /deployments/
COPY ClientHandler.java /deployments/
RUN cd /deployments && javac *.java
CMD cd /deployments; java MtServer

