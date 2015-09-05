
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private int sigNum=0;
    ArrayList<Thread> threadQueue = new ArrayList<Thread>();
    
    
    public void Wait(){
	sigNum--;
	if(sigNum<=0){
	    threadQueue.add(Thread.currentThread());
	    try {
		Thread.currentThread().wait();
	    } catch (InterruptedException ex) {
		Logger.getLogger(Signaler.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    public int Signal(){
	sigNum++;
	if(sigNum<=0){
	    threadQueue.get(0).notify();
	    threadQueue.remove(0);
	}
	if(sigNum>1){
	    sigNum=1;
	}
	return sigNum;
    }
    
}
