package model.dao;

import java.util.List;

import model.User;
import model.Weapon;

public class DBUserDao implements UserDao{

	@Override
	public List getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(String type, int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Weapon getUserWeapon(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUserScore(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}