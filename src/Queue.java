
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aaron
 */
 
// TODO:    - check to make sure that admit failures are logged
//          - make sure that request that can't be processed are logged
 
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
		if (r.rORc == reqType.CANCEL) {
			// push the cancel to the front of the queue
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
	
	// return true if at least 1 request is processed
	// return false if 0 requests are processed
	public boolean tick() {
		
		if (list.isEmpty()){
			return false;
		}
		
		// find out what the next batch is
		Request r0 = list.get(0);
		
		List<Request> batchList = new ArrayList<Request>();
                if (r0.rORc == reqType.REQUEST) {
                    // is there room?			
                    if (aircraft.seatsNotTaken(r0.type) >= r0.seatNum) {
                        batchList.add(r0);
                        
                        // is there more request we can fit in this batch
                        int spareSeats = aircraft.seatsNotTaken(r0.type) - r0.seatNum;
                        int i = 1;
                        while (i < list.size() && spareSeats > 0) {
                            Request rx = list.get(i);
                            if (!(rx.rORc == r0.rORc && rx.type == r0.type && rx.seatNum == r0.seatNum)) {
                                break;
                            }
                            spareSeats = spareSeats - rx.seatNum;
                            
                            if (spareSeats >= 0){				
                                batchList.add(rx);
                            }
                            i++;
                        }
                        for(Request r: batchList){
                            list.remove(r);
                        }
                    }else{                            
                        // push the failed request to the end of the queue
                        this.pop();
                        this.push(r0);
                    }
                } else if (r0.rORc == reqType.CANCEL) {
			batchList.add(r0);
			
			// find more requests that we can fit in this batch
			int i = 1;
			while (i < list.size()) {
				Request rx = list.get(i);
				if (!(rx.rORc == r0.rORc)) {
					break;
				}				

				batchList.add(rx);
                i++;
			}
                        for(Request r: batchList){
                            list.remove(r);
                        }
		}
		
		// check if there is any requests to process
		if (batchList.isEmpty()){
			return false;
		}else{
			logger.logAdmitBatch(batchList);
		}
		
        // create threads
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
        
        
        int over = 0;
        while(over<list.size()){
            r0 = list.get(0);
            if(r0.rORc == reqType.CANCEL){
                break;
            }
            if (aircraft.seatsNotTaken(r0.type) < r0.seatNum) {
                this.pop();
                this.push(r0);
            }else{
                break;
            }
            over++;
        }
        
	return true;
	}
}