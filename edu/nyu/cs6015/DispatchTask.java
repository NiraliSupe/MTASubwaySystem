package edu.nyu.cs6015;

class DispatchTask implements Runnable {
	private final World world;
	private final Train train;

	DispatchTask(World world, Train train) {
		this.world = world;
		this.train = train;
	}

// This will first update the passenger information and then it will drive and board the passenger for that station.
	@Override
	public void run(){
		try{
			train.drive();
			Dispatcher.getInstance(world).futureSchedule(train);
		} catch (TrainFullException e) {
			e.printStackTrace();
		}
  	} 
}
