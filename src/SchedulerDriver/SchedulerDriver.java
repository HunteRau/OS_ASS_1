package SchedulerDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Hayden & Aaron
 */
public class SchedulerDriver {
    public static void main(String args[]){
        readIn();
    }
    
    static void readIn(){
        try{
	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String s = bufferRead.readLine();
            //test for empty string
            Request r = strToReq(s);
            //do something with r
	}catch(IOException e){
		System.exit(1);
	}
    }
    
    static Request strToReq(String s){
	String tokens[] = s.split(" ");
        Request r = new Request();
        r.agent = tokens[0];
        switch(tokens[1]){
            case "R":
                r.rORc = reqType.REQUEST;
                break;
            case "C":
                r.rORc = reqType.CANCEL;
                break;
        }
        switch(tokens[2]){
            case "F":
                r.type = seatType.FIRST;
                break;
            case "B":
                r.type = seatType.BUSINESS;
                break;
            case "E":
                r.type = seatType.ECONOMY;
                break;
        }
        r.seatNum = Integer.parseInt(tokens[3]);
        r.arrivalTime = Integer.parseInt(tokens[4]);
        return r;
    }
    
    
}
