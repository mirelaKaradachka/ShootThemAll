package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Weapon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class WeaponsStore
 */
@WebServlet("/WeaponsStore")
public class WeaponsStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WeaponsStore() {

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		line = test.toJSONString();
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(userObj.get("userId").toString());
			
			/*
			 *  select в базата данни по userId за точките които потребителя има и за активните за него оръжия
			 * 
			 */
			
			int score = 400;
			Weapon weapons[] = new Weapon[3];
			weapons[0] = new Weapon(1, 1, 0, true);
			weapons[1] = new Weapon(2, 2, 200, false);
			weapons[2] = new Weapon(2, 2, 500, false);
			
			JSONObject result = new JSONObject();
			result.put("score", score);
			
			JSONObject weaponsObj[] = new JSONObject[weapons.length];
			JSONArray weaponsArr = new JSONArray();
			
			for(int i = 0 ; i <weapons.length; i++){
				weaponsObj[i] = new JSONObject();
				weaponsObj[i].put("type", weapons[i].getType());
				weaponsObj[i].put("damage", weapons[i].getDamage());
				weaponsObj[i].put("price", weapons[i].getPrice());
				weaponsObj[i].put("isActive", weapons[i].isActive());
				weaponsArr.add(weaponsObj[i]);
			}
			
			result.put("weapons", weaponsArr);
			
			System.out.println(result.toJSONString());
			response.getWriter().write(result.toJSONString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
