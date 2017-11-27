package AppKickstarter.testUtil;

/**
 * Created by maureen on 11/27/17.
 */
public class Record implements Comparable<Record>{
    String clientId;
    int time;
    String msg;

    public Record(String clientId, int time, String msg){
        this.clientId = clientId;
        this.time = time;
        this.msg = msg;
    }

    @Override
    public int compareTo(Record o) {
        return this.time - o.time;
    }
}
