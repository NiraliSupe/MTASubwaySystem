package edu.nyu.cs6015;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Track {
	private String trackName;
	private int currStIndex;
	private int nextStIndex = 0;
	private ArrayList<Station> stationTrackArray;
	private String []stationNamearray = {"86 Street","77 Street","Bayridge","59 Street","53 Street","45 Street","36 Street","25 Street","Union Street","DeKalb Avenue","Court Street-Borough Hal","Whitehall Street  South Ferry","Rector Street","Cortlandt Street","City Hall","Canal Street","Prince Street","Eighth Street – New York University","14th Street – Union Square","23rd Street","28th Street","34th Street – Herald Square"};

	public Track(String trackName, int[]stationNos){
		this.trackName = trackName;
		this.stationTrackArray = new ArrayList<Station>();
		for (int cnt = 0; cnt < stationNos.length; cnt++) {
			Station S = Station.getInstance(stationNamearray[stationNos[cnt]]);
			stationTrackArray.add(S);		
		}
	}
	
	public String[] getStationNamearray() {
		return stationNamearray;
	}

	public void setStationNamearray(String[] stationNamearray) {
		this.stationNamearray = stationNamearray;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	
	public ArrayList<Station> getStationTrackArray() {
		return stationTrackArray;
	}

	public void setStationTrackArray(ArrayList<Station> stationTrackArray) {
		this.stationTrackArray = stationTrackArray;
	}

	public void setStation(Station station){
		this.stationTrackArray.add(station);
	}
	
// Returns the current station object
	public Station getStation(String stationName){
		for (Station ST : stationTrackArray) {
		    if (stationName.equals(ST.getName())) {
		        return ST;
		    }
		}
		return null;
	}
	
// Gives the First station
	public Station getFirstStation(Direction trackDirection){
		if(trackDirection == Train.DIRECTION_UP){
			return stationTrackArray.get(0);
		}else{
			return stationTrackArray.get(stationTrackArray.size() - 1);
		}
	}

// Gives the Last station
	public Station getLastStation(Direction trackDirection){
		if(trackDirection == Train.DIRECTION_UP){
			return stationTrackArray.get(stationTrackArray.size()- 1);
		}else{
			return stationTrackArray.get(0);
		}		
	} 

// Gives the stop number on particular stop
	public int getStopNumber(Station station){
	    return stationTrackArray.indexOf(station);
	}
		
// Gives the next station.
	public Station getNextStation(Station currentStation, Direction direction){
		int stationNumber = stationTrackArray.indexOf(currentStation);
		if(direction == Train.DIRECTION_UP){
			return stationTrackArray.get(++stationNumber);
		}else{
			return stationTrackArray.get(--stationNumber);
		}
	}

// Check whether station is exist or not.
	public boolean hasStation(String stationName){
		for (Station ST : stationTrackArray) {
		    if (stationName.equals(ST.getName())) {
		        return true;
		    }
		}
		return false;
	}

// Check whether station is exist or not.
	public boolean hasStation(Station station){
		for (Station ST : stationTrackArray) {
			if (station.getName().equals(ST.getName())) {
		        return true;
		    }
		}
		return false;
	}
	
// Get current and next station Index
	public void getStationNo(int stationNo, Direction direction){
		currStIndex = Arrays.asList(stationNamearray).indexOf(stationTrackArray.get(stationNo).getName());
		if(stationNo == 0){
			nextStIndex = Arrays.asList(stationNamearray).indexOf(stationTrackArray.get(++stationNo).getName());
		}else if(stationNo == stationTrackArray.size()- 1){
			nextStIndex = currStIndex;
			currStIndex = Arrays.asList(stationNamearray).indexOf(stationTrackArray.get(--stationNo).getName());
		}else if(direction == Train.DIRECTION_UP){
			nextStIndex = Arrays.asList(stationNamearray).indexOf(stationTrackArray.get(++stationNo).getName());
		}else{
			nextStIndex = currStIndex;
			currStIndex = Arrays.asList(stationNamearray).indexOf(stationTrackArray.get(--stationNo).getName());
		}
	}

// Return current station Index	for delay time retrieval
	public int getCurrentStationIndex(){
		return currStIndex;
	}

// Return next station Index for delay time retrieval
	public int getNextStationIndex(){
		return nextStIndex;
	}
}
