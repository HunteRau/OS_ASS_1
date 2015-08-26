class Aircraft {
	int firstClassSeats;
	int businessClassSeats;
	int echoClassSeats;
	
	int firstClassSeatsFilled;
	int businessClassSeatsFilled;
	int echoClassFilled;
	
	public Aircraft(int numFirstClass, int numBusinessClass, int numEcoClass) {
		firstClassSeats = numFirstClass;
		businessClassSeats = numBusinessClass;
		echoClassSeats = numEcoClass;
	}
	
	boolean reserve(SeatType type) {
		if (type == FIRST) {
			if (firstClassSeatsFilled < firstClassSeats) {
				firstClassSeats++;
				return true;
			}								
		} else if (type == BUSINESS) {
			if (businessClassSeatsFilled < businessClassSeats) {
				businessClassSeats++;
				return true;
			}			
		} else if (type == ECONOMY) {
			if (echoClassSeatsFilled < echoClassSeats) {
				echoClassSeats++;
				return true;
			}
		}
		return false;
	}
	
	boolean reserve(SeatType type, int numOfSeats) {
		if (type == FIRST) {
			if (firstClassSeatsFilled + numOfSeats <= firstClassSeats) {
				firstClassSeats = numOfSeats + firstClassSeats;
				return true;
			}								
		} else if (type == BUSINESS) {
			if (businessClassSeatsFilled + numOfSeats <= businessClassSeats) {
				businessClassSeats = numOfSeats + businessClassSeats;
				return true;
			}			
		} else if (type == ECONOMY) {
			if (echoClassSeatsFilled + numOfSeats <= echoClassSeats) {
				echoClassSeats = numOfSeats + echoClassSeats;
				return true;
			}
		}
		return false;	
	}
	
	void cancel(SeatType type) {
		if (type == FIRST) {
			if (firstClassSeatsFilled > 0) {
				firstClassSeatsFilled--;
			}								
		} else if (type == BUSINESS) {
			if (businessClassSeatsFilled > 0) {
				businessClassSeatsFilled--;
			}			
		} else if (type == ECONOMY) {
			if (echoClassSeatsFilled > 0) {
				echoClassSeatsFilled--;
			}
		}
	}
	
	void cancel(SeatType type, int numOfSeats) {
		if (type == FIRST) {
			if (firstClassSeatsFilled - numOfSeats < 0) {
				firstClassSeatsFilled--;
			}								
		} else if (type == BUSINESS) {
			if (businessClassSeatsFilled - numOfSeats < 0) {
				businessClassSeatsFilled--;
			}			
		} else if (type == ECONOMY) {
			if (echoClassSeatsFilled - numOfSeats < 0) {
				echoClassSeatsFilled--;
			}
		}
	}
}