package edu.nyu.cs6015;
import java.util.*;

public class World {
	private static ArrayList<Train> trainsList = new ArrayList<Train>();
    private static boolean flag;
     private static Train train;
    private static int trainCnt = -1;
 	private HashMap<List<Integer>, Integer> stationsDelayInfo;
	private static int[] stationNos_R;
	private static int[] stationNos_N;
	private static int[] stationNos_Q;
	private static Random randomno = new Random();
	public World(){
		this.stationsDelayInfo = new HashMap<List<Integer>, Integer>(){{
			put(Arrays.asList(0, 1) , 2); put(Arrays.asList(1, 2) , 3); put(Arrays.asList(2,3)  , 3);
			put(Arrays.asList(3,4)  , 2); put(Arrays.asList(3,6)  , 5); put(Arrays.asList(4,5)  , 3);
			put(Arrays.asList(4,6)  , 3); put(Arrays.asList(5,6)  , 2); put(Arrays.asList(6,7)  , 4);
			put(Arrays.asList(6,9)  , 4); put(Arrays.asList(6,12) , 4); put(Arrays.asList(7,8)  , 2);
			put(Arrays.asList(8,9)  , 2); put(Arrays.asList(9,10) , 2); put(Arrays.asList(9,15) , 7);
			put(Arrays.asList(10,11), 3); put(Arrays.asList(11,12), 2); put(Arrays.asList(12,13), 2);
			put(Arrays.asList(13,14), 1); put(Arrays.asList(14,15), 3); put(Arrays.asList(15,16), 2);
			put(Arrays.asList(16,17), 2); put(Arrays.asList(16,18), 4); put(Arrays.asList(18,19), 3);
			put(Arrays.asList(19,20), 3); put(Arrays.asList(20,21), 4);
		}};
	}

	public static ArrayList<Train> getTrainsList() {
		return trainsList;
	}
	
	public void setTrainsList(ArrayList<Train> trainsList) {
		this.trainsList = trainsList;
	}
		
	public HashMap<List<Integer>, Integer> getStationsDelayInfo() {
		return stationsDelayInfo;
	}

	public void setStationsDelayInfo(HashMap<List<Integer>, Integer> stationsDelayInfo) {
		this.stationsDelayInfo = stationsDelayInfo;
	}

// Create 3 tracks and 2 trains for each track. 
	public static void initialize() throws TrainFullException, InvalidStationException{
		stationNos_R = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		stationNos_N = new int[]{ 4, 6, 12, 13, 14, 15, 16, 17};
		stationNos_Q = new int[]{1, 2, 3, 6, 9, 15, 16, 18, 19, 20, 21};
		Track track_R   = new Track("track_R", stationNos_R);
		Track track_N   = new Track("track_N", stationNos_N);
		Track track_Q   = new Track("track_Q", stationNos_Q);
		Train train_R1  = new Train(track_R);
		Train train_R2  = new Train(track_R);
		Train train_N1  = new Train(track_N);
		Train train_N2  = new Train(track_N);
		Train train_Q1  = new Train(track_Q);
		Train train_Q2  = new Train(track_Q);
		trainsList.add(train_R1);
	    trainsList.add(train_R2);
		trainsList.add(train_N1);
		trainsList.add(train_N2);
		trainsList.add(train_Q1);
		trainsList.add(train_Q2);
		ArrayList<Station> stationTrackArray_R = track_R.getStationTrackArray(); 
		for (Station ST : stationTrackArray_R) {
			ST.registerTrack(track_R);
		}
		ArrayList<Station> stationTrackArray_N = track_N.getStationTrackArray(); 
		for (Station ST : stationTrackArray_N) {
			ST.registerTrack(track_N);
		}
		ArrayList<Station> stationTrackArray_Q = track_Q.getStationTrackArray(); 
		for (Station ST : stationTrackArray_Q) {
			ST.registerTrack(track_Q);
		}
		
}
	public static void setTrainCnt(int trainCnt) {
		World.trainCnt = trainCnt;
	}
	public static void setTrain(Train train) {
		World.train = train;
	}
	
// Add passengers to station depending on probability. 
	public static void actPassengers() throws InvalidStationException, TrainFullException{
		if(trainCnt < (trainsList.size()-1)){
			trainCnt++;
		}else{
			trainCnt = 0;
		}
		train = trainsList.get(trainCnt);
		Station currentStationName;
		ArrayList<Station> stationList = trainsList.get(trainCnt).getTheTrack().getStationTrackArray();
		int max = stationList.size()-1;
		int min = 0;
		int i=0;
				
		for(int stationNumber = 0; stationNumber < max; stationNumber++){
			if (randomno.nextBoolean()){
				currentStationName  = stationList.get(stationNumber);
				for (; i < 10 ;) {
// Use random to choose destination stations for this 10 passengers.
					int randomNum = randomno.nextInt((max - min) + 1) + min;
					if(randomNum != stationNumber){
						Passenger P = new Passenger(stationList.get(randomNum));
						P.setCurrentStation(currentStationName);
						currentStationName.waitForTrain(P);
						i++;
					}
				}
			}
		} 
	}

// If passenger's current station is not present in the other tracks, then drive passenger to common station and then 
// update his/her's destination station
	public static void differentTrack(Passenger P) throws InvalidStationException{
		Station station = P.getCurrentStation();
		boolean flagOn = false;
		flag = true;
		ArrayList<Station> stationList = train.getTheTrack().getStationTrackArray();
		int min = stationList.indexOf(station);
		int max = stationList.size();
		for(int i = min; i < max; i++ ){
			if(findCommonSt(stationList.get(i), P)){
				flagOn = true;
				break;
			}
		}
		max = min -1;
		min = 0;
		if(!flagOn){
			for(int i = max; i >= min; i--){
				if(findCommonSt(stationList.get(i),P)){
				break;
				}
			}
		}
	}
	
