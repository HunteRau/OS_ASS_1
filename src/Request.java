/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Hayden
 */

public class Request{
    
    public String agent;
    public reqType rORc;
    public seatType type;
    public int seatNum;
    public int arrivalTime;
    
    public Request(String s){	
		String tokens[] = s.split(" ");
        agent = tokens[0];
        switch(tokens[1]){
            case "R":
                rORc = reqType.REQUEST;
                break;
            case "C":
                rORc = reqType.CANCEL;
                break;
        }
        switch(tokens[2]){
            case "F":
                type = seatType.FIRST;
                break;
            case "B":
                type = seatType.BUSINESS;
                break;
            case "E":
                type = seatType.ECONOMY;
                break;
        }
        seatNum = Integer.parseInt(tokens[3]);
        arrivalTime = Integer.parseInt(tokens[4]);
    }
    
    public boolean equals(Object obj){
		if(!(obj instanceof Request)){
			return false;
		}
		if(obj==this){
			return true;
		}
		Request rhs = (Request) obj;
		if(this.agent.equals(rhs.agent) && this.arrivalTime==rhs.arrivalTime &&
			this.rORc.equals(rhs.rORc) && this.seatNum==rhs.seatNum &&
			this.type.equals(rhs.type)){
			return true;
		}else{
			return false;
		}
	}
		
	public int hashCode(){
		return agent.hashCode()+arrivalTime+rORc.hashCode()+seatNum+type.hashCode();
	}
		
	public String toString(){
		String ans = "";
		ans = ans.concat(agent);
		if(rORc == reqType.CANCEL){
			ans = ans.concat(" C");
		}else{
			ans = ans.concat(" R");
		}
		switch(type){
			case FIRST:
			ans = ans.concat(" F");
			break;
			case BUSINESS:
			ans = ans.concat(" B");
			break;
			case ECONOMY:
			ans = ans.concat(" E");	    
			break;
		}
		ans = ans.concat(" " + Integer.toString(seatNum));
		ans = ans.concat(" " + Integer.toString(arrivalTime));
		return ans;
    }
    
}
