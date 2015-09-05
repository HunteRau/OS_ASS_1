/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aaron
 */
 
 class ProcessQueue extends Thread {
	private Request request;
	private Signaler signaler;
	private OutputLogger logger;
    private Aircraft aircraft;
	public ProcessThread(Request r, Aircraft a, Signaler s, OutputLogger l) {
		this.request = Request(r);
        this.signaler = s;
        this.logger = l;
        this.aircraft = a;
	}
    
    public void run() {
        // ask to access the aircraft
        int semNum;
        logger.logWait(request.agent, semNum);
        
        // decide on what to do
        logger.logEnter(request.agent);
        if (request.rORc == reqType.REQUEST) {
            aircraft.reserve(request.type, seatNum);
        } else (request.rORc == reqType.CANCEL) {
            aircraft.cancel(request.type, seatNum);
        }
        logger.logExit(request.agent);
        
        // signal exiting so others can come
        semNum = signaler.signal();
        logger.logSignal(
    }
 }