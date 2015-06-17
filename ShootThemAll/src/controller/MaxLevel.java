package controller;

public class MaxLevel {
	private static MaxLevel ls;
	private final int maxLevel;
	
	private MaxLevel(){
		maxLevel = 3;
	}
	
	public static int getMaxLevel(){
		if(ls == null){
			return new MaxLevel().maxLevel;
		}else{
			return ls.maxLevel;
		}
	}

}
