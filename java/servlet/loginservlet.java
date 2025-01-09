package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutil.Dbutil;

/**
 * Servlet implementation class loginservlet
 */
@WebServlet("/loginservlet")
public class loginservlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("mail");
		String pass=request.getParameter("passcode");
		String conPass="";
		
		try {
			Connection con=Dbutil.getConnection();
	        PreparedStatement pst =con.prepareStatement( "select password from user where email=?");
	        pst.setString(1,email);
	        
	        ResultSet rs=pst.executeQuery();
	        
	        if(rs.next()) {
	        	conPass=rs.getString(1);
	        }
	        
	        if(pass.equals(conPass)) {
	        	response.sendRedirect("qwe.html");
	        }
	        else {
	        	response.sendRedirect("login.jsp");
	        }
	       
	        
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
