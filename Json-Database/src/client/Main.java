package client;
import java.io.*;
import java.net.*;


public class Main {

    public static void main(String[] args)
    {
        String address  = "127.0.0.1";
        int PORT = 23473;

        System.out.println("Client started!");
        try (
                Socket socket = new Socket(InetAddress.getByName(address), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        )

        {
            String msg =  "";
            for(int i = 1; i < args.length ; i = i+2)
            {
                msg = (msg + args[i] +" ");
            }
            msg = msg.trim();

            output.writeUTF(msg); // sending message to the server
            System.out.println("Sent: " + msg);
            String receivedMsg = input.readUTF(); // response messag
            System.out.println("Received: " + receivedMsg);
            socket.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}