/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */
 
class Aircraft {
	private int firstClassSeats;
	private int businessClassSeats;
	private int ecoClassSeats;
	
	private int firstClassSeatsFilled;
	private int businessClassSeatsFilled;
	private int ecoClassSeatsFilled;
	
	public Aircraft(int numFirstClass, int numBusinessClass, int numEcoClass) {
		firstClassSeats = numFirstClass;
		businessClassSeats = numBusinessClass;
		ecoClassSeats = numEcoClass;
	}
	
	public int seatsNotTaken(seatType type) {
        // return the seats avaliable for option 'type'
		if (type == seatType.FIRST) {
			return firstClassSeats - firstClassSeatsFilled;
		} else if (type == seatType.BUSINESS) {
			return businessClassSeats - businessClassSeatsFilled;
		} else {
			return ecoClassSeats - ecoClassSeatsFilled;
		}
	}
	
	public boolean reserve(seatType type) {
        // returns false if a seat of 'type' can't be reserved
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled < firstClassSeats) {
				firstClassSeatsFilled++;
				return true;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled < businessClassSeats) {
				businessClassSeatsFilled++;
				return true;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled < ecoClassSeats) {
				ecoClassSeatsFilled++;
				return true;
			}
		}
		return false;
	}
	
	public boolean reserve(seatType type, int numOfSeats) {
        // returns false if the number of seats of 'type' can't be reserved
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled + numOfSeats <= firstClassSeats) {
				firstClassSeatsFilled += numOfSeats;
				return true;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled + numOfSeats <= businessClassSeats) {
				businessClassSeatsFilled += numOfSeats;
				return true;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled + numOfSeats <= ecoClassSeats) {
				ecoClassSeatsFilled += numOfSeats;
				return true;
			}
		}
		return false;	
	}
	
	public void cancel(seatType type) {
        // reallocated a seat of 'type' if there is one to reallocate
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled > 0) {
				firstClassSeatsFilled--;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled > 0) {
				businessClassSeatsFilled--;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled > 0) {
				ecoClassSeatsFilled--;
			}
		}
	}
	
	public void cancel(seatType type, int numOfSeats) {
        // reallocated 'numOfSeats' or as much as possible seats of 'type'
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled - numOfSeats >= 0) {
				firstClassSeatsFilled -= numOfSeats;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled - numOfSeats >= 0) {
				businessClassSeatsFilled -= numOfSeats;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled - numOfSeats >= 0) {
				ecoClassSeatsFilled -= numOfSeats;
			}
		}
	}
}