/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */
 
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Collections;
 
public class Signaler {
    
    private OutputLogger logger;
    private int sigNum;
    private final Object sigNum_lock = new Object();
    private List<Thread> threadQueue;
    
    public Signaler(OutputLogger l) {
        this.sigNum = 1;
        this.logger = l;
        threadQueue = Collections.synchronizedList(new ArrayList<Thread>());
    }
    
    public synchronized boolean Wait(int agent){
        synchronized (sigNum_lock) {
            sigNum--;
            logger.logWait(agent, sigNum);
            if(sigNum<0){
                threadQueue.add(Thread.currentThread());
                return false;
            }
            return true;
        }
    }
    
    public synchronized void Signal(int agent){
        synchronized (sigNum_lock) {
            if (sigNum < 1)
                sigNum++;
            logger.logSignal(agent, sigNum);
            synchronized (threadQueue) {
                if(sigNum<=0 || !threadQueue.isEmpty()){
                    synchronized(threadQueue.get(0)){
                        threadQueue.get(0).notify();
                        threadQueue.remove(0);
                    }
                }
            }
        }
    }
    
    public int sigNum() {
        synchronized (sigNum_lock) {
            return sigNum;
        }
    }
}
