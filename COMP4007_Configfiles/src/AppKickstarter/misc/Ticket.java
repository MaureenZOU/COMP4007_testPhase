package AppKickstarter.misc;

public class Ticket {
    public int ticketNo;
    public boolean isACKed;
    public String cid;
    public int nPersons;
    public Ticket(int ticketNo, String cid, int nPersons, boolean isACKed){
        this.ticketNo = ticketNo;
        this.isACKed = isACKed;
        this.cid = cid;
        this.nPersons = nPersons;
    }
}
