package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.User;
import model.Weapon;
import db.DBManager;

public class DBUserDao implements UserDao {
	private Connection connection = DBManager.getDBManager().getConnection();
	//private Statement st;

	@Override
	public List getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		Statement st;
		try {
			connection.setAutoCommit(false);
			st = connection.createStatement();
			ResultSet results = st
					.executeQuery("select id, username, password, email,"
							+ " notificationAllow, levelNo, score, "
							+ "choosen_weapon_id,last_activity_on from app.users;");
			while (results.next()) {
				int id = results.getInt("id");
				String username = results.getString("username");
				String password = results.getString("password");
				String email = results.getString("email");
				boolean notificationAllow = results.getInt("notificationAllow") == 0 ? false
						: true;
				int levelNo = results.getInt("levelNo");
				int score = results.getInt("score");
				int weapon_id = results.getInt("choosen_weapon_id");
				Date date = results.getDate("last_activity_on");

				// Getting weapon atributes
				// trqbva da se promeni kato izpolzva getWeapon(int id) ot
				// BDWeaponDao
				PreparedStatement pst = connection
						.prepareStatement("select damage, price from app.weapons where id = ?");
				pst.setInt(1, weapon_id);
				ResultSet rs = pst.executeQuery();
				rs.next();
				int damage = rs.getInt("damage");
				int price = rs.getInt("price");
				connection.commit();
				User user = new User(id, username, password, email, score,
						levelNo, new Weapon(weapon_id, damage, price),
						notificationAllow);
				users.add(user);

			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("error rollback");
				e1.printStackTrace();
			}
			System.out.println("error in select all users");
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User getUser(int userId) {
		User user = null;
		try {
			connection.setAutoCommit(false);
			PreparedStatement pst = connection
					.prepareStatement("select username, password, email, notificationAllow, levelNo, score, choosen_weapon_id,last_activity_on from app.users where id = ?");
			pst.setInt(1, userId);
			ResultSet results = pst.executeQuery();
			results.next();
			String username = results.getString("username");
			String password = results.getString("password");
			String email = results.getString("email");
			boolean notificationAllow = results.getInt("notificationAllow") == 0 ? false
					: true;
			int levelNo = results.getInt("levelNo");
			int score = results.getInt("score");
			int weapon_id = results.getInt("choosen_weapon_id");
			Date date = results.getDate("last_activity_on");

			// Getting weapon atributes
			PreparedStatement pstW = connection
					.prepareStatement("select damage, price from app.weapons where id = ?");
			pstW.setInt(1, weapon_id);
			ResultSet rs = pstW.executeQuery();
			rs.next();
			int damage = rs.getInt("damage");
			int price = rs.getInt("price");
			connection.commit();
			user = new User(userId, username, password, email, score, levelNo,
					new Weapon(weapon_id, damage, price), notificationAllow);

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("error in select user by id");
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUser(String username) {
		User user = null;
		try {
			connection.setAutoCommit(false);
			PreparedStatement pst = connection
					.prepareStatement("select id, password, email, notificationAllow, levelNo, score, choosen_weapon_id,last_activity_on from app.users where username = ?");
			pst.setString(1, username);
			ResultSet results = pst.executeQuery();
			if (results != null) {
				results.next();
				int id = results.getInt("id");
				String password = results.getString("password");
				String email = results.getString("email");
				boolean notificationAllow = results.getInt("notificationAllow") == 0 ? false
						: true;
				int levelNo = results.getInt("levelNo");
				int score = results.getInt("score");
				int weapon_id = results.getInt("choosen_weapon_id");
				Date date = results.getDate("last_activity_on");

				// Weapon currentWeapon = getWeapon(weapon_id);
				// Getting weapon atributes
				PreparedStatement pstW = connection
						.prepareStatement("select damage, price from app.weapons where id = ?");
				pstW.setInt(1, weapon_id);
				ResultSet rs = pstW.executeQuery();
				rs.next();
				int damage = rs.getInt("damage");
				int price = rs.getInt("price");
				connection.commit();
				user = new User(id, username, password, email, score, levelNo,
						new Weapon(weapon_id, damage, price), notificationAllow);
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("error in select user by name");
			e.printStackTrace();
		}
		// System.out.println(user);
		return user;
	}

	@Override
	public int addUser(User u) {
		int userId = -1;
		// default notificationAllow, levelNo, weapon_id
		String insertInUser = "insert into app.users (username, password, email) "
				+ " values (?,?,?) ";
		String insertInUnlockWeapon = "insert into app.unlockedWeapons (user_id, weapon_id) values(?, ?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement pst = connection.prepareStatement(insertInUser,
					PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, u.getUsername());
			pst.setString(2, u.getPassword());
			pst.setString(3, u.getEmail());
			// set current date time
			// Date date = new Date ();
			// pst.setDate(4, (java.sql.Date) date);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next()) {
				userId = rs.getInt(1);
			}

			PreparedStatement pst2 = connection
					.prepareStatement(insertInUnlockWeapon);
			// gettin user id
			// gettin weapon id
			pst2.setInt(1, userId);
			pst2.setInt(2, 1);
			pst2.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Error rollback");
				e1.printStackTrace();
			}
			System.out.println("error insert user");
			e.printStackTrace();
		}
		return userId;

	}

	@Override
	public void deletUser(int userId) {
		// TODO Auto-generated method stub
		PreparedStatement pst;
		PreparedStatement pst1;
		try {
			// transackiq - trie ot user i ot
			connection.setAutoCommit(false);
			pst = connection
					.prepareStatement("delete from app.users where id = ?");
			pst.setInt(1, userId);
			pst.executeUpdate();

			pst1 = connection
					.prepareStatement("delete from app.unlockedWeapons where user_id = ?");
			pst.setInt(1, userId);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Error rollback");
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println("Error deleting user");
		}

	}

