package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutil.Dbutil;

@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname=request.getParameter("fname");
		String username=request.getParameter("uname");
		String email=request.getParameter("mail");
		String phone_number=request.getParameter("phno");
		String password=request.getParameter("passcode");
		String confirmpassword=request.getParameter("cpasscode");
		String gender=request.getParameter("gender");
		
		try {
			Connection con=Dbutil.getConnection();
	        PreparedStatement pstmt = null;
	            // Prepare SQL INSERT query with placeholders for values
	            String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)";

	            // Create PreparedStatement object
	            pstmt = con.prepareStatement(sql);

	            
	            
	            // Set the values for the placeholders
	            pstmt.setString(1, fullname);
	            pstmt.setString(2, username);
	            pstmt.setString(3, email);
	            pstmt.setString(4, phone_number);
	            pstmt.setString(5, password);
	            pstmt.setString(6, gender);

	            int affected=pstmt.executeUpdate();
	            System.out.println(affected);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

