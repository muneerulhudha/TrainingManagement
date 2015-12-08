package com.tms.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tms.control.DBManager;
import com.tms.main.Training;

public class Administrator extends Person{
	
	private String adminID;

	public Administrator(String id){
		this.adminID = id;
		this.loadPersonDetails(this.adminID);
	}
	
	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	
	public void updateTrainingInfo(){
		
	}
	
	public void approveTrainingSession(){
		
	}

	public List<Training> getAllTrainings() {
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		
		try {

			List<Training> trainingList = new ArrayList<Training>();

			rs = statement.executeQuery("select TrainingID, Title, Category from TRAINING");
			
			while(rs.next()){
				Training t = new Training();
				t.setTrainingID(rs.getString(1));
				t.setTrainingName(rs.getString(2));
				t.setTrainingType(rs.getString(3));
				trainingList.add(t);
			}

			return trainingList;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.close(statement, conn);
		}
		return null;
	}
	
}
