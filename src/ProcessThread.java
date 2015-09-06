/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Aaron
 */
 
 class ProcessThread extends Thread {
	private Request request;
	private Signaler signaler;
	private OutputLogger logger;
    private Aircraft aircraft;
    
	public ProcessThread(Request r, Aircraft a, Signaler s, OutputLogger l) {
        this.request = new Request(r);
        this.signaler = s;
        this.logger = l;
        this.aircraft = a;
	}
    
    public void run() {
        // ask to access the aircraft
        Boolean w = signaler.Wait(request.agent);
        
        if(!w){
            synchronized(Thread.currentThread()){
                try {
                    Thread.currentThread().wait();
                } catch (Exception ex) {
                    Logger.getLogger(Signaler.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ex.getLocalizedMessage());
                }
            }
        }
        
            try {
                // process the request

                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        logger.logEnter(request.agent);
        if (request.rORc == reqType.REQUEST) {
            if (aircraft.reserve(request.type, request.seatNum)) 
                logger.logRequest(request);
            else
                logger.logReservationFailure(request.agent);
        } else if (request.rORc == reqType.CANCEL) {
            aircraft.cancel(request.type, request.seatNum);
            logger.logRequest(request);
        }
        
        // signal exiting so others can come
        signaler.Signal(request.agent);
        logger.logExit(request.agent);
    }
 }