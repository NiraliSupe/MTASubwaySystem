package edu.nyu.cs6015.Test;
import edu.nyu.cs6015.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StationTest {

	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test(expected = InvalidStationException.class)
//	@Test
	public void stationTest() throws InvalidStationException{
		
// Create track and train object
		int[] stationNos_R = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		Track track_R = new Track("track_R", stationNos_R);
		Station a = Station.getInstance("A");
		Station b = Station.getInstance("B");
		assertEquals(a,Station.getInstance("A"));
		Station S1 = track_R.getFirstStation(Train.DIRECTION_UP);
	    Station SL = Station.getInstance("95 street");
	  	Passenger P = new Passenger(SL);
		S1.waitForTrain(P);
	}
}