	public static boolean findCommonSt(Station station, Passenger P) throws InvalidStationException{
		int stNum = 0;
		Track track = train.getTheTrack();
		for(Train T : trainsList){
// As two trains are running on same tracks, so check for common station in 3 tracks only.
			flag = !flag;
			if(flag){
				if(!(T.getTheTrack().getTrackName().equals(track.getTrackName()))){
					if(T.getTheTrack().hasStation(station)){
						stNum = T.getTheTrack().getStationTrackArray().indexOf(station);
						int randNum = 0; int min = stNum+1;
						int max = T.getTheTrack().getStationTrackArray().size()-1;
						randNum = randomno.nextInt((max - min) + 1) + min;
						Station ST = T.getTheTrack().getStationTrackArray().get(randNum);
						P.setCurrentStation(station);
						P.setpDestinationStation(ST);
						T.getDepartingPassengersList().add(P);
						return true;
					} // End-if
				} // End-if
			} // End-if flag
		} // End-For
		
		return false; 
	}

// change passengers destination to a station on the current track in the current direction
	public static void currentDir(Passenger P){
		int max; int min;
		ArrayList<Station> stationList = train.getTheTrack().getStationTrackArray();
		int currentNo = stationList.indexOf(P.getCurrentStation());
		if(train.getTravelDirection() == Direction.MANHATTAN_BOUND){
			max = stationList.size() -1;
			min = currentNo;
			if(min != max - 1){
				min++;
			}
		}else{
			max = currentNo;
			if(max != 0){
				max--;
			}
			min = 0;
		}
		int randomNum = randomno.nextInt((max - min) + 1) + min;
		P.setpDestinationStation(stationList.get(randomNum)); 
	}

// Change passengers destination to a station on the current track in the opposite direction.
// As on each track trains are running in opposite direction, remove the passenger from current trains list, change it's destination
// add add it to train which is running in the opposite direction.
	public static void oppositeDir(Passenger P){
		int max; int min; int count;
		ArrayList<Station> stationList = train.getTheTrack().getStationTrackArray();
		int currentNo = stationList.indexOf(P.getCurrentStation());
		if(train.getTravelDirection() == Direction.MANHATTAN_BOUND){
			max = currentNo;
			if(currentNo != 0){
				max--;
			}
			min = 0;
		}else{
			min = currentNo;
			if(currentNo != stationList.size() -1){
				min++;
			}
			max = stationList.size() -1;
		}
		int randomNum = randomno.nextInt((max - min) + 1) + min;
		P.setpDestinationStation(stationList.get(randomNum));
		if(trainCnt%2 != 0 ){
			count = trainCnt - 1;
			trainsList.get(count).getDepartingPassengersList().add(P);
		}else{
			count = trainCnt + 1;
			trainsList.get(count).getDepartingPassengersList().add(P);
		}
	}

}
