package edu.nyu.cs6015;

public class Passenger {
	private Station pCurrentStation;
	private Station pDestinationStation;
//	private Station S_Undefined = Station.getInstance("UNDEFINED_STATION");
	public static final Station S_Undefined = null;
		
	public Passenger(Station destinationStation){
		this.pDestinationStation = destinationStation;
		this.pCurrentStation = S_Undefined;
	}

// Gives current station of passenger
	public Station getCurrentStation(){
		return pCurrentStation;
	}

// Gives the destination of passenger
	public Station getDestinationStation(){
		return pDestinationStation;
	} 
	
	public void setpDestinationStation(Station pDestinationStation) {
		this.pDestinationStation = pDestinationStation;
	}	
	
	public void waitForTrain(Station destinationStation){
		this.pDestinationStation = destinationStation;
	}

// Once passenger has boared, it change it's current station to undefined_station.
	public void boardTrain(){
		this.pCurrentStation = S_Undefined;
	}

	public void arrive(){
		this.pCurrentStation = this.pDestinationStation;
		this.pDestinationStation = S_Undefined;
	}

// Set passenger's current station.
	public Station setCurrentStation(Station station){
		return pCurrentStation = station;
	}
	
	public static boolean isBetween(float x, float lower, float upper) {
		  return lower <= x && x <= upper;
	}

// If it returns false means no need to remove passenger from current trains's depature list.
	public boolean changeMind(float percent) throws InvalidStationException{
		if(isBetween(percent, 0.04f, 0.07f)){
			World.oppositeDir(this);
			return true;
		}
		
		if(isBetween(percent, 0.08f, 0.13f)){
			World.currentDir(this);
			return false;
		} 
		
		if(isBetween(percent, 0.14f, 0.19f)){
			this.setpDestinationStation(this.getCurrentStation());
       		return false;
     	}
		
		if(isBetween(percent, 0.00f, 0.03f)){
			World.differentTrack(this);
     		return true;
     	} 
		return false;
	}
	
	
}
