package AppKickstarter.myThreads;

import AppKickstarter.misc.*;
import AppKickstarter.Main;

import java.io.*;


public class OutThread extends AppThread{


    DataOutputStream out;

    public OutThread(String id, Main main, DataOutputStream out){
        super(id, main);
        this.out = out;

    }


    public void run(){

        log.info(id + ": out thread starting...");

        for (boolean quit = false; !quit;) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");

            switch (msg.getType()) {
                case OutMsg:
                    try {
                        out.writeBytes(msg.getDetails());
                        out.flush();
                        if (msg.getDetails().contains("TicketCall")){
                        	//TicketCall should notify table too
                        	int tableNo = Integer.parseInt(msg.getDetails().split("\r")[0].split(" ")[2]);
//                        	System.out.println("123123TicketCall: " + msg.getDetails() + tableNo);
                        	main.tables[tableNo].isWaitingSendMsg = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case Terminate:
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
