/**
 * The Queue class which maintains a queue of requests to be processed by the aircraft. 
 * It is processed by calling a tick() function that returns false if nothing can be processed otherwise true.
 * The requests are processed in separate threads to allow parallelism for request with the same priority
 */

/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
 
public class Queue {
	private List<Request> list;
	private Aircraft aircraft;
	private OutputLogger logger;
    private Signaler semaphore;
    private int lastTickDuration;
	
	public Queue(Aircraft ac, OutputLogger l) {
		list = new ArrayList<Request>();
		aircraft = ac;
		logger = l;
        semaphore = new Signaler(l);
        lastTickDuration = 0;
	}
		
	public boolean tick() {
        // processes 1 request or x requests of the same priority and 
        // pushes unprocessable request to the back of the queue
		// returns: return false if nothing was processed
		if (list.isEmpty()){
			return false;
		}
		
		List<Request> batchList = calculateProcessBatch();
        for(Request r: batchList){
            list.remove(r);
        }
        
        lastTickDuration = 0;
        for(Request r: batchList){
            lastTickDuration = lastTickDuration + r.seatNum;
        }
		
		if (!batchList.isEmpty()){
			logger.logAdmitBatch(batchList);
		}
		
        List<ProcessThread> threadList = new ArrayList<ProcessThread>();
        for (Request r : batchList) {
            ProcessThread p = new ProcessThread(r, aircraft, semaphore, logger);
            threadList.add(p);
            p.start();
        }
        
        for (ProcessThread p : threadList) {
            try {
                p.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread Interrupted");
            }
        }
                
        // return false if nothing was processed
        if (batchList.size() == 0)
            return false;
        else 
            return true;
	}
    
    private List<Request> calculateProcessBatch() {
        // returns a list of equivalent requests that are able to be processed in the aircraft
        // choosing the request or requests with the highest priority
        List<Request> batchList = new ArrayList<Request>();
        
        // find the highest priority request that can be processed
        int r0pos = 0;
        for(r0pos=0;r0pos<list.size();r0pos++) {
            Request r0 = list.get(r0pos);
            if (r0.rORc == reqType.CANCEL) {
                batchList.add(r0);
                break;
            } else if (r0.rORc == reqType.REQUEST && aircraft.seatsNotTaken(r0.type) >= r0.seatNum) {
                batchList.add(r0);
                break;
            }
        }
        
        // add equivalent requests that can be processed
        if (batchList.size() != 0) {
            Request r0 = batchList.get(0);
            if (r0.rORc == reqType.CANCEL) {
                int i = r0pos + 1;
                while (i < list.size()) {
                    Request rx = list.get(i);
                    if (!(rx.rORc == r0.rORc && rx.type == r0.type && rx.seatNum == r0.seatNum)) {
                        // the request isn't equivalent
                        break;
                    } else {
                        batchList.add(rx);
                    }
                    i++;
                }
            } else if (r0.rORc == reqType.REQUEST) {
                // loop from the position of r0+1 to list.size() checking for equivalents
                int spareSeats = aircraft.seatsNotTaken(r0.type) - r0.seatNum;
                int i = r0pos + 1;
                while (i < list.size() && spareSeats > 0) {
                    Request rx = list.get(i);
                    if (!(rx.rORc == r0.rORc && rx.type == r0.type && rx.seatNum == r0.seatNum)) {
                        // the request isn't equivalent, there won't be more, stop searching
                        break;
                    }
                    spareSeats = spareSeats - rx.seatNum;
                    if (spareSeats >= 0){				
                        batchList.add(rx);
                    }
                    i++;
                }
            }
        }
        
        return batchList;
    }
    
    public void push(Request r) {
        // push cancel request to the front of the queue to reduce sorting (probably should be removed)
        // after pushing, sort the queue to make sure priority order is maintained
        if (r.rORc == reqType.CANCEL) {
			list.add(0, r);
		}else{
		    list.add(r);
		}
	}
    
    public void logFailures() {
        if (list.size() == 0)
            return;
            
        logger.logAdmitBatchFailure();
        for (Request r : list) {
            logger.logReservationFailure(r.agent);
        }
    }
	
	private void pop() {
		if (list.size() > 0) {
			list.remove(0);
		}
	}
	
	public int size() {
		return list.size();
	}
    
    public int getLastTickDuration() {
        return lastTickDuration;
    }
}