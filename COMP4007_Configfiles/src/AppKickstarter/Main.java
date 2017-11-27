package AppKickstarter;

import AppKickstarter.misc.*;
import AppKickstarter.myThreads.*;
import AppKickstarter.timer.Timer;


import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Main extends AppKickstarter{
    public String ServerIP;
    public int portNum;
    public Table[] tables = new Table[32];
    public Socket cSocket;

    DataOutputStream outToClient;
    DataInputStream inFromClient;

    public InThread in;
    public OutThread out;
    public MainThread mt;

    private static ArrayList<Ticket> queue1 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue2 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue3 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue4 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue5 = new ArrayList<Ticket>();
    private static ArrayList<Table> etl1 = new ArrayList<Table>();
    private static ArrayList<Table> etl2 = new ArrayList<Table>();
    private static ArrayList<Table> etl3 = new ArrayList<Table>();
    private static ArrayList<Table> etl4 = new ArrayList<Table>();
    private static ArrayList<Table> etl5 = new ArrayList<Table>();

    public Main(String id, String ServerIP, int portNum) throws IOException {
        super(id);
        this.ServerIP = ServerIP;
        this.portNum = portNum;

        mt = new MainThread("mt", this);
        new Thread(mt).start();

        for (int i = 0; i < 10; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 2);
            new Thread(tables[i]).start();
        }
        for (int i = 10; i < 20; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 4);
            new Thread(tables[i]).start();
        }
        for (int i = 20; i < 28; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 6);
            new Thread(tables[i]).start();
        }
        for (int i = 28; i < 30; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 8);
            new Thread(tables[i]).start();
        }
        for (int i = 30; i < 32; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 10);
            new Thread(tables[i]).start();
        }

        ServerSocket sSocket = new ServerSocket(portNum);
        System.out.println(String.format("Listening at port %d... ", portNum));

        cSocket = sSocket.accept();
        outToClient=new DataOutputStream(new BufferedOutputStream(cSocket.getOutputStream()));
        inFromClient=new DataInputStream(new BufferedInputStream(cSocket.getInputStream()));

        in = new InThread("in", this, inFromClient);
        out = new OutThread("out", this, outToClient);
        new Thread(in).start();
        new Thread(out).start();

    }

    public ArrayList<Table> getEmptyTableList(int nPersons) {
        if (nPersons <= 2) {
            return etl1;
        } else if (nPersons <= 4) {
            return etl2;
        } else if (nPersons <= 6) {
            return etl3;
        } else if (nPersons <= 8) {
            return etl4;
        } else {
            return etl5;
        }
    }

    public synchronized void addToEmptyTableList(Table table) {
        int seats = table.seats;
        if (seats <= 2) {
            etl1.add(table);
        } else if (seats <= 4) {
            etl2.add(table);
        } else if (seats <= 6) {
            etl3.add(table);
        } else if (seats <= 8) {
            etl4.add(table);
        } else {
            etl5.add(table);
        }
//        System.out.println("empty table added.");
    }

    public synchronized void removeFromEmptyTableList(Table table) {
        int seats = table.seats;
        if (seats <= 2) {
            etl1.remove(table);
            etl1.trimToSize();
        } else if (seats <= 4) {
            etl2.remove(table);
            etl2.trimToSize();
        } else if (seats <= 6) {
            etl3.remove(table);
            etl3.trimToSize();
        } else if (seats <= 8) {
            etl4.remove(table);
            etl4.trimToSize();
        } else {
            etl5.remove(table);
            etl5.trimToSize();
        }

        System.out.println(table.tid + " removed.");
    }


    public ArrayList<Ticket> getQueue(int nPersons) {

        if (nPersons <= 2) {
            return queue1;
        } else if (nPersons <= 4) {
            return queue2;
        } else if (nPersons <= 6) {
            return queue3;
        } else if (nPersons <= 8) {
            return queue4;
        } else {
            return queue5;
        }
    }

    public synchronized void removeFromQueue(Ticket t) {

        System.out.println("ticket " + t.ticketNo + " removed from queue.");
        ArrayList<Ticket> theQueue = getQueue(t.nPersons);
        theQueue.remove(t);
        theQueue.trimToSize();
    }

    public synchronized void addToQueue(Ticket ticket) {
        int nPersons = ticket.nPersons;
        getQueue(nPersons).add(ticket);
        System.out.println("ticket No. " + ticket.ticketNo + " of " + nPersons + " added to queue.");
//        System.out.println("current queue size: " + getQueue(nPersons).size());

    }

    public boolean checkTooLong(int nPersons) {
        boolean isTooLong;
//        if (queues[nPersons-1].size() > 10)
        if (getQueue(nPersons).size()>=10){
            isTooLong = true;
        } else {
            isTooLong = false;
        }
        return isTooLong;
    }



    public void startApp() {
        // start our application
        log.info("");
        log.info("");
        log.info("============================================================");
        log.info(id + ": Application Starting...");


        timer = new Timer("timer", this);
        new Thread(timer).start();
    }



    public static void main(String[] args) throws IOException {
        Main main = new Main("sjx", "127.0.0.1", 54321);
        main.startApp();
    }


}
