package edu.nyu.cs6015;
import java.io.IOException;

class main{

	public static void main(String args[]) throws Exception {
		World.initialize();
		Dispatcher dispatcher = new Dispatcher(new World());
// Start the train.
		dispatcher.trainStart();
		System.out.println("Train is running. Please press Enter to stop train!!!!");
		try {
			System.in.read();
			dispatcher.trainStop();
			System.out.println("Train is Stoppted");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
