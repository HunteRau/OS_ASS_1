/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
 
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
        signaler.Wait(request.agent);
        
        // process the request
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
        logger.logExit(request.agent);
        
        // signal exiting so others can come
        signaler.Signal(request.agent);
    }
 }