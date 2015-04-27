package edu.nyu.cs6015;

public class InvalidStationException extends Exception{

private static final long serialVersionUID = -8753756612049413522L;

	public String InvalidStationException(){
		return "Invalid Station. Station not exist.";
	}
}
