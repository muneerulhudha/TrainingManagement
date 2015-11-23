package com.tms.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tms.main.Training;

public class Trainee extends Person{

	private String traineeID;

	public Trainee(String id) {
		// TODO Auto-generated constructor stub
		this.traineeID = id;
	}

	public String getTraineeID() {
		return traineeID;
	}

	public void setTraineeID(String traineeID) {
		this.traineeID = traineeID;
	}
	
	public List<Training> getEnrolledTrainings(){
		//write logic to fetch trainings enrolled for the trainee.
		List<Training> trainings = new ArrayList<Training>();
		
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/test",
							"muneer", "1234567");
			stmt = conn.createStatement();
			//System.out.println(stmt);
			
			ResultSet rs = stmt.executeQuery("select TrainingID, EnrollStatus from Enrollment where TraineeID ='"+ this.traineeID +"'");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return trainings;
		
	}
}
