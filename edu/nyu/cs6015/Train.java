package edu.nyu.cs6015;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Train {

	public static final Direction DIRECTION_UP = Direction.MANHATTAN_BOUND;
	public static final Direction DIRECTION_DOWN = Direction.BROOKLYN_BOUND;
	public static final int CAPACITY = 100;
	
	public int currentStation;
	private int previousStation;
	private Station station;
	public Direction travelDirection;
	private int noOfPassengerDeparted;
	private ArrayList<Passenger> departingPassengersList;
	private int totalPassengers;
	private Track theTrack;
	private boolean flag;
	public Train(Track track){
		theTrack = track;
		this.currentStation = 0;
		this.travelDirection=  Direction.MANHATTAN_BOUND;
		this.departingPassengersList = new ArrayList<Passenger>();
     	this.totalPassengers =0;	
     	this.previousStation = 0;
   }

	public Track getTheTrack() {
		return theTrack;
	}

	public int getCurrentStation(){
		return currentStation;		
	}
	
	public Direction getTravelDirection(){
		return this.travelDirection;
	}
	
	public int getPreviousStation() {
		return previousStation;
	}
	
	public ArrayList<Passenger> getDepartingPassengersList() {
		return departingPassengersList;
	}

	public void setDepartingPassengersList(
			ArrayList<Passenger> departingPassengersList) {
		this.departingPassengersList = departingPassengersList;
	}
	
	public void setTheTrack(Track theTrack) {
		this.theTrack = theTrack;
	}
		
	public void setCurrentStation(int currentStation){
		this.currentStation = currentStation;		
	}

	public void setTravelDirection(Direction travelDirection){
		this.travelDirection = travelDirection;
	}
	
	public void setPreviousStation(int previousStation) {
		this.previousStation = previousStation;
	}
	
	public int gettotalPassengers(){
		return this.totalPassengers;
	}

// Gives Current station
	public int getStationNumber(){
		return currentStation ;
	}

// Gives the direction of Train - UP or DOWN
	public Direction getDirection(){
		return travelDirection;
	}
// This method updates the travel direction and current station.
// It calls the boardpassengers method to update the boarding and departing passengers. 
	public void drive() throws TrainFullException{
		ArrayList<Station> stationTrackList = this.theTrack.getStationTrackArray();
		flag = true;
		if(this.currentStation == 0){
			this.travelDirection= DIRECTION_UP;
			station = this.theTrack.getFirstStation(DIRECTION_UP);
		}else if (this.currentStation == stationTrackList.size() - 1){
			this.travelDirection = DIRECTION_DOWN;
			station = this.theTrack.getFirstStation(DIRECTION_DOWN);
		}
		
	    if(this.travelDirection == Direction.MANHATTAN_BOUND && this.currentStation < stationTrackList.size() ){
	    	this.currentStation = this.currentStation + 1;
	    	station = this.theTrack.getNextStation(station, this.travelDirection);
	    }

		if(this.travelDirection == Direction.BROOKLYN_BOUND && this.currentStation >= 1){
			this.currentStation = this.currentStation - 1;
			station = this.theTrack.getNextStation(station, this.travelDirection);
		}
		station.boardPassengers(this);
	}

// This method 
//	1. Calculates the Total number of passengers. 
//	2. Updates departing people with their corresponding station.
//  3. Update the total number of passengers on departing of them on desired station.
	public void boardPassenger(Passenger passenger)throws TrainFullException{
		if(this.previousStation == this.currentStation){
		   this.totalPassengers ++;
// Add passenger to HashMap with destination object as a key
		   	
			departingPassengersList.add(passenger);
// Depart the passenger for current station
			if(flag){
				departPassengers(station);
				this.totalPassengers -= noOfPassengerDeparted;
				flag = false;
			}
		}else{
			departPassengers(station);
			this.totalPassengers -= noOfPassengerDeparted;
			this.previousStation = this.currentStation;
		}
		
		if(this.totalPassengers > 100){
			throw new TrainFullException();
		}
	}

// Update passenger's current station.	
	public void updateCurrentPST(){
		for (Passenger P : this.departingPassengersList) {
			P.setCurrentStation(station);
		}
	}

// Depart passenger
	public int departPassengers(Station currentSt){
			noOfPassengerDeparted = 0;
				Iterator<Passenger> iter = this.departingPassengersList.iterator();	
				while(iter.hasNext()){
					Passenger p = iter.next();
					if(p.getDestinationStation().getName().equals(currentSt.getName()))
					{
						p.arrive();
						iter.remove();
						noOfPassengerDeparted ++;
					}
				}
			return noOfPassengerDeparted;
	}
}
