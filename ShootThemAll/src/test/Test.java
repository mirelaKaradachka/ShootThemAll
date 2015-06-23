package test;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.DBWeaponDao;
import model.dao.UserDao;
import model.dao.WeaponDao;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().write("<html><head></head>");
		response.getWriter().write("<h1>Hello World</h1>");
		
		WeaponDao wd = new DBWeaponDao();
		
		ArrayList<Weapon> list = (ArrayList<Weapon>) wd.getWeapons();
		StringBuffer sb = new StringBuffer();
		for(int i =  0 ; i < list.size(); i++){
			sb.append(list.get(i).getType() + " ");
		}
		System.out.println(sb.toString());
		
		Weapon w = wd.getWeapon(1);
		System.out.println("price : " + w.getPrice());
//		
//		User a = new User("Petkan", "121", "aa@aaa.aa");
		UserDao ud = new DBUserDao();
//		ud.addUser(a);
		
		System.out.println();
		User u1 = ud.getUser("Petq");
		System.out.println(u1.getUsername());
		
		response.getWriter().write("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
