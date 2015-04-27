package edu.nyu.cs6015;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
	final ScheduledExecutorService schExService;
	private static Dispatcher instance;
	private boolean firstTimeFlag = true;
	private World world;
	private Runnable ob_dispatchTask  = null;
	
		Dispatcher(World world) {
			this.world = world;
			this.schExService = Executors.newScheduledThreadPool(world.getTrainsList().size());
		}

		public static Dispatcher getInstance(World world) {
			if (instance == null) {
				instance = new Dispatcher(world);
			}
			return instance;
		}
		
// no of threads = no of trains. Object creation of runnable thread.
		public void trainStart(){
			ArrayList<Train> trainsList = World.getTrainsList();
			Runnable ob_worldTask = new WorldTask();
			schExService.scheduleAtFixedRate(ob_worldTask, 1, 5, TimeUnit.SECONDS);
		//	schExService.schedule(ob_worldTask, 1, TimeUnit.SECONDS);
						
	        for (int cnt = 0; cnt < trainsList.size(); cnt++) {
	        	Train train = trainsList.get(cnt);
	        	ob_dispatchTask = new DispatchTask(this.world, train);
	   
// Thread scheduling
// Each track have two trains and they are running in opposite directions.
// So for first time, start the second train on each track from the last station.
	       		if(firstTimeFlag && cnt%2 != 0){
	       			train.setCurrentStation(train.getTheTrack().getStationTrackArray().size()-1);
	       			train.setTravelDirection(Train.DIRECTION_DOWN);
	       		}

	       		int delayTm = getDelayTime(train);
	       		schExService.schedule(ob_dispatchTask , delayTm, TimeUnit.SECONDS);
	       }
	        	firstTimeFlag = false; 
		}

// Future schedule
		public void futureSchedule(Train train){
			ob_dispatchTask  = new DispatchTask(this.world, train);
			int delayTm = getDelayTime(train);
       		schExService.schedule(ob_dispatchTask, delayTm, TimeUnit.SECONDS);
		}

// Get delay Time to the next station.
		public int getDelayTime(Train train){
			HashMap<List<Integer>, Integer> stationsDelayInfo = world.getStationsDelayInfo();
			train.getTheTrack().getStationNo(train.getCurrentStation(), train.getDirection());
			int cStIndex = train.getTheTrack().getCurrentStationIndex();
			int nStIndex = train.getTheTrack().getNextStationIndex();
			int value = stationsDelayInfo.get(Arrays.asList(cStIndex, nStIndex));
			return value;
	    }

// shutdown thread. This will stop train.
		public void trainStop(){
			schExService.shutdownNow();
		}
}
