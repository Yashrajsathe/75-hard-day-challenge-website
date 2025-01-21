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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginPage
 */
@WebServlet("/login")
public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginPage() {
       
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // JDBC connection parameters
            String jdbcUrl = "jdbc:mysql://localhost:3306/gym";
            String dbUser = "root";
            String dbPassword = "root";

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // SQL query to check if the user exists
            String selectQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // User exists, login successful
                    RequestDispatcher dispatcher = request.getRequestDispatcher("useryashraj.html");
                    dispatcher.forward(request, response);
                } else {
                    // User does not exist, login failed
                    out.println("<h2>Login failed. Please check your username and password.</h2>");
                }
            }

            // Close the connection
            connection.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }

        out.close();
    }

}
