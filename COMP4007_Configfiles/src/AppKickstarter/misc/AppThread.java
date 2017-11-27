package AppKickstarter.misc;

import AppKickstarter.Main;
import java.util.logging.Logger;


//======================================================================
// AppThread
public abstract class AppThread implements Runnable {
    protected String id;
    protected Main main;
    protected MBox mbox = null;
    protected Logger log = null;

    //------------------------------------------------------------
    // AppThread
    public AppThread(String id, Main main) {
	this.id = id;
	this.main = main;
	log = main.getLogger();
	mbox = new MBox(id, log);
	main.regThread(this);
	log.fine(id + ": created!");
    } // AppThread


    //------------------------------------------------------------
    // getters
    public MBox getMBox() { return mbox; }
    public String getID() { return id; }
} // AppThread
