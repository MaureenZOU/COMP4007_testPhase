package AppKickstarter.testUtil;

/**
 * Created by maureen on 11/23/17.
 */
public class Client {
    String clientId;
    int numPerson;
    int ticketNum = -1;
    int tableNum = -1;
    int totalSpending;
    int reqTime;
    int checkoutTime;
    int estimateCheckOutTime = -1;

    public Client(String clientId, int numPerson, int totalSpending, int reqTime, int checkoutTime){
        this.clientId = clientId;
        this.numPerson = numPerson;
        this.totalSpending = totalSpending;
        this.reqTime = reqTime;
        this.checkoutTime = checkoutTime;
    }

    public String toString(){
        return "clientId: " + clientId + " numPerson: " + numPerson + " totalSpending: " + totalSpending + " reqTime: " + reqTime + " checkOutTime: " + checkoutTime;
    }

    public String getClientId(){
        return this.clientId;
    }

    public int getTicketNum(){
        return this.ticketNum;
    }

}
