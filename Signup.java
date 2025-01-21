package com.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class Signup
 */

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "root");
			if(conn != null) {

				 String username = request.getParameter("username");
			        String email = request.getParameter("email");
			        String password = request.getParameter("password");
			        
			        
			        // SQL query to insert user data into the users table
		            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

		    		PrintWriter out = response.getWriter();
		            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, email);
		                preparedStatement.setString(3, password);

		                // Execute the update
		                int rowsAffected = preparedStatement.executeUpdate();

		                if (rowsAffected > 0) {
		                    // Registration successful, forward to index.html
		                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
		                    dispatcher.forward(request, response);
		                } else {
		                    out.println("<h2>Registration failed</h2>");
		                }
		            }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
