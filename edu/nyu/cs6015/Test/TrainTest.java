package edu.nyu.cs6015.Test;
import edu.nyu.cs6015.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TrainTest {
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void trainDrivetest() throws TrainFullException, InvalidStationException {
		int[] stationNos_R = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
// Create track and train object
		Track track = new Track("track_R", stationNos_R);
		Train train   = new Train(track);
		
		ArrayList<Station> stationTrackArray = track.getStationTrackArray(); 
		for (Station ST : stationTrackArray) {
		     ST.registerTrack(track);
		}

// go "UP" to the end of the track
		Station expectedStationName = track.getFirstStation(Train.DIRECTION_UP);
		int expectedStationNumber = track.getStopNumber(expectedStationName);
		int expectedNumPassengers = 0;
		Station lastStationName = track.getLastStation(Train.DIRECTION_UP);
		int lastStationNumber = track.getStopNumber(lastStationName);
		for (; expectedStationNumber < lastStationNumber; expectedStationNumber++) {
        	   checkTrain(train, expectedStationNumber, expectedNumPassengers);
			   train.drive();
		}
	
// go "DOWN" to the end of the track
		lastStationName = track.getLastStation(Train.DIRECTION_DOWN);
		lastStationNumber = track.getStopNumber(lastStationName);
		for (; expectedStationNumber > lastStationNumber; expectedStationNumber--) {
			checkTrain(train, expectedStationNumber, expectedNumPassengers);
			train.drive();
		}

	    for(int i = 0; i < 10; i++){
	    	Passenger P = new Passenger(stationTrackArray.get(4));
        	train.boardPassenger(P);
        }
	    
        checkTrain(train, 0, 10);
	    
        train.drive(); //1
        checkTrain(train, 1, 10);
        
	    train.drive(); //2
	    checkTrain(train, 2, 10);
	    
	    train.drive(); //3
	    checkTrain(train, 3, 10);
	    
	    for(int i =4; i < 13; i++){
	    	train.drive(); //4 - 13 Total Passenger in Train 0.
	    	checkTrain(train, i, 0);
	    }
	    
// Set current station to 0. To start train in UP direction
	    Station S1 = stationTrackArray.get(1);
	    Station S5 = stationTrackArray.get(5);

	    train.setCurrentStation(0);
	    int i = 0;
		int waiting = (int)(Train.CAPACITY * 1.5);
		for (i = 0; i < waiting; i++) {
			Passenger P = new Passenger(S5);
			S1.waitForTrain(P);
		}
		
		checkTrain(train, 0, 0);
	
	    train.drive(); //1  Total Passenger waiting 150, Boarded 100
	    checkTrain(train, 1, 100);
	    
	    for(i = 2; i < 5; i++){
	    	train.drive(); //1 - 4 Total Passenger in Train 100
		    checkTrain(train, i, 100);
	    }
	    
	    for(i =5; i < 13; i++){
	    	train.drive(); //5 - 13 Total Passenger in Train 0, 100 will get down at S6
		    checkTrain(train, i, 0);
	    }

// drive to the end of the track in this direction
	    i = train.getCurrentStation();
		lastStationName = track.getLastStation(Train.DIRECTION_DOWN);
		lastStationNumber = track.getStopNumber(lastStationName);
		for (; i > lastStationNumber; i--) {
			    checkTrain(train, i, 0);
				train.drive();
		}
		
// now, go back up to the 1st station where 50 passenger should be waiting to go to S6 (5th station)
		train.drive(); // 1
		checkTrain(train, 1, waiting - Train.CAPACITY);
// drive to the end of the track in this direction	
		lastStationName = track.getLastStation(Train.DIRECTION_UP);
		lastStationNumber = track.getStopNumber(lastStationName);
		for (i = train.getStationNumber(); i < lastStationNumber; i++){
			if (i >= 5){
				checkTrain(train, i, 0);
			}else{
				checkTrain(train, i, waiting - Train.CAPACITY);
			}
			 train.drive();
		}		
  }
	
	private void checkTrain(Train train, int stationNumber, int numPassengers) {
		       assertEquals(stationNumber, train.getStationNumber());
		       assertEquals(numPassengers, train.gettotalPassengers());
	}
  }
