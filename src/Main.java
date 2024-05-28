/* SERVER - may be enhanched to work for multiple clients */
import java.net.*;
import java.io.*;



public class Main {
    public static void main(String[] args) {

        ServerSocket server = null;
        Socket client;

        //Deafult port number we are going to use
        int portnumber = 53000;
        if (args.length >= 1){
            portnumber = Integer.parseInt(args[0]);
        }
        // Create server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie){
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created"+ server);

        //Wait for the data drom the client and reply
        while (true){
            try {

                // Listens for a connection to be made to
                // This socket and accepts it. The method blocks until
                // a connection is made
                System.out.println("Waiting for  connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + "Client port = " + clientPort);

                // Read data from client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message recived from vlient = " + msgFromClient);

                // Send response to the client
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("Bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, " + msgFromClient;
                    pw.println(ansMsg);
                }
                // close sockets
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }
            }catch (IOException ie){
                System.out.println("something went wrong :/");
            }
            }
        }
    }