	// ...To be continued
	// Change password update
	@Override
	public void updatePassword(String password, int userId) {
		PreparedStatement pst;
		try {
			pst = connection
					.prepareStatement("update app.users set password = ? where id =?");
		pst.setString(1, password);
		pst.setInt(2,userId);
		pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error with updating password!");
			e.printStackTrace();
		}
		
	}

	// update e-mail
	//update app.users set email = 'email.bg' where id =1;
	@Override
	public void updateEmail(String email, int userId) {
		PreparedStatement pst;
		try {
			pst = connection
					.prepareStatement("update app.users set email = ? where id =?");
		pst.setString(1, email);
		pst.setInt(2,userId);
		pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error with updating email!");
			e.printStackTrace();
		}
	}

	// Update score
	//add score to curret
	@Override
	public void updateScore(int score, int userId) {
		PreparedStatement pst;
		try {
			pst = connection
					.prepareStatement("update app.users set score = score + ? where id =?");
		pst.setInt(1, score);
		pst.setInt(2,userId);
		pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error with updating score!");
			e.printStackTrace();
		}
	}
	// Update level input levelNo
	//za preskachane na opredelno nivo - ne poredni
		@Override
		public void updateLevel(int level, int userId) {
			PreparedStatement pst;
			try {
				pst = connection
						.prepareStatement("update app.users set levelNo =  ? where id =?");
			pst.setInt(1, level);
			pst.setInt(2, userId);
			pst.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error with updating level!");
				e.printStackTrace();
			}
		}
		
		//update level - levelUp
		@Override
		public void updateLevelUp( int userId) {
			PreparedStatement pst;
			try {
				pst = connection
						.prepareStatement("update app.users set levelNo = levelNo +1 where id =?");
			pst.setInt(1, userId);
			pst.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error with updating level!");
				e.printStackTrace();
			}
		}

	// Update notification
	@Override
	public void updateNotification(boolean noficationAllow, int userId) {
		PreparedStatement pst;
		int notificationINT =0;
		try {
			pst = connection
					.prepareStatement("update app.users set notificationAllow = ? where id =?");
			if(noficationAllow == true)
				notificationINT = 1;
		pst.setInt(1, notificationINT);
		pst.setInt(2,userId);
		pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error with updating notification!");
			e.printStackTrace();
		}
	}

	// update user e razdelen na otdelni update
	@Override
	public void updateUser(String type, String value, int userId) {

	}

	@Override
	public int existUser(String username, String password) {
		int userId = -1;
		try {

			String selecQuery = "select id, username , password from app.users where username = ? and password = ?";
			PreparedStatement pst = connection.prepareStatement(selecQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet results = pst.executeQuery();
			if(results != null){
				results.next();
				userId = results.getInt("id");				
			}
			
		} catch (SQLException e) {
			System.out.println("error in existUser");
			e.printStackTrace();
		}
		return userId;
	}

	// transaction
	@Override
	public Weapon getUserWeapon(int userId) {
		Weapon weapon = null;
		try {
			connection.setAutoCommit(false);
			String selectWeaponId = "select choosen_weapon_id from app.users where id = ?";
			PreparedStatement pst = connection.prepareStatement(selectWeaponId);
			pst.setInt(1, userId);
			ResultSet results = pst.executeQuery();

			results.next();
			int weapon_id = results.getInt("choosen_weapon_id");

			// weapon = getWeapon(weapon_id);
			// Getting weapon atributes
			PreparedStatement pst1 = connection
					.prepareStatement("select damage, price from app.weapons where id = ?");
			pst1.setInt(1, weapon_id);
			ResultSet rs = pst1.executeQuery();
			rs.next();
			int damage = rs.getInt("damage");
			int price = rs.getInt("price");
			weapon = new Weapon(weapon_id, damage, price);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("error in existUser");
			e.printStackTrace();
		}
		return weapon;
	}

	@Override
	public int getUserScore(int userId) {
		int score = 0;

		PreparedStatement pst;
		try {
			String selectScore = "select score from APP.USERS where id = ?";
			pst = connection.prepareStatement(selectScore);

			pst.setInt(1, userId);
			ResultSet results = pst.executeQuery();
			results.next();
			score = results.getInt("score");
		} catch (SQLException e) {
			System.out.println("Error in getUserScore");
			e.printStackTrace();
		}
		return score;
	}
	
	public int getUserLevel(int userId){
		int level = 0;

		PreparedStatement pst;
		try {
			String selectScore = "select levelNo from APP.USERS where id = ?";
			pst = connection.prepareStatement(selectScore);
			pst.setInt(1, userId);
			ResultSet results = pst.executeQuery();
			results.next();
			level = results.getInt("levelNo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
		
	}

	
	public boolean hasQuery(){
		boolean result = false;
		int count = 0;
		Statement st;
		try {

		    st = connection.createStatement();
			ResultSet results = st
					.executeQuery("select count(*) from app.users");
			results.next();
			count = results.getInt(1);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		if(count > 0){
			result = true;
		}
		System.out.println("num rows in user : " + count );
		return result;
		
	}
	
	public ArrayList<Integer> getUnlockedWeapons(int userId){
		ArrayList<Integer> weapon = new ArrayList<Integer>();
		PreparedStatement pst;
		try {
			String selectWeapon = "select weapon_id from APP.UnlockedWeapons where user_id  = ?";
			pst = connection.prepareStatement(selectWeapon);
			pst.setInt(1, userId);
			ResultSet results = pst.executeQuery();
			while(results.next()){
				Integer weaponId = results.getInt("weapon_id");
				System.out.println(weaponId);
				weapon.add(weaponId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return weapon;
	}
}
