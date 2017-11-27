package AppKickstarter.testUtil;

import java.io.IOException;

/**
 * Created by maureen on 11/27/17.
 */
public class TickHandler implements Runnable{
    private int type; //0 - sendOut, 1 - receiveIn
    private int time;
    private String inMsg;
    private Messager messager;

    public TickHandler(int type, int time, Messager messager){
        this.type = type;
        this.time = time;
        this.messager = messager;
    }

    public TickHandler(int type, int time, String inMsg){
        this.type = type;
        this.time = time;
        this.inMsg = inMsg;
    }


    public void sendOut() throws IOException {
        while(EventHandler.outEvent.size() > 0 && EventHandler.outEvent.get(0).time <= this.time){
            Event event = EventHandler.outEvent.remove(0);
            messager.send(event.msg);
        }
    }

    public void receiveIn() throws IOException {
        String[] data = inMsg.split(": ");
        String action = data[0];
        String[] contents = null;
        if(data.length>=2) {
            contents = data[1].split(" ");
        }
        new Thread(new Event(action, contents)).start();
    }

    @Override
    public void run() {
        if(this.type == 0){
            try {
                this.sendOut();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                this.receiveIn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
