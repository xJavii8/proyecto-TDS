package umu.tds.model;

import java.util.Date;

public class Photo extends Publication {
	private String path;

	// Método constructor
	public Photo(String title, Date datePublication, String description, int likes, String path) {
		super(title, datePublication, description, likes);
		this.path = path;
	}
	
	public Photo(String title, Date datePublication, String description, String path) {
		super(title, datePublication, description);
		this.path = path;
	}

	//GETTERS AND SETTERS
	public String getPath() {
		return path;
	}

}
