package controller;

public class LevelSingleton {
	private static LevelSingleton ls;
	private final int maxLevel;
	
	private LevelSingleton(){
		maxLevel = 3;
	}
	
	public static int getMaxLevel(){
		if(ls == null){
			return new LevelSingleton().maxLevel;
		}else{
			return ls.maxLevel;
		}
	}

}
