package AppKickstarter.myThreads;

import AppKickstarter.misc.*;
import AppKickstarter.Main;
import java.io.*;

public class InThread extends AppThread {

    DataInputStream in;
    String inmsg;

    public InThread(String id, Main main, DataInputStream in){
        super(id, main);
        this.in = in;
    }


    public void run(){
//        System.out.println("inthread starting.");
        main.getLogger().info("in thread starting...");
        while(true){
            //receive from instream
            try {
                int length = in.readInt();
                byte b[] = new byte[length];
                in.readFully(b);
                inmsg = new String(b,"UTF-8");
            } catch(IOException ex){
                ex.printStackTrace();
            }

            //send inmsg to mt mbox
            main.mt.getMBox().send(new Msg(id, null, Msg.Type.InMsg, inmsg));
        }


    }
}
