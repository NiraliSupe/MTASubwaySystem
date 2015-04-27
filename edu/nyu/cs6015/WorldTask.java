package edu.nyu.cs6015;
import java.util.*;

public class WorldTask implements Runnable{
	private ArrayList<Passenger> departingPassengersList;
	private ArrayList<Passenger> removePList = new ArrayList<Passenger>();
	WorldTask() {
		
	}

// This will first update the passengers and then it will execute changeMind method for each passenger.
	@Override
	public void run() {
		try{
			Random random = new Random();
			int cnt = 0;
	        for (; cnt <  World.getTrainsList().size(); cnt++) {
	        	Train train = World.getTrainsList().get(cnt);
	        	World.actPassengers();
	        	departingPassengersList = train.getDepartingPassengersList();
	    		for(Passenger P : departingPassengersList){
	    			float percent = (Math.round((0.00f + random.nextFloat() * (1.00f - 0.00f))*100))/100.00f;
	    			if(percent < 0.20f){
	    				if(P.changeMind(percent)){
	    					removePList.add(P);
	    				}
	    			}
	    		}
	      		train.getDepartingPassengersList().removeAll(removePList);
	    		removePList.clear();
	       }
		} catch (InvalidStationException e) {
			e.printStackTrace();
		} catch (TrainFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  	} 
}
