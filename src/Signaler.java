
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Collections;
import java.util.Collections.synchronizedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hayden
 */
public class Signaler {
    
    private OutputLogger logger;
    private int sigNum;
    private List<Thread> threadQueue;
    
    public Signaler(OutputLogger l) {
        this.sigNum = 1;
        this.logger = l;
        threadQueue = Collections.synchronizedList(new ArrayList<Thread>());
    }
    
    public synchronized void Wait(int agent){
    synchronized (sigNum) {
        sigNum--;
    }
    logger.logWait(agent, sigNum);
	if(sigNum<0){
        synchronized (threadQueue) {
            threadQueue.add(Thread.currentThread());
        }
	    try {
		Thread.currentThread().wait();
	    } catch (InterruptedException ex) {
		Logger.getLogger(Signaler.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    public synchronized void Signal(int agent){
	synchronized (sigNum) {
        if (sigNum < 1)
            sigNum++;
    }
    logger.logSignal(agent, sigNum);
	if(sigNum<=0){
        synchronized (threadQueue) {
            threadQueue.get(0).notify();
            threadQueue.remove(0);
        }
	}
    }
    
    public int sigNum() {
        synchronized (sigNum) {
            return sigNum;
        }
    }
    
}
