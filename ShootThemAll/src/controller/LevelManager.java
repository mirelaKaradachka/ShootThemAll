package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class SaveLevelInfo
 */
@WebServlet("/levelManager")
public class LevelManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LevelManager() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("userId");
		String userLevel = request.getParameter("level");

		// test
		user = "1";
		userLevel = "2";

		JSONObject result = new JSONObject();

		if (user != null && userLevel != null) {

			int userId = Integer.parseInt(user);
			int level = Integer.parseInt(userLevel);

			LevelBuilder levelBuilder = new LevelBuilder();
			result = levelBuilder.buildLevel(userId, level);

			response.setStatus(200);

		} else {

			response.setStatus(400);
			result.put("error", "Invalid parameter");
		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("score", 0);
		line = test.toJSONString();

		String message = null;
		JSONObject result = new JSONObject();

		JSONParser parser = new JSONParser();
		try {
			JSONObject scoreObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(scoreObj.get("userId").toString());
			int score = Integer.parseInt(scoreObj.get("score").toString());

			if (score > 0) {
				/*
				 * Тук записваме точките на потребителя с userId в базата данни
				 * Правим проверка дали потребителя е минал нечии точки и
				 * уведомяваме през мейл
				 */
			}

			message = "success";
			response.setStatus(200);
			result.put("message", message);

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());

	}

}
