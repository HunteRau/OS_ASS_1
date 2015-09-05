/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hayden
 */
public class Signaler {
    
    private int sigNum=0;
    
    public synchronized void Wait(){
	while(sigNum<=0){
	    //noop
	}
	sigNum--;
    }
    
    public synchronized void Signal(){
	sigNum++;
    }
    
    int getSigNum(){
	return sigNum;
    }
    
}
