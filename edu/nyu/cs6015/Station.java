package edu.nyu.cs6015;
import java.util.ArrayList;
import java.util.Iterator;


public class Station {

    private String name;
   	private ArrayList<Passenger> passengerInfoWaitingUp;
	private ArrayList<Passenger> passengerInfoWaitingDown;
	private ArrayList<Passenger> passengerInfoWaiting;
	private static ArrayList<Station> nameofStationsArray = new ArrayList<Station>();
	private ArrayList<Track> tracks;
	private static Station station;
	private int currentStation = 0;
	private int destinationStationNo= 0;
	
	private Station(String stationName){
		this.name = stationName;
		this.passengerInfoWaitingUp = new ArrayList<Passenger>();
		this.passengerInfoWaitingDown = new ArrayList<Passenger>();
		this.tracks =  new ArrayList<Track>();
	}
	
	public ArrayList<Passenger> getPassengerInfoWaitingUp() {
		return passengerInfoWaitingUp;
	}

	public void setPassengerInfoWaitingUp(
			ArrayList<Passenger> passengerInfoWaitingUp) {
		this.passengerInfoWaitingUp = passengerInfoWaitingUp;
	}

	public ArrayList<Passenger> getPassengerInfoWaitingDown() {
		return passengerInfoWaitingDown;
	}

	public void setPassengerInfoWaitingDown(
			ArrayList<Passenger> passengerInfoWaitingDown) {
		this.passengerInfoWaitingDown = passengerInfoWaitingDown;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ArrayList<Station> getNameofStationsArray() {
		return nameofStationsArray;
	}

	public static void setNameofStationsArray(ArrayList<Station> nameofStationsArray) {
		Station.nameofStationsArray = nameofStationsArray;
	}
	
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	public static Station getInstance(String stationName){
		if(nameofStationsArray != null){
			for (Station station : nameofStationsArray) {
			    if (station.getName().equals(stationName)) {
			        return station;
			    }
			}
		}
		station = new Station(stationName); 
		nameofStationsArray.add(station);
	    return station;
	}

// Count the number of passengers waiting for train. It considers the train direction to update the count. 
// Also get the track for passenger.
// Check whether destination is a valid station.
	public void waitForTrain(Passenger passenger) throws InvalidStationException{
		if (!this.isValidDestinationStation(passenger.getDestinationStation())) {
		    throw new InvalidStationException();
		}
		passenger.setCurrentStation(this);
		if(currentStation < destinationStationNo){
        	this.passengerInfoWaitingUp.add(passenger);
		}else{
			this.passengerInfoWaitingDown.add(passenger);
		}
	}

	public boolean isValidDestinationStation(Station destinationStation) {
		for (Track track : this.tracks) {
			if (track.hasStation(destinationStation)&& track.hasStation(this)) {
	        	destinationStationNo = track.getStopNumber(destinationStation);
				currentStation =  track.getStopNumber(this);
	            return true;
	        }
	    }
	    return false;
	}
	
	public void registerTrack(Track track){
		tracks.add(track);
	}
	
//This methods -
// 1. Board the passengers to train.
// 2. As capacity of train is 100, if train is full, then board the passengers next time when train comes in that direction.
// 3. Throws exception when number passengers in train exceeds 100.
	public int boardPassengers(Train train) throws TrainFullException{
		int noOfPassengersBoarded = 0;
		train.updateCurrentPST();
		if( train.getTravelDirection() == train.DIRECTION_UP){
			passengerInfoWaiting = this.passengerInfoWaitingUp;
		}else{
			passengerInfoWaiting = this.passengerInfoWaitingDown;
		}
		
		if(this.passengerInfoWaiting.size() == 0){
			train.boardPassenger(null);
		}else{ 
			train.setPreviousStation(train.getCurrentStation());	
			Iterator<Passenger> iter = passengerInfoWaiting.iterator();	
		//	while(iter.hasNext()&& (noOfPassengersBoarded < Train.CAPACITY )){
			while(iter.hasNext()&& (train.gettotalPassengers() < Train.CAPACITY )){
				Passenger P = iter.next();
				train.boardPassenger(P);
		//		P.arrive();
			//	System.out.println("beforeeeeee");
				iter.remove();
		//		System.out.println("Afterrrrr");
				noOfPassengersBoarded++;
			}
		}
		return noOfPassengersBoarded;
	}
}
