package AppKickstarter.testUtil;

import AppKickstarter.Test;

import java.util.Collections;

/**
 * Created by maureen on 11/27/17.
 */
public class Event implements Runnable, Comparable<Event>{
    String type;
    Client client;
    int time;
    String msg;
    String[] contents;

    public static int numTicketReq = 0;
    public static int numTicketRep = 0;
    public static int numTicketCall = 0;
    public static int numTicketAck = 0;
    public static int numTableAssign = 0;
    public static int numCheckOut = 0;
    public static int numQueueTooLong = 0;

    public Event(String type, Client client){
        this.client = client;
        this.type = type;
    }

    public Event(String type, String[] contents){
        this.type = type;
        this.contents = contents;
    }

    public void ticketReq(){ //0
        this.time = this.client.reqTime;
        this.msg = "TicketReq: " + this.client.clientId + " " + this.client.numPerson;

        synchronized (EventHandler.outEvent) {
            EventHandler.outEvent.add(this);
            Collections.sort(EventHandler.outEvent);
        }

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "TicketReq"));
        }

        numTicketReq ++;
    }

    public void ticketRep(){ //1
        String clientId = this.contents[0];
        int nPersons = Integer.parseInt(this.contents[1]);
        int ticketNum = Integer.parseInt(this.contents[2]);
        this.client = Test.getClientId(clientId);
        this.client.ticketNum =ticketNum;

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "TicketRep"));
        }
        numTicketRep ++;
    }

    public void ticketCall(){ //2
        int ticketNum = Integer.parseInt(contents[0]);
        int tableNum = Integer.parseInt(contents[1]);
        this.client = Test.getClientTicket(ticketNum);

        if(this.client == null){
            return;
        }

        this.client.tableNum = tableNum;


        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "TicketCall"));
        }

        numTicketCall ++;
        this.ticketAck();
    }

    public void tableAssign(){ //3
        int ticketNum = Integer.parseInt(contents[0]);
        int tableNum = Integer.parseInt(contents[1]);
        this.client = Test.getClientTicket(ticketNum);
        this.checkOut();

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "tableAssign"));
        }

        numTableAssign ++;
    }

    public void checkOut(){
        this.client.estimateCheckOutTime = sendTick.global_sendTime + this.client.checkoutTime;
        this.time = this.client.estimateCheckOutTime;
        this.msg = "CheckOut: " + this.client.tableNum + " " + this.client.totalSpending;

        synchronized (EventHandler.outEvent) {
            EventHandler.outEvent.add(this);
            Collections.sort(EventHandler.outEvent);
        }

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "checkOut"));
        }

        numCheckOut ++;
    }

    public void queueToolLong(){
        String clientID = this.contents[0];
        this.client = Test.getClientId(clientID);

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "queueToolLong"));
        }

        numQueueTooLong ++;
    }

    public void ticketAck(){
        this.time = -1;
        this.msg = "TicketAck: " + this.client.ticketNum + " " + this.client.tableNum + " " + this.client.numPerson;
        synchronized (EventHandler.outEvent) {
            EventHandler.outEvent.add(0, this);
        }

        synchronized (Test.records) {
            Test.records.add(new Record(this.client.clientId, SystemTime.global_Timer, "ticketAck"));
        }
        numTicketAck ++;
    }


    @Override
    public void run() {

        switch (this.type){
            case "TicketReq":
                this.ticketReq();
                break;
            case "TicketRep":
                this.ticketRep();
                break;
            case "TicketCall":
                this.ticketCall();
                break;
            case "TicketAck":
                this.ticketAck();
                break;
            case "TableAssign":
                this.tableAssign();
                break;
            case "CheckOut":
                this.checkOut();
                break;
            case "QueueTooLong":
                this.queueToolLong();
                break;
        }


    }

    @Override
    public int compareTo(Event o) {
        return this.time - o.time;
    }
}
