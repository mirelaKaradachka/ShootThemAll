package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Weapon;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<User> cacheUsers;
	private ServletConfig config;
	private  ServletContext sc;
       

    public Login() {
               
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	if(cacheUsers == null){
     	   cacheUsers = new ArrayList();
        }
        /*
         * вземане на потребителите от базата данни и пълнене на cacheUsers
         * 
         */
        
        //тест
        User u1 = new User(1, "Ivan", "Ivan", "Ivan", 20, 0, new Weapon(1, 1, 0), false);
        User u2 = new User(2, "Petko", "Petko", "Petko", 10, 0, new Weapon(1, 1, 0), false);
        User u3 = new User(3, "Tanq", "Tanq", "Tanq", 14, 0, new Weapon(1, 1, 0), false);
        
        //cacheUsers.sort(new CompareUsers());
        config = getServletConfig();
        
        sc = config.getServletContext();
        
        if(sc.getAttribute("cacheUsers") == null){
     	   sc.setAttribute("cacheUsers", cacheUsers);
        }
        
        cacheUsers = (ArrayList<User>) sc.getAttribute("cacheUsers");
        cacheUsers.add(u1);
        cacheUsers.add(u2);
        cacheUsers.add(u3);
        cacheUsers.sort(new CompareUsers());
        //sc.setAttribute("cacheUsers", cacheUsers);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
