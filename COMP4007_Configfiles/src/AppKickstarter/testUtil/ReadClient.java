package AppKickstarter.testUtil;

import AppKickstarter.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by maureen on 11/27/17.
 */
public class ReadClient {
    String filename;

    public ReadClient(String filename){
        this.filename = filename;
        String csvFile = this.filename;
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] content = line.split(cvsSplitBy);
                if(i > 0){
                    Client client = new Client(content[0], Integer.parseInt(content[1]), Integer.parseInt(content[2]), Integer.parseInt(content[3]), Integer.parseInt(content[4]));
                    Test.clients.add(client);
                    new Thread(new Event("TicketReq", client)).start();

                    System.out.println(client);
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
