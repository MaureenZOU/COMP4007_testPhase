package AppKickstarter.testUtil;

/**
 * Created by maureen on 11/27/17.
 */
public class SystemTime {
    public static int global_Timer;

    public void run(){
        while(true){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            global_Timer ++;
        }
    }
}
