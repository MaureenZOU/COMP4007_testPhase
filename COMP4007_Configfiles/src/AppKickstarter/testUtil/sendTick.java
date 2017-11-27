package AppKickstarter.testUtil;
/**
 * Created by maureen on 11/27/17.
 */
public class sendTick implements Runnable{
    private Messager messager;
    public static int global_sendTime;

    public sendTick(Messager messager){
        this.messager = messager;
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new TickHandler(0, global_sendTime, messager)).start();

            global_sendTime ++;
        }

    }
}
