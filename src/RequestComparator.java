/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */

import java.util.Comparator;

public class RequestComparator implements Comparator<Request>{

    @Override
    public int compare(Request o1, Request o2) {
        // sort by time first, then put cancels before request, 
        // then seatType's in order First, BUSINESS, ECONOMY and finally
        // who has more seats
		if(o1.arrivalTime < o2.arrivalTime){
			return -1;
		}else if(o1.arrivalTime > o2.arrivalTime){
			return 1;
		}
		
		if(o1.rORc == reqType.CANCEL && o2.rORc == reqType.REQUEST){
			return -1;
		}else if(o1.rORc == reqType.REQUEST && o2.rORc == reqType.CANCEL){
			return 1;
		}
		
		if(o1.type == seatType.FIRST && o2.type != seatType.FIRST){
			return -1;
		}else if(o1.type != seatType.FIRST && o2.type == seatType.FIRST){
			return 1;
		}else if(o1.type == seatType.BUSINESS && o2.type == seatType.ECONOMY){
			return -1;
		}else if(o1.type == seatType.ECONOMY && o2.type == seatType.BUSINESS){
			return 1;
		}
		
		if(o1.seatNum < o2.seatNum){
			return 1;
		}else if(o1.seatNum > o2.seatNum){
			return -1;
		}
		return 0;
    }
}
