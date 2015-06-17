package controller;

public class SettingsManager {
	private static SettingsManager ls;
	private final int maxLevel;
	
	private SettingsManager(){
		maxLevel = 3;
	}
	
	public static int getMaxLevel(){
		if(ls == null){
			return new SettingsManager().maxLevel;
		}else{
			return ls.maxLevel;
		}
	}

}
