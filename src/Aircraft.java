/**
 *
 * @author Aaron
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
		if (type == seatType.FIRST) {
			return firstClassSeats - firstClassSeatsFilled;
		} else if (type == seatType.BUSINESS) {
			return businessClassSeats - businessClassSeatsFilled;
		} else {
			return ecoClassSeats - ecoClassSeatsFilled;
		}
	}
	
	public boolean reserve(seatType type) {
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled < firstClassSeats) {
				firstClassSeats++;
				return true;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled < businessClassSeats) {
				businessClassSeats++;
				return true;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled < ecoClassSeats) {
				ecoClassSeats++;
				return true;
			}
		}
		return false;
	}
	
	public boolean reserve(seatType type, int numOfSeats) {
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled + numOfSeats <= firstClassSeats) {
				firstClassSeats = numOfSeats + firstClassSeats;
				return true;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled + numOfSeats <= businessClassSeats) {
				businessClassSeats = numOfSeats + businessClassSeats;
				return true;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled + numOfSeats <= ecoClassSeats) {
				ecoClassSeats = numOfSeats + ecoClassSeats;
				return true;
			}
		}
		return false;	
	}
	
	public void cancel(seatType type) {
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
		if (type == seatType.FIRST) {
			if (firstClassSeatsFilled - numOfSeats >= 0) {
				firstClassSeatsFilled--;
			}								
		} else if (type == seatType.BUSINESS) {
			if (businessClassSeatsFilled - numOfSeats >= 0) {
				businessClassSeatsFilled--;
			}			
		} else if (type == seatType.ECONOMY) {
			if (ecoClassSeatsFilled - numOfSeats >= 0) {
				ecoClassSeatsFilled--;
			}
		}
	}
}