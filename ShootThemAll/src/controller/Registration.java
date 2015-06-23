package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cache.Cache;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Registration() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// test
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("username", "Preq");
		test.put("password", "123");
		test.put("email", "tatata");
		line = test.toJSONString();

		JSONObject result = new JSONObject();

		JSONParser parser = new JSONParser();
		try {

			JSONObject userObj = (JSONObject) parser.parse(line);
			String username = userObj.get("username").toString();
			String password = userObj.get("password").toString();

			// MD5
			String cryptPass = SettingsManager.cryptMD5(password);
			password = cryptPass;

			String email = userObj.get("email").toString();

			if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {

				/*
				 * Проверка дали този username e зает Проверкa в базата данни
				 * или в хеша
				 */

				User user = new User(username, password, email);
				UserDao ud = new DBUserDao();

				// проверка в хеша
				// if (getServletContext().getAttribute("cacheUsers") == null) {
				// if (Cache.getCache().isEmpty()) {

				// prowerqvame dali ima zapisi v bazata danni
				
				if (ud.hasQuery()) {

					// няма такъв потребител = > insert в базата данни и
					// добавяне в хеша

					// това идва като резултат от INSERT ... SELECT

					
					int userId = ud.addUser(user);

					// ArrayList<User> cacheUsers = new ArrayList<User>();
					// cacheUsers.add(user);
					// getServletConfig().getServletContext().setAttribute(
					// "cacheUsers", cacheUsers);

					// добавяме в базата данни за активни оръжия 1вото оръжие
					if (userId > 0) {
						response.setStatus(200);
						result.put("userId", userId);
					} else {
						result.put("error", "DataBase error ");
						response.setStatus(400);

					}

				} else{
					User existUser = ud.getUser(user.getUsername());

					if(existUser != null){
						
						result.put("error", "Existing username");
					    response.setStatus(400);
					    
					}else{
						int userId = ud.addUser(user);
						
						if (userId > 0) {
							response.setStatus(200);
							result.put("userId", userId);
						} else {
							result.put("error", "DataBase error ");
							response.setStatus(400);

						}
						
					}
					
					
					
					
					// boolean existUser = false;
					// // използваме кеша -> друг вариант е от базата данни
					// // ArrayList<User> list = (ArrayList<User>)
					// getServletConfig()
					// // .getServletContext().getAttribute("cacheUsers");
					//
					// Vector<User> list = Cache.getCache().getAllUsers();
					// for (int i = 0; i < list.size(); i++) {
					// if (list.get(i).getUsername().equals(username)) {
					// existUser = true;
					// }
					// }
					//
					// if (existUser) {
					// result.put("error", "Existing username");
					// response.setStatus(400);
					// } else {
					//
					// // няма такъв потребител = > insert в базата данни и
					// // добавяне в хеша
					//
					// // това идва като резултат от INSERT ... SELECT
					// int userId = 6;
					// User user = new User(userId, username, password, email,
					// 0, 0, new Weapon(1, 1, 1), false);
					//
					// // добавяме в базата данни за активни оръжия 1вото
					// // оръжие
					//
					// list.add(user);
					// response.setStatus(200);
					// result.put("userId", userId);
					// }
					//
				}

			} else {

				result.put("error", "Empty fields");
				response.setStatus(400);

			}

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}
}
