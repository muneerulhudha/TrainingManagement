package com.tms.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.tms.beans.Trainee;
import com.tms.main.Enroll;
import com.tms.main.Training;

public class TMSController {

	public static String id;
	public static String type;
	
	public static void loginControl(HttpServletRequest request, HttpServletResponse response) {
		
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		JSONObject obj = null;
		
		statement = DBManager.connect(conn);
		
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
				setLoginData(id, type);
				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("Type_of_User", type);
//				PrintWriter out = response.getWriter();
//				out.write(obj.toString());
				
				response.setContentType("text/html");
				request.setAttribute("LoginData", obj);
				if(type.equals("Emp")){
					Trainee t = new Trainee(id);
					List<Training> trainings = t.getEnrolledTrainings();
					request.setAttribute("enrolledTrainingList", trainings);
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
		
		DBManager.close(statement, conn);
	}

	private static void setLoginData(String id2, String type2) {
		// TODO Auto-generated method stub
		id = id2;
		type = type2;
	}

	public static void getEnrolledTrainings(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		
		Trainee t = new Trainee(empID);
		List<Training> trainingList = t.getEnrolledTrainings();
		
		response.setContentType("text/html");
		request.setAttribute("enrolledTrainingList", trainingList);
		
	}

	public static void getProfileInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String empID = request.getParameter("empID");
		JSONObject obj = null;
		
		Trainee t = new Trainee(empID);
		List<Trainee> tList = new ArrayList<Trainee>();
		try{
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);
		}catch(Exception e){
			e.printStackTrace();
		}
		tList.add(t);
		response.setContentType("text/html");
		
		request.setAttribute("traineeDetails", tList);
		request.setAttribute("LoginData", obj);
		RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
		rd.forward(request, response);
	}

	public static void withdrawTraining(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		String trainingID = request.getParameter("trainingID");
		JSONObject obj = null;
		
		try{
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);
		}catch(Exception e){
			e.printStackTrace();
		}
		Trainee t = new Trainee(empID);
		Training train = new Training(trainingID);
		
		System.out.println("In control " + train.getTrainingID());
		
		Enroll enr = new Enroll();
		enr.withdrawTraining(t, train);
		try {
			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("message", "You have been withdrawn from the Training.");
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
		
			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
