package AppKickstarter.testUtil;

import AppKickstarter.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * Created by maureen on 11/27/17.
 */
public class Report {
    int clientNum;
    int recordNum;
    String filename;

    public Report(String filename) throws FileNotFoundException {
        Collections.sort(Test.records);
        this.clientNum = Test.clients.size();
        this.recordNum = Test.records.size();
        this.filename = filename;
        this.writeClient(filename);
    }

    public int getInt(String num){
        String out = num.replaceAll("^0*", "");
        return Integer.parseInt(num);
    }

    public void writeClient(String filename) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(filename));
        StringBuilder sb = new StringBuilder();
        String[][] table = new String[clientNum][recordNum];
        for(int i=0; i < this.recordNum; i++){
            for(int j=0; j<this.clientNum; j++){
                table[j][i] = "";
            }
        }

        for(int i = 0; i < this.recordNum; i++){
            table[this.getInt(Test.records.get(i).clientId)][i] = Test.records.get(i).msg;
        }


        for(int i=0; i < this.clientNum; i++ ){
            sb.append("Client " + i);
            sb.append(',');
        }
        sb.append('\n');

        for(int i=0; i < this.recordNum; i++){
            for(int j=0; j<this.clientNum; j++){
                sb.append(table[j][i]);
                sb.append(',');
            }
            sb.append("\n");
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("Finish Report");
    }

}
