package edu.nyu.cs6015;

public enum Direction {
	BROOKLYN_BOUND, MANHATTAN_BOUND;
	
	public String toString()
	{
		if(this.equals(MANHATTAN_BOUND)){
			return "Manhattan Bound";
		}else if(this.equals(BROOKLYN_BOUND)){
			return "Brooklyn Bound";
		}else {
			throw new IllegalArgumentException();
		}
	}
}
