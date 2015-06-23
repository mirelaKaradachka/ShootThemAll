package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db.DBManager;

/**
 * Servlet implementation class LeaderBord
 */
@WebServlet("/leaderBoard")
public class LeaderBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaderBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// репрезентация на класация
		String line = request.getParameter("userId");

		// test
		line = "2";

		JSONArray result = new JSONArray();
		JSONObject error = new JSONObject();

		if (line != null) {
			int userId = Integer.parseInt(line);
			int userPosition = -1;

//			int count = 10;
//
//			// използваме кеша -> друг вариант е от базата данни
//			ArrayList<User> list = (ArrayList<User>) getServletConfig()
//					.getServletContext().getAttribute("cacheUsers");
//			list.sort(new UsersComparator());
//			for (Iterator<User> i = list.iterator(); i.hasNext();) {
//				User u = i.next();
//				if (u.getId() == userId) {
//					userPosition = list.indexOf(u);
//
//				}
//
//				if (count > 0) {
//					JSONObject userObj = new JSONObject();
//					userObj.put("position", list.indexOf(u) + 1);
//					userObj.put("username", u.getUsername());
//					userObj.put("score", u.getScore());
//					result.add(userObj);
//				}
//				count--;
//
//			}
//
//			if (userPosition > 9) {
//				JSONObject userObj = new JSONObject();
//				userObj.put("position", (userPosition + 1));
//				userObj.put("username", list.get(userPosition).getUsername());
//				userObj.put("score", list.get(userPosition).getUsername());
//				result.add(userObj);
//			}
			
			result = topUsers(userId);		

			if(result != null){
				response.setStatus(200);
			}else{
				response.setStatus(400);
				error.put("error", "DB Error");
				response.getWriter().write(error.toJSONString());
				return;
			}

		} else {
			response.setStatus(400);
			error.put("error", "Invalid parameter");
			response.getWriter().write(error.toJSONString());
			return;
		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	public JSONArray topUsers(int userId) {
		
		JSONArray result = new JSONArray();
		
		Connection connect = DBManager.getDBManager().getConnection();
		//TreeMap<String, Integer> topUsers = new TreeMap();
		Statement statement;
		PreparedStatement preparedStatement;
		PreparedStatement countUsersStatement;
		try {

			preparedStatement = connect
					.prepareStatement("SELECT USERNAME, SCORE FROM APP.USERS WHERE ID = ?");
			preparedStatement.setInt(1, userId);
			ResultSet userResult = preparedStatement.executeQuery();
			userResult.next();
			String userUsername = userResult.getString("username");
			int userScore = userResult.getInt("score");

			countUsersStatement = connect
					.prepareStatement("SELECT COUNT(*) AS COUNT FROM APP.USERS WHERE SCORE > ? ");
			countUsersStatement.setInt(1, userScore);
			ResultSet countUsers = countUsersStatement.executeQuery();
			countUsers.next();
			int userPosition = countUsers.getInt("count");

			statement = connect.createStatement();
			statement.setMaxRows(10); 
			ResultSet resultSet  = statement
					.executeQuery("SELECT USERNAME, SCORE FROM APP.USERS ORDER BY SCORE DESC");
			
			int position = 0;
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				int score = resultSet.getInt("score");
				//topUsers.put(username, score);
				position++;
				JSONObject userObj = new JSONObject();
				userObj.put("position", position);
				userObj.put("username", username);
				userObj.put("score", score);
				result.add(userObj);
			}
			if (userPosition > 9) {
				
					//topUsers.put(userUsername, userScore);
				JSONObject userObj = new JSONObject();
				userObj.put("position",(userPosition + 1));
				userObj.put("username", userUsername);
				userObj.put("score", userScore);
				result.add(userObj);
				
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL error");
		}
		
		return result;

	}
}
