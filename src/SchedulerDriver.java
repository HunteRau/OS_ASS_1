

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Hayden & Aaron
 */
public class SchedulerDriver {
    
    static List<Request> input;
    
    public static void main(String args[]){
	input = new ArrayList<Request>();
	String fName = "";
	if(args.length<1){
	    System.exit(1);
	}else{
	    fName = args[0];
	}
        readIn(fName);
	for (Request r : input) {
	    System.out.println(r.toString());
	}
	Collections.sort(input,new RequestComparator());
	System.out.println("---------------------------------------");
	for (Request r : input) {
	    System.out.println(r.toString());
	}
    }
    
    static void readIn(String fileName){
	try{
	    BufferedReader bufferRead = new BufferedReader(new FileReader(fileName));
	    String s;
	    while((s = bufferRead.readLine()) != null){
		Request r = new Request(s);
		input.add(r);
	    }
	}catch(IOException e){
	    System.exit(1);
	}
    }
}
