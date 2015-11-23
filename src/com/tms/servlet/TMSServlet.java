package com.tms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.tms.beans.Trainee;
import com.tms.main.Training;

/**
 * Servlet implementation class TMSServlet
 */
@WebServlet("/TMSServlet")
public class TMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TMSServlet() {
        // TODO Auto-generated constructor stub
    }

    protected Statement connect(Connection conn) {

		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/test",
							"muneer", "1234567");
			stmt = conn.createStatement();
			//System.out.println(stmt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stmt;
	}

    private void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
    
    public static String getRequestContent(HttpServletRequest request)
			throws IOException {

		String body = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;

		//String hell = request.getParameter("hello");
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					sb.append(charBuffer, 0, bytesRead);
				}
			} else {
				sb.append("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		body = sb.toString();
		//System.out.println("Hello : " + hell);
		System.out.println("body: " + body);
		return body;
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//String st = getRequestContent(request);
	
		JSONObject json = null;
		int fromRequest = -1;
		
		
		try {
			//json = new JSONObject(st);
			System.out.println("Initalised");
			System.out.println(request.getParameter("hiddenValue"));
			fromRequest = Integer.parseInt(request.getParameter("hiddenValue"));
			System.out.println(fromRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		JSONObject obj = null;
		
		switch (fromRequest) {
		case 1: //sign-in
			statement = connect(conn);
		
			try {
				rs = statement
						.executeQuery("select ID, Type_of_User from Login where Username='"
								+ request.getParameter("userName")
								+ "' and Password='"
								+ request.getParameter("passWord") + "'");
				if (rs.next()) {
					String id = rs.getString(1);
					String type = rs.getString(2);
					System.out.println("ID: " + id);
					System.out.println("Type: "+ type);
					obj = new JSONObject();
					obj.put("ID", id);
					obj.put("Type_of_User", type);
//					PrintWriter out = response.getWriter();
//					out.write(obj.toString());
					
					response.setContentType("text/html");
					request.setAttribute("LoginData", obj);
					if(type.equals("Emp")){
						Trainee t = new Trainee(id);
						List<Training> trainings = t.getEnrolledTrainings();
						RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
						rd.forward(request, response);
					}
					

				} else {
					obj = new JSONObject();
					obj.put("result", "No user found");
					
					response.setContentType("text/html");
					request.setAttribute("errorMsg", obj);
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(statement, conn);
			break;
		
		//case 2:
		
		}
	}

}
