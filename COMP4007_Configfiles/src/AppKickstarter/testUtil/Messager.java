package AppKickstarter.testUtil;

import java.io.*;
import java.net.Socket;

/**
 * Created by maureen on 11/27/17.
 */
public class Messager {
    private BufferedReader inFromClient;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private Socket clientSocket;

    public Messager(Socket socket) throws IOException {
        this.clientSocket = socket;
    }

    public void send(String msg) throws IOException {
        inFromClient = new BufferedReader(new StringReader(msg));
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        String outMsg = inFromClient.readLine();
        System.out.println("----outMsg----" + outMsg);
        byte[] byteMsg = outMsg.getBytes("UTF-8");
        outToServer.writeInt(byteMsg.length);
        outToServer.write(byteMsg);
    }

    public String receive() throws IOException {
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inMsg = inFromServer.readLine();
        System.out.println("----inMsg----" + inMsg);
        return inMsg;
    }



}
