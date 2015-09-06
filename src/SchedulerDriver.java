/**
 * SchedulerDriver is the driver class for the system.
 * It passes the process the input into request objects and sorts them.
 * Then it then simulates time and passes the requests into the queue once the correct
 * time occurs.
 */

/**
 * @author Hayden Russell a1606924
 * @author Aaron Hunter a1627530
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
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
		
		tickLoop();	
    }
    
    static void tickLoop(){
	int tickNum = 0;
    // process one tick
	while(q.size()!=0 || input.size()>0){
        // push requests at current time
        for(int i=0;i<input.size();i++){
            if(input.get(i).arrivalTime <= tickNum){
                q.push(input.get(i));
                input.set(i, null);
            }
        }
        // remove empty request pushed into the queue from the input list
        while(input.remove(null)){
        }

        // process the queue
        if(!q.tick() && input.isEmpty()){
                break;
        }
            tickNum++;
        }

        // log any requests that couldn't process
        q.logFailures();
    }
    
    static void printLists(){	
        // print the request, sort the request then print them again to test sorting
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
        // read in a file and process the commands to requests
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
