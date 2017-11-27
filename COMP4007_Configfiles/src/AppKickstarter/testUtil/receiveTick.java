package AppKickstarter.testUtil;

import java.io.IOException;

/**
 * Created by maureen on 11/27/17.
 */
public class receiveTick implements Runnable{
    private Messager messager;

    public receiveTick(Messager messager){
        this.messager = messager;
    }

    public void run(){
        int i = 0;

        while(true){

            String msg = null;
            try {
                msg = messager.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(new TickHandler(1, i, msg)).start();
            i = i + 1;

        }

    }

}
