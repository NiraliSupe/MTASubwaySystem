package edu.nyu.cs6015.Test;
import edu.nyu.cs6015.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class PassengerTest {
	@Test
	public void passengerTest() throws InvalidStationException{
// Create track and train object
		int[] stationNos_R = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		Track track = new Track("track_R", stationNos_R);
		Train train   = new Train(track);
		ArrayList<Station> stationTrackArray = track.getStationTrackArray(); 
		for (Station ST : stationTrackArray) {
			    ST.registerTrack(track);
		}	
	    Station S1 = stationTrackArray.get(1);
	    Station S5 = stationTrackArray.get(5);
	  	Passenger P = new Passenger(S5);
		S1.waitForTrain(P);
		assertEquals(P.getCurrentStation() , S1);
		assertEquals(P.getDestinationStation(), S5);
	}
	
	@Test
	public void changeMindTest() throws TrainFullException, InvalidStationException{
		World.initialize();
// Test for 1.0 results in no change
		Train train = World.getTrainsList().get(0);
		ArrayList<Station> stationList = train.getTheTrack().getStationTrackArray();
		Passenger P = new Passenger(stationList.get(2));
	    stationList.get(1).waitForTrain(P);
		P.changeMind(1.0f);
		assertEquals(P.getDestinationStation() ,stationList.get(2));
	 
        
// Test for 0.17 results in a change to the current station
        train = World.getTrainsList().get(1);
		stationList = train.getTheTrack().getStationTrackArray();  
	   	Passenger P1 = new Passenger(stationList.get(5));
	    stationList.get(1).waitForTrain(P1);
	    P1.changeMind(0.17f);
	    assertEquals(P1.getDestinationStation() ,P1.getCurrentStation()); 
	   
	    
// Test for 0.11 results in a change to a station on the current track in the current direction
        train = World.getTrainsList().get(2);
        World.setTrain(train);
        stationList = train.getTheTrack().getStationTrackArray(); 
		Passenger P3 = new Passenger(stationList.get(5));
	    stationList.get(1).waitForTrain(P3);
        P3.changeMind(0.11f);
        int stationNum = train.getTheTrack().getStationTrackArray().indexOf(P3.getDestinationStation());
        assertTrue(train.getTheTrack().hasStation(P3.getDestinationStation()));
        assertTrue((stationNum > 1));
	    
// Test for 0.06 results in a change to a station on the current track in the opposite direction
        train = World.getTrainsList().get(3);
        World.setTrain(train);
        World.setTrainCnt(3);
		stationList = train.getTheTrack().getStationTrackArray(); 
		Passenger P4 = new Passenger(stationList.get(5));
	    stationList.get(4).waitForTrain(P4);
    	P4.changeMind(0.06f); 
    	stationNum = train.getTheTrack().getStationTrackArray().indexOf(P4.getDestinationStation());
    	assertTrue(train.getTheTrack().hasStation(P4.getDestinationStation()));
    	assertTrue((stationNum < 4));
	    
// Test for 0.01 results in a change to a station on a different track
        Train train_Q = World.getTrainsList().get(4);
        World.setTrain(train_Q);
		stationList = train_Q.getTheTrack().getStationTrackArray(); 
		Passenger P5 = new Passenger(stationList.get(9));
	    stationList.get(7).waitForTrain(P5);
	    P5.changeMind(0.01f);
	    Train train_R = World.getTrainsList().get(0);
	    Train train_N = World.getTrainsList().get(2);
	    assertEquals(P5.getCurrentStation(), stationList.get(6));
    	assertTrue((train_R.getTheTrack().hasStation(P5.getDestinationStation()))||(train_N.getTheTrack().hasStation(P5.getDestinationStation())));
	}
	
}
