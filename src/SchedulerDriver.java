

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
 
// TODO: 	-tick the queue where 1 tick is 1 time unit.
//			-after enough time units has passed for the request, push the request 
//				to the queue
public class SchedulerDriver {
    
    static List<Request> input;
    static Aircraft ac = new Aircraft(10,20,70);
    static OutputLogger ol = new OutputLogger();
    static Queue q = new Queue(ac,ol);
    
    public static void main(String args[]){
		input = new ArrayList<Request>();
		String fName = "";
		if(args.length<1){
			System.exit(1);
		}else{
			fName = args[0];
		}
		readIn(fName);
		printLists();
		
		tickLoop();
		
    }
    
    static void tickLoop(){
	int tickNum = 0;
	while(q.size()!=0 || input.size()>0){
	    for(int i=0;i<input.size();i++){
		if(input.get(i).arrivalTime == tickNum){
		    q.push(input.get(i));
		    input.set(i, null);
		}
	    }
	    while(input.remove(null)){}
	    if(!q.tick()){
		break;
	    }
	}
    }
    
    static void printLists(){	
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
