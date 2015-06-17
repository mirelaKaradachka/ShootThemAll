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
 * Servlet implementation class LevelsMap
 */
@WebServlet("/levelsMap")
public class LevelsMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LevelsMap() {

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line = request.getParameter("userId");

		// test
		line = "1";

		JSONObject result = new JSONObject();

		int numberOfAllLevels = SettingsManager.getMaxLevel();

		if (line != null) {

			int userId = Integer.parseInt(line);

			int numberOfActiveLevels = 1;
			/*
			 * 
			 * тук взимаме от потребителя нивото до което е стигнал от базата
			 * данни
			 */

			if (numberOfActiveLevels > numberOfAllLevels) {
				numberOfActiveLevels = numberOfAllLevels;
			}

			response.setStatus(200);
			result.put("numberOfAllLevels", numberOfAllLevels);
			result.put("numberOfActiveLevels", numberOfActiveLevels);

		} else {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}
		response.getWriter().write(result.toJSONString());
	}

}
