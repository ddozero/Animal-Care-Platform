package com.animal.api.admin.shelter.service;

public interface AdminShelterService {
	public static int POST_SUCCESS = 1;
	public static int UPDATE_SUCCESS = 2;
	public static int DELETE_SUCCESS = 3;
	public static int UPLOAD_SUCCESS = 4;
	public static int NOT_ANIMAL = 5;
	public static int ERROR = -1;
	
	public int deleteAnimal(int idx);
}
