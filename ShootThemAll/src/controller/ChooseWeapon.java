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
 * Servlet implementation class ChooseWeapon
 */
@WebServlet("/ChooseWeapon")
public class ChooseWeapon extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public ChooseWeapon() {

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("weaponType", 2);
		line = test.toJSONString();
		
		JSONObject result = new JSONObject();
		String message = null;
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(userObj.get("userId").toString());
			int weponType = Integer.parseInt(userObj.get("weaponType").toString());
			
			/*
			 *  проверка в базата данни дали оръжието от този тип е отключено за потребителя с това userId
			 *  ако е отключено го записваме за потребителя като избрано оръжие
			 * 
			 * */
			
			message = "success";
			result.put("message", message);
			
			
		} catch (ParseException e) {
			
			result.put("error", "Invalid JSON");
			response.setStatus(400);
			
		}
		
		System.out.println(result.toJSONString());
		response.setStatus(200);
		response.getWriter().write(result.toJSONString());
	}

}
