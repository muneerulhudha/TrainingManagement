package com.tms.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tms.beans.Trainee;
import com.tms.control.DBManager;

public class Enroll {

	Trainee trainee;
	Training training;
	
	public void withdrawTraining(Trainee trainee, Training training){
		Statement statement = null;
		Connection conn = null;
		System.out.println("Inside enroll " + trainee.getTraineeID() + " "+ training.getTrainingID());
		statement = DBManager.connect(conn);
		try {
			statement.executeUpdate("DELETE FROM ENROLLMENT	"
					+ "WHERE `TraineeID` ='"+ trainee.getTraineeID() +"' AND `TrainingID` = '"+ training.getTrainingID() +"'");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager.close(statement, conn);
	}
}
