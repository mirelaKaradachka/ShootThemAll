package controller;

import java.util.Comparator;

import model.User;

public class CompareUsers implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		if (o1.getScore() > o2.getScore())
			return -1;
		if (o1.getScore() < o2.getScore())
			return 1;
		return o1.getUsername().compareTo(o2.getUsername());
	}

}
