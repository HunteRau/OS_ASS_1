/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aaron
 */
 
 class ProcessThread extends Thread {
	private Request request;
	private Signaler signaler;
	private OutputLogger logger;
    private Aircraft aircraft;
	public void ProcessThread(Request r, Aircraft a, Signaler s, OutputLogger l) {
        this.request = new Request(r);
        this.signaler = s;
        this.logger = l;
        this.aircraft = a;
	}
    
    public void run() {
        // ask to access the aircraft
        int simNum = signaler.Wait();
        
        // process the request
        logger.logEnter(request.agent);
        if (request.rORc == reqType.REQUEST) {
            if (aircraft.reserve(request.type, seatNum)) 
                logger.logRequest(request);
            else
                logger.logReservationFailure(request.agent);
        } else (request.rORc == reqType.CANCEL) {
            aircraft.cancel(request.type, seatNum);
            logger.logRequest(request)
        }
        logger.logExit(request.agent);
        
        // signal exiting so others can come
        simNum = signaler.Signal();
    }
 }