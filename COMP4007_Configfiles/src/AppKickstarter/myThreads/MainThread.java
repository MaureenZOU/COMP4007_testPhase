package AppKickstarter.myThreads;


import AppKickstarter.misc.*;
import AppKickstarter.Main;

import java.util.ArrayList;

public class MainThread extends AppThread {

    String[] instrs;
//    String instr;
    String intype;
    String details;
    String cid;
    int nPersons;
//    int ticketNo = 0;
    int ticketAckNo;
    int ticketIssueNo = 0;
    int tid;
    String tidstr;
    boolean isTooLong;

    public MainThread(String id, Main main){
        super(id, main);
    }

    public void run(){
        log.info(id + ": main thread starting...");

        for (boolean quit = false; !quit;) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");
            
            ArrayList<Ticket> temp = main.getQueue(2);
//            System.out.println("+++++++++++++++");
//            for(Ticket t:temp) {
//            	System.out.println(t.cid + " " + t.ticketNo);
//            }

            instrs = msg.getDetails().split(": ");
            intype = instrs[0];
            if(instrs.length>=2) {
                instrs = instrs[1].split(" ");
            } else {
                //for WakeAndCall,
                //instr = instrs[1];
            }

            switch (intype) {
                case "TicketReq":
                    cid = instrs[0];
                    nPersons = Integer.parseInt(instrs[1]);

                    ticketIssueNo++;
                    Ticket t = new Ticket(ticketIssueNo, cid, nPersons, false);

                    System.out.println("+++++++" + ticketIssueNo + "---------" + t.ticketNo);


                    isTooLong=main.checkTooLong(nPersons);
                    if(!isTooLong){
                        //main.getQueue(nPersons).add(t);
                        main.addToQueue(t);

                        System.out.println("---------" + ticketIssueNo + "---------" + t.ticketNo);
                        //TicketRep
                        details = "TicketRep: " + cid + " " + nPersons + " " + ticketIssueNo + "\r\n";
                        main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    }else{

                        details = "QueueTooLong: " + cid + " " + nPersons+ "\r\n";
                        main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    }



                    break;

//                case "WakeAndCall":
//                    tid = Integer.parseInt(instrs[0]);
//                    nPersons = Integer.parseInt(instrs[1]);
//
//
//                    //TicketCall
//                    details = "TicketCall: ";
//                    main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
//                    break;

                case "TicketAck":
                    ticketAckNo = Integer.parseInt(instrs[0]);
                    tidstr = instrs[1];
                    nPersons = Integer.parseInt(instrs[2]);

                    main.tables[Integer.parseInt(tidstr)].theTicket.isACKed = true;
//                    ArrayList<Ticket> theQueue = main.getQueue(nPersons);
//                    for (Ticket ticket : theQueue) {
//                        if(ticket.ticketNo == ticketNo){
//                            ticket.isACKed = true;
//                            System.out.println("ticket " + ticketNo + " ACK set true!");
//
//                            break;
//                        }
//                    }

                    //TableAssign
                    details = "TableAssign: " + ticketAckNo + " " + tidstr + "\r\n";
                    main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    break;

                case "CheckOut":
                    tidstr = instrs[0];
                    //manipulate empty table list
                    //send nothing
                    details = "Client CheckOut!" + "\r\n";
                    main.getThread(tidstr).getMBox().send(new Msg(id, null, Msg.Type.CheckOut, details));
                    break;

                case "Terminate":
                    quit = true;
                    break;

                default:
                    log.severe(id + ": unknown message type!!");
                    break;
            }

        }

        // declaring our departure
        main.unregThread(this);
        log.info(id + ": terminating...");
    }
}
