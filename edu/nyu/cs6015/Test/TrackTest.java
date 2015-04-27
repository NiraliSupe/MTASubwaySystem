package edu.nyu.cs6015.Test;
import edu.nyu.cs6015.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class TrackTest {
	
	@Test
	public void trackTest(){
// Create track and train object
		int[] stationNos_R = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		Track track = new Track("track_R", stationNos_R);
		Train train   = new Train(track);
		Station firstStation = track.getFirstStation(Train.DIRECTION_UP);
		Station lastStation  = track.getLastStation(Train.DIRECTION_DOWN);
		assertTrue(track.hasStation(firstStation));
		assertEquals("86 Street", firstStation.getName());
		assertEquals("86 Street", lastStation.getName());
	}

}
