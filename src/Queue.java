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
 
public class Queue {
	private List<Request> list;
	private Aircraft aircraft;
	private OutputLogger logger;
    private Signaler semaphore;
	
	public Queue(Aircraft ac, OutputLogger l) {
		list = new ArrayList<Request>();
		aircraft = ac;
		logger = l;
        semaphore = new Signaler(l);
	}
	
	public void push(Request r) {
        // push cancel request to the front of the queue so that seat availability is
        // promptly reflected and less reservation requests fail.
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
	
	public boolean tick() {
        // processes 1 request or x requests of the same priority and 
        // pushes unprocessable request to the back of the queue
		// returns: true if a request is still processable, else false
		if (list.isEmpty()){
			return false;
		}
		
		List<Request> batchList = calculateProcessBatch();
        for(Request r: batchList){
            list.remove(r);
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
        
        // push unprocessable requests to the end of the queue
        int iter = 0;
        while(iter<list.size()){
            Request r0 = list.get(0);
            if(r0.rORc == reqType.CANCEL){
                break;
            }
            if (aircraft.seatsNotTaken(r0.type) < r0.seatNum) {
                this.pop();
                this.push(r0);
            }else{
                break;
            }
            iter++;
        }
        
        // if we stepped over the whole list; there isn't any processable requests
        if (iter == list.size())
            return false;
        else 
            return true;
	}
    
    private List<Request> calculateProcessBatch() {
        // returns a list of equivalent requests that are able to be processed in the aircraft
        
        List<Request> batchList = new ArrayList<Request>();
        Request r0 = list.get(0);
        if (r0.rORc == reqType.REQUEST) {		
            if (aircraft.seatsNotTaken(r0.type) >= r0.seatNum) {
                batchList.add(r0);
                // search for more equivalent requests to put in the same batch
                int spareSeats = aircraft.seatsNotTaken(r0.type) - r0.seatNum;
                int i = 1;
                while (i < list.size() && spareSeats > 0) {
                    Request rx = list.get(i);
                    if (!(rx.rORc == r0.rORc && rx.type == r0.type && rx.seatNum == r0.seatNum)) {
                        // the request isn't equivalent
                        break;
                    }
                    spareSeats = spareSeats - rx.seatNum;
                    if (spareSeats >= 0){				
                        batchList.add(rx);
                    }
                    i++;
                }
            }
        } else if (r0.rORc == reqType.CANCEL) {
			batchList.add(r0);
			// search for more cancel requests to put in the same batch
			int i = 1;
			while (i < list.size()) {
				Request rx = list.get(i);
				if (!(rx.rORc == r0.rORc)) {
					break;
				}				
				batchList.add(rx);
                i++;
			}
		}
        return batchList;
    }
}