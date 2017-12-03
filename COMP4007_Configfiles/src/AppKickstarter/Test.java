package AppKickstarter;

import AppKickstarter.testUtil.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by maureen on 11/23/17.
 */
public class Test {
    private Socket clientSocket;
    private Messager message;
    private static String path = "./testCase/";
    public static int[] result;
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Record> records = new ArrayList<>();
    public static String Name = "queueTooLong.csv";

    public Test(String serverName, int portNum) throws IOException {
        this.clientSocket = new Socket(serverName, portNum);

        new ReadClient(path + Name);
        this.message = new Messager(clientSocket);
        new Thread(new receiveTick(message)).start();
        new Thread(new sendTick(message)).start();
        //clientSocket.close();
    }

    public static Client getClientTicket(int ticketNum){
        for(Client c: clients){
            if(c.getTicketNum() == ticketNum){
                return c;
            }
        }
        return null;
    }

    public static  Client getClientId(String clientNum){
        for(Client c: clients){
            if(c.getClientId().equals(clientNum)){
                return c;
            }
        }
        return null;
    }

    static class ShutDown extends Thread {

        public void run() {
            System.out.println("numTicketReq: " + Event.numTicketReq);
            System.out.println("numTicketRep: " + Event.numTicketRep);
            System.out.println("numTicketCall: " + Event.numTicketCall );
            System.out.println("numTicketAck: " + Event.numTicketAck );
            System.out.println("numTableAssign: " + Event.numTableAssign);
            System.out.println("numCheckOut: " + Event.numCheckOut);
            System.out.println("numQueueTooLong: " + Event.numQueueTooLong);

            try {
                new Report("./report/"+Name);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new ShutDown());
        new Test("127.0.0.1", 54321);
    }

}
