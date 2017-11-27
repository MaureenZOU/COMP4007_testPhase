package AppKickstarter.myThreads;

import AppKickstarter.misc.*;
import AppKickstarter.Main;
import AppKickstarter.timer.Timer;

import java.util.ArrayList;


public class Table extends AppThread{

    public int tid;
    public int seats;
    boolean isEmpty, isWaitingSendMsg;
    Ticket theTicket;

    //constructor, copy from ThreadA
    //id is for system reference totally how many threads, tid is for transmission
    public Table(String id, Main main, int tid, int seats) {
        super(id, main);
        this.tid = tid;
        this.seats = seats;
        this.isEmpty = true;
        this.isWaitingSendMsg = false;
    }


    public void run() {
        log.info("table " + id  + " created; tid = " + Integer.toString(tid));
//        main.getEmptyTableList(seats).add(this);
        main.addToEmptyTableList(this);
        checkQueue(seats);

        for (boolean quit = false; !quit; ) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");

            switch (msg.getType()) {
                case CheckOut:
                    log.info(tid + ": Client at Table " + tid + " has CheckOut.");
                    isEmpty = true;
                    main.addToEmptyTableList(this);
                    checkQueue(seats);
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



    public void checkQueue(int nPersons) {
        ArrayList<Table> etl;

        long startTime;

        //cheat
        int printnum = 0;

        while(isEmpty){
//            System.out.println("table " + tid + " still running!");

            do {
                //keep checking
                try {
                    Thread.currentThread().sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(main.getQueue(nPersons).isEmpty());

//            theTicket = main.removeFromQueue(nPersons);
            etl = main.getEmptyTableList(nPersons);

            theTicket = main.getQueue(nPersons).get(0);

            if(etl.get(0) == this && !theTicket.isACKed){
            	main.removeFromQueue(theTicket);
                System.out.println(tid + " is the first in etl!");
                main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, "TicketCall: " + theTicket.ticketNo + " " + tid + "\r\n"));
                isWaitingSendMsg = true;
                
                while (isWaitingSendMsg) {
					try {
						Thread.currentThread().sleep(5);
						System.out.println(tid + " wait for sending Msg!");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(tid + " wait for the client!");                
                
                startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime <= 100)){
                    try {
                        Thread.currentThread().sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(theTicket.isACKed) {
                        System.out.println(tid + " got the client!");
//                            theTicket.isACKed = false;
                        isEmpty = false;
                        main.removeFromEmptyTableList(this);
                        break;
                    }
                }

                //test
//                try {
//                    Thread.currentThread().sleep(100);
//                    isEmpty = false;
//                    main.removeFromEmptyTableList(this);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


            }
//            if(etl.get(0) == this && printnum > 0){
//                System.out.println(tid + " unfortunately time out!");
//                printnum++;
//            }
        }



    }









}
