/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */
 
 import java.util.List;
 
 class OutputLogger {
	public void logAdmitBatch(List<Request> requests) {
		// log = "Admit a batch (Agent 3, Agent 4 ...)"
		StringBuilder builder = new StringBuilder();
		for (Request  r : requests) {
			builder.append("Agent " + Integer.toString(r.agent) + ", ");
		}
		builder.delete(builder.length()-2, builder.length());
		synchronized (this) {    
            System.out.println("Admit a batch (" + builder.toString() + ")");
        }
	}
	
	public void logWait(int agent, int semaphoreState) {
        // log = "Agent x executes wait operation, semaphore = y"
        synchronized (this) {
            System.out.println("Agent " + Integer.toString(agent) 
                + " executes wait operation, semaphore = "
                + Integer.toString(semaphoreState));
        }
	}
    
    public void logEnter(int agent) {
        // log = "Agent x enters the system"
        synchronized (this) {
            System.out.println("Agent " + Integer.toString(agent) 
                    + " enters the system");
        }            
    }
    
    public void logExit(int agent) {
        // log = "Agent x exits the system"
        synchronized (this) {
            System.out.println("Agent "+ Integer.toString(agent) 
                + " exits the system");
        }            
    }
	
	public void logSignal(int agent, int semaphoreState) {
        // log = "Agent x executes signal operation, semaphore = y"
        synchronized (this) {
            System.out.println("Agent " + Integer.toString(agent) 
                + " executes signal operation, semaphore = "
                + Integer.toString(semaphoreState));
        }
	}
	
	public void logRequest(Request r) {
		// log = "Agent x reserves y seat(s) in CLASS"
		StringBuilder builder = new StringBuilder();
		builder.append("Agent ");
		builder.append(Integer.toString(r.agent) + " ");
		if (r.rORc == reqType.REQUEST) {
			builder.append("reserves ");
		} else if (r.rORc == reqType.CANCEL) {
			builder.append("cancels ");
		}
		if (r.seatNum == 1) {
			builder.append("1 seat in ");
		} else {
			builder.append(Integer.toString(r.seatNum) + " seats in ");
		}
		if (r.type == seatType.FIRST) {
			builder.append("First-class");
		} else if (r.type == seatType.BUSINESS) {
			builder.append("Business-class");
		} else if (r.type == seatType.ECONOMY) {
			builder.append("Economy-class");
		}
		synchronized (this) {
            System.out.println(builder.toString());
        }
	}
	
	public void logAdmitBatchFailure() {
        synchronized (this) {
            System.out.println("Cannot admit a batch");
        }
	}
	
	public void logReservationFailure(int agent) {
        synchronized (this) {
            System.out.println("Agent " + Integer.toString(agent) 
                + " cannot reserve any seats in this system");
        }
	}
 }
 