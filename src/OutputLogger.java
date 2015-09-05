
import java.util.List;

/**
 *
 * @author Aaron
 */
 
 class OutputLogger {
	public void logAdmitBatch(List<Request> requests) {
		// create a string first
		StringBuilder builder = new StringBuilder();
		for (Request  r : requests) {
			builder.append("Agent " + Integer.toString(r.agent) + ", ");
		}
		builder.delete(builder.length()-2, builder.length());
		System.out.println("Admit a batch (" + builder.toString() + ")");
	}
	
	//builder.deleteCharAt(orgStr.length()-1);
	
	public void logWaitOp(int agent, int semaphoreState) {
		System.out.println("Agent " + Integer.toString(agent) 
			+ "executes wait operation, semaphore = "
			+ Integer.toString(semaphoreState));
		System.out.println("Agent " + Integer.toString(agent) 
			+ " enters the system");
	}
	
	public void logSignalOp(int agent, int semaphoreState) {
		System.out.println("Agent " + Integer.toString(agent) 
			+ "executes signal operation, semaphore = "
			+ Integer.toString(semaphoreState));
		System.out.println("Agent "+ Integer.toString(agent) 
			+ " exits the system");
	}
	
	public void logRequest(Request r) {
		// log - "Agent x reserves y seat/s in CLASS"
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
		System.out.println(builder.toString());
	}
	
	public void logAdmitBatchFailure() {
		System.out.println("Cannot admit a batch");
	}
	
	public void logReservationFailure(int agent) {
		System.out.println("Agent " + Integer.toString(agent) 
			+ " cannot reserve any seats in this system");
	}
 }
 