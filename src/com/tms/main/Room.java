package com.tms.main;

public class Room {
	private String roomID;
	private String roomType;
	private Boolean roomAvailability;
	private int roomCapacity;
	private int roomLevel;
	
	public Room(String rID) {
		this.roomID = rID;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Boolean getRoomAvailability() {
		return roomAvailability;
	}
	public void setRoomAvailability(Boolean roomAvailability) {
		this.roomAvailability = roomAvailability;
	}
	public int getRoomCapacity() {
		return roomCapacity;
	}
	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
	public int getRoomLevel() {
		return roomLevel;
	}
	public void setRoomLevel(int roomLevel) {
		this.roomLevel = roomLevel;
	}
	
	public void getRoomMatch(){
		
	}
	
	public void cancelRoom(){
		
	}
}
